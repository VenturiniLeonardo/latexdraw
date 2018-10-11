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

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import net.sf.latexdraw.models.interfaces.prop.IPlotProp;

/**
 * The API for plots.
 * @author Arnaud BLOUIN
 */
public interface IPlot extends IPositionShape, IPlotProp {
	/**
	 * @param x The X coordinate.
	 * @return The corresponding Y coordinate or NaN if a problem occurs.
	 */
	double getY(final double x);

	BooleanProperty polarProperty();

	StringProperty plotEquationProperty();

	DoubleProperty plotMinXProperty();

	DoubleProperty plotMaxXProperty();

	IntegerProperty nbPlottedPointsProperty();

	ObjectProperty<PlotStyle> plotStyleProperty();

	ObjectProperty<DotStyle> dotStyleProperty();

	DoubleProperty dotDiametreProperty();

	DoubleProperty xScaleProperty();

	DoubleProperty yScaleProperty();

	@Override
	IPlot duplicate();
}
