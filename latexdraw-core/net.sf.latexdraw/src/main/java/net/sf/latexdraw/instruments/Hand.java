/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2017 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package net.sf.latexdraw.instruments;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.transform.NonInvertibleTransformException;
import javafx.scene.transform.Transform;
import net.sf.latexdraw.actions.shape.InitTextSetter;
import net.sf.latexdraw.actions.shape.SelectShapes;
import net.sf.latexdraw.actions.shape.TranslateShapes;
import net.sf.latexdraw.actions.shape.UpdateToGrid;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.IPlot;
import net.sf.latexdraw.models.interfaces.shape.IPoint;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import net.sf.latexdraw.models.interfaces.shape.IText;
import net.sf.latexdraw.util.Inject;
import net.sf.latexdraw.util.LSystem;
import net.sf.latexdraw.view.jfx.Canvas;
import net.sf.latexdraw.view.jfx.ViewPlot;
import net.sf.latexdraw.view.jfx.ViewShape;
import net.sf.latexdraw.view.jfx.ViewText;
import org.malai.action.Action;
import org.malai.javafx.action.MoveCamera;
import org.malai.javafx.binding.JfXWidgetBinding;
import org.malai.javafx.interaction.library.DnD;
import org.malai.javafx.interaction.library.DoubleClick;
import org.malai.javafx.interaction.library.Press;

/**
 * This instrument allows to manipulate (e.g. move or select) shapes.
 * @author Arnaud BLOUIN
 */
public class Hand extends CanvasInstrument {
	@Inject private MetaShapeCustomiser metaCustomiser;
	@Inject private TextSetter textSetter;

	public Hand() {
		super();
	}

	@Override
	protected void configureBindings() throws InstantiationException, IllegalAccessException {
		canvas.getViews().getChildren().addListener((ListChangeListener<Node>) evt -> {
			while(evt.next()) {
				if(evt.wasAdded()) {
					evt.getAddedSubList().forEach(v -> {
						v.setOnMouseEntered(mouseEvt -> {
							if(isActivated()) {
								canvas.setCursor(Cursor.HAND);
							}
						});
						v.setOnMouseExited(mouseEvt -> {
							if(isActivated()) {
								canvas.setCursor(Cursor.DEFAULT);
							}
						});
					});
				}
			}
		});

		// Pressure to select shapes
		nodeBinder(SelectShapes.class, new Press()).on(canvas).first((a, i) -> {
			a.setDrawing(canvas.getDrawing());
			getViewShape(i.getSrcObject()).map(src -> src.getModel()).ifPresent(targetSh -> {
				if(i.isShiftPressed()) {
					canvas.getDrawing().getSelection().getShapes().stream().filter(sh -> sh != targetSh).forEach(sh -> a.addShape(sh));
					return;
				}
				if(i.isCtrlPressed()) {
					canvas.getDrawing().getSelection().getShapes().forEach(sh -> a.addShape(sh));
					a.addShape(targetSh);
					return;
				}
				a.setShape(targetSh);
			});
		}).when(i -> getViewShape(i.getSrcObject()).isPresent()).bind();

		addBinding(new DnD2Select(this));
		addBinding(new DnD2Translate(this));
		addBinding(new DnD2MoveViewport(this));
		addBinding(new DoubleClick2InitTextSetter(this));

		keyNodeBinder(SelectShapes.class).on(canvas).with(KeyCode.A, LSystem.INSTANCE.getControlKey()).first(action -> {
			canvas.getDrawing().getShapes().forEach(sh -> action.addShape(sh));
			action.setDrawing(canvas.getDrawing());
		}).bind();

		keyNodeBinder(UpdateToGrid.class).on(canvas).with(KeyCode.U, LSystem.INSTANCE.getControlKey()).first(action -> {
			action.setShape(canvas.getDrawing().getSelection().duplicateDeep(false));
			action.setGrid(canvas.getMagneticGrid());
		}).when(i -> canvas.getMagneticGrid().isMagnetic()).bind();
	}

	@Override
	public void setActivated(final boolean activ) {
		if(activated != activ) {
			super.setActivated(activ);
			canvas.getSelectionBorder().setVisible(activated);
			canvas.getSelectionBorder().setDisable(!activated);
			if(activated) {
				canvas.update();
			}
		}
	}

	@Override
	public void interimFeedback() {
		// The rectangle used for the interim feedback of the selection is removed.
		canvas.setOngoingSelectionBorder(null);
		canvas.setCursor(Cursor.DEFAULT);
	}

