package net.sf.latexdraw.instruments.pencil;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import net.sf.latexdraw.instruments.CompositeGUIVoidCommand;
import net.sf.latexdraw.instruments.Hand;
import net.sf.latexdraw.instruments.MetaShapeCustomiser;
import net.sf.latexdraw.instruments.Pencil;
import net.sf.latexdraw.instruments.ShapeArcCustomiser;
import net.sf.latexdraw.instruments.ShapePropInjector;
import net.sf.latexdraw.instruments.TestArcStyleGUI;
import net.sf.latexdraw.instruments.TextSetter;
import net.sf.latexdraw.models.interfaces.shape.ArcStyle;
import net.sf.latexdraw.models.interfaces.shape.IArc;
import net.sf.latexdraw.util.Injector;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class TestPencilArcStyle extends TestArcStyleGUI {
	@Override
	protected Injector createInjector() {
		return new ShapePropInjector() {
			@Override
			protected void configure() throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
				super.configure();
				hand = mock(Hand.class);
				bindAsEagerSingleton(ShapeArcCustomiser.class);
				bindAsEagerSingleton(Pencil.class);
				bindToInstance(MetaShapeCustomiser.class, mock(MetaShapeCustomiser.class));
				bindToInstance(TextSetter.class, mock(TextSetter.class));
				bindToInstance(Hand.class, hand);
			}
		};
	}

	@Test
	public void testControllerActivatedWhenGoodPencilUsed() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesArc, updateIns, checkInsActivated).execute();
	}

	@Test
	public void testControllerNotActivatedWhenBadPencilUsed() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesRec, updateIns, checkInsDeactivated).execute();
	}

	@Test
	public void testWidgetsGoodStateWhenGoodPencilUsed() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesArc, updateIns).execute();
		assertTrue(titledPane.isVisible());
	}

	@Test
	public void testWidgetsGoodStateWhenBadPencilUsed() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesRec, updateIns).execute();
		assertFalse(titledPane.isVisible());
	}

	@Test
	public void testClickChordUnselectOthersPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesArc, updateIns, selectWedge, selectChord, waitFXEvents).execute();
		assertFalse(arcB.isSelected());
		assertFalse(wedgeB.isSelected());
		assertTrue(chordB.isSelected());
	}

	@Test
	public void testClickWedgeUnselectOthersPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesArc, updateIns, selectChord, selectWedge, waitFXEvents).execute();
		assertFalse(arcB.isSelected());
		assertTrue(wedgeB.isSelected());
		assertFalse(chordB.isSelected());
	}

	@Test
	public void testClickArcUnselectOthersPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesArc, updateIns, selectChord, selectArc, waitFXEvents).execute();
		assertTrue(arcB.isSelected());
		assertFalse(wedgeB.isSelected());
		assertFalse(chordB.isSelected());
	}

	@Test
	public void testArcEndAnglePencil() {
		doTestSpinner(new CompositeGUIVoidCommand(activatePencil, pencilCreatesArc, updateIns), endAngleS, incrementEndAngle,
			Collections.singletonList(() -> Math.toDegrees(((IArc) pencil.createShapeInstance()).getAngleEnd())));
	}

	@Test
	public void testArcStartAnglePencil() {
		doTestSpinner(new CompositeGUIVoidCommand(activatePencil, pencilCreatesArc, updateIns), startAngleS, incrementStartAngle,
			Collections.singletonList(() -> Math.toDegrees(((IArc) pencil.createShapeInstance()).getAngleStart())));
	}

	@Test
	public void testArcTypeWedgePencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesArc, updateIns, selectChord, selectWedge).execute();
		assertEquals(ArcStyle.WEDGE, ((IArc) pencil.createShapeInstance()).getArcStyle());
	}

	@Test
	public void testArcTypeArcPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesArc, updateIns, selectChord, selectArc).execute();
		assertEquals(ArcStyle.ARC, ((IArc) pencil.createShapeInstance()).getArcStyle());
	}

	@Test
	public void testArcTypeChordPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesArc, updateIns, selectArc, selectChord).execute();
		assertEquals(ArcStyle.CHORD, ((IArc) pencil.createShapeInstance()).getArcStyle());
	}
}
