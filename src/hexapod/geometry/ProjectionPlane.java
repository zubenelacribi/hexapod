package hexapod.geometry;

public class ProjectionPlane {

	public final Base b;
	public final Point f;
	public final Plane pl;
	public final int width, height;
	
	public ProjectionPlane(Base b, double focusDist, int width, int height) {
		assert b != null : "base is null";
		assert focusDist > 0 : "focusDist should be positive";
		assert width > 0 && height > 0 : "screen dimensions should be positive numbers";
		this.b = b;
		this.f = b.toAbsCoords(Point.O.translate(Vector.Z.mul(-focusDist)));
		this.pl = new Plane(b.o, b.z);
		this.width = width;
		this.height = height;
	}
	
	@Override
	public String toString() {
		return f + ", " + pl + ", " + width + "x" + height + "\n" + b;
	}
	
	public boolean canProject(Point p) {
		return pl.distance(p) >= 0.0f;
	}
	
	public Point project(Point p) {
		return b.toRelCoords(new Ray(f, p).intersect(pl)).translate(new Vector((double) width / 2, (double) height / 2, pl.distance(p)));
	}
	
}