	@Override
	public void onActionDone(final Action action) {
		if(action instanceof TranslateShapes) {
			metaCustomiser.dimPosCustomiser.update();
		}
	}

	/**
	 * A tricky workaround to get the real plot view hidden behind its content views (Bezier curve, dots, etc.).
	 * If the view has a ViewPlot as its user data, this view plot is returned. The source view is returned otherwise.
	 * setMouseTransparency cannot be used since the mouse over would not work anymore.
	 * @param view The view to check. Cannot be null.
	 * @return The given view or the plot view.
	 */
	private static ViewShape<?> getRealViewShape(final ViewShape<?> view) {
		if(view != null && view.getUserData() instanceof ViewPlot) {
			return (ViewShape<?>) view.getUserData();
		}
		return view;
	}

	private static Optional<ViewShape<?>> getViewShape(final Optional<Node> node) {
		if(node.isPresent()) {
			final Node value = node.get();
			Node parent = value.getParent();

			while(parent != null && !(parent instanceof ViewShape<?>)) {
				parent = parent.getParent();
			}

			return Optional.ofNullable(getRealViewShape((ViewShape<?>) parent));
		}
		return Optional.empty();
	}


	private static class DoubleClick2InitTextSetter extends JfXWidgetBinding<InitTextSetter, DoubleClick, Hand> {
		DoubleClick2InitTextSetter(final Hand ins) throws IllegalAccessException, InstantiationException {
			super(ins, false, InitTextSetter.class, new DoubleClick(), ins.canvas);
		}

		@Override
		public void initAction() {
			interaction.getSrcObject().ifPresent(srcObj -> {
				Optional<IPoint> pos = Optional.empty();
				final IShape sh = getRealViewShape((ViewShape<?>) srcObj.getParent()).getModel();

				if(sh instanceof IText) {
					final IText text = (IText) sh;
					action.setTextShape(text);
					pos = Optional.of(text.getPosition());
				}else {
					if(sh instanceof IPlot) {
						final IPlot plot = (IPlot) sh;
						action.setPlotShape(plot);
						pos = Optional.of(plot.getPosition());
					}
				}

				pos.ifPresent(position -> {
					final double zoom = instrument.canvas.getZoom();
					action.setInstrument(instrument.textSetter);
					action.setTextSetter(instrument.textSetter);
					action.setPosition(ShapeFactory.INST.createPoint(position.getX() * zoom, position.getY() * zoom));
				});
			});
		}

		@Override
		public boolean isConditionRespected() {
			final Optional<Node> src = interaction.getSrcObject();
			return src.isPresent() && src.get().getParent() != null &&
				(src.get().getParent() instanceof ViewText || src.get().getParent().getUserData() instanceof ViewPlot);
		}
	}


	private static class DnD2Translate extends JfXWidgetBinding<TranslateShapes, DnD, Hand> {
		DnD2Translate(final Hand hand) throws IllegalAccessException, InstantiationException {
			super(hand, true, TranslateShapes.class, new DnD(), hand.canvas);
		}

		@Override
		public void initAction() {
			action.setDrawing(instrument.canvas.getDrawing());
			action.setShape(instrument.canvas.getDrawing().getSelection().duplicateDeep(false));
		}


		@Override
		public void updateAction() {
			final IPoint startPt = instrument.grid.getTransformedPointToGrid(interaction.getSrcPoint());
			final IPoint endPt = instrument.grid.getTransformedPointToGrid(interaction.getEndPt());
			action.setTx(endPt.getX() - startPt.getX());
			action.setTy(endPt.getY() - startPt.getY());
		}

		@Override
		public boolean isConditionRespected() {
			final Optional<Node> startObject = interaction.getSrcObject();
			final MouseButton button = interaction.getButton();

			return startObject.isPresent() && !instrument.canvas.getDrawing().getSelection().isEmpty() &&
				(startObject.get() == instrument.canvas && button == MouseButton.SECONDARY ||
				getViewShape(startObject).isPresent() && (button == MouseButton.PRIMARY || button == MouseButton.SECONDARY));
		}


		@Override
		public void interimFeedback() {
			super.interimFeedback();
			instrument.canvas.setCursor(Cursor.MOVE);
		}
	}


	private static class DnD2Select extends JfXWidgetBinding<SelectShapes, DnD, Hand> {
		/** The is rectangle is used as interim feedback to show the rectangle made by the user to select some shapes. */
		private Bounds selectionBorder;
		private List<IShape> selectedShapes;
		private List<ViewShape<?>> selectedViews;

