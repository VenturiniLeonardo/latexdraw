/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2018 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package net.sf.latexdraw.instrument;

import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;
import net.sf.latexdraw.command.Export;
import net.sf.latexdraw.command.ExportFormat;
import net.sf.latexdraw.command.ExportTemplate;
import net.sf.latexdraw.model.api.shape.Shape;
import net.sf.latexdraw.util.Inject;
import net.sf.latexdraw.util.LNamespace;
import net.sf.latexdraw.util.LangService;
import net.sf.latexdraw.util.SystemService;
import net.sf.latexdraw.view.jfx.Canvas;
import net.sf.latexdraw.view.pst.PSTCodeGenerator;
import net.sf.latexdraw.view.svg.SVGDocumentGenerator;
import org.malai.command.Command;
import org.malai.javafx.instrument.JfxInstrument;
import org.malai.javafx.ui.JfxUI;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * This instrument exports a drawing to different formats.
 * @author Arnaud BLOUIN
 */
public class Exporter extends JfxInstrument implements Initializable {
	/** The export menu that contains all the export menu item. */
	@FXML protected MenuButton exportMenu;
	/** The menu item that export as PST code. */
	@FXML protected MenuItem menuItemPST;
	/** The menu item that export as JPG picture. */
	@FXML protected MenuItem menuItemJPG;
	/** The menu item that export as PNG picture. */
	@FXML protected MenuItem menuItemPNG;
	/** The menu item that export as BMP picture. */
	@FXML protected MenuItem menuItemBMP;
	/** The menu item that export as PDF document. */
	@FXML protected MenuItem menuItemPDF;
	/** The menu item that export as PS (using latex) document. */
	@FXML protected MenuItem menuItemEPSLatex;
	/** The menu item that export as PDF (using pdfcrop) document. */
	@FXML protected MenuItem menuItemPDFcrop;
	@FXML protected MenuItem exportTemplateMenu;

	/** The default location of the exports. */
	private String pathExport;
	/** The dialog box that allows to define where the drawing must be exported. */
	private FileChooser fileChooserExport;
	/**
	 * The latex packages that the interactive system saves by default. These
	 * packages should by set by the user and must be general, i.e. independent of any document.
	 */
	private String defaultPackages;

	@Inject private FileLoaderSaver loader;
	/** The PST generator. */
	@Inject private PSTCodeGenerator pstGen;
	@Inject private StatusBarController statusBar;
	@Inject private TemplateManager templateManager;
	@Inject private LangService lang;
	@Inject private JfxUI app;
	/**
	 * The canvas that contains the shapes to export. The canvas is used instead
	 * of the drawing because to export as picture, we paint the views into a graphics.
	 */
	@Inject private Canvas canvas;
	@Inject private SystemService system;
	@Inject private SVGDocumentGenerator svgGen;



	/**
	 * Creates the instrument.
	 */
	public Exporter() {
		super();
		defaultPackages = "";
	}

	@Override
	public void reinit() {
		pstGen.setPackages(defaultPackages);
	}

	@Override
	public void load(final boolean generalPreferences, final String nsURI, final Element root) {
		super.load(generalPreferences, nsURI, root);

		final String name = root.getNodeName();

		if(name.endsWith(LNamespace.XML_LATEX_INCLUDES)) {
			final String[] lines = root.getTextContent().split(SystemService.EOL);
			final String pkgs = pstGen.getPackages();
			pstGen.setPackages(pstGen.getPackages() +
				Arrays.stream(lines).filter(line -> !pkgs.contains(line)).collect(Collectors.joining(SystemService.EOL, SystemService.EOL, "")));
		}
	}

	@Override
	public void save(final boolean generalPreferences, final String nsURI, final Document document, final Element root) {
		super.save(generalPreferences, nsURI, document, root);

		if(document == null || root == null) {
			return;
		}

		if(generalPreferences) {
			Element elt = document.createElement(LNamespace.XML_PATH_EXPORT);
			elt.setTextContent(pathExport);
			root.appendChild(elt);

			elt = document.createElement(LNamespace.XML_LATEX_INCLUDES);
			elt.setTextContent(defaultPackages);
			root.appendChild(elt);
		}else {
			final String ns = system.getNormaliseNamespaceURI(nsURI);
			final Element elt = document.createElement(ns + LNamespace.XML_LATEX_INCLUDES);
			elt.appendChild(document.createTextNode(pstGen.getPackages()));
			root.appendChild(elt);
		}
	}

