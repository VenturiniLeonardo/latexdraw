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

import java.util.List;
import net.sf.latexdraw.models.interfaces.prop.IArcProp;
import net.sf.latexdraw.models.interfaces.prop.IAxesProp;
import net.sf.latexdraw.models.interfaces.prop.IFreeHandProp;
import net.sf.latexdraw.models.interfaces.prop.IGridProp;
import net.sf.latexdraw.models.interfaces.prop.ILineArcProp;
import net.sf.latexdraw.models.interfaces.prop.IPlotProp;
import net.sf.latexdraw.models.interfaces.prop.ISetShapesProp;
import net.sf.latexdraw.models.interfaces.prop.ITextProp;

/**
 * The API for groups of shapes.
 * @author Arnaud BLOUIN
 */
public interface IGroup extends IArrowableShape, ISetShapesProp, ILineArcProp, ITextProp, IArcProp, IAxesProp, IGridProp, IFreeHandProp, IPlotProp {
	/**
	 * Duplicates the group of shapes.
	 * @param duplicateShapes True: the shapes will be duplicated as well.
	 * @return The duplicated group of shapes.
	 * @since 3.0
	 */
	IGroup duplicateDeep(final boolean duplicateShapes);

	@Override
	IGroup duplicate();

	/**
	 * @return The list of polar/cartesian coordinates of the plots contained by the group.
	 * If a shape of the group does not support this property, null is added
	 * to the list. The list cannot be null.
	 */
	List<java.lang.Boolean> getPlotPolarList();

	/**
	 * Sets if polar coordinates for the plots of the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 */
	void setPlotPolarList(final List<java.lang.Boolean> values);

	/**
	 * @return The list of max X of the plots contained by the group.
	 * If a shape of the group does not support this property, null is added
	 * to the list. The list cannot be null.
	 */
	List<java.lang.Double> getPlotMaxXList();

	/**
	 * Sets the max X of the plots of the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 */
	void setPlotMaxXList(final List<java.lang.Double> values);

	/**
	 * @return The list of min X of the plots contained by the group.
	 * If a shape of the group does not support this property, null is added
	 * to the list. The list cannot be null.
	 */
	List<java.lang.Double> getPlotMinXList();

	/**
	 * Sets the min X of the plots of the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 */
	void setPlotMinXList(final List<java.lang.Double> values);

	/**
	 * @return The list of number of plotted points the shapes contained by the group.
	 * If a shape of the group does not support this property, null is added
	 * to the list. The list cannot be null.
	 */
	List<java.lang.Integer> getNbPlottedPointsList();

	/**
	 * Sets the number of plotted points of the shapes of the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 */
	void setNbPlottedPointsList(final List<java.lang.Integer> values);

	/**
	 * @return The list of the plot style of the shapes contained by the group.
	 * If a shape of the group does not support this property, null is added
	 * to the list. The list cannot be null.
	 */
	List<PlotStyle> getPlotStyleList();

	/**
	 * Sets the plot style of the shapes of the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 */
	void setPlotStyleList(final List<PlotStyle> values);

	/**
	 * @return The list of Y-scale of the shapes contained by the group.
	 * If a shape of the group does not support this property, null is added
	 * to the list. The list cannot be null.
	 */
	List<Double> getYScaleList();

	/**
	 * Sets the Y-scale shapes of the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 */
	void setYScaleList(final List<Double> values);

	/**
	 * @return The list of X scale of the shapes contained by the group.
	 * If a shape of the group does not support this property, null is added
	 * to the list. The list cannot be null.
	 * @since 3.0
	 */
	List<Double> getXScaleList();

	/**
	 * Sets the X-scale shapes of the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 */
	void setXScaleList(final List<Double> values);

	/**
	 * @return The list of plot equations of the shapes contained by the group.
	 * If a shape of the group does not support the border position property, null is added
	 * to the list. The list cannot be null.
	 * @since 3.0
	 */
	List<String> getPlotEquationList();

	/**
	 * Sets the equation of plot shapes of the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 */
	void setPlotEquationList(final List<String> values);

	/**
	 * @return The list of the border positions of the shapes contained by the group.
	 * If a shape of the group does not support the border position property, null is added
	 * to the list. The list cannot be null.
	 * @since 3.0
	 */
	List<BorderPos> getBordersPositionList();

