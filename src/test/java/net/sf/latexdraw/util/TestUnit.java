package net.sf.latexdraw.util;

import net.sf.latexdraw.service.PreferencesService;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.core.IsNot.not;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestUnit {
	@ParameterizedTest
	@EnumSource(Unit.class)
	public void testGetUnit(final Unit unit) {
		assertEquals(unit, Unit.getUnit(unit.name()));
	}

	@ParameterizedTest
	@EnumSource(Unit.class)
	public void testgetLabel(final Unit unit) {
		assertThat(unit.getLabel(new PreferencesService().getBundle()), not(is(emptyOrNullString())));
	}

	@ParameterizedTest
	@EnumSource(Unit.class)
	public void testGetUnitNULL() {
		assertEquals(Unit.CM, Unit.getUnit(null));
	}

	@ParameterizedTest
	@EnumSource(Unit.class)
	public void testGetUnitKO() {
		assertEquals(Unit.CM, Unit.getUnit("bad"));
	}
}
