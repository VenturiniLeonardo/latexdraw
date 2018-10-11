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
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import net.sf.latexdraw.commands.DrawingCmdImpl;
import net.sf.latexdraw.commands.Modifying;
import net.sf.latexdraw.commands.ShapesCmd;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.IGroup;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import net.sf.latexdraw.util.LangTool;
import org.malai.undo.Undoable;

/**
 * This command joins shapes.
 * @author Arnaud Blouin
 */
public class JoinShapes extends DrawingCmdImpl implements ShapesCmd, Undoable, Modifying {
	/** The added group of shapes. */
	final IGroup addedGroup;

	/** The shapes to handle. */
	final List<IShape> shapes;

	public JoinShapes() {
		super();
		addedGroup = ShapeFactory.INST.createGroup();
		shapes = new ArrayList<>();
	}

	@Override
	protected void doCmdBody() {
		joinShapes();
	}

	@Override
	public boolean canDo() {
		return super.canDo() && !shapes.isEmpty();
	}

	private void joinShapes() {
		final List<IShape> drawingSh = drawing.getShapes();
		shapes.stream().sorted((s1, s2) -> drawingSh.indexOf(s1) < drawingSh.indexOf(s2) ? -1 : 1).forEach(sh -> {
			drawing.removeShape(sh);
			addedGroup.addShape(sh);
		});

		drawing.addShape(addedGroup);
		drawing.setModified(true);
	}

	@Override
	public void undo() {
		final List<IShape> drawingSh = drawing.getShapes();
		final Map<IShape, Integer> map = shapes.stream().collect(Collectors.toMap(sh -> sh, drawingSh::indexOf));

		drawing.removeShape(addedGroup);
		addedGroup.getShapes().forEach(sh -> drawing.addShape(sh, map.get(sh)));
		addedGroup.clear();
		drawing.setModified(true);
	}

	@Override
	public void redo() {
		joinShapes();
	}

	@Override
	public String getUndoName() {
		return LangTool.INSTANCE.getBundle().getString("UndoRedoManager.join");
	}

	@Override
	public RegistrationPolicy getRegistrationPolicy() {
		return RegistrationPolicy.LIMITED;
	}

	@Override
	public List<IShape> getShapes() {
		return shapes;
	}
}
