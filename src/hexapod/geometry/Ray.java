package hexapod.geometry;

public class Ray {

	public final Point p;
	public final Vector v;
	
	public Ray(Point p, Vector v) {
		assert !v.isNullVector() : "null vector";
		this.p = p;
		this.v = v;
	}
	
	public Ray(Point p1, Point p2) {
		this(p1, new Vector(p1, p2));
	}
	
	@Override
	public String toString() {
		return "{" + p + ", " + v + "}";
	}
	
	public boolean intersects(Ray r) {
		return !v.isCollinearWith(r.v) && p.isComplanarWith(r.p, v, r.v);
	}
	
	public boolean intersects(Plane p) {
		return !p.isComplanarWith(v);
	}

	public Point intersect(Plane pl) {
		return p.translate(v.mul((new Vector(pl.p).scalarProduct(pl.v) - new Vector(p).scalarProduct(pl.v)) / v.scalarProduct(pl.v)));
	}
	
	public boolean contains(Point p) {
		return p.isCollinearWith(p, v);
	}
	
	public Point[] intersect(Cone c) {
		Plane[] pl = new Plane[c.numPoints + 2];
		Base[] b = new Base[c.numPoints + 2];
		Point[][] bounds = new Point[c.numPoints + 2][];

		bounds[bounds.length - 2] = new Point[c.numPoints];
		bounds[bounds.length - 1] = new Point[c.numPoints];
		
		pl[pl.length - 2] = new Plane(c.b.o, c.b.z);
		b[b.length - 2] = c.b;
		pl[pl.length - 1] = new Plane(c.b.o.translate(c.b.z.mul(c.height)), c.b.z);
		b[b.length - 1] = c.b.translate(c.b.z.mul(c.height));
		
		for (int i = 0; i < c.numPoints; i++) {
			Point p1 = c.getPoint(i, true);
			Point p2 = c.getPoint(i, false);
			Point p3 = c.getPoint((i + 1) % c.numPoints, true);
			Point p4 = c.getPoint((i + 1) % c.numPoints, false);
			pl[i] = new Plane(p1, p2, p3);
			b[i] = new Base(p1, new Vector(p1, p2), new Vector(p1, p3));
			bounds[i] = new Point[] { b[i].toRelCoords(p1), b[i].toRelCoords(p2), b[i].toRelCoords(p4), b[i].toRelCoords(p3) }; // p1, p2, p4, p3 is the convex quadrilateral.
			bounds[bounds.length - 2][i] = b[b.length - 2].toRelCoords(p1);
			bounds[bounds.length - 1][i] = b[b.length - 1].toRelCoords(p2);
		}
		
		Point[] res = new Point[2]; // The ray can hit the cone in max 2 points.
		
		for (int i = 0; i < pl.length; i++) {
			if (intersects(pl[i])) {
				Point p = intersect(pl[i]);
				if (pl[i].distance(p) < 0) {
					continue;
				}
				p = b[i].toRelCoords(p);
				if (isInBounds(p, bounds[i])) {
					if (res[0] == null) {
						res[0] = b[i].toAbsCoords(p);
					}
					else {
						res[1] = b[i].toAbsCoords(p);
						break;
					}
				}
			}
		}

		if (res[0] == null) {
			return new Point[0];
		}
		if (res[1] == null) {
			return new Point[] { res[0] };
		}
		if (new Vector(p, res[0]).scalarSquare() > new Vector(p, res[1]).scalarSquare()) {
			Point swap = res[0];
			res[0] = res[1];
			res[1] = swap;
		}
		else if (res[0].equals(res[1])) {
			return new Point[] { res[0] };
		}
		return res;
	}
	
	private boolean isInBounds(Point p, Point[] bounds) {
		for (int i = 0; i < bounds.length; i++) {
			if (new Vector(bounds[i], bounds[(i + 1) % bounds.length]).vectorProduct(new Vector(bounds[i], p)).z < 0) {
				return false;
			}
		}
		return true;
	}
	
}
