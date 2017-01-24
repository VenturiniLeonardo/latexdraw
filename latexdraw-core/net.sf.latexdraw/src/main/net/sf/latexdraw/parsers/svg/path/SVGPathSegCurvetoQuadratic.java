package net.sf.latexdraw.parsers.svg.path;

/**
 * Defines the SVGPath quadratic curveto segment.<br>
 *<br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2017 Arnaud BLOUIN<br>
 *<br>
 *  LaTeXDraw is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.<br>
 *<br>
 *  LaTeXDraw is distributed without any warranty; without even the
 *  implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 *  PURPOSE. See the GNU General Public License for more details.<br>
 *<br>
 * 10/20/07<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 */
public class SVGPathSegCurvetoQuadratic extends SVGPathPointSeg {
	/** The x-coordinate of the first control point. @since 2.0 */
	protected double x1;

	/** The y-coordinate of the first control point. @since 2.0 */
	protected double y1;


	/**
	 * The main constructor.
	 * @param x The X-coordinate of the second point of the curve.
	 * @param y The Y-coordinate of the second point of the curve.
	 * @param x1 The x-coordinate of the first control point.
	 * @param y1 The y-coordinate of the first control point
	 * @param isRelative isRelative True: the path segment is relative, false it is absolute.
	 */
	public SVGPathSegCurvetoQuadratic(final double x, final double y, final double x1, final double y1, final boolean isRelative) {
		super(isRelative, x, y);

		this.x1 = x1;
		this.y1 = y1;
	}



	@Override
	public String toString() {
        return String.valueOf(isRelative() ? 'q' : 'Q') + ' ' + x1 + ' ' + y1 + ' ' + x + ' ' + y;
	}


	/**
	 * @return the x1.
	 * @since 2.0
	 */
	public double getX1() {
		return x1;
	}


	/**
	 * @return the y1.
	 * @since 2.0
	 */
	public double getY1() {
		return y1;
	}
}
