package net.sf.latexdraw;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public interface CollectionMatcher {
	default <E> void assertListEquals(final List<E> c1, final List<E> c2, final BiConsumer<E, E> matcher) {
		assertEquals(c1.size(), c2.size());
		for(int i = 0, size = c1.size(); i < size; i++) {
			matcher.accept(c1.get(i), c2.get(i));
		}
	}

	default <E, F> void assertAllEquals(final Stream<E> coll, final Function<E, F> matcher, final F expected) {
		if(expected instanceof Double) {
			coll.forEach(elt -> assertEquals((Double) expected, (Double) matcher.apply(elt), 0.00001));
		}else {
			coll.forEach(elt -> assertEquals(expected, matcher.apply(elt)));
		}
	}

	default <E> void assertAllNull(final Stream<E> coll, final Function<E, Object> matcher) {
		coll.forEach(elt -> assertNull(matcher.apply(elt)));
	}

	default <E> void assertAllFalse(final Stream<E> coll, final Function<E, Boolean> matcher) {
		coll.forEach(elt -> assertFalse(matcher.apply(elt)));
	}

	default <E> void assertAllTrue(final Stream<E> coll, final Function<E, Boolean> matcher) {
		coll.forEach(elt -> assertTrue(matcher.apply(elt)));
	}
}
