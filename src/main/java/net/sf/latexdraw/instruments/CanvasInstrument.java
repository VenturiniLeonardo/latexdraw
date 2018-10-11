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

import javafx.geometry.Point3D;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.IPoint;
import net.sf.latexdraw.util.Inject;
import net.sf.latexdraw.view.MagneticGrid;
import net.sf.latexdraw.view.jfx.Canvas;
import org.malai.javafx.instrument.JfxInstrument;

/**
 * This abstract instrument encapsulates common operations dealing with a canvas.
 * @author Arnaud Blouin
 */
abstract class CanvasInstrument extends JfxInstrument {
	@Inject protected Canvas canvas;
	@Inject protected MagneticGrid grid;

	CanvasInstrument() {
		super();
	}

	/**
	 * Computes the point depending on the the zoom level and the origin of the canvas.
	 * @param pt The point to adapted.
	 * @return The computed point.
	 */
	public IPoint getAdaptedOriginPoint(final IPoint pt) {
		return canvas.convertToOrigin(pt);
	}

	/**
	 * Computes the point depending on the the zoom level and the origin of the canvas.
	 * @param pt The point to adapted.
	 * @return The computed point.
	 */
	public IPoint getAdaptedOriginPoint(final Point3D pt) {
		return pt == null ? null : getAdaptedOriginPoint(ShapeFactory.INST.createPoint(pt.getX(), pt.getY()));
	}

	/**
	 * Computes the point depending on the the zoom level and the magnetic grid.
	 * @param pt The point to adapted.
	 * @return The computed point.
	 */
	public IPoint getAdaptedPoint(final Point3D pt) {
		return canvas.convertToOrigin(grid.getTransformedPointToGrid(pt));
	}
}
