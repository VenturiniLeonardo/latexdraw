package net.sf.latexdraw.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import net.sf.latexdraw.glib.ui.LCanvas;
import net.sf.latexdraw.lang.LangTool;
import org.malai.instrument.Instrument;
import org.malai.swing.ui.SwingUIComposer;
import org.malai.swing.widget.MComboBox;
import org.malai.swing.widget.MPanel;
import org.malai.swing.widget.MProgressBar;
import org.malai.swing.widget.MSpinner;
import org.malai.swing.widget.MToolBar;
import org.malai.swing.widget.ScrollableWidget;

/**
 * This composer composes the latexdraw user interface.<br>
 * <br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2014 Arnaud BLOUIN<br>
 * <br>
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * <br>
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 * 20/31/2010<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
public class UIBuilder extends SwingUIComposer<LFrame> {
	/** The max height of the textfield widget. */
	protected static final int HEIGHT_TEXTFIELD = 30;

	/** The space added between widgets. */
	protected static final int SEPARATION_WIDTH = 5;

	/** The menu bar composer. */
	protected MenubarBuilder menubarBuilder;

	/** The tool bar composer. */
	protected ToolbarBuilder toolbarBuilder;

	/** The properties tool bar composer. */
	protected PropertiesToolbarBuilder propToolbarBuilder;


	/**
	 * Creates the composer of the latexdraw user interface.
	 * @param frame The frame of the interactive system that contains the instruments and presentations to compose.
	 * @since 3.0
	 */
	public UIBuilder(final LFrame frame) {
		super();
		widget = frame;
		menubarBuilder 		= new MenubarBuilder(widget);
		toolbarBuilder 		= new ToolbarBuilder(widget);
		propToolbarBuilder 	= new PropertiesToolbarBuilder(widget);
	}


	/**
	 * @return The toolbar of the application.
	 * @since 3.0
	 */
	public MToolBar getToolbar() {
		return toolbarBuilder.getWidget();
	}


	protected static void addSpinner(final Container cont, final MSpinner spinner, final int width) {
		spinner.setPreferredSize(new Dimension(width, HEIGHT_TEXTFIELD));
		if(spinner.getLabel()!=null)
			cont.add(spinner.getLabel());
		cont.add(spinner);
	}


	protected static void addCombobox(final Container cont, final MComboBox<?> spinner) {
		if(spinner.getLabel()!=null)
			cont.add(spinner.getLabel());
		cont.add(spinner);
	}


	@Override
	public void setWidgetVisible(final Component widget, final boolean visible) {
		final Component comp;

		if(widget==null || visible==widget.isVisible()) return;

		// For widgets having a ScrollPane we must check their containing ScollPane.
		if(widget instanceof ScrollableWidget && ((ScrollableWidget)widget).hasScrollPane())
			comp = ((ScrollableWidget)widget).getScrollpane();
		else comp = widget;

		super.setWidgetVisible(comp, visible);

		WidgetMiniToolbar list = toolbarBuilder.mapContainers.get(comp);

		if(list==null)
			list = propToolbarBuilder.mapContainers.get(comp);

		if(list!=null) {
			list.setVisible(visible || list.isContentVisible());
			if(list.isVisible() && list.getWindowToolBar().isVisible())
				list.update();
		}
	}


	@Override
	public void compose(final MProgressBar progressBar) {
		final MPanel statusPanel = new MPanel(false, false);

		menubarBuilder.compose(progressBar);
		toolbarBuilder.compose(progressBar);
		propToolbarBuilder.compose(progressBar);

		/* Creation of the drawing area composed of the canvas, the scales, etc. */
		final MPanel drawingArea = new MPanel(false, false);
		drawingArea.setLayout(new BorderLayout());
		drawingArea.add(widget.xScaleRuler, BorderLayout.NORTH);
		drawingArea.add(widget.yScaleRuler, BorderLayout.WEST);
		drawingArea.add(widget.layeredPanel, BorderLayout.CENTER);
		drawingArea.add(propToolbarBuilder.getWidget(), BorderLayout.SOUTH);
		if(progressBar!=null) progressBar.addToProgressBar(5);

		/* Creation of the tabbed pane. */
		widget.tabbedPanel.addTab(LangTool.INSTANCE.getStringActions("UIBuilder.1"), drawingArea); //$NON-NLS-1$
		widget.tabbedPanel.addTab("PST", widget.getCodePanel()); //$NON-NLS-1$

		statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.LINE_AXIS));
		statusPanel.add(widget.fileLoader.getProgressBar());
		statusPanel.add(widget.statusBar);
		widget.fileLoader.getProgressBar().setMaximumSize(new Dimension(200, 60));

		widget.setLayout(new BorderLayout());
		widget.add(toolbarBuilder.getWidget(), BorderLayout.NORTH);
		widget.add(widget.tabbedPanel, BorderLayout.CENTER);
		widget.add(statusPanel, BorderLayout.SOUTH);
		if(progressBar!=null) progressBar.addToProgressBar(5);

		widget.setJMenuBar(menubarBuilder.getWidget());
		// Updating the concrete presentations.
		widget.updatePresentations();

		setEventableToInstruments();
		initialiseInstrumentsActivation();

		if(progressBar!=null) progressBar.addToProgressBar(5);
	}


	/**
	 * Sets the eventable objects to the instruments.
	 * @since 3.0
	 */
	protected void setEventableToInstruments() {
		final LCanvas canvas 	= widget.getCanvas();
		final MToolBar toolbar 	= toolbarBuilder.getWidget();

		widget.prefActivator.addEventable(menubarBuilder.editMenu);
		widget.exceptionsManager.addEventable(toolbar);
		widget.editingSelector.addEventable(toolbar);
		widget.hand.addEventable(canvas);
		widget.pencil.addEventable(canvas);
		widget.exporter.addEventable(toolbar);
		widget.exporter.addEventable(widget.exporter.getExportMenu());
		widget.scaleRulersCustomiser.addEventable(menubarBuilder.displayMenu);
		widget.scaleRulersCustomiser.addEventable(menubarBuilder.unitMenu);
		widget.fileLoader.addEventable(widget.fileLoader.getRecentFilesMenu());
		widget.gridCustomiser.addEventable(toolbarBuilder.magneticGridB.getToolbar());
		widget.helper.addEventable(menubarBuilder.helpMenu);
		setGlobalShortcutEventable(widget.undoManager, canvas);
		setGlobalShortcutEventable(widget.zoomer, canvas);
		setGlobalShortcutEventable(widget.deleter, canvas);
		setGlobalShortcutEventable(widget.paster, canvas);
		setGlobalShortcutEventable(widget.fileLoader, canvas);
		widget.fileLoader.addEventable(widget);
		widget.tabSelector.addEventable(widget.tabbedPanel);
		widget.drawingPropCustomiser.addEventable(toolbarBuilder.drawingB.getToolbar());
		widget.templateManager.addEventable(widget.templateManager.templateMenu());
		widget.templateManager.addEventable(widget.exporter.getExportMenu());
	}


	protected void setGlobalShortcutEventable(final Instrument instrument, final LCanvas canvas) {
		if(instrument!=null) {
			instrument.addEventable(toolbarBuilder.getWidget());
			instrument.addEventable(propToolbarBuilder.getWidget());
			instrument.addEventable(canvas);
			instrument.addEventable(widget.getTabbedPanel());
			instrument.addEventable(widget.getCodePanel());
			instrument.addEventable(menubarBuilder.displayMenu);
			instrument.addEventable(menubarBuilder.drawingMenu);
			instrument.addEventable(menubarBuilder.editMenu);
			instrument.addEventable(menubarBuilder.helpMenu);
			instrument.addEventable(menubarBuilder.unitMenu);
		}
	}


	protected void initialiseInstrumentsActivation() {
		widget.prefActivator.setActivated(true);
		widget.helper.setActivated(true);
		widget.gridCustomiser.setActivated(true);
		widget.exporter.setActivated(false);
		widget.editingSelector.setActivated(true);
		widget.hand.setActivated(true);
		widget.pencil.setActivated(false);
		widget.deleter.setActivated(false);
		widget.undoManager.setActivated(true);
		widget.zoomer.setActivated(true);
		widget.fileLoader.setActivated(true);
		widget.scaleRulersCustomiser.setActivated(true);
		widget.paster.setActivated(true);
		widget.tabSelector.setActivated(true);
		widget.drawingPropCustomiser.setActivated(true);
		widget.templateManager.setActivated(true);
	}
}
