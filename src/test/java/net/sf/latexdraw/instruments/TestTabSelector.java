package net.sf.latexdraw.instruments;

import java.lang.reflect.InvocationTargetException;
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.scene.control.TabPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import net.sf.latexdraw.CollectionMatcher;
import net.sf.latexdraw.badaboom.BadaboomCollector;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.IDrawing;
import net.sf.latexdraw.models.interfaces.shape.IRectangle;
import net.sf.latexdraw.util.Injector;
import net.sf.latexdraw.view.MagneticGrid;
import net.sf.latexdraw.view.ViewsSynchroniserHandler;
import net.sf.latexdraw.view.jfx.Canvas;
import net.sf.latexdraw.view.latex.LaTeXGenerator;
import net.sf.latexdraw.view.pst.PSTCodeGenerator;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.testfx.util.WaitForAsyncUtils;

import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class TestTabSelector extends TestLatexdrawGUI implements CollectionMatcher {
	TabPane tabPane;
	@Mock EditingSelector selector;
	@Mock CopierCutterPaster paster;
	@Mock UndoRedoManager undo;
	@Mock Zoomer zoomer;
	@Mock ShapeDeleter deleter;
	@Mock TextSetter textSetter;
	@Mock MetaShapeCustomiser meta;
	@Mock PreferencesSetter prefSetter;

	@Override
	public void start(final Stage aStage) {
		super.start(aStage);

		tabPane = find("#tabPane");
		final int width = 800;
		final int height = 600;
		stage.minHeightProperty().unbind();
		stage.minWidthProperty().unbind();
		final Canvas canvas = injector.getInstance(Canvas.class);
		canvas.setMaxWidth(width);
		canvas.setMaxHeight(height);
		canvas.getScene().getWindow().setWidth(width);
		canvas.getScene().getWindow().setHeight(height);
		stage.setMaxWidth(width);
		stage.setMaxHeight(height);
		stage.setMinWidth(width);
		stage.setMinHeight(height);
		stage.centerOnScreen();
		stage.toFront();
	}

	@Override
	protected String getFXMLPathFromLatexdraw() {
		return "/fxml/Tabs.fxml";
	}

	@Override
	@After
	public void tearDown() {
		release(new KeyCode[] {});
		release(new MouseButton[] {});
	}

	@Override
	protected Injector createInjector() {
		return new Injector() {
			@Override
			protected void configure() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
				bindAsEagerSingleton(ExceptionsManager.class);
				bindAsEagerSingleton(ShortcutsController.class);
				bindAsEagerSingleton(StatusBarController.class);
				bindAsEagerSingleton(AboutController.class);
				bindAsEagerSingleton(Canvas.class);
				bindAsEagerSingleton(FacadeCanvasController.class);
				bindAsEagerSingleton(CanvasController.class);
				bindToInstance(HostServices.class, Mockito.mock(HostServices.class));
				bindWithCommand(IDrawing.class, Canvas.class, canvas -> canvas.getDrawing());
				bindWithCommand(MagneticGrid.class, Canvas.class, canvas -> canvas.getMagneticGrid());
				bindWithCommand(ViewsSynchroniserHandler.class, Canvas.class, canvas -> canvas);
				bindToInstance(Zoomer.class, zoomer);
				bindToInstance(UndoRedoManager.class, undo);
				bindAsEagerSingleton(PSTCodeGenerator.class);
				bindWithCommand(LaTeXGenerator.class, PSTCodeGenerator.class, gen -> gen);
				bindAsEagerSingleton(CodePanelController.class);
				bindToInstance(DrawingPropertiesCustomiser.class, Mockito.mock(DrawingPropertiesCustomiser.class));
				bindToInstance(TemplateManager.class, Mockito.mock(TemplateManager.class));
				bindToInstance(CodeInserter.class, Mockito.mock(CodeInserter.class));
				bindToInstance(CopierCutterPaster.class, paster);
				bindToInstance(MetaShapeCustomiser.class, meta);
				bindToInstance(Border.class, Mockito.mock(Border.class));
				bindToInstance(PreferencesSetter.class, prefSetter);
				bindToInstance(FileLoaderSaver.class, Mockito.mock(FileLoaderSaver.class));
				bindToInstance(Exporter.class, Mockito.mock(Exporter.class));
				bindToInstance(EditingSelector.class, selector);
				bindToInstance(TextSetter.class, textSetter);
				bindToInstance(Hand.class, Mockito.mock(Hand.class));
				bindToInstance(Helper.class, Mockito.mock(Helper.class));
				bindToInstance(Pencil.class, Mockito.mock(Pencil.class));
				bindToInstance(ShapeArcCustomiser.class, Mockito.mock(ShapeArcCustomiser.class));
				bindToInstance(ShapeArrowCustomiser.class, Mockito.mock(ShapeArrowCustomiser.class));
				bindToInstance(ShapeAxesCustomiser.class, Mockito.mock(ShapeAxesCustomiser.class));
				bindToInstance(ShapeBorderCustomiser.class, Mockito.mock(ShapeBorderCustomiser.class));
				bindToInstance(ShapeCoordDimCustomiser.class, Mockito.mock(ShapeCoordDimCustomiser.class));
				bindToInstance(ShapeDeleter.class, deleter);
				bindToInstance(ShapeDotCustomiser.class, Mockito.mock(ShapeDotCustomiser.class));
				bindToInstance(ShapeDoubleBorderCustomiser.class, Mockito.mock(ShapeDoubleBorderCustomiser.class));
				bindToInstance(ShapeFillingCustomiser.class, Mockito.mock(ShapeFillingCustomiser.class));
				bindToInstance(ShapeFreeHandCustomiser.class, Mockito.mock(ShapeFreeHandCustomiser.class));
				bindToInstance(ShapeGridCustomiser.class, Mockito.mock(ShapeGridCustomiser.class));
				bindToInstance(ShapeGrouper.class, Mockito.mock(ShapeGrouper.class));
				bindToInstance(ShapePlotCustomiser.class, Mockito.mock(ShapePlotCustomiser.class));
				bindToInstance(ShapePositioner.class, Mockito.mock(ShapePositioner.class));
				bindToInstance(ShapeRotationCustomiser.class, Mockito.mock(ShapeRotationCustomiser.class));
				bindToInstance(ShapeShadowCustomiser.class, Mockito.mock(ShapeShadowCustomiser.class));
				bindToInstance(ShapeStdGridCustomiser.class, Mockito.mock(ShapeStdGridCustomiser.class));
				bindToInstance(ShapeTextCustomiser.class, Mockito.mock(ShapeTextCustomiser.class));
				bindToInstance(ShapeTransformer.class, Mockito.mock(ShapeTransformer.class));
				bindAsEagerSingleton(TabSelector.class);
			}
		};
	}

	@Test
	public void testNotTabsClosable() {
		assertAllFalse(tabPane.getTabs().stream(), tab -> tab.isClosable());
	}

	@Test
	public void testClickAnotherTabNoCrash() {
		tabPane.lookupAll(".tab").forEach(n -> {
			clickOn(n);
			WaitForAsyncUtils.waitForFxEvents();
			sleep(200L);
		});
		assertTrue(BadaboomCollector.INSTANCE.isEmpty());
	}

	@Test
	public void testClickPrefActivations() {
		clickOn(tabPane.lookup("#prefTab"));
		WaitForAsyncUtils.waitForFxEvents();
		sleep(100L);

		Mockito.verify(selector, Mockito.times(1)).setActivated(false, false);
		Mockito.verify(paster, Mockito.times(1)).setActivated(false, false);
		Mockito.verify(undo, Mockito.times(1)).setActivated(false, false);
		Mockito.verify(zoomer, Mockito.times(1)).setActivated(false, false);
		Mockito.verify(deleter, Mockito.times(1)).setActivated(false, false);
		Mockito.verify(zoomer, Mockito.times(1)).setActivated(false, false);
		Mockito.verify(prefSetter, Mockito.times(1)).setActivated(true);
	}

	@Test
	public void testClickPSTActivations() {
		clickOn(tabPane.lookup("#tabPST"));
		WaitForAsyncUtils.waitForFxEvents();
		sleep(100L);

		Mockito.verify(selector, Mockito.times(1)).setActivated(false, false);
		Mockito.verify(paster, Mockito.times(1)).setActivated(false, false);
		Mockito.verify(undo, Mockito.times(1)).setActivated(false, false);
		Mockito.verify(zoomer, Mockito.times(1)).setActivated(false, false);
		Mockito.verify(deleter, Mockito.times(1)).setActivated(false, false);
		Mockito.verify(zoomer, Mockito.times(1)).setActivated(false, false);
	}

	@Test
	public void testClickCanvasActivations() {
		clickOn(tabPane.lookup("#tabPST"));
		clickOn(tabPane.lookup("#canvasTab"));
		WaitForAsyncUtils.waitForFxEvents();
		sleep(100L);

		Mockito.verify(selector, Mockito.times(1)).setActivated(true);
		Mockito.verify(paster, Mockito.times(1)).setActivated(true);
		Mockito.verify(undo, Mockito.times(1)).setActivated(true);
		Mockito.verify(zoomer, Mockito.times(1)).setActivated(true);
		Mockito.verify(deleter, Mockito.times(2)).setActivated(false, false);
		Mockito.verify(zoomer, Mockito.times(1)).setActivated(true);
	}

	@Test
	public void testClickCanvasActivationsOnSelectedShape() {
		final IRectangle rectangle = ShapeFactory.INST.createRectangle(ShapeFactory.INST.createPoint(), 100, 50);
		final Canvas canvas = injector.getInstance(Canvas.class);
		Platform.runLater(() -> {
			canvas.getDrawing().addShape(rectangle);
			canvas.getDrawing().getSelection().addShape(rectangle);
		});
		WaitForAsyncUtils.waitForFxEvents();
		clickOn(tabPane.lookup("#tabPST"));
		clickOn(tabPane.lookup("#canvasTab"));
		WaitForAsyncUtils.waitForFxEvents();
		sleep(100L);

		Mockito.verify(deleter, Mockito.times(1)).setActivated(true);
	}
}
