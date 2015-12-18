package hexapod.parts;

import hexapod.geometry.Base;

public class DistalPhalanx extends MergedCylinders {

	public static final double R1 = 50;
	public static final double R2 = 30;
	public static final double H = 30;
	public static final double LENGTH = 150;
	
	public DistalPhalanx(Base b, int numPoints) {
		super(b, R1, R2, H, LENGTH, numPoints);
	}

}
