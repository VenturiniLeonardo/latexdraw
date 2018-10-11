package net.sf.latexdraw.models.impl;

import java.util.Arrays;
import java.util.Collections;
import net.sf.latexdraw.HelperTest;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.IBezierCurve;
import net.sf.latexdraw.models.interfaces.shape.ICircle;
import net.sf.latexdraw.models.interfaces.shape.IControlPointShape;
import net.sf.latexdraw.models.interfaces.shape.IModifiablePointsShape;
import net.sf.latexdraw.models.interfaces.shape.IRectangle;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(Theories.class)
public class TestIBezierCurve implements HelperTest {
	IBezierCurve shape;

	@Before
	public void setUp() {
		shape = ShapeFactory.INST.createBezierCurve(Collections.emptyList());
	}

	@Test
	public void testIsTypeOf() {
		assertFalse(shape.isTypeOf(null));
		assertFalse(shape.isTypeOf(IRectangle.class));
		assertFalse(shape.isTypeOf(ICircle.class));
		assertTrue(shape.isTypeOf(IShape.class));
		assertTrue(shape.isTypeOf(IModifiablePointsShape.class));
		assertTrue(shape.isTypeOf(IControlPointShape.class));
		assertTrue(shape.isTypeOf(IBezierCurve.class));
		assertTrue(shape.isTypeOf(shape.getClass()));
	}

	@Test
	public void testConstructors() {
		assertEquals(0, shape.getPoints().size());
		assertEquals(0, shape.getFirstCtrlPts().size());
		assertEquals(0, shape.getSecondCtrlPts().size());
		assertEquals(2, shape.getNbArrows());
	}

	@Test
	public void testConstructors2() {
		final IBezierCurve curve = ShapeFactory.INST.createBezierCurve(Arrays.asList(
			ShapeFactory.INST.createPoint(100, 200), ShapeFactory.INST.createPoint(300, 400)));

		assertEquals(2, curve.getPoints().size());
		assertEqualsDouble(100., curve.getPoints().get(0).getX());
		assertEqualsDouble(200., curve.getPoints().get(0).getY());
		assertEqualsDouble(300., curve.getPoints().get(1).getX());
		assertEqualsDouble(400., curve.getPoints().get(1).getY());
		assertEquals(2, curve.getFirstCtrlPts().size());
		assertEquals(2, curve.getSecondCtrlPts().size());
		assertEquals(2, curve.getNbArrows());
	}

	@Theory
	public void testSetIsOpened(final boolean value) {
		shape.setOpened(value);
		assertEquals(value, shape.isOpened());
	}

	@Test
	public void testCopy() {
		final IBezierCurve sh2 = ShapeFactory.INST.createBezierCurve(Collections.emptyList());
		shape.setOpened(sh2.isOpened());
		sh2.setOpened(!sh2.isOpened());
		shape.copy(sh2);

		assertEquals(shape.isOpened(), sh2.isOpened());
	}

	@Test
	public void testDuplicate() {
		shape.setOpened(!shape.isOpened());
		final IBezierCurve dup = shape.duplicate();
		assertEquals(shape.isOpened(), dup.isOpened());
	}

}