	@Override
	public void setActivated(final boolean isActivated, final boolean hide) {
		super.setActivated(isActivated);
		exportMenu.setVisible(isActivated || !hide);
		exportMenu.setDisable(!isActivated);
	}

	@Override
	public void setActivated(final boolean activated) {
		setActivated(activated, false);
	}

	@Override
	protected void configureBindings() {
		menuItemBinder(() -> new Export(canvas, pstGen)).
			on(menuItemBMP, menuItemEPSLatex, menuItemJPG, menuItemPDF, menuItemPDFcrop, menuItemPNG, menuItemPST).
			first((i, c) -> {
			if(i.getWidget().getUserData() instanceof ExportFormat) {
				final ExportFormat format = (ExportFormat) i.getWidget().getUserData();
				c.setDialogueBox(getExportDialog(format));
				c.setFormat(format);
			}
		}).bind();

		menuItemBinder(() -> new ExportTemplate(templateManager.templatePane, system, svgGen, lang)).
			on(exportTemplateMenu).first(c -> {
			c.setUi(app);
			c.setProgressBar(statusBar.getProgressBar());
			c.setStatusWidget(statusBar.getLabel());
		}).bind();

		exportTemplateMenu.disableProperty().bind(canvas.getDrawing().getSelection().getShapes().emptyProperty());
	}

	/**
	 * @param format The format of the document to export.
	 * @return The export dialog to select a path.
	 */
	protected FileChooser getExportDialog(final ExportFormat format) {
		if(fileChooserExport == null) {
			fileChooserExport = new FileChooser();
			fileChooserExport.setTitle(lang.getBundle().getString("Exporter.1"));
		}

		fileChooserExport.getExtensionFilters().clear();
		fileChooserExport.getExtensionFilters().addAll(format.getFilter());

		if(pathExport == null || !new File(pathExport).isDirectory()) {
			final File currentFile = loader.getCurrentFile();
			if(currentFile != null && currentFile.isDirectory()) {
				fileChooserExport.setInitialDirectory(new File(currentFile.getPath()).getParentFile());
			}
		}else {
			fileChooserExport.setInitialDirectory(new File(pathExport));
		}

		return fileChooserExport;
	}

	@Override
	public void onCmdExecuted(final Command cmd) {
		statusBar.getLabel().setText(lang.getBundle().getString("LaTeXDrawFrame.184"));

		if(cmd instanceof Export) {
			final File outputFile = ((Export) cmd).getOutputFile();
			if(outputFile != null) {
				pathExport = outputFile.getParentFile().getPath();
			}
		}
	}

	/**
	 * @return The latex packages that the interactive system saves by default.
	 */
	public String getDefaultPackages() {
		return defaultPackages;
	}

	/**
	 * @param defaultPkgs The latex packages that the interactive system saves by
	 * default. These packages should by set by the user and must be
	 * general, i.e. independent of any document. Packages for a
	 * given document should by set using
	 * {@link #setPackages(String)}.
	 */
	public void setDefaultPackages(final String defaultPkgs) {
		if(defaultPkgs != null) {
			if(defaultPackages.isEmpty()) {
				pstGen.setPackages(defaultPkgs + SystemService.EOL + pstGen.getPackages());
			}
			defaultPackages = defaultPkgs;
		}
	}

	/**
	 * @param packages The latex packages used when exporting using latex. These packages are defined for the current document but not for all
	 * documents. These general packages can be set using {@link #setDefaultPackages(String)}.
	 */
	public void setPackages(final String packages) {
		if(packages != null) {
			pstGen.setPackages(packages);
			setModified(true);
		}
	}

	/**
	 * @return The path where files are exported.
	 */
	public String getPathExport() {
		return pathExport;
	}

	/**
	 * @param path The path where files are exported.
	 */
	public void setPathExport(final String path) {
		if(path != null) {
			pathExport = path;
		}
	}

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		setActivated(false);
		canvas.getDrawing().getShapes().addListener((ListChangeListener<Shape>) c -> setActivated(!canvas.getDrawing().isEmpty()));

		menuItemPDF.setUserData(ExportFormat.PDF);
		menuItemPDFcrop.setUserData(ExportFormat.PDF_CROP);
		menuItemEPSLatex.setUserData(ExportFormat.EPS_LATEX);
		menuItemJPG.setUserData(ExportFormat.JPG);
		menuItemPST.setUserData(ExportFormat.TEX);
		menuItemPNG.setUserData(ExportFormat.PNG);
		menuItemBMP.setUserData(ExportFormat.BMP);
	}
}
