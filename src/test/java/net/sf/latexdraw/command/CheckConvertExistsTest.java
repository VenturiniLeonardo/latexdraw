package net.sf.latexdraw.command;

import io.github.interacto.command.Command;
import io.github.interacto.jfx.test.CommandTest;
import java.util.stream.Stream;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import net.sf.latexdraw.util.SystemUtils;
import net.sf.latexdraw.util.Tuple;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.testfx.framework.junit5.ApplicationExtension;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test class for the command CheckConvertExists. Generated by Interacto test-gen.
 */
@Tag("command")
@ExtendWith(ApplicationExtension.class)
class CheckConvertExistsTest extends CommandTest<CheckConvertExists> {
	Label statusLabel;
	Hyperlink link;
	SystemUtils system;

	@BeforeEach
	void setUp() {
		system = SystemUtils.getInstance();
	}

	@Override
	protected Stream<Runnable> canDoFixtures() {
		return Stream.of(() -> {
			commonFixture();
			cmd = new CheckConvertExists(statusLabel, link);
		}, () -> {
			commonFixture();
			final SystemUtils mockSys = Mockito.mock(SystemUtils.class);
			Mockito.when(mockSys.execute(Mockito.any(), Mockito.any())).thenReturn(new Tuple<>(false, ""));
			SystemUtils.setSingleton(mockSys);
			cmd = new CheckConvertExists(statusLabel, link);
		});
	}

	@Override
	protected Stream<Runnable> doCheckers() {
		return Stream.of(() -> {
			assertThat(link.getText().isEmpty()).isTrue();
			assertThat(statusLabel.getText().isEmpty()).isTrue();
		}, () -> {
			assertThat(link.isVisible()).isTrue();
			assertThat(link.getText().isEmpty()).isFalse();
			assertThat(statusLabel.getText().isEmpty()).isFalse();
		});
	}

	private void commonFixture() {
		statusLabel = new Label();
		link = new Hyperlink();
		statusLabel.setVisible(false);
	}

	@ParameterizedTest
	@MethodSource({"canDoFixtures"})
	protected void testRegistration(final Runnable fixture) {
		fixture.run();
		assertThat(cmd.getRegistrationPolicy()).isEqualTo(Command.RegistrationPolicy.NONE);
	}

	@AfterEach
	void tearDown() {
		SystemUtils.setSingleton(system);
	}
}
