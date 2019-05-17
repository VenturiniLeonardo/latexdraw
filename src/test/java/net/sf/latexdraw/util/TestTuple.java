package net.sf.latexdraw.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestTuple {
	Tuple<Double, String> tuple;

	@BeforeEach
	void setUp() {
		tuple = new Tuple<>(2.2, "foo");
	}

	@Test
	void testA() {
		assertEquals(2.2, tuple.a, 0.00001);
	}

	@Test
	void testB() {
		assertEquals("foo", tuple.b);
	}

	@Test
	void testToString() {
		assertThat(tuple.toString()).isNotEmpty();
	}
}