	/**
	 * Sets the border position of the shapes of the group.
	 * @param list The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 * @since 3.0
	 */
	void setBordersPositionList(final List<BorderPos> list);

	/**
	 * @return The list of line colours of the shapes contained by the group.
	 * If a shape of the group does not support the line colour property, null is added
	 * to the list. The list cannot be null.
	 * @since 3.0
	 */
	List<Color> getLineColourList();

	/**
	 * Sets the line colour of the shapes of the group.
	 * @param list The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 * @since 3.0
	 */
	void setLineColourList(final List<Color> list);

	/**
	 * @return The list of start angle of the arc shapes contained by the group.
	 * If a shape of the group does not support the start angle property, null is added
	 * to the list. The list cannot be null.
	 * @since 3.0
	 */
	List<Double> getAngleStartList();

	/**
	 * Sets the starting angle of the arcable shapes of the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 * @since 3.0
	 */
	void setAngleStartList(final List<Double> values);

	/**
	 * @return The list of end angle of the arc shapes contained by the group.
	 * If a shape of the group does not support the end angle property, null is added
	 * to the list. The list cannot be null.
	 * @since 3.0
	 */
	List<Double> getAngleEndList();

	/**
	 * Sets the ending angle of the arcable shapes of the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 * @since 3.0
	 */
	void setAngleEndList(final List<Double> values);

	/**
	 * @return The list of arc styles of the arc shapes contained by the group.
	 * If a shape of the group does not support the arc style property, null is added
	 * to the list. The list cannot be null.
	 * @since 3.0
	 */
	List<ArcStyle> getArcStyleList();

	/**
	 * Sets the arc style of the arcable shapes of the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 * @since 3.0
	 */
	void setArcStyleList(final List<ArcStyle> values);

	/**
	 * @param i The index of the arrows to get.
	 * @return The list of arrow style of the shapes contained by the group.
	 * If a shape of the group does not support the arrow style property, null is added
	 * to the list. The list cannot be null.
	 * @since 3.0
	 */
	List<ArrowStyle> getArrowStyleList(final int i);

	/**
	 * @return The list of the rotation angles of the shapes contained by the group.
	 * If a shape of the group does not support the rotation angle property, null is added
	 * to the list. The list cannot be null.
	 * @since 3.0
	 */
	List<Double> getRotationAngleList();

	/**
	 * Sets the rotation angle of the shapes of the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 * @since 3.0
	 */
	void setRotationAngleList(final List<Double> values);

	/**
	 * @return The list of the text positions the shapes contained by the group.
	 * If a shape of the group does not support the text position property, null is added
	 * to the list. The list cannot be null.
	 * @since 3.0
	 */
	List<TextPosition> getTextPositionList();

	/**
	 * Sets the text position of the text shapes of the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 * @since 3.0
	 */
	void setTextPositionList(final List<TextPosition> values);

	/**
	 * @return The list of the text contents of the shapes contained by the group.
	 * If a shape of the group does not support the text property, null is added
	 * to the list. The list cannot be null.
	 * @since 3.0
	 */
	List<String> getTextList();

	/**
	 * Sets the text content of the text shapes of the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 * @since 3.0
	 */
	void setTextList(final List<String> values);

	/**
	 * @return The list of the hatchings angle of the shapes contained by the group.
	 * If a shape of the group does not support the hatchings angle property, null is added
	 * to the list. The list cannot be null.
	 * @since 3.0
	 */
	List<Double> getHatchingsAngleList();

	/**
	 * Sets the hatchings angle of the shapes of the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 * @since 3.0
	 */
	void setHatchingsAngleList(final List<Double> values);

	/**
	 * @return The list of the hatchings width of the shapes contained by the group.
	 * If a shape of the group does not support the hatchings width property, null is added
	 * to the list. The list cannot be null.
	 * @since 3.0
	 */
	List<Double> getHatchingsWidthList();

	/**
	 * Sets the hatchings width of the shapes of the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 * @since 3.0
	 */
	void setHatchingsWidthList(final List<Double> values);

	/**
	 * @return The list of the hatchings size of the shapes contained by the group.
	 * If a shape of the group does not support the hatchings size property, null is added
	 * to the list. The list cannot be null.
	 * @since 3.0
	 */
	List<Double> getHatchingsSepList();

	/**
	 * Sets the hatchings gap of the shapes of the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 * @since 3.0
	 */
	void setHatchingsSepList(final List<Double> values);

