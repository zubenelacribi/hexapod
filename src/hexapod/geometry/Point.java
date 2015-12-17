package hexapod.geometry;

public class Point {

	public static final Point O = new Point();
	
	public final double x, y, z;
	
	public Point() {
		this(0, 0, 0);
	}
	
	public Point(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Point(Vector v) {
		this.x = v.x;
		this.y = v.y;
		this.z = v.z;
	}
	
	public static int digits(double f) {
		boolean sign = f < 0.0f;
		f = Math.abs(f);
		if (f < 1) {
			return 1;
		}
		return (int) Math.log10((int) f) + (sign ? 1 : 0) + 1;
	}

	public static int getLongestNumber(Point p) {
		return Math.max(digits(p.x), Math.max(digits(p.y), digits(p.z)));
	}

	public static String format(double f) {
		boolean sign = f < 0.0f;
		f = Math.abs(f);
		return (sign ? "-" : "") + (int) f + "." + (int) ((f - (int) f) * 10) + (int) ((f * 10 - (int) (f * 10)) * 10) + (int) ((f * 100 - (int) (f * 100)) * 10);
	}
	
	public static String addLeadingSpaces(String s, int overallLength) {
		StringBuffer buff = new StringBuffer();
		while (buff.length() + s.length() < overallLength) {
			buff.append(' ');
		}
		buff.append(s);
		return buff.toString();
	}
	
	public static String f(double f, int digits) {
		return addLeadingSpaces(format(f), digits);
	}
	
	@Override
	public String toString() {
		int d = getLongestNumber(this) + 5;
		return "[" + f(x, d) + ", " + f(y, d) + ", " + f(z, d) + "]";
	}
	
	public Point translate(Vector v) {
		return new Point(x + v.x, y + v.y, z + v.z);
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof Point) {
			return new Vector(this, (Point) o).isNullVector();
		}
		return false;
	}

	public boolean isCollinearWith(Point p1, Point p2) {
		return isCollinearWith(p1, new Vector(p1, p2));
	}
	
	public boolean isCollinearWith(Point p, Vector v) {
		return new Vector(this, p).isCollinearWith(v);
	}

	public boolean isComplanarWith(Point p1, Point p2, Point p3) {
		return isComplanarWith(p1, new Vector(this, p2), new Vector(this, p3));
	}
	
	public boolean isComplanarWith(Point p, Vector v1, Vector v2) {
		return Math.abs(new Vector(this, p).tripleProduct(v1, v2)) < Vector.EPSILON;
	}
	
}
