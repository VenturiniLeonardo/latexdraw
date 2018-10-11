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
package net.sf.latexdraw.instruments;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import net.sf.latexdraw.LaTeXDraw;
import net.sf.latexdraw.util.LFileUtils;
import net.sf.latexdraw.util.LSystem;
import net.sf.latexdraw.util.LangTool;
import net.sf.latexdraw.util.VersionChecker;

/**
 * The controller of the "About Latexdraw" dialogue box.
 * @author Arnaud BLOUIN
 */
public class AboutController implements Initializable {
	@FXML private Label aboutText;
	@FXML private TextArea noteText;
	@FXML private TextArea contribText;
	@FXML private TextArea sysText;
	@FXML private TextArea licenseText;

	/**
	 * Creates the controller.
	 */
	public AboutController() {
		super();
	}

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		aboutText.setText(LangTool.INSTANCE.getBundle().getString("LaTeXDrawFrame.219") + ' ' +  //NON-NLS
			VersionChecker.VERSION + VersionChecker.VERSION_STABILITY + ", build " + VersionChecker.ID_BUILD + LSystem.EOL +  //NON-NLS
			LaTeXDraw.LABEL_APP + LangTool.INSTANCE.getBundle().getString("LaTeXDrawFrame.221") + LSystem.EOL + //NON-NLS
			"Copyright(c) 2005-2018 - Arnaud BLOUIN" + LSystem.EOL + //NON-NLS
			"http://latexdraw.sourceforge.net/"); //NON-NLS
		noteText.setText(LFileUtils.INSTANCE.readTextFile("/res/release_note.txt")); //NON-NLS
		contribText.setText(LFileUtils.INSTANCE.readTextFile("/res/contributors.txt")); //NON-NLS
		licenseText.setText(LFileUtils.INSTANCE.readTextFile("/res/license.txt")); //NON-NLS

		final StringBuilder builder = new StringBuilder();
		builder.append("LaTeX version:").append(LSystem.INSTANCE.getLaTeXVersion()).append(LSystem.EOL); //NON-NLS
		builder.append("DviPS version:").append(LSystem.INSTANCE.getDVIPSVersion()).append(LSystem.EOL); //NON-NLS
		builder.append("PS2PDF version:").append(LSystem.EOL).append(LSystem.INSTANCE.getPS2PDFVersion()).append(LSystem.EOL); //NON-NLS
		builder.append("PS2EPSI version:").append(LSystem.INSTANCE.getPS2EPSVersion()).append(LSystem.EOL); //NON-NLS
		builder.append("PDFcrop version:").append(LSystem.INSTANCE.getPDFCROPVersion()).append(LSystem.EOL); //NON-NLS
		builder.append("Java properties:").append(LSystem.EOL); //NON-NLS
		System.getProperties().forEach((key, value) -> builder.append(key).append(':').append(' ').append(value).append(LSystem.EOL));
		sysText.setText(builder.toString());
	}
}