	/**
	 * @return The list of the gradient angle of the shapes contained by the group.
	 * If a shape of the group does not support the gradient angle property, null is added
	 * to the list. The list cannot be null.
	 * @since 3.0
	 */
	List<Double> getGradAngleList();

	/**
	 * Sets the starting angle of the gradient of the shapes of the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 * @since 3.0
	 */
	void setGradAngleList(final List<Double> values);

	/**
	 * @return The list of the gradient middle point of the shapes contained by the group.
	 * If a shape of the group does not support the gradient middle point property, null is added
	 * to the list. The list cannot be null.
	 * @since 3.0
	 */
	List<Double> getGradMidPtList();

	/**
	 * Sets the middle point reference of the gradient of the shapes of the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 * @since 3.0
	 */
	void setGradMidPtList(final List<Double> values);

	/**
	 * @return The list of the line arc values of the shapes contained by the group.
	 * If a shape of the group does not support the line arc property, null is added
	 * to the list. The list cannot be null.
	 * @since 3.0
	 */
	List<Double> getLineArcList();

	/**
	 * Sets the line arc value of the line-arcable shapes of the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 * @since 3.0
	 */
	void setLineArcList(final List<Double> values);

	/**
	 * @return The list of filling colours of the shapes contained by the group.
	 * If a shape of the group does not support the filling colour property, null is added
	 * to the list. The list cannot be null.
	 * @since 3.0
	 */
	List<Color> getFillingColList();

	/**
	 * Sets the filling colour of the shapes of the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 * @since 3.0
	 */
	void setFillingColList(final List<Color> values);

	/**
	 * @return The list of hatchings colours of the shapes contained by the group.
	 * If a shape of the group does not support the hatchings colour property, null is added
	 * to the list. The list cannot be null.
	 * @since 3.0
	 */
	List<Color> getHatchingsColList();

	/**
	 * Sets the hatchings colour of the shapes of the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 * @since 3.0
	 */
	void setHatchingsColList(final List<Color> values);

	/**
	 * @return The list of boolean defining if the shapes contained by the group have double borders.
	 * If a shape of the group does not support the double border property, null is added
	 * to the list. The list cannot be null.
	 * @since 3.0
	 */
	List<Boolean> hasDbleBordList();

	/**
	 * @return The list of double border width of the shapes contained by the group.
	 * If a shape of the group does not support the double border width property, null is added
	 * to the list. The list cannot be null.
	 * @since 3.0
	 */
	List<Double> getDbleBordSepList();

	/**
	 * Sets the double border gap of the shapes of the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 * @since 3.0
	 */
	void setDbleBordSepList(final List<Double> values);

	/**
	 * @return The list of double border colours of the shapes contained by the group.
	 * If a shape of the group does not support the double border colour property, null is added
	 * to the list. The list cannot be null.
	 * @since 3.0
	 */
	List<Color> getDbleBordColList();

	/**
	 * Sets the double borders colour of the shapes of the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 * @since 3.0
	 */
	void setDbleBordColList(final List<Color> values);

	/**
	 * @return The list of boolean defining if the shapes contained by the group have shadow.
	 * If a shape of the group does not support the shadow property, null is added
	 * to the list. The list cannot be null.
	 * @since 3.0
	 */
	List<Boolean> hasShadowList();

	/**
	 * @return The list of shadow size of the shapes contained by the group.
	 * If a shape of the group does not support the shadow size property, null is added
	 * to the list. The list cannot be null.
	 * @since 3.0
	 */
	List<Double> getShadowSizeList();

	/**
	 * Sets the shadow sizes of the shapes of the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 * @since 3.0
	 */
	void setShadowSizeList(final List<Double> values);

	/**
	 * @return The list of shadow angle of the shapes contained by the group.
	 * If a shape of the group does not support the shadow angle property, null is added
	 * to the list. The list cannot be null.
	 * @since 3.0
	 */
	List<Double> getShadowAngleList();

	/**
	 * Sets the shadow angles of the shapes of the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 * @since 3.0
	 */
	void setShadowAngleList(final List<Double> values);

	/**
	 * @return The list of shadow colours of the shapes contained by the group.
	 * If a shape of the group does not support the shadow colour property, null is added
	 * to the list. The list cannot be null.
	 * @since 3.0
	 */
	List<Color> getShadowColList();

