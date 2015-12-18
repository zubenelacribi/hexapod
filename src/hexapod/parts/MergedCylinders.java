package hexapod.parts;

import hexapod.geometry.Base;
import hexapod.geometry.Cone;
import hexapod.geometry.Cylinder;
import hexapod.geometry.Point;

public class MergedCylinders extends Cylinder {

	private final Cylinder second;
	private final double cosA;
	private final int edge;
	private final double centreDist;
	
	public MergedCylinders(Base b, double r1, double r2, double h, double centreDist, int numPoints) {
		super(b, r1, h, numPoints);
		assert centreDist > r1 && centreDist > r2 : "centreDist should be > from both r1 & r2";
		second = new Cylinder(b.translate(b.x.mul(-centreDist)), r2, h, numPoints);
		cosA = (r1 - r2) / centreDist;
		double A = Math.PI - Math.acos(cosA);
		edge = (int) (numPoints * A / Math.PI / 2);
		this.centreDist = centreDist;
	}

	@Override
	public Point getPoint(int index, boolean base) {
		if (index < edge || index >= numPoints - edge) {
			return super.getPoint(index, base);
		}
		else {
			return second.getPoint(index, base);
		}
	}
	
	@Override
	public Cone setBase(Base b) {
		return new MergedCylinders(b, r1, second.r1, height, centreDist, numPoints);
	}
	
}
