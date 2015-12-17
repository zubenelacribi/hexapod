package test;

import hexapod.geometry.Base;
import hexapod.geometry.Point;
import hexapod.geometry.Vector;

public class TestBase {

	public static void main(String[] args) {
		Base b = Base.DEFAULT;
		System.out.println(b);
		System.out.println(b.toRelCoords(Point.O));
		System.out.println(b.toRelCoords(Vector.X));
		System.out.println(b.toAbsCoords(Point.O));
		System.out.println(b.toAbsCoords(Vector.X));
		b = b.rotateX(0.1f);
		System.out.println(b);
		System.out.println(b.x.length() + " " + b.y.length() + " " + b.z.length());
		System.out.println(b.x.scalarProduct(b.y) + " " + b.x.scalarProduct(b.z) + " " + b.y.scalarProduct(b.z));
		System.out.println(b.x.tripleProduct(b.y, b.z));
		System.out.println(b.toRelCoords(Vector.Y) + " " + b.toAbsCoords(b.toRelCoords(Vector.Y)));
		b = Base.DEFAULT;
		b = b.rotateY(0.1f);
		System.out.println(b);
		System.out.println(b.toRelCoords(Vector.Y));
		System.out.println(b.toRelCoords(Vector.X) + " " + b.toAbsCoords(b.toRelCoords(Vector.X)));
		System.out.println(b.toRelCoords(Vector.Z) + " " + b.toAbsCoords(b.toRelCoords(Vector.Z)));
		b = Base.DEFAULT;
		b = b.rotateZ(0.1f);
		System.out.println(b);
		System.out.println(b.toRelCoords(Vector.Z));
		System.out.println(b.toRelCoords(Vector.X) + " " + b.toAbsCoords(b.toRelCoords(Vector.X)));
		System.out.println(b.toRelCoords(Vector.Y) + " " + b.toAbsCoords(b.toRelCoords(Vector.Y)));
	}
	
}