	/**
	 * Sets the shadow colour of the shapes of the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 * @since 3.0
	 */
	void setShadowColList(final List<Color> values);

	/**
	 * @return The list of ending gradient colours of the shapes contained by the group.
	 * If a shape of the group does not support the ending gradient colour property, null is added
	 * to the list. The list cannot be null.
	 * @since 3.0
	 */
	List<Color> getGradColStartList();

	/**
	 * Sets the first gradient colour of the shapes of the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 * @since 3.0
	 */
	void setGradColStartList(final List<Color> values);

	/**
	 * @return The list of starting gradient colours of the shapes contained by the group.
	 * If a shape of the group does not support the starting gradient colour property, null is added
	 * to the list. The list cannot be null.
	 * @since 3.0
	 */
	List<Color> getGradColEndList();

	/**
	 * Sets the last gradient colour of the shapes of the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 * @since 3.0
	 */
	void setGradColEndList(final List<Color> values);

	/**
	 * @return The list of the thicknesses of the shapes contained by the group.
	 * If a shape of the group does not support the thickness property, null is added
	 * to the list. The list cannot be null.
	 * @since 3.0
	 */
	List<Double> getThicknessList();

	/**
	 * Sets the thickness of the shapes of the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 * @since 3.0
	 */
	void setThicknessList(final List<Double> values);

	/**
	 * @return The list of the filling styles of the shapes contained by the group.
	 * If a shape of the group does not support the filling style property, null is added
	 * to the list. The list cannot be null.
	 * @since 3.0
	 */
	List<FillingStyle> getFillingStyleList();

	/**
	 * Sets the style of the filling of the shapes of the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 * @since 3.0
	 */
	void setFillingStyleList(final List<FillingStyle> values);

	/**
	 * @return The list of the line styles of the shapes contained by the group.
	 * If a shape of the group does not support the line style property, null is added
	 * to the list. The list cannot be null.
	 * @since 3.0
	 */
	List<LineStyle> getLineStyleList();

	/**
	 * Sets the line style colour of the shapes of the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 * @since 3.0
	 */
	void setLineStyleList(final List<LineStyle> values);

	/**
	 * @return The list of filling colours of the dot shapes contained by the group.
	 * If a shape of the group does not support the dot filling colour property, null is added
	 * to the list. The list cannot be null.
	 * @since 3.0
	 */
	List<Color> getDotFillingColList();

	/**
	 * Sets the filling colour of the dot shapes of the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 * @since 3.0
	 */
	void setDotFillingColList(final List<Color> values);

	/**
	 * @return The list of the dot styles of the shapes contained by the group.
	 * If a shape of the group does not support the dot style property, null is added
	 * to the list. The list cannot be null.
	 * @since 3.0
	 */
	List<DotStyle> getDotStyleList();

	/**
	 * Sets the dot style of the dottable shapes of the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 * @since 3.0
	 */
	void setDotStyleList(final List<DotStyle> values);

	/**
	 * @return The list of the dot sizes of the shapes contained by the group.
	 * If a shape of the group does not support the dot size property, null is added
	 * to the list. The list cannot be null.
	 * @since 3.0
	 */
	List<Double> getDotSizeList();

	/**
	 * Sets the size of the dot shapes of the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 * @since 3.0
	 */
	void setDotSizeList(final List<Double> values);

	/**
	 * Sets the arrow style of the arrowable shapes of the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 * @param i The index of the arrow to set.
	 * @since 3.0
	 */
	void setArrowStyleList(final List<ArrowStyle> values, final int i);

	/**
	 * Defines if the shapes of the group have double borders.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 * @since 3.0
	 */
	void setHasDbleBordList(final List<Boolean> values);

	/**
	 * Defines if the shapes of the group have a shadow.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 * @since 3.0
	 */
	void setHasShadowList(final List<Boolean> values);

	/**
	 * @return The list of the starting points of the grid shapes contained by the group.
	 * If a shape of the group does not support the starting point property, null is added
	 * to the list. The list cannot be null.
	 * @since 3.0
	 */
	List<IPoint> getGridStartList();

	/**
	 * Sets the starting points of the grid shapes of the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 * @since 3.0
	 */
	void setGridStartList(final List<IPoint> values);

	/**
	 * @return The list of the ending points of the grid shapes contained by the group.
	 * If a shape of the group does not support the starting point property, null is added
	 * to the list. The list cannot be null.
	 * @since 3.0
	 */
	List<IPoint> getGridEndList();

