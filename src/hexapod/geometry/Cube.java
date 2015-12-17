package hexapod.geometry;

public class Cube extends Cylinder {

	public final double sideA, sideB, sideC;
	
	public Cube(Base b, double side) {
		this(b, side, side, side);
	}

	public Cube(Base b, double sideA, double sideB, double sideC) {
		super(b, Math.sqrt(sideA*sideA + sideB*sideB) / 2.0, sideC, 4);
		this.sideA = sideA;
		this.sideB = sideB;
		this.sideC = sideC;
	}

	@Override
	public Cone setBase(Base b) {
		return new Cube(b, sideA, sideB, sideC);
	}
	
	@Override
	public Point getPoint(int index, boolean base) {
		assert index >= 0 && index <= 3 : "index out of bounds";
		return b.toAbsCoords(new Point(
				sideA / 2 * (index == 0 || index == 3 ? +1 : -1),
				sideB / 2 * (index == 0 || index == 1 ? +1 : -1),
				base ? 0 : sideC));
	}
	
}
