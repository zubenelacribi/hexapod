package hexapod.parts;

import hexapod.geometry.Base;
import hexapod.geometry.Cone;
import hexapod.geometry.Point;

public class LongHalfCylinder extends Cone {

	private final HalfCone half;
	private final double length;
	
	public LongHalfCylinder(Base b, double r, double height, double length, int numPoints) {
		super(b, r, r, height, numPoints);
		this.length = length;
		this.half = new HalfCone(b, r, r, height, numPoints);
	}
	
	@Override
	public Point getPoint(int index, boolean base) {
		if (index < numPoints - 2) {
			return half.getPoint(index, base);
		}
		else if (index == numPoints - 2) {
			return half.getPoint(numPoints - 1, base).translate(b.y.mul(-length));
		}
		else {
			return half.getPoint(0, base).translate(b.y.mul(-length));
		}
	}
	
	@Override
	public Cone setBase(Base b) {
		return new LongHalfCylinder(b, r1, height, length, numPoints);
	}
	
}