	/**
	 * Sets the ending points of the grid shapes of the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 * @since 3.0
	 */
	void setGridEndList(final List<IPoint> values);

	/**
	 * @return The list of the origin points of the grid shapes contained by the group.
	 * If a shape of the group does not support the starting point property, null is added
	 * to the list. The list cannot be null.
	 * @since 3.0
	 */
	List<IPoint> getGridOriginList();

	/**
	 * Sets the origin points of the grid shapes of the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 * @since 3.0
	 */
	void setGridOriginList(final List<IPoint> values);

	/**
	 * @return The list of the sizes of the labels of the grid shapes contained by the group.
	 * If a shape of the group does not support the starting point property, null is added
	 * to the list. The list cannot be null.
	 * @since 3.0
	 */
	List<Integer> getGridLabelSizeList();

	/**
	 * Sets the size of the labels of the grid shapes of the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 * @since 3.0
	 */
	void setGridLabelSizeList(final List<Integer> values);

	/**
	 * @return The list of the Y-coordinate labels of the grid shapes contained by the group.
	 * If a shape of the group does not support the starting point property, null is added
	 * to the list. The list cannot be null.
	 * @since 3.0
	 */
	List<Boolean> getGridXLabelSouthList();

	/**
	 * Sets the Y-coordinate of the labels of the grid contained by the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 * @since 3.0
	 */
	void setGridXLabelSouthList(final List<Boolean> values);

	/**
	 * @return The list of the Y-coordinate labels of the grid shapes contained by the group.
	 * If a shape of the group does not support the starting point property, null is added
	 * to the list. The list cannot be null.
	 * @since 3.0
	 */
	List<Boolean> getGridYLabelWestList();

	/**
	 * Sets the X-coordinate of the labels of the grid contained by the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 * @since 3.0
	 */
	void setGridYLabelWestList(final List<Boolean> values);

	/**
	 * @return The list of the styles of the axes contained by the group.
	 * If a shape of the group is not an axe, null is added.
	 * to the list. The list cannot be null.
	 * @since 3.0
	 */
	List<AxesStyle> getAxesStyleList();

	/**
	 * Sets the style of the axes contained by the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 * @since 3.0
	 */
	void setAxesStyleList(final List<AxesStyle> values);

	/**
	 * @return The list of the styles of the axes' ticks contained by the group.
	 * If a shape of the group is not an axe, null is added.
	 * to the list. The list cannot be null.
	 * @since 3.0
	 */
	List<TicksStyle> getAxesTicksStyleList();

	/**
	 * Sets the style of the axes' ticks contained by the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 * @since 3.0
	 */
	void setAxesTicksStyleList(final List<TicksStyle> values);

	/**
	 * @return The list of the sizes of the axes' ticks contained by the group.
	 * If a shape of the group is not an axe, null is added.
	 * to the list. The list cannot be null.
	 * @since 3.0
	 */
	List<Double> getAxesTicksSizeList();

	/**
	 * Sets the size of the axes' ticks contained by the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 * @since 3.0
	 */
	void setAxesTicksSizeList(final List<Double> values);

	/**
	 * @return The list of the plotting styles of the axes' ticks contained in the group.
	 * If a shape of the group is not an axe, null is added.
	 * to the list. The list cannot be null.
	 * @since 3.0
	 */
	List<PlottingStyle> getAxesTicksDisplayedList();

	/**
	 * Sets how the ticks of the axes contained by the group are displayed.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 * @since 3.0
	 */
	void setAxesTicksDisplayedList(final List<PlottingStyle> values);

	/**
	 * @return The list of the labels' increments of the axes' ticks contained in the group.
	 * If a shape of the group is not an axe, null is added.
	 * to the list. The list cannot be null.
	 * @since 3.0
	 */
	List<IPoint> getAxesIncrementsList();

	/**
	 * Sets the labels' increments of the axes contained by the group are displayed.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 * @since 3.0
	 */
	void setAxesIncrementsList(final List<IPoint> values);

	/**
	 * @return The list of the plotting styles of the axes' labels contained in the group.
	 * If a shape of the group is not an axe, null is added.
	 * to the list. The list cannot be null.
	 * @since 3.0
	 */
	List<PlottingStyle> getAxesLabelsDisplayedList();

