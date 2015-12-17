package test;

import hexapod.geometry.Base;
import hexapod.geometry.Cone;
import hexapod.geometry.Point;
import hexapod.geometry.Ray;

public class TestCone {

	public static void main(String[] args) {
		Cone c = new Cone(Base.DEFAULT, 50, 20, 30, 5);
		System.out.println(c);
		Ray r = new Ray(new Point(0, 0, -10), new Point(0, 0, -5));
		System.out.println(r);
		Point[] p = r.intersect(c);
		System.out.println(p.length + " " + (p.length > 0 ? p[0] : "") + " " + (p.length > 1 ? p[1] : ""));
	}
	
}
