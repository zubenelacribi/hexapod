package test.part;

import hexapod.geometry.Base;
import hexapod.geometry.Point;
import hexapod.geometry.Vector;
import hexapod.parts.Construct;
import hexapod.parts.DistalPhalanx;
import hexapod.parts.ProximalPhalanx;

public class SpecialConstruct extends Construct {

	public SpecialConstruct(double rotateX, double rotateY) {
		super(new Base(Point.O.translate(Vector.Z.mul(300)), Vector.X, Vector.Y).rotateX(rotateX).rotateY(rotateY),
				new DistalPhalanx(Base.DEFAULT, 40),
				new ProximalPhalanx(Base.DEFAULT.translate(Vector.X.mul(DistalPhalanx.LENGTH + DistalPhalanx.R1 + DistalPhalanx.R2*2))
						.translate(Vector.Z.mul(ProximalPhalanx.H)), 40));
	}
	
}
