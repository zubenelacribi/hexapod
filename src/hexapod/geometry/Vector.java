package hexapod.geometry;

import static hexapod.geometry.Point.digits;
import static hexapod.geometry.Point.f;

public class Vector {

	public static final double EPSILON = 0.000001f;
	
	public static final Vector O = new Vector();
	public static final Vector X = new Vector(1.0f, 0.0f, 0.0f);
	public static final Vector Y = new Vector(0.0f, 1.0f, 0.0f);
	public static final Vector Z = new Vector(0.0f, 0.0f, 1.0f);
	
	public final double x, y, z;

	public Vector() {
		this(0, 0, 0);
	}
	
	public Vector(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vector(Point p) {
		this.x = p.x;
		this.y = p.y;
		this.z = p.z;
	}
	
	public Vector(Point p1, Point p2) {
		this.x = p2.x - p1.x;
		this.y = p2.y - p1.y;
		this.z = p2.z - p1.z;
	}

	public static int getLongestNumber(Vector v) {
		return Math.max(digits(v.x), Math.max(digits(v.y), digits(v.z)));
	}

	public static int getLongestNumber(Vector... v) {
		if (v.length == 0) {
			return 1;
		}
		int max = getLongestNumber(v[0]);
		for (int i = 1; i < v.length; i++) {
			int digits = getLongestNumber(v[i]);
			if (max < digits) {
				max = digits;
			}
		}
		return max;
	}

	@Override
	public String toString() {
		int d = getLongestNumber(this) + 5;
		return "(" + f(x, d) + ", " + f(y, d) + ", " + f(z, d) + ")";
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof Vector) {
			return sub((Vector) o).isNullVector();
		}
		return false;
	}
	
	public Vector add(Vector v) {
		return new Vector(x + v.x, y + v.y, z + v.z);
	}
	
	public Vector mul(double d) {
		return new Vector(x * d, y * d, z * d);
	}
	
	public double scalarProduct(Vector v) {
		return x * v.x + y * v.y + z * v.z;
	}
	
	public double scalarSquare() {
		return scalarProduct(this);
	}
	
	public double length() {
		return (double) Math.sqrt(scalarSquare());
	}
	
	public boolean isNullVector() {
		return Math.abs(x) < EPSILON && Math.abs(y) < EPSILON && Math.abs(z) < EPSILON;
	}
	
	public boolean isUnitVector() {
		return Math.abs(scalarSquare() - 1.0f) < EPSILON;
	}
	
	public Vector vectorProduct(Vector v) {
		return new Vector(y * v.z - z * v.y, z * v.x - x * v.z, x * v.y - y * v.x);
	}
	
	public Vector sub(Vector v) {
		return new Vector(x - v.x, y - v.y, z - v.z);
	}
	
	public Vector div(double f) {
		if (Math.abs(f) < EPSILON) {
			throw new DivisionByZeroException();
		}
		return mul(1.0f / f);
	}
	
	public double tripleProduct(Vector u, Vector v) {
		return x * (u.y * v.z - u.z * v.y) + y * (u.z * v.x - u.x * v.z) + z * (u.x * v.y - u.y * v.x);
	}
	
	public double cosAngle(Vector v) {
		return scalarProduct(v) / length() / v.length();
	}
	
	public boolean isOrthogonalTo(Vector v) {
		return Math.abs(scalarProduct(v)) < EPSILON;
	}
	
	public double sinAngle(Vector v) {
		return vectorProduct(v).length() / length() / v.length();
	}
	
	public boolean isCollinearWith(Vector v) {
		return vectorProduct(v).isNullVector();
	}
	
	public Vector norm() {
		return mul(1 / length());
	}

}
