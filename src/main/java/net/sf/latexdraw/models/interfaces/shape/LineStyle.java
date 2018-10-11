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
package net.sf.latexdraw.models.interfaces.shape;

import java.util.Arrays;
import net.sf.latexdraw.view.pst.PSTricksConstants;

/**
 * The different styles of the lines.
 * @author Arnaud Blouin
 */
public enum LineStyle {
	SOLID {
		@Override
		public String getLatexToken() {
			return PSTricksConstants.LINE_SOLID_STYLE;
		}
	}, DASHED {
		@Override
		public String getLatexToken() {
			return PSTricksConstants.LINE_DASHED_STYLE;
		}
	}, DOTTED {
		@Override
		public String getLatexToken() {
			return PSTricksConstants.LINE_DOTTED_STYLE;
		}
	};

	/**
	 * @param style The style to get.
	 * @return The style which name is the given name style.
	 */
	public static LineStyle getStyle(final String style) {
		return Arrays.stream(values()).filter(it -> it.toString().equals(style) || it.getLatexToken().equals(style)).findFirst().orElse(SOLID);
	}

	/**
	 * @return The latex token corresponding to the line style.
	 */
	public abstract String getLatexToken();
}
