package test;

import hexapod.geometry.Plane;
import hexapod.geometry.Point;
import hexapod.geometry.Ray;
import hexapod.geometry.Vector;

public class TestRayPlane {

	public static void main(String[] args) {
		Plane pl = new Plane(Point.O, Vector.Z);
		System.out.println(pl);
		System.out.println(pl.contains(Point.O));
		System.out.println(pl.contains(new Point(1, 2, 3)));
		System.out.println(pl.isComplanarWith(Vector.X));
		Ray r = new Ray(new Point(1, 2, -10), new Point(2, 2, 2));
		System.out.println(r);
		System.out.println(r.intersects(pl));
		Point p = r.intersect(pl);
		System.out.println(p);
		System.out.println(pl.contains(p));
		System.out.println(p.isCollinearWith(r.p, r.v));
		System.out.println(r.contains(p));
		System.out.println(pl.isOrthogonalTo(Vector.Z));
		System.out.println(pl.distance(r.p));
		
		Point p1 = new Point(1, 2, 3);
		Point p2 = new Point(-1, 2, 100);
		Point p3 = new Point(0, 0, -5);
		pl = new Plane(p1, p2, p3);
		System.out.println(pl);
		System.out.println(pl.contains(p1));
		System.out.println(pl.contains(p2));
		System.out.println(pl.contains(p3));
	}
	
}
