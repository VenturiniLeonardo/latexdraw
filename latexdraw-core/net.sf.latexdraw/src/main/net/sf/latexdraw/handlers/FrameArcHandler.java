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
package net.sf.latexdraw.handlers;

//import java.awt.Shape;
//import java.awt.geom.Ellipse2D;
//import java.awt.geom.Rectangle2D;
//
///**
// * A handler to change the frame arc attribute.
// * @author Arnaud BLOUIN
// */
//public class FrameArcHandler extends Handler<Ellipse2D> {
//	/**
//	 * Creates the handler.
//	 */
//	public FrameArcHandler() {
//		super();
//		shape = new Ellipse2D.Double();
//		size  = size/4.*3.;
//	}
//
//
//	@Override
//	protected void updateShape() {
//		shape.setFrame(point.getX(), point.getY()-size, size, size);
//	}
//
//
//	@Override
//	public void updateFromShape(final Shape sh) {
//		if(sh instanceof Rectangle2D) {
//			final Rectangle2D rec = (Rectangle2D)sh;
//
//			if(rec.getHeight()>rec.getWidth())
//				setPoint(rec.getMinX()-size, rec.getMinY()+size);
//			else
//				setPoint(rec.getMinX(), rec.getMinY());
//		}
//	}
//}
