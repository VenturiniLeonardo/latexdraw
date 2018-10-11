package net.sf.latexdraw.parsers.pst;

import net.sf.latexdraw.models.interfaces.shape.DotStyle;
import net.sf.latexdraw.models.interfaces.shape.IDot;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import net.sf.latexdraw.view.latex.DviPsColors;
import net.sf.latexdraw.view.pst.PSTricksConstants;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assume.assumeThat;

@RunWith(Theories.class)
public class TestParsingPSdot extends TestPSTParser {
	@DataPoints
	public static String[] cmds() {
		return new String[]{"\\psdot", "\\psdots"};
	}

	@Theory
	public void testPssetunityunit(final String cmd) {
		parser("\\psset{unit=2,yunit=3}" + cmd + "(1,1)");
		final IDot dot = getShapeAt(0);
		assertEquals(2d * IShape.PPC, dot.getX(), 0.000001);
		assertEquals(-2d * 3d * IShape.PPC, dot.getY(), 0.000001);
	}

	@Theory
	public void testPssetunitxunit(final String cmd) {
		parser("\\psset{unit=2,xunit=3}" + cmd + "(1,1)");
		final IDot dot = getShapeAt(0);
		assertEquals(2d * 3d * IShape.PPC, dot.getX(), 0.000001);
		assertEquals(-2d * IShape.PPC, dot.getY(), 0.000001);
	}

	@Theory
	public void testPssetdotunitdot(final String cmd) {
		parser("\\psdot(1,1)\\psset{unit=2}" + cmd + "(1,1)");
		IDot dot = getShapeAt(1);
		assertEquals(2d * IShape.PPC, dot.getX(), 0.000001);
		assertEquals(-2d * IShape.PPC, dot.getY(), 0.000001);
		dot = getShapeAt(0);
		assertEquals(IShape.PPC, dot.getX(), 0.000001);
		assertEquals(-IShape.PPC, dot.getY(), 0.000001);
	}

	@Theory
	public void testPssetunit(final String cmd) {
		parser("\\psset{unit=2}" + cmd + "(1,1)");
		final IDot dot = getShapeAt(0);
		assertEquals(2d * IShape.PPC, dot.getX(), 0.000001);
		assertEquals(-2d * IShape.PPC, dot.getY(), 0.000001);
	}

	@Theory
	public void testDotAngle(final String cmd) {
		parser(cmd + "[dotangle=90](1,1)");
		final IDot dot = getShapeAt(0);
		assertEquals(Math.PI / 2d, dot.getRotationAngle(), 0.001);
	}

	@Theory
	public void testDotScale2num(final String cmd) {
		parser(cmd + "(1,1)" + cmd + "[dotscale=2 3](1,1)");
		final IDot dot1 = getShapeAt(0);
		final IDot dot2 = getShapeAt(1);
		assertEquals(dot1.getDiametre() * 2d, dot2.getDiametre(), 0.001);
	}

	@Theory
	public void testDotScale1num(final String cmd) {
		parser(cmd + "(1,1)" + cmd + "[dotscale=2](1,1)");
		final IDot dot1 = getShapeAt(0);
		final IDot dot2 = getShapeAt(1);
		assertEquals(dot1.getDiametre() * 2d, dot2.getDiametre(), 0.001);
	}

	@Theory
	public void testDotsizeNoUnit(final String cmd) {
		parser(cmd + "[dotsize=1.5 2](1,1)");
		final IDot dot = getShapeAt(0);
		assertEquals(1.5 * IShape.PPC + 2d * PSTricksConstants.DEFAULT_LINE_WIDTH * IShape.PPC, dot.getDiametre(), 0.001);
	}

	@Theory
	public void testDotsizeNoNum(final String cmd) {
		parser(cmd + "[dotsize=1.5](1,1)");
		final IDot dot = getShapeAt(0);
		assertEquals(1.5 * IShape.PPC, dot.getDiametre(), 0.001);
	}

	@Theory
	public void testDotsizeNoNumWithUnit(final String cmd) {
		parser(cmd + "[dotsize=15 mm](1,1)");
		final IDot dot = getShapeAt(0);
		assertEquals(1.5 * IShape.PPC, dot.getDiametre(), 0.001);
	}

	@Theory
	public void testDotsizeNoNumWithWhitespace(final String cmd) {
		parser(cmd + "[dotsize=15 mm](1,1)");
		final IDot dot = getShapeAt(0);
		assertEquals(1.5 * IShape.PPC, dot.getDiametre(), 0.001);
	}

