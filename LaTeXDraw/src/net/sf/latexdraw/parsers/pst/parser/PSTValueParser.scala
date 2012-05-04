package net.sf.latexdraw.parsers.pst.parser

import java.awt.Color
import net.sf.latexdraw.glib.models.interfaces.IAxes
import net.sf.latexdraw.glib.views.latex.DviPsColors
import net.sf.latexdraw.glib.models.interfaces.IShape
import net.sf.latexdraw.glib.views.pst.PSTricksConstants
import net.sf.latexdraw.glib.models.interfaces.IArrow

/**
 * A parser that parses a value corresponding to an object.
 * An object can be a name or a command.<br>
 *<br>
 * This file is part of LaTeXDraw<br>
 * Copyright (c) 2005-2012 Arnaud BLOUIN<br>
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
 * 2012-04-26<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 */
trait PSTValueParser extends PSTNumberParser {
	/** The regex expression of an identifier. */
	val identPattern = """(\w)+""".r
	/** The regex expression of PST command name. */
	val cmdPattern = """(\\\\)ps\w+""".r
	/** The regex expression of PST arrows. */
	val arrowPattern = """([\]\[\)\(\*\|<>ocC]{0,2})[ ]?-[ ]?([\]\[\)\(\*\|<>ocC]{0,2})""".r


	/**
	 * Parses the value of the curvature parameter.
	 */
	def parseValueCurvature(value : String) : Option[Tuple3[Double, Double, Double]] = {
		value.split(" ") match {
			case Array(val1, val2, val3) =>
				try { Some(Tuple3(val1.toDouble, val2.toDouble, val3.toDouble)) }
				catch{case _ => None}
			case _ => None
		}
	}



	/**
	 * Parses arrows.
	 */
	def parseValueArrows(value : String) : Option[Tuple2[IArrow.ArrowStyle, IArrow.ArrowStyle]] =
		value match {
			// The arrow string must contains the separator - and at least one arrow.
			case arrowPattern(arr1, arr2) => Some(Tuple2(IArrow.ArrowStyle.getArrowStyle(arr1), IArrow.ArrowStyle.getArrowStyle(arr2)))
			case _ => None
		}



	def setArrows(sh : IShape, arrowsRaw : Option[String], invert : Boolean) {
		arrowsRaw match {
			case Some(value) =>
				val arrows = parseValueArrows(value)
				if(arrows.isDefined) {
					if(invert) {
						sh.setArrowStyle(arrows.get._2, 0)
						sh.setArrowStyle(arrows.get._1, 1)
					}else {
						sh.setArrowStyle(arrows.get._1, 0)
						sh.setArrowStyle(arrows.get._2, 1)
					}
				}
			case None =>
		}
	}


	/**
	 * Parses the cornersize value and returns true if the corner is relative, false
	 * is absolute and None is the value is not correct.
	 */
	def parseValueCornersize(value : String) : Option[Boolean] =
		value match {
			case PSTricksConstants.TOKEN_RELATIVE => Some(true)
			case PSTricksConstants.TOKEN_ABSOLUTE => Some(false)
			case _ => None
		}


	/**
	 * Parses the line styles.
	 */
	def parseValueDimen(value : String) : Option[IShape.BorderPos] =
		value match {
			case PSTricksConstants.BORDERS_INSIDE => Some(IShape.BorderPos.INTO)
			case PSTricksConstants.BORDERS_MIDDLE => Some(IShape.BorderPos.MID)
			case PSTricksConstants.BORDERS_OUTSIDE=> Some(IShape.BorderPos.OUT)
			case _ => PSTParser.errorLogs += "Unknown border position: " + value; None
		}


	/**
	 * Parses the line styles.
	 */
	def parseValueLineStyle(value : String) : Option[IShape.LineStyle] =
		value match {
			case PSTricksConstants.LINE_DASHED_STYLE => Some(IShape.LineStyle.DASHED)
			case PSTricksConstants.LINE_DOTTED_STYLE => Some(IShape.LineStyle.DOTTED)
			case PSTricksConstants.LINE_SOLID_STYLE => Some(IShape.LineStyle.SOLID)
			case PSTricksConstants.LINE_NONE_STYLE =>
				PSTParser.errorLogs += "line style '"+PSTricksConstants.LINE_NONE_STYLE+"' not supported yet"
				None
			case _ => PSTParser.errorLogs += "Unknown line style: " + value; None
		}


	/**
	 * Parses the filling styles.
	 */
	def parseValueFillingStyle(value : String) : Option[IShape.FillingStyle] =
		value match {
			case "plain" => Some(IShape.FillingStyle.PLAIN)
			case "none" => Some(IShape.FillingStyle.NONE)
			case "vlines" => Some(IShape.FillingStyle.VLINES)
			case "vlines*" => Some(IShape.FillingStyle.VLINES_PLAIN)
			case "hlines" => Some(IShape.FillingStyle.HLINES)
			case "hlines*" => Some(IShape.FillingStyle.HLINES_PLAIN)
			case "clines" => Some(IShape.FillingStyle.CLINES)
			case "clines*" => Some(IShape.FillingStyle.CLINES_PLAIN)
			case "gradient" => Some(IShape.FillingStyle.GRAD)
			case _ => PSTParser.errorLogs += "Unknown filling style: " + value; None
		}


	/** This parser parses a object value that can be either a name nor a command. */
	def parseValueColour(value : String, ctx : PSTContext) : Option[Color] =
		try {
			value match {
				case cmdPattern(_) =>
					ctx.getParam(value.replace("\\\\ps", "")) match {
						case col : Color => Some(col)
						case _ => None
					}
				case identPattern(_) =>
					DviPsColors.INSTANCE.getColour(value) match {
						case col : Color => Some(col)
						case _ => PSTParser.errorLogs += "The following colour is unknown: " + value; None
					}
				case _ => None
			}
		}catch{case ex => None }


	/**
	 * Parses a boolean value.
	 */
	def parseValueBoolean(value : String) : Option[Boolean] =
		value match {
			case "true" => Some(true)
			case "false" => Some(false)
			case _ => None
		}
}