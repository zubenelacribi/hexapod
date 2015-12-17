package hexapod.visualizer;

import hexapod.geometry.Cone;
import hexapod.geometry.Point;
import hexapod.geometry.Ray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class Raytracer extends Visualizer {

	private double maxDist;
	
	public Raytracer(int width, int height, double focusDist, double maxDist, boolean visible) {
		super(width, height, focusDist, visible);
		this.maxDist = maxDist;
	}
/*
	@Override
	public void draw(Scene s) {
		if (s.lightPoint == null) {
			throw new RuntimeException("Light point not set");
		}
		int splitX = 2;
		int splitY = 2;
		final int[] threadCount = new int[1];
		
		for (int i = 0; i < splitY; i++) {
			final int y1 = height * i / splitY;
			final int y2 = height * (i + 1) / splitY;
			for (int j = 0; j < splitX; j++) {
				final int x1 = width * j / splitX;
				final int x2 = width * (j + 1) / splitX;
				synchronized (threadCount) {
					threadCount[0]++;
				}
				
				new Thread() {
					@Override
					public void run() {
						for (int y = y1; y < y2; y++) {
							for (int x = x1; x < x2; x++) {
								ray(s, x, y);
							}
						}
						synchronized (threadCount) {
							threadCount[0]--;
							threadCount.notify();
						}
					}
				}.start();
			}
		}
		
		while (true) {
			synchronized (threadCount) {
				if (threadCount[0] == 0) {
					break;
				}
				try {
					threadCount.wait();
				}
				catch (InterruptedException ex) {}
			}
		}
	}
	
	private void ray(Scene s, int x, int y) {
		Ray r = new Ray(proj.f, proj.b.toAbsCoords(new Point(x - (double) width / 2f, y - (double) height / 2f, 0)));
		ArrayList<Point> list = new ArrayList<>();
		for (Cone c: s.objects) {
			for (Point p: r.intersect(c)) {
				list.add(c.b.toRelCoords(p));
			}
		}
		Point[] p = list.toArray(new Point[list.size()]);
		Arrays.sort(p, new Comparator<Point>() {
			@Override
			public int compare(Point p1, Point p2) {
				return p1.z < p2.z ? -1 : p1.z > p2.z ? +1 : 0;
			}
		});
		if (p.length > 0) {
			buff.setRGB(x, y, 0xffffff);
		}
	}
*/
	
	@Override
	public void draw(Cone c) {
		// TODO Auto-generated method stub
		
	}
	
}
