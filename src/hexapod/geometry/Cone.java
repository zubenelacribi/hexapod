package hexapod.geometry;

public class Cone {

	public final Base b;
	public final double r1, r2, height;
	public final int numPoints;
	
	public Cone(Base b, double r1, double r2, double height, int numPoints) {
		assert b != null : "b is null";
		this.b = b;
		this.r1 = r1;
		this.r2 = r2;
		this.height = height;
		this.numPoints = numPoints;
	}
	
	@Override
	public String toString() {
		return "r1=" + r1 + ", r2=" + r2 + ", h=" + height + "\n" + b;
	}
	
	public Point getPoint(int index, boolean base) {
		double r = base ? r1 : r2;
		double angle = (double) (index * Math.PI * 2 / numPoints);
		return b.toAbsCoords(new Point(r * Math.cos(angle), r * Math.sin(angle), base ? 0 : height));
	}
	
	public Cone setBase(Base b) {
		return new Cone(b, r1, r2, height, numPoints);
	}
	
}
