package test.part;

import hexapod.geometry.Base;
import hexapod.geometry.Cone;
import hexapod.geometry.Cube;
import hexapod.geometry.Cylinder;
import hexapod.geometry.Point;
import hexapod.geometry.Vector;
import hexapod.parts.Construct;
import hexapod.parts.DistalPhalanx;
import hexapod.parts.LongHalfCylinder;
import hexapod.parts.ProximalPhalanx;

public class SpecialConstruct extends Construct {

	public SpecialConstruct(double rotateX, double rotateY,
			double basisAngle) {
		super(new Base(Point.O.translate(Vector.Z.mul(300)), Vector.X, Vector.Y).rotateX(rotateX).rotateY(rotateY),
				new Cone[] {
					new LongHalfCylinder(Base.DEFAULT, ProximalPhalanx.R1, DistalPhalanx.H, DistalPhalanx.R1*2, 40),
					new Cylinder(Base.DEFAULT, 10, ProximalPhalanx.H*2, 20)
				},
				new Construct[] {
						new Construct(Base.DEFAULT,
								new Cube(Base.DEFAULT, 100)
						)
				}
		);
	}
	
//	public SpecialConstruct(double rotateX, double rotateY) {
//		super(new Base(Point.O.translate(Vector.Z.mul(300)), Vector.X, Vector.Y).rotateX(rotateX).rotateY(rotateY),
//				new DistalPhalanx(Base.DEFAULT, 40),
//				new ProximalPhalanx(Base.DEFAULT.translate(Vector.X.mul(DistalPhalanx.LENGTH + DistalPhalanx.R1 + DistalPhalanx.R2*2))
//						.translate(Vector.Z.mul(ProximalPhalanx.H)), 40),
//				new Cylinder(Base.DEFAULT, 10, DistalPhalanx.H*2, 10),
//				new LongHalfCylinder(Base.DEFAULT.rotateZ(Math.PI/2)
//						.translate(Vector.X.mul(DistalPhalanx.LENGTH + DistalPhalanx.R1*2 + DistalPhalanx.R2)), ProximalPhalanx.R1, DistalPhalanx.H, DistalPhalanx.R1*3, 40),
//				new Cylinder(Base.DEFAULT.translate(Vector.X.mul(DistalPhalanx.LENGTH + DistalPhalanx.R1*2 + DistalPhalanx.R2)), 10, DistalPhalanx.H*2, 10),
//				new LongHalfCylinder(Base.DEFAULT.rotateX(Math.PI/2)
//						.translate(Vector.X.mul(DistalPhalanx.LENGTH + DistalPhalanx.R1*4))
//						.translate(Vector.Y.mul(ProximalPhalanx.R1+ProximalPhalanx.H))
//						.translate(Vector.Z.mul(DistalPhalanx.R1*2)),
//						ProximalPhalanx.R1, DistalPhalanx.H, DistalPhalanx.R1*2, 40),
//				new Cylinder(Base.DEFAULT.rotateX(Math.PI/2)
//						.translate(Vector.X.mul(DistalPhalanx.LENGTH + DistalPhalanx.R1*2 + DistalPhalanx.R2 + ProximalPhalanx.R1))
//						.translate(Vector.Y.mul(ProximalPhalanx.R1+ProximalPhalanx.H))
//						.translate(Vector.Z.mul(ProximalPhalanx.R1))
//						, 10, DistalPhalanx.H*2, 10)
//		);
//	}
	
}
