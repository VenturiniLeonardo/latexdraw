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
package net.sf.latexdraw.parsers.svg;

import java.text.ParseException;
import net.sf.latexdraw.parsers.svg.parsers.SVGLengthParser;
import org.w3c.dom.Node;

/**
 * Defines the SVG tag <code>marker</code>.
 * @author Arnaud BLOUIN
 */
public class SVGMarkerElement extends SVGElement {
	/**
	 * {@link SVGElement#SVGElement(Node, SVGElement)}
	 * @param owner The owner document.
	 */
	public SVGMarkerElement(final SVGDocument owner) {
		super(owner);

		setNodeName(SVGElements.SVG_MARKER);
	}


	/**
	 * {@link SVGElement#SVGElement(Node, SVGElement)}
	 */
	public SVGMarkerElement(final Node n, final SVGElement p) throws MalformedSVGDocument {
		super(n, p);
	}


	/**
	 * @return The x-axis coordinate of the reference point which is to be aligned exactly at the marker position.
	 */
	public double getRefX() {
		final String v = getAttribute(getUsablePrefix() + SVGAttributes.SVG_REF_X);
		double refx;

		try {
			refx = v == null ? 0 : new SVGLengthParser(v).parseCoordinate().getValue();
		}catch(final ParseException e) {
			refx = 0;
		}

		return refx;
	}


	/**
	 * @return The y-axis coordinate of the reference point which is to be aligned exactly at the marker position.
	 */
	public double getRefY() {
		final String v = getAttribute(getUsablePrefix() + SVGAttributes.SVG_REF_Y);
		double refy;

		try {
			refy = v == null ? 0 : new SVGLengthParser(v).parseCoordinate().getValue();
		}catch(final ParseException e) {
			refy = 0;
		}

		return refy;
	}


	/**
	 * @return Represents the width of the viewport into which the marker is to be fitted when it is rendered.
	 */
	public double getMarkerWidth() {
		final String v = getAttribute(getUsablePrefix() + SVGAttributes.SVG_MARKER_WIDTH);
		double markerW;

		try {
			markerW = v == null ? 3 : new SVGLengthParser(v).parseLength().getValue();
		}catch(final ParseException e) {
			markerW = 3;
		}

		return markerW;
	}


	/**
	 * @return Represents the height of the viewport into which the marker is to be fitted when it is rendered.
	 */
	public double getMarkerHeight() {
		final String v = getAttribute(getUsablePrefix() + SVGAttributes.SVG_MARKER_HEIGHT);
		double markerH;

		try {
			markerH = v == null ? 3 : new SVGLengthParser(v).parseCoordinate().getValue();
		}catch(final ParseException e) {
			markerH = 3;
		}

		return markerH;
	}


	/**
	 * @return The coordinate system for attributes markerWidth, markerHeight and the contents of the 'marker'.
	 */
	public String getMarkerUnits() {
		final String v = getAttribute(getUsablePrefix() + SVGAttributes.SVG_MARKER_UNITS);

		return (v == null || (!SVGAttributes.SVG_UNITS_VALUE_STROKE.equals(v) && !SVGAttributes.SVG_UNITS_VALUE_USR.equals(v))) ?
			SVGAttributes.SVG_UNITS_VALUE_STROKE : v;
	}


	@Override
	public boolean checkAttributes() {
		return getMarkerWidth() >= 0 && getMarkerHeight() >= 0;
	}


	@Override
	public boolean enableRendering() {
		return getMarkerWidth() > 0 && getMarkerHeight() > 0;
	}
}