	@Theory
	public void testDotsize(final String cmd) {
		parser(cmd + "[dotsize=1.5 cm 4](1,1)");
		final IDot dot = getShapeAt(0);
		assertEquals(1.5 * IShape.PPC + 4d * PSTricksConstants.DEFAULT_LINE_WIDTH * IShape.PPC, dot.getDiametre(), 0.001);
	}

	@Theory
	public void testDotStyleOK(final String cmd, final DotStyle style) {
		parser(cmd + "[dotstyle=" + style.getPSTToken() + "](1,1)");
		final IDot dot = getShapeAt(0);
		assertEquals(style, dot.getDotStyle());
	}

	@Theory
	public void testNoDotStyle(final String cmd) {
		assumeThat(cmd, equalTo("\\psdot"));
		parser(cmd);
		final IDot dot = getShapeAt(0);
		assertEquals(DotStyle.DOT, dot.getDotStyle());
	}

	@Theory
	public void testNoCoordinate(final String cmd) {
		assumeThat(cmd, equalTo("\\psdot"));
		parser(cmd);
		final IDot dot = getShapeAt(0);
		assertEquals(0d, dot.getPtAt(0).getX(), 0.0001);
		assertEquals(0d, dot.getPtAt(0).getY(), 0.0001);
	}

	@Theory
	public void testParse1Coordinates(final String cmd) {
		parser(cmd + "(5,10)");
		final IDot dot = getShapeAt(0);
		assertEquals(5d * IShape.PPC, dot.getPtAt(0).getX(), 0.0001);
		assertEquals(-10d * IShape.PPC, dot.getPtAt(0).getY(), 0.0001);
	}

	@Theory
	public void testCoordinatesPt(final String cmd) {
		parser(cmd + "(35pt,20pt)");
		final IDot dot = getShapeAt(0);
		assertEquals(35d * IShape.PPC / PSTricksConstants.CM_VAL_PT, dot.getPtAt(0).getX(), 0.0001);
		assertEquals(-20d * IShape.PPC / PSTricksConstants.CM_VAL_PT, dot.getPtAt(0).getY(), 0.0001);
	}

	@Theory
	public void testCoordinatesMm(final String cmd) {
		parser(cmd + "(350mm,200mm)");
		final IDot dot = getShapeAt(0);
		assertEquals(35d * IShape.PPC, dot.getPtAt(0).getX(), 0.0001);
		assertEquals(-20d * IShape.PPC, dot.getPtAt(0).getY(), 0.0001);
	}

	@Theory
	public void testCoordinatesInch(final String cmd) {
		parser(cmd + "(35in,20in)");
		final IDot dot = getShapeAt(0);
		assertEquals(35d * IShape.PPC / 2.54, dot.getPtAt(0).getX(), 0.0001);
		assertEquals(-20d * IShape.PPC / 2.54, dot.getPtAt(0).getY(), 0.0001);
	}

	@Theory
	public void testCoordinatesCm(final String cmd) {
		parser(cmd + "(35cm,20cm)");
		final IDot dot = getShapeAt(0);
		assertEquals(35d * IShape.PPC, dot.getPtAt(0).getX(), 0.0001);
		assertEquals(-20d * IShape.PPC, dot.getPtAt(0).getY(), 0.0001);
	}

	@Theory
	public void testFloatSigns(final String cmd) {
		parser(cmd + "(+++35.5,--50.5)");
		final IDot dot = getShapeAt(0);
		assertEquals(35.5 * IShape.PPC, dot.getPtAt(0).getX(), 0.0001);
		assertEquals(-50.5 * IShape.PPC, dot.getPtAt(0).getY(), 0.0001);
	}

	@Theory
	public void testStarLineColourIsFillingColour(final String cmd) {
		parser(cmd + "*[" + "linecolor=green, dotstyle=o](1,1)");
		final IDot dot = getShapeAt(0);
		assertEquals(DviPsColors.GREEN, dot.getFillingCol());
		assertEquals(DviPsColors.GREEN, dot.getLineColour());
	}

	@Theory
	public void testCoordinatesFloat2(final String cmd) {
		parser(cmd + "(35.5,50.5)");
		final IDot dot = getShapeAt(0);
		assertEquals(35.5 * IShape.PPC, dot.getPtAt(0).getX(), 0.0001);
		assertEquals(-50.5 * IShape.PPC, dot.getPtAt(0).getY(), 0.0001);
	}
}
