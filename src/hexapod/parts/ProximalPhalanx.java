package hexapod.parts;

import hexapod.geometry.Base;

public class ProximalPhalanx extends MergedCylinders {

	public static final double R1 = 80;
	public static final double R2 = 50;
	public static final double H = 30;
	public static final double LENGTH = 250;
	
	public ProximalPhalanx(Base b, int numPoints) {
		super(b, R1, R2, H, LENGTH, numPoints);
	}

}