	/**
	 * Sets how the labels of the axes contained by the group are displayed.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 * @since 3.0
	 */
	void setAxesLabelsDisplayedList(final List<PlottingStyle> values);

	/**
	 * @return The list of booleans defining if the origin of the axes contained in the group must be shown.
	 * If a shape of the group is not an axe, null is added.
	 * to the list. The list cannot be null.
	 * @since 3.0
	 */
	List<Boolean> getAxesShowOriginList();

	/**
	 * Defines if the origin of the axes contained by the group are displayed.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 * @since 3.0
	 */
	void setAxesShowOriginList(final List<Boolean> values);

	/**
	 * @return The list of the distances between the labels of the axes contained in the group.
	 * If a shape of the group is not an axe, null is added.
	 * to the list. The list cannot be null.
	 * @since 3.0
	 */
	List<IPoint> getAxesDistLabelsList();

	/**
	 * Sets the distances between the labels of the axes contained by the group are displayed.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 * @since 3.0
	 */
	void setAxesDistLabelsList(final List<IPoint> values);

	/**
	 * @return The list of labels' colours of the grids contained by the group.
	 * If a shape of the group is not an axe, null is added.
	 * to the list. The list cannot be null.
	 * @since 3.0
	 */
	List<Color> getGridLabelsColourList();

	/**
	 * Sets the labels' colours of the grids contained by the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 * @since 3.0
	 */
	void setGridLabelsColourList(final List<Color> values);

	/**
	 * @return The list of labels' colours of the grids contained by the group.
	 * If a shape of the group is not an axe, null is added.
	 * to the list. The list cannot be null.
	 * @since 3.0
	 */
	List<Color> getSubGridColourList();

	/**
	 * Sets the labels' colours of the grids contained by the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 * @since 3.0
	 */
	void setSubGridColourList(final List<Color> values);

	/**
	 * @return The width of the grids contained by the group.
	 * If a shape of the group is not an axe, null is added.
	 * to the list. The list cannot be null.
	 * @since 3.0
	 */
	List<Double> getGridWidthList();

	/**
	 * Sets the width of the grids contained by the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 * @since 3.0
	 */
	void setGridWidthList(final List<Double> values);

	/**
	 * @return The width of the sub-grids contained by the group.
	 * If a shape of the group is not an axe, null is added.
	 * to the list. The list cannot be null.
	 * @since 3.0
	 */
	List<Double> getSubGridWidthList();

	/**
	 * Sets the width of the sub-grids contained by the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 * @since 3.0
	 */
	void setSubGridWidthList(final List<Double> values);

	/**
	 * @return The number of dots composing the main lines of each grids contained by the group.
	 * If a shape of the group is not an axe, null is added.
	 * to the list. The list cannot be null.
	 * @since 3.0
	 */
	List<Integer> getGridDotsList();

	/**
	 * Sets the number of dots composing the main lines of each grids contained by the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 * @since 3.0
	 */
	void setGridDotsList(final List<Integer> values);

	/**
	 * @return The number of dots composing the sub-lines of each grids contained by the group.
	 * If a shape of the group is not an axe, null is added.
	 * to the list. The list cannot be null.
	 * @since 3.0
	 */
	List<Integer> getSubGridDotsList();

	/**
	 * Sets the number of dots composing the sub-lines of each grids contained by the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 * @since 3.0
	 */
	void setSubGridDotsList(final List<Integer> values);

	/**
	 * @return The division of the sub-lines of each grids contained by the group.
	 * If a shape of the group is not an axe, null is added.
	 * to the list. The list cannot be null.
	 * @since 3.0
	 */
	List<Integer> getSubGridDivList();

	/**
	 * Sets the division of the sub-lines of each grids contained by the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 * @since 3.0
	 */
	void setSubGridDivList(final List<Integer> values);

	/**
	 * @return The types of the freehand shapes contained in the group.
	 * If a shape of the group is not an axe, null is added.
	 * to the list. The list cannot be null.
	 * @since 3.0
	 */
	List<FreeHandStyle> getFreeHandTypeList();

	/**
	 * Sets the type of the freehand shapes contained in the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 * @since 3.0
	 */
	void setFreeHandTypeList(final List<FreeHandStyle> values);

	/**
	 * @return The intervals of the freehand shapes contained in the group.
	 * If a shape of the group is not an axe, null is added.
	 * to the list. The list cannot be null.
	 * @since 3.0
	 */
	List<Integer> getFreeHandIntervalList();

