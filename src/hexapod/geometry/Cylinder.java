package hexapod.geometry;

public class Cylinder extends Cone {

	public Cylinder(Base b, double r, double h, int numPoints) {
		super(b, r, r, h, numPoints);
	}
	
}
