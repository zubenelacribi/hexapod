package hexapod.geometry;

import static hexapod.geometry.Point.f;

public class Plane {

	public final Point p;
	public final Vector v;
	
	public Plane(Point p, Vector v) {
		assert !v.isNullVector() : "null vector";
		this.p = p;
		this.v = v;
	}
	
	public Plane(Point p1, Point p2, Point p3) {
		this(p1, new Vector(p1, p2), new Vector(p1, p3));
	}

	public Plane(Point p1, Vector v1, Vector v2) {
		this(p1, v1.vectorProduct(v2));
	}
	
	@Override
	public String toString() {
		int d = Math.max(Point.getLongestNumber(p), Vector.getLongestNumber(v)) + 5;
		return f(v.x, d) + ".x + " + f(v.y, d) + ".y + " + f(v.z, d) + ".z + " + f(-v.scalarProduct(new Vector(p)), d) + " = 0";
	}
	
	public boolean isComplanarWith(Vector v) {
		return v.isOrthogonalTo(this.v);
	}
	
	public boolean isOrthogonalTo(Vector v) {
		return v.isCollinearWith(this.v);
	}
	
	public double distance(Point p) {
		return new Vector(this.p, p).scalarProduct(v);
	}

	public double absDistance(Point p) {
		return Math.abs(distance(p));
	}
	
	public boolean contains(Point p) {
		return absDistance(p) < Vector.EPSILON;
	}

}
