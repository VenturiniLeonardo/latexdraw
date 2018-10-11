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
package net.sf.latexdraw.commands.shape;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.sf.latexdraw.commands.DrawingCmdImpl;
import net.sf.latexdraw.commands.Modifying;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.IDrawing;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import net.sf.latexdraw.util.LangTool;
import net.sf.latexdraw.view.MagneticGrid;
import org.malai.command.Command;
import org.malai.undo.Undoable;

/**
 * This command pastes the copied or cut shapes.
 * @author Arnaud Blouin
 */
public class PasteShapes extends DrawingCmdImpl implements Undoable, Modifying {
	/** The cut or copy command. */
	private final CopyShapes copy;
	/** The magnetic grid to use. */
	private final MagneticGrid grid;
	private final List<IShape> pastedShapes;

	public PasteShapes(final CopyShapes copyCmd, final MagneticGrid magnetGrid, final IDrawing drawing) {
		super(drawing);
		copy = copyCmd;
		grid = magnetGrid;
		pastedShapes = new ArrayList<>();
	}

	@Override
	public boolean canDo() {
		return super.canDo() && copy != null && grid != null;
	}

	@Override
	public RegistrationPolicy getRegistrationPolicy() {
		return RegistrationPolicy.LIMITED;
	}

	@Override
	public void doCmdBody() {
		// While pasting cut shapes, the first paste must be at the same position that the original shapes.
		// But for pasting after just copying, a initial gap must be used.
		if(!(copy instanceof CutShapes)) {
			copy.nbTimeCopied++;
		}

		final int gapPaste = grid.isMagnetic() ? grid.getGridSpacing() : 10;
		final int gap = copy.nbTimeCopied * gapPaste;

		copy.copiedShapes.forEach(shape -> {
			final IShape sh = ShapeFactory.INST.duplicate(shape);
			pastedShapes.add(sh);
			sh.translate(gap, gap);
			drawing.addShape(sh);
		});

		if(copy instanceof CutShapes) {
			copy.nbTimeCopied++;
		}

		drawing.setModified(true);
	}

	@Override
	public void undo() {
		int i = 0;
		final int nbShapes = copy.copiedShapes.size();

		while(i < nbShapes && !drawing.isEmpty()) {
			drawing.removeShape(drawing.size() - 1);
			i++;
		}

		copy.nbTimeCopied--;
		drawing.setModified(true);
	}

	@Override
	public void redo() {
		if(!(copy instanceof CutShapes)) {
			copy.nbTimeCopied++;
		}

		pastedShapes.forEach(drawing::addShape);

		if(copy instanceof CutShapes) {
			copy.nbTimeCopied++;
		}

		drawing.setModified(true);
	}

	@Override
	public String getUndoName() {
		return LangTool.INSTANCE.getBundle().getString("LaTeXDrawFrame.43");
	}

	@Override
	public List<Command> followingCmds() {
		if(drawing == null) {
			return Collections.emptyList();
		}

		final List<Command> list = new ArrayList<>();
		final SelectShapes selectCmd = new SelectShapes();
		selectCmd.setDrawing(drawing);
		pastedShapes.forEach(selectCmd::addShape);
		list.add(selectCmd);
		return list;
	}

	public CopyShapes getCopy() {
		return copy;
	}
}
