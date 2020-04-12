package net.sf.latexdraw.command;

import io.github.interacto.jfx.test.CommandTest;
import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import net.sf.latexdraw.LatexdrawExtension;
import net.sf.latexdraw.util.BadaboomCollector;
import net.sf.latexdraw.view.jfx.Canvas;
import net.sf.latexdraw.view.pst.PSTCodeGenerator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.testfx.framework.junit5.ApplicationExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Test class for the command Export. Generated by Interacto test-gen.
 */
@Tag("command")
@ExtendWith(LatexdrawExtension.class)
@ExtendWith(ApplicationExtension.class)
class ExportTest extends CommandTest<Export> {
	ExportFormat format;
	Canvas canvas;
	FileChooser dialogueBox;
	PSTCodeGenerator pstGen;
	File file;
	File psFile;
	Group views;

	@Override
	protected Stream<Runnable> canDoFixtures() {
		return Stream.concat(Stream.of(
			() -> cmd = new Export(canvas, pstGen, ExportFormat.PDF, dialogueBox),
			() -> {
				Mockito.when(dialogueBox.showSaveDialog(Mockito.any())).thenReturn(file);
				Mockito.when(pstGen.createPDFFile(Mockito.anyString())).thenReturn(Optional.empty());
				cmd = new Export(canvas, pstGen, ExportFormat.PDF, dialogueBox);
			}, () -> {
				Mockito.when(dialogueBox.showSaveDialog(Mockito.any())).thenReturn(file);
				Mockito.when(pstGen.createEPSFile(Mockito.anyString())).thenReturn(Optional.empty());
				cmd = new Export(canvas, pstGen, ExportFormat.EPS_LATEX, dialogueBox);
			}, () -> {
				Mockito.when(dialogueBox.showSaveDialog(Mockito.any())).thenReturn(file);
				Mockito.when(pstGen.createEPSFile(Mockito.anyString())).thenReturn(Optional.of(new File("eifdjfd__hsi")));
				cmd = new Export(canvas, pstGen, ExportFormat.EPS_LATEX, dialogueBox);
			}, () -> {
				Mockito.when(file.getName()).thenReturn("foo" + ExportFormat.EPS_LATEX.getFileExtension());
				Mockito.when(dialogueBox.showSaveDialog(Mockito.any())).thenReturn(file);
				Mockito.when(pstGen.createEPSFile(Mockito.anyString())).thenReturn(Optional.of(file));
				cmd = new Export(canvas, pstGen, ExportFormat.EPS_LATEX, dialogueBox);
			}, () -> {
				Mockito.when(dialogueBox.showSaveDialog(Mockito.any())).thenReturn(file);
				Mockito.when(pstGen.createPDFFile(Mockito.anyString())).thenReturn(Optional.of(new File("eifdjfdhsizr__u")));
				cmd = new Export(canvas, pstGen, ExportFormat.PDF, dialogueBox);
			}, () -> {
				Mockito.when(dialogueBox.showSaveDialog(Mockito.any())).thenReturn(file);
				Mockito.when(pstGen.createPDFFile(Mockito.anyString())).thenThrow(SecurityException.class);
				cmd = new Export(canvas, pstGen, ExportFormat.PDF, dialogueBox);
			}, () -> {
				Mockito.when(dialogueBox.showSaveDialog(Mockito.any())).thenReturn(file);
				Mockito.when(pstGen.createEPSFile(Mockito.any())).thenThrow(SecurityException.class);
				cmd = new Export(canvas, pstGen, ExportFormat.EPS_LATEX, dialogueBox);
			}, () -> {
				Mockito.when(dialogueBox.showSaveDialog(Mockito.any())).thenReturn(new File(System.getProperty("java.io.tmpdir")));
				cmd = new Export(canvas, pstGen, ExportFormat.TEX, dialogueBox);
			}, () -> {
				Mockito.when(dialogueBox.showSaveDialog(Mockito.any())).thenReturn(new File(System.getProperty("java.io.tmpdir")));
				cmd = new Export(canvas, pstGen, ExportFormat.PNG, dialogueBox);
			}),
			Stream.of(ExportFormat.values()).map(f -> () -> {
				try {
					final File tempFile = File.createTempFile("foo", "");
					tempFile.deleteOnExit();
					Mockito.when(dialogueBox.showSaveDialog(Mockito.any())).thenReturn(tempFile);
					cmd = new Export(canvas, pstGen, f, dialogueBox);
					assertThat(cmd.hadEffect()).isFalse();
				}catch(final IOException ex) {
					fail();
				}
			}));
	}

	@Override
	protected void commonCanDoFixture() {
		BadaboomCollector.INSTANCE.clear();
		format = ExportFormat.BMP;
		canvas = Mockito.mock(Canvas.class);
		pstGen = Mockito.mock(PSTCodeGenerator.class);
		dialogueBox = Mockito.mock(FileChooser.class);
		views = new Group(new Rectangle(1, 2, 3, 4));
		Mockito.when(canvas.getScene()).thenReturn(Mockito.mock(Scene.class));
		Mockito.when(canvas.getViews()).thenReturn(views);
		file = Mockito.mock(File.class);
		Mockito.when(file.getName()).thenReturn("foo");
		psFile = Mockito.mock(File.class);
		Mockito.when(psFile.exists()).thenReturn(true);


		Mockito.when(pstGen.createPSFile(Mockito.any())).thenReturn(Optional.of(psFile));
		Mockito.when(pstGen.createEPSFile(Mockito.any())).thenReturn(Optional.of(psFile));
		Mockito.when(pstGen.createPDFFile(Mockito.any())).thenReturn(Optional.of(psFile));

	}

	@Override
	protected Stream<Runnable> doCheckers() {
		return Stream.concat(Stream.of(
			() -> assertThat(cmd.hadEffect()).isFalse(),
			() -> assertThat(cmd.hadEffect()).isFalse(),
			() -> assertThat(cmd.hadEffect()).isFalse(),
			() -> assertThat(cmd.hadEffect()).isFalse(),
			() -> assertThat(cmd.hadEffect()).isTrue(),
			() -> assertThat(cmd.hadEffect()).isFalse(),
			() -> {
				assertThat(cmd.hadEffect()).isFalse();
				assertThat(BadaboomCollector.INSTANCE.errorsProperty().size()).isEqualTo(1);
			},
			() -> {
				assertThat(cmd.hadEffect()).isFalse();
				assertThat(BadaboomCollector.INSTANCE.errorsProperty().size()).isEqualTo(1);
			},
			() -> {
				assertThat(cmd.hadEffect()).isFalse();
				assertThat(BadaboomCollector.INSTANCE.errorsProperty().size()).isEqualTo(1);
			},
			() -> {
				assertThat(cmd.hadEffect()).isFalse();
				assertThat(BadaboomCollector.INSTANCE.errorsProperty().size()).isEqualTo(1);
			}),
			Stream.of(ExportFormat.values()).map(f -> () -> assertThat(cmd.hadEffect()).isTrue()));
	}

	@Override
	@ParameterizedTest
	@MethodSource({"doProvider"})
	protected void testDo(final Runnable fixture, final Runnable oracle) {
		final CountDownLatch latch = new CountDownLatch(1);
		Platform.runLater(() -> {
			super.testDo(fixture, oracle);
			latch.countDown();
		});

		try {
			latch.await(10, TimeUnit.SECONDS);
		}catch(final InterruptedException ignore) {
			fail();
		}
	}

	@AfterEach
	void tearDown() {
		BadaboomCollector.INSTANCE.clear();
	}
}
