package net.sf.latexdraw.instruments;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import javafx.util.Callback;
import net.sf.latexdraw.LaTeXDraw;
import net.sf.latexdraw.badaboom.BadaboomCollector;
import net.sf.latexdraw.util.Injector;
import net.sf.latexdraw.util.LangTool;
import net.sf.latexdraw.util.Page;
import net.sf.latexdraw.view.jfx.Canvas;
import net.sf.latexdraw.view.latex.DviPsColors;
import org.junit.After;
import org.junit.Test;
import org.malai.command.CommandsRegistry;
import org.malai.instrument.Instrument;
import org.malai.undo.UndoCollector;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;

import static org.junit.Assert.assertTrue;

public abstract class TestLatexdrawGUI extends ApplicationTest {
	protected Callback<Class<?>, Object> injectorFactory;

	protected final GUIVoidCommand waitFXEvents = WaitForAsyncUtils::waitForFxEvents;

	protected TitledPane titledPane;
	protected Stage stage;
	Injector injector;

	@After
	public void tearDown() throws TimeoutException {
		stage.minHeightProperty().unbind();
		stage.minWidthProperty().unbind();

		injector.getInstances().stream().filter(obj -> obj instanceof Instrument<?>).forEach(ins -> ((Instrument<?>) ins).uninstallBindings());
		injector.clear();
		stage = null;

		CommandsRegistry.INSTANCE.clear();
		CommandsRegistry.INSTANCE.removeAllHandlers();
		BadaboomCollector.INSTANCE.clear();
		UndoCollector.INSTANCE.clear();
		DviPsColors.INSTANCE.clearUserColours();
	}

	@Override
	public void start(final Stage aStage) {
		Canvas.setMargins(20);
		Canvas.setDefaultPage(Page.HORIZONTAL);
		stage = aStage;

		try {
			injector = createInjector();
			injectorFactory = injector::getInstance;
			final Parent root = FXMLLoader.load(LaTeXDraw.class.getResource(getFXMLPathFromLatexdraw()), LangTool.INSTANCE.getBundle(),
				new LatexdrawBuilderFactory(injector), injectorFactory);

			Parent parent = root;


			// If the root is not a pane, its content may not be visible.
			// So, need to add a fictive pane to contain the widgets.
			if(root instanceof Pane) {
				parent = root;
			}else {
				// TitledPane leads to flaky tests with TestFX. So, replacing the TitlePane with a classical pane.
				if(parent instanceof TitledPane) {
					titledPane = (TitledPane) parent;
					final Node content = ((TitledPane) parent).getContent();
					if(content instanceof Parent) {
						parent = (Parent) content;
					}else {
						final BorderPane pane = new BorderPane();
						pane.setCenter(content);
						parent = pane;
					}
				}
			}

			final Scene scene = new Scene(parent);
			aStage.setScene(scene);
			aStage.show();
			aStage.toFront();
			if(root instanceof Region) {
				aStage.minHeightProperty().bind(((Region)root).heightProperty());
				aStage.minWidthProperty().bind(((Region)root).widthProperty());
			}
			aStage.sizeToScene();
		}catch(final IOException ex) {
			ex.printStackTrace();
		}
	}

	public <T extends Node> T find(final String query) {
		return lookup(query).query();
	}

	protected abstract String getFXMLPathFromLatexdraw();

	protected Injector createInjector() {
		return new Injector() {
			@Override
			protected void configure() {
			}
		};
	}

	@Test
	public void testLaunchNoCrash() {
		assertTrue(BadaboomCollector.INSTANCE.isEmpty());
	}
}
