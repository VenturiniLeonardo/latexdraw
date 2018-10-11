package net.sf.latexdraw.data;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.IBezierCurve;
import net.sf.latexdraw.models.interfaces.shape.IPicture;
import net.sf.latexdraw.models.interfaces.shape.IPolygon;
import net.sf.latexdraw.models.interfaces.shape.IPolyline;
import net.sf.latexdraw.models.interfaces.shape.IRhombus;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import net.sf.latexdraw.models.interfaces.shape.ISquare;
import net.sf.latexdraw.models.interfaces.shape.ITriangle;
import org.junit.experimental.theories.ParameterSignature;
import org.junit.experimental.theories.ParameterSupplier;
import org.junit.experimental.theories.PotentialAssignment;

public class ShapeSupplier extends ParameterSupplier {
	public static IPicture createPicture() {
		return ShapeFactory.INST.createPicture(ShapeFactory.INST.createPoint(76, 45));
	}

	public static ISquare createSquare() {
		return ShapeFactory.INST.createSquare(ShapeFactory.INST.createPoint(13d, 84d), 233);
	}

	public static Stream<ISquare> createDiversifiedSquare() {
		return Stream.of(ShapeFactory.INST.createSquare(ShapeFactory.INST.createPoint(13d, 84d), 233), ParameteriseShapeData.INST.setSquareData1(createSquare()));
	}

	public static IRhombus createRhombus() {
		return ShapeFactory.INST.createRhombus(ShapeFactory.INST.createPoint(251d, 33d), 220, 376);
	}

	public static ITriangle createTriangle() {
		return ShapeFactory.INST.createTriangle(ShapeFactory.INST.createPoint(251d, 33d), 76, 12);
	}

	public static IBezierCurve createBezierCurve() {
		return ShapeFactory.INST.createBezierCurve(Arrays.asList(ShapeFactory.INST.createPoint(51d, 73d), ShapeFactory.INST.createPoint(151d, 173d),
			ShapeFactory.INST.createPoint(251d, 33d)));
	}

	public static IPolyline createPolyline() {
		return ShapeFactory.INST.createPolyline(Arrays.asList(ShapeFactory.INST.createPoint(51d, 73d), ShapeFactory.INST.createPoint(151d, 173d),
			ShapeFactory.INST.createPoint(251d, 33d)));
	}

	public static IPolygon createPolygon() {
		return ShapeFactory.INST.createPolygon(Arrays.asList(ShapeFactory.INST.createPoint(51d, 73d), ShapeFactory.INST.createPoint(151d, 173d),
			ShapeFactory.INST.createPoint(251d, 33d)));
	}

	public static Stream<IShape> getDiversifiedShapes() throws IOException {
		return
			Stream.concat(RectSupplier.createDiversifiedRectangle(), Stream.concat(ArcSupplier.createDiversifiedArc(),
				Stream.concat(DotSupplier.createDiversifiedDot(), Stream.concat(PlotSupplier.createDiversifiedPlot(),
				Stream.concat(GridSupplier.createDiversifiedGrid(), Stream.concat(AxesSupplier.createDiversifiedAxes(),
				Stream.concat(TextSupplier.createDiversifiedText(),
					Stream.of((IShape) EllSupplier.createEllipse(), (IShape) createBezierCurve(), CircleSupplier.createCircle(), createPolyline(), createPolygon(), createSquare(),
			ShapeFactory.INST.createFreeHand(Arrays.asList(ShapeFactory.INST.createPoint(51d, 73d), ShapeFactory.INST.createPoint(151d, 173d),
				ShapeFactory.INST.createPoint(251d, 33d), ShapeFactory.INST.createPoint(251d, 35d), ShapeFactory.INST.createPoint(151d, 233d))),
			createRhombus(), createTriangle(),
			ParameteriseShapeData.INST.setPictureData1(createPicture()))))))))).
			map(sh -> Arrays.asList(ParameteriseShapeData.INST.setShapeData1(sh.duplicate()),
				ParameteriseShapeData.INST.setShapeData2(sh.duplicate()),
				ParameteriseShapeData.INST.setShapeData3(sh.duplicate()),
				ParameteriseShapeData.INST.setShapeData4(sh.duplicate()))).flatMap(s -> s.stream());
	}

	public static Stream<IShape> getStdShapesStream() {
		return Stream.of(EllSupplier.createEllipse(), ArcSupplier.createArc(), GridSupplier.createGrid(), CircleSupplier.createCircle(),
			RectSupplier.createRectangle(), createBezierCurve(), TextSupplier.createText(), DotSupplier.createDot(), createPolyline(),
			createPolygon(), AxesSupplier.createAxes(), createSquare(),
			ShapeFactory.INST.createFreeHand(Arrays.asList(ShapeFactory.INST.createPoint(51d, 73d), ShapeFactory.INST.createPoint(151d, 173d),
				ShapeFactory.INST.createPoint(251d, 33d), ShapeFactory.INST.createPoint(251d, 35d), ShapeFactory.INST.createPoint(151d, 233d))),
			PlotSupplier.createPlot(), createRhombus(), createTriangle(), createPicture());
	}

	@Override
	public List<PotentialAssignment> getValueSources(final ParameterSignature sig) throws IOException {
		final ShapeData shapeData = sig.getAnnotation(ShapeData.class);
		final Stream<IShape> instances;

		if(shapeData.withParamVariants()) {
			instances = getDiversifiedShapes();
		}else {
			instances = getStdShapesStream();
		}

		return instances.map(r -> PotentialAssignment.forValue("", r)).collect(Collectors.toList());
	}
}
