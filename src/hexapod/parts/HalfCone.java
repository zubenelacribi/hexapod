package hexapod.parts;

import hexapod.geometry.Base;
import hexapod.geometry.Cone;
import hexapod.geometry.Point;

public class HalfCone extends Cone {

	public HalfCone(Base b, double r1, double r2, double height, int numPoints) {
		super(b, r1, r2, height, numPoints);
	}
	
	@Override
	public String toString() {
		return "half, " + super.toString();
	}
	
	@Override
	public Point getPoint(int index, boolean base) {
		double r = base ? r1 : r2;
		double angle = (double) (index * Math.PI / (numPoints - 1));
		return b.toAbsCoords(new Point(r * Math.cos(angle), r * Math.sin(angle), base ? 0 : height));
	}
	
	@Override
	public Cone setBase(Base b) {
		return new HalfCone(b, r1, r2, height, numPoints);
	}
	
}
