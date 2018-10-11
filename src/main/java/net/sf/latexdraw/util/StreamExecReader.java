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
package net.sf.latexdraw.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;
import net.sf.latexdraw.badaboom.BadaboomCollector;

/**
 * A thread for managing command execution. While the process is running, the log is gathered from it.
 * @author Arnaud BLOUIN
 */
public class StreamExecReader extends Thread {
	/** The stream to listen. */
	private final InputStream stream;

	/** The read log. */
	private StringBuilder log;


	/**
	 * Default constructor.
	 * @param is The stream to listen.
	 */
	public StreamExecReader(final InputStream is) {
		super();
		stream = Objects.requireNonNull(is);
	}


	@Override
	public void run() {
		try {
			try(final InputStreamReader isr = new InputStreamReader(stream);
				final BufferedReader br = new BufferedReader(isr)) {
				log = new StringBuilder();
				String line = br.readLine();

				while(line != null) {
					log.append(line).append(LSystem.EOL);
					line = br.readLine();
				}
			}
		}catch(final IOException ex) {
			BadaboomCollector.INSTANCE.add(ex);
		}
	}


	/**
	 * @return The read log.
	 */
	public String getLog() {
		return log == null ? "" : log.toString(); //NON-NLS
	}
}
