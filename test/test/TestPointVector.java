package test;

import hexapod.geometry.Point;
import hexapod.geometry.Vector;

public class TestPointVector {

	public static void main(String[] args) {
		Point p = new Point();
		System.out.println(p);
		System.out.println(p.equals(p));
		Vector v = Vector.X;
		System.out.println(v);
		p = p.translate(v);
		System.out.println(p);
		System.out.println(p.equals(p));
		System.out.println(v.equals(v));
		System.out.println(v.length());
		Vector u = new Vector(1.0f, 1.0f, 0.0f);
		System.out.println(u);
		System.out.println(u.length());
		System.out.println(v.cosAngle(v));
		System.out.println(v.cosAngle(Vector.Y));
		System.out.println(v.cosAngle(u));
		System.out.println(v.isCollinearWith(v));
		System.out.println(v.isCollinearWith(u));
		Vector w = v.vectorProduct(v);
		System.out.println(w);
		System.out.println(w.isNullVector());
		w = v.vectorProduct(u);
		System.out.println(w);
		System.out.println(w.isNullVector());
		System.out.println(v.sinAngle(Vector.Y));
		System.out.println(v.sinAngle(u));
		System.out.println(p.isCollinearWith(p.translate(v), p.translate(v.mul(2))));
		System.out.println(p.isComplanarWith(new Point(2, 0, 0), new Point(1, 1, 0), new Point()));
		System.out.println(p.isComplanarWith(new Point(2, 0, 2), new Point(1, 1, 1), new Point()));
		System.out.println(Vector.X.tripleProduct(Vector.Y, Vector.Z));
		System.out.println(Vector.X.tripleProduct(Vector.Z, Vector.Y));
	}
	
}
