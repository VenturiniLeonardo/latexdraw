package net.sf.latexdraw.instruments;

import javafx.fxml.JavaFXBuilderFactory;
import javafx.util.Builder;
import javafx.util.BuilderFactory;
import net.sf.latexdraw.util.Injector;
import net.sf.latexdraw.view.jfx.Canvas;

class LatexdrawBuilderFactory implements BuilderFactory {
	private final Injector injector;
	private final BuilderFactory defaultFactory;

	LatexdrawBuilderFactory(final Injector inj) {
		super();
		this.injector = inj;
		defaultFactory = new JavaFXBuilderFactory();
	}

	@Override
	public Builder<?> getBuilder(final Class<?> type) {
		if(type == Canvas.class)
			return () -> injector.getInstance(type);
		return defaultFactory.getBuilder(type);
	}
}
