package test;

import java.io.File;
import java.io.FileInputStream;

import test.part.SpecialConstruct;
import hexapod.geometry.Base;
import hexapod.geometry.Cube;
import hexapod.geometry.Point;
import hexapod.geometry.Vector;
import hexapod.parts.Construct;
import hexapod.parts.DistalPhalanx;
import hexapod.parts.Foot;
import hexapod.parts.Leg;
import hexapod.parts.MergedCylinders;
import hexapod.parts.ProximalPhalanx;
import hexapod.visualizer.Raytracer;
import hexapod.visualizer.Scene;
import hexapod.visualizer.Visualizer;
import hexapod.visualizer.WireframeVisualizer;

public class FlyingCube {

	public static void main(String[] args) throws Exception {
		WireframeVisualizer w = new WireframeVisualizer(640, 480, 500, 600, true);
		Raytracer r = new Raytracer(640, 480, 500, 600, false);
		r.fr = w.fr;
		r.buff = w.buff;
		Visualizer v = w;
		v.setTitle("Flying cube");
		Scene s = new Scene();
		s.lightPoint = new Point(1000, 200, 200);
		for (double d = 0; ; d += 0.01) {
			if (s.objects.size() > 0) {
				s.objects.clear();
			}

			//			s.objects.add(new Construct(Base.DEFAULT, new Cube(new Base(Point.O.translate(Vector.Z.mul(300)), Vector.X, Vector.Y).rotateX(d).rotateY(2 * d), 200)));
			//			s.objects.add(new Construct(Base.DEFAULT, new Cube(new Base(Point.O.translate(Vector.Z.mul(300)), Vector.X, Vector.Y).rotateX(d).rotateY(2 * d), 50, 100, 200)));
			//			s.objects.add(new Construct(Base.DEFAULT, new Leg(new Base(Point.O.translate(Vector.Z.mul(300)), Vector.X, Vector.Y).rotateZ(d), 30)));

			//			s.objects.add(new Construct(Base.DEFAULT, new HalfCone(new Base(Point.O.translate(Vector.Z.mul(300)), Vector.X, Vector.Y).rotateX(d).rotateY(2 * d), 200, 100, 300, 20)));
			//			s.objects.add(new Construct(Base.DEFAULT, new MergedCylinders(new Base(Point.O.translate(Vector.Z.mul(300)), Vector.X, Vector.Y).rotateX(d).rotateY(2 * d), 80, 60 + 20*Math.sin(d), 100, 100, 40)));
			//			s.objects.add(new Knee(new Base(Point.O.translate(Vector.Z.mul(300)), Vector.X, Vector.Y).rotateX(d), 80, 60, 40, 40, 100, 60, Math.sin(d), 10, 40));
			//			s.objects.add(new Femur(new Base(Point.O.translate(Vector.Z.mul(300)), Vector.X, Vector.Y).rotateX(d), 80, 60, 60, 40, 100, 20, 80, Math.sin(d), 40));
			//			s.objects.add(new Construct(Base.DEFAULT, new LongHalfCylinder(new Base(Point.O.translate(Vector.Z.mul(300)), Vector.X, Vector.Y).rotateX(d), 80, 40, 100, 40)));

			//			s.objects.add(new Construct(Base.DEFAULT, new DistalPhalanx(new Base(Point.O.translate(Vector.Z.mul(300)), Vector.X, Vector.Y).rotateX(d).rotateY(2 * d), 40)));
			//			s.objects.add(new Construct(Base.DEFAULT, new ProximalPhalanx(new Base(Point.O.translate(Vector.Z.mul(300)), Vector.X, Vector.Y).rotateX(d).rotateY(2 * d), 40)));

			//			s.objects.add(new Construct(new Base(Point.O.translate(Vector.Z.mul(300)), Vector.X, Vector.Y).rotateX(d).rotateY(2 * d),
			//					new DistalPhalanx(Base.DEFAULT, 40),
			//					new ProximalPhalanx(Base.DEFAULT.translate(Vector.X.mul(DistalPhalanx.LENGTH + DistalPhalanx.R1 + DistalPhalanx.R2)), 40)));

			s.objects.add(instantiate("test.part.SpecialConstruct", d, 2 * d));

			if (w.getSpaceToggle()) {
				v = r;
			}
			else {
				v = w;
			}
			v.visualize(s);
			Thread.sleep(20);
		}
	}

	private static class ByteCodeClassLoader extends ClassLoader {

		private long lastModified = -1;
		private Class<?> cached;
		
		@Override
		public Class<?> loadClass(String name) throws ClassNotFoundException {
			if (name.equals("test.part.SpecialConstruct")) {
				File f = new File("bin/test/part/SpecialConstruct.class");
				if (lastModified < 0 || f.lastModified() > lastModified) {
					byte[] buff = new byte[(int) f.length()];
					try {
						FileInputStream inp = new FileInputStream(f);
						inp.read(buff, 0, buff.length);
						inp.close();
						lastModified = f.lastModified();
					}
					catch (Exception ex) {
						throw new ClassNotFoundException();
					}
					return cached = defineClass(name, buff, 0, buff.length);
				}
				else {
					return cached;
				}
			}
			return super.loadClass(name);
		}

	}
	
	private static ClassLoader classLoader = ClassLoader.getSystemClassLoader();//new ByteCodeClassLoader();

	private static Construct instantiate(String className, double rotateX, double rotateY) {
		try {
			Class<?> clazz = classLoader.loadClass(className);
			return (Construct) clazz.getConstructors()[0].newInstance(rotateX, rotateY);
		}
		catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

}