	/**
	 * Sets the interval of the freehand shapes contained in the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 * @since 3.0
	 */
	void setFreeHandIntervalList(final List<Integer> values);

	/**
	 * @return The boolean value defining whether the shapes contained in the group are opened.
	 * If a shape of the group is not an axe, null is added.
	 * to the list. The list cannot be null.
	 * @since 3.0
	 */
	List<Boolean> getOpenList();

	/**
	 * Defines whether the shapes contained in the group are opened.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 * @since 3.0
	 */
	void setOpenList(final List<Boolean> values);

	/**
	 * @return The boolean value defining if the shapes contained in the group must show their points.
	 * If a shape of the group cannot show their points, null is added.
	 * to the list. The list cannot be null.
	 * @since 3.0
	 */
	List<Boolean> getShowPointsList();

	/**
	 * Defines if the shapes contained in the group must show their points.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 * @since 3.0
	 */
	void setShowPointsList(final List<Boolean> values);

	/**
	 * @return The tbarsizedim values of the shapes of the group.
	 * @since 3.1
	 */
	List<Double> getTBarSizeDimList();

	/**
	 * Sets the tbarsizedim parameters to the shapes of the group.
	 * @param values The values to use.
	 * @since 3.1
	 */
	void setTBarSizeDimList(List<Double> values);

	/**
	 * @return The tbarsizenum values of the shapes of the group.
	 * @since 3.1
	 */
	List<Double> getTBarSizeNumList();

	/**
	 * Sets the tbarsizenum parameters to the shapes of the group.
	 * @param values The values to use.
	 * @since 3.1
	 */
	void setTBarSizeNumList(List<Double> values);

	/**
	 * @return The dotsizenum values of the shapes of the group.
	 * @since 3.1
	 */
	List<Double> getDotSizeNumList();

	/**
	 * Sets the dotsizenum parameters to the shapes of the group.
	 * @param values The values to use.
	 * @since 3.1
	 */
	void setDotSizeNumList(List<Double> values);

	/**
	 * @return The dotsizedim values of the shapes of the group.
	 * @since 3.1
	 */
	List<Double> getDotSizeDimList();

	/**
	 * Sets the dotsizedim parameters to the shapes of the group.
	 * @param values The values to use.
	 * @since 3.1
	 */
	void setDotSizeDimList(List<Double> values);

	/**
	 * @return The bracketNum values of the shapes of the group.
	 * @since 3.1
	 */
	List<Double> getBracketNumList();

	/**
	 * Sets the bracketNum parameters to the shapes of the group.
	 * @param values The values to use.
	 * @since 3.1
	 */
	void setBracketNumList(List<Double> values);

	/**
	 * @return The rbracketNum values of the shapes of the group.
	 * @since 3.1
	 */
	List<Double> getRBracketNumList();

	/**
	 * Sets the rbracketNum parameters to the shapes of the group.
	 * @param values The values to use.
	 * @since 3.1
	 */
	void setRBracketNumList(List<Double> values);

	/**
	 * @return The arrowsizenum values of the shapes of the group.
	 * @since 3.1
	 */
	List<Double> getArrowSizeNumList();

	/**
	 * Sets the arrowsizenum parameters to the shapes of the group.
	 * @param values The values to use.
	 * @since 3.1
	 */
	void setArrowSizeNumList(List<Double> values);

	/**
	 * @return The arrowsizedim values of the shapes of the group.
	 * @since 3.1
	 */
	List<Double> getArrowSizeDimList();

	/**
	 * Sets the arrowsizedim parameters to the shapes of the group.
	 * @param values The values to use.
	 * @since 3.1
	 */
	void setArrowSizeDimList(List<Double> values);

	/**
	 * @return The arrowLength values of the shapes of the group.
	 * @since 3.1
	 */
	List<Double> getArrowLengthList();

	/**
	 * Sets the arrowLength parameters to the shapes of the group.
	 * @param values The values to use.
	 * @since 3.1
	 */
	void setArrowLengthList(List<Double> values);

	/**
	 * @return The arrowInset values of the shapes of the group.
	 * @since 3.1
	 */
	List<Double> getArrowInsetList();

	/**
	 * Sets the arrowInset parameters to the shapes of the group.
	 * @param values The values to use.
	 * @since 3.1
	 */
	void setArrowInsetList(List<Double> values);
}
