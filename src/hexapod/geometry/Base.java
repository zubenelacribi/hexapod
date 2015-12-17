package hexapod.geometry;

import static hexapod.geometry.Point.f;
import static hexapod.geometry.Vector.getLongestNumber;

public class Base {
	
	public static final Base DEFAULT = new Base(Point.O, Vector.X, Vector.Y);

	public final Point o;
	public final Vector x, y, z;
	
	public Base(Point o, Vector x, Vector aux) {
		assert !x.isNullVector() : "x is null";
		assert !aux.isNullVector() : "aux is null";
		assert !x.isCollinearWith(aux) : "x and aux are collinear";
		this.o = o;
		this.x = x.norm();
		this.z = x.vectorProduct(aux).norm();
		this.y = z.vectorProduct(x);
	}
	
	@Override
	public String toString() {
		int d = getLongestNumber(x, y, z) + 5;
		int d1 = Point.getLongestNumber(o) + 5;
		return
				"[" + f(x.x, d) + " " + f(y.x, d) + " " + f(z.x, d) + "][" + f(o.x, d1) + "]\n" +
				"[" + f(x.y, d) + " " + f(y.y, d) + " " + f(z.y, d) + "][" + f(o.y, d1) + "]\n" +
				"[" + f(x.z, d) + " " + f(y.z, d) + " " + f(z.z, d) + "][" + f(o.z, d1) + "]";
	}
	
	public Vector toRelCoords(Vector v) {
		return new Vector(v.scalarProduct(x), v.scalarProduct(y), v.scalarProduct(z));
	}
	
	public Point toRelCoords(Point p) {
		return new Point(toRelCoords(new Vector(o, p)));
	}
	
	public Vector toAbsCoords(Vector v) {
		return x.mul(v.x).add(y.mul(v.y)).add(z.mul(v.z));
	}
	
	public Point toAbsCoords(Point p) {
		return o.translate(toAbsCoords(new Vector(p)));
	}
	
	public Base translate(Vector v) {
		return new Base(o.translate(v), x, y);
	}
	
	public Base rotateX(double angle) {
		return new Base(o, x, y.mul((double) Math.cos(angle)).add(z.mul((double) Math.sin(angle))));
	}

	public Base rotateY(double angle) {
		Base b = new Base(o, y, z).rotateX(angle);
		return new Base(o, b.z, b.x);
	}
	
	public Base rotateZ(double angle) {
		Base b = new Base(o, z, x).rotateX(angle);
		return new Base(o, b.y, b.z);
	}

	public Base toRelCoords(Base b) {
		return new Base(toRelCoords(b.o), toRelCoords(b.x), toRelCoords(b.y));
	}

	public Base toAbsdCoords(Base b) {
		return new Base(toAbsCoords(b.o), toAbsCoords(b.x), toAbsCoords(b.y));
	}

}
