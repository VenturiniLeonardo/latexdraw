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

package net.sf.latexdraw.parsers.pst.parser

import net.sf.latexdraw.view.latex.DviPsColors

/**
 * A parser parsing psframebox commands.
 * @author Arnaud BLOUIN
 */
trait PSFrameboxParser extends PSTAbstractParser with PSTBracketBlockParser {
	def parsePsFrameboxCmds(ctx:PSTContext) : Parser[Unit] =
		("\\psframebox" | "\\psframebox*" | "\\psdblframebox" | "\\psdblframebox*" | "\\psshadowbox" | "\\psshadowbox*" |
		"\\pscirclebox" | "\\pscirclebox*" | "\\psovalbox" | "\\psovalbox*" | "\\psdiabox" | "\\psdiabox*"  | "\\pstribox" | "\\pstribox*") ~
		opt(parseSquaredBracket(ctx)) ~ parseBracket(ctx) ^^ {
		case nameCmd ~ param ~ block =>
				val colStr = "color"
				val paramStr = param match {
					case Some(p) =>
						// Searching into the parameters a user colour.
						// These colours must be included into the parsed text.
						p.split(",").filter(s=>s.contains(colStr)).map(s=>s.split("=")).filter(s=>s.length==2).map(s=>s(1)).foreach{
							col => DviPsColors.INSTANCE.getUsercolourCode(col) match {
								case "" =>
								// The colour must not be already included.
								case str if !ctx.textParsed.contains(str) => ctx.textParsed += str
								case _ =>
							}
						}
						"[" + param.get + "]"
					case _ => ""
				}
			ctx.textParsed += nameCmd + paramStr + "{" + block + "}"
			ctx.parsedTxtNoTxt = false
	}
}