		DnD2Select(final Hand hand) throws IllegalAccessException, InstantiationException {
			super(hand, true, SelectShapes.class, new DnD(), hand.canvas);
		}

		@Override
		public void initAction() {
			action.setDrawing(instrument.canvas.getDrawing());
			selectedShapes = new ArrayList<>(instrument.canvas.getDrawing().getSelection().getShapes());
			selectedViews = instrument.canvas.getSelectedViews();
			Platform.runLater(() -> instrument.canvas.requestFocus());
		}

		@Override
		public void updateAction() {
			final IPoint start = instrument.getAdaptedOriginPoint(interaction.getSrcPoint());
			final IPoint end = instrument.getAdaptedOriginPoint(interaction.getEndPt());
			final double minX = Math.min(start.getX(), end.getX());
			final double maxX = Math.max(start.getX(), end.getX());
			final double minY = Math.min(start.getY(), end.getY());
			final double maxY = Math.max(start.getY(), end.getY());

			// Updating the rectangle used for the interim feedback and for the selection of shapes.
			selectionBorder = new BoundingBox(minX, minY, Math.max(maxX - minX, 1d), Math.max(maxY - minY, 1d));
			final Rectangle selectionRec = new Rectangle(selectionBorder.getMinX() + Canvas.ORIGIN.getX(),
				selectionBorder.getMinY() + Canvas.ORIGIN.getY(), selectionBorder.getWidth(), selectionBorder.getHeight());
			// Transforming the selection rectangle to match the transformation of the canvas.
			selectionRec.getTransforms().setAll(getInstrument().canvas.getLocalToSceneTransform());
			// Cleaning the selected shapes in the action.
			action.setShape(null);

			if(interaction.isShiftPressed()) {
				selectedViews.stream().filter(view -> !view.intersects(selectionBorder)).forEach(view -> action.addShape(view.getModel()));
			}else {
				if(interaction.isCtrlPressed()) {
					selectedShapes.forEach(sh -> action.addShape(sh));
				}
				if(!selectionBorder.isEmpty()) {
					instrument.canvas.getViews().getChildren().stream().filter(view -> {
						Bounds bounds;
						final Transform transform = view.getLocalToParentTransform();
						if(transform.isIdentity()) {
							bounds = selectionBorder;
						}else {
							try {
								bounds = transform.createInverse().transform(selectionBorder);
							}catch(final NonInvertibleTransformException ex) {
								bounds = selectionBorder;
								//TODO log
							}
						}
						return view.intersects(bounds) &&
							((ViewShape<?>) view).getActivatedShapes().stream().anyMatch(sh -> !Shape.intersect(sh, selectionRec).getLayoutBounds().isEmpty());
					}).forEach(view -> action.addShape((IShape) view.getUserData()));
				}
			}
		}

		@Override
		public boolean isConditionRespected() {
			return interaction.getButton() == MouseButton.PRIMARY && interaction.getSrcObject().orElse(null) == instrument.canvas;
		}

		@Override
		public void interimFeedback() {
			instrument.canvas.setOngoingSelectionBorder(selectionBorder);
			selectionBorder = null;
		}
	}

	static class DnD2MoveViewport extends JfXWidgetBinding<MoveCamera, DnD, CanvasInstrument> {
		private final IPoint pt;

		DnD2MoveViewport(final CanvasInstrument ins) throws IllegalAccessException, InstantiationException {
			super(ins, true, MoveCamera.class, new DnD(), ins.canvas);
			pt = ShapeFactory.INST.createPoint();
		}

		@Override
		public void initAction() {
			action.setScrollPane(instrument.canvas.getScrollPane());
			pt.setPoint(interaction.getSrcPoint().getX(), interaction.getSrcPoint().getY());
		}

		@Override
		public void updateAction() {
			final ScrollPane pane = instrument.canvas.getScrollPane();
			action.setPx(pane.getHvalue() - (interaction.getEndPt().getX() - pt.getX()) / instrument.canvas.getWidth());
			action.setPy(pane.getVvalue() - (interaction.getEndPt().getY() - pt.getY()) / instrument.canvas.getHeight());
			pt.setPoint(pt.centralSymmetry(ShapeFactory.INST.createPoint(interaction.getSrcPoint())));
		}

		@Override
		public boolean isConditionRespected() {
			return interaction.getButton() == MouseButton.MIDDLE;
		}

		@Override
		public void interimFeedback() {
			instrument.canvas.setCursor(Cursor.MOVE);
		}
	}
}
