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

/**
 * The API for lines.
 * @author Arnaud BLOUIN
 */
public interface ILine {
	/**
	 * Computes the angle of the line.
	 * @return The angle of the line.
	 * @since 3.0
	 */
	double getLineAngle();

	/**
	 * @return The x coordinate of the first point.
	 * @since 3.0
	 */
	double getX1();

	/**
	 * Sets the x coordinate of the first point.
	 * @param x1 The new x coordinate of the first point.
	 * @since 3.0
	 */
	void setX1(final double x1);

	/**
	 * @return The x coordinate of the second point.
	 * @since 3.0
	 */
	double getX2();

	/**
	 * Sets the x coordinate of the second point.
	 * @param x2 The new x coordinate of the second point.
	 * @since 3.0
	 */
	void setX2(final double x2);

	/**
	 * @return The y coordinate of the first point.
	 * @since 3.0
	 */
	double getY1();

	/**
	 * Sets the y coordinate of the first point.
	 * @param y1 The new y coordinate of the first point.
	 * @since 3.0
	 */
	void setY1(final double y1);

	/**
	 * @return The y coordinate of the second point.
	 * @since 3.0
	 */
	double getY2();

	/**
	 * Sets the y coordinate of the second point.
	 * @param y2 The new y coordinate of the second point.
	 * @since 3.0
	 */
	void setY2(final double y2);

	/**
	 * @return The first point.
	 * @since 3.0
	 */
	IPoint getPoint1();

	/**
	 * @return The second point.
	 * @since 3.0
	 */
	IPoint getPoint2();

	/**
	 * @return True if the line is vertical.
	 * @since 3.0
	 */
	boolean isVerticalLine();

	/**
	 * @return True if the line is horizontal.
	 * @since 3.0
	 */
	boolean isHorizontalLine();

	/**
	 * @param pt The point to check.
	 * @return True if the segment defined by the line contains the given point. False otherwise or
	 * if the given point is null.
	 * @since 3.0
	 */
	boolean isInSegment(final IPoint pt);

	/**
	 * Sets the position of the line. Do nothing if one of the given parameter
	 * is not valid.
	 * @param x1 The x coordinate of the first point.
	 * @param y1 The y coordinate of the first point.
	 * @param x2 The x coordinate of the second point.
	 * @param y2 The y coordinate of the second point.
	 * @since 3.0
	 */
	void setLine(final double x1, final double y1, final double x2, final double y2);

	/**
	 * Sets the first point.
	 * @param pt The new first point.
	 * @since 3.0
	 */
	void setP1(final IPoint pt);

	/**
	 * Sets the second point.
	 * @param pt The new second point.
	 * @since 3.0
	 */
	void setP2(final IPoint pt);

	/**
	 * @return the a parameter of the line.
	 */
	double getA();

	/**
	 * @return the b parameter of the line.
	 */
	double getB();

	/**
	 * @return The top left point of the line (may be not a point on the line).
	 */
	IPoint getTopLeftPoint();

	/**
	 * @return The bottom right point of the line (may be not a point on the line).
	 */
	IPoint getBottomRightPoint();

	/**
	 * Creates the line which is perpendicular to the current line at the point pt.
	 * @param pt The point of crossing between the two lines.
	 * @return The perpendicular line.
	 */
	ILine getPerpendicularLine(final IPoint pt);

	/**
	 * @param l The second lines
	 * @return The intersection between two lines. Null if the given is not valid or
	 * if both lines are parallels or both lines are identical.
	 */
	IPoint getIntersection(final ILine l);

	/**
	 * @param l The second line.
	 * @return The point of the intersection between of the two segments.
	 */
	IPoint getIntersectionSegment(final ILine l);

	/**
	 * Gets the points which are on the line and at the distance
	 * "distance" of the point "p" of the line.
	 * @param x The x-coordinate of the point of reference.
	 * @param y The y-coordinate of the point of reference.
	 * @param distance The distance between p and the points we must find.
	 * @return The found points or an empty array if a problem occurs.
	 */
	IPoint[] findPoints(final double x, final double y, final double distance);

	/**
	 * Gets the points which are on the line and at the distance
	 * "distance" of the point "p" of the line.
	 * @param p The point of reference.
	 * @param distance The distance between p and the points we must find.
	 * @return The found points or an empty array if a problem occurs.
	 */
	IPoint[] findPoints(final IPoint p, final double distance);

	/**
	 * Update the y-intercept <code>b</code> and slope <code>a</code>.
	 */
	void updateAandB();
}
