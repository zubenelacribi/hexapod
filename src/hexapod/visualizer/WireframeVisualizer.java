package hexapod.visualizer;

import hexapod.geometry.Cone;
import hexapod.geometry.Point;

public class WireframeVisualizer extends Visualizer {
	
	private double maxDist;
	
	public WireframeVisualizer(int width, int height, double focusDist, double maxDist, boolean visible) {
		super(width, height, focusDist, visible);
		this.maxDist = maxDist;
	}

	@Override
	public void draw(Cone c) {
		Point[] base = new Point[c.numPoints];
		Point[] peak = new Point[c.numPoints];
		for (int i = 0; i < c.numPoints; i++) {
			base[i] = proj.project(c.getPoint(i, true));
			peak[i] = proj.project(c.getPoint(i, false));
		}
		for (int i = 0; i < c.numPoints; i++) {
			int next = (i + 1) % c.numPoints;
			drawLine(base[i], base[next]);
			drawLine(peak[i], peak[next]);
			drawLine(base[i], peak[i]);
		}
	}
	
	private void drawLine(Point p1, Point p2) {
		double stepX = p2.x - p1.x;
		double stepY = p2.y - p1.y;
		double stepZ = p2.z - p1.z;
		double f = Math.abs(stepX);
		if (f < Math.abs(stepY)) {
			f = Math.abs(stepY);
		}
		stepX /= f;
		stepY /= f;
		stepZ /= f;
		for (double x = p1.x, y = p1.y, z = p1.z; Math.abs(x - p1.x) <= Math.abs(p2.x - p1.x) && Math.abs(y - p1.y) <= Math.abs(p2.y - p1.y); x += stepX, y += stepY, z += stepZ) {
			if (z < 0.0f || z > maxDist) {
				continue;
			}
			int scrX = (int) x;
			int scrY = (int) y;
			if (scrX < 0 || scrX >= width || scrY < 0 || scrY >= height) {
				continue;
			}
			int color = (int) ((maxDist - z) / maxDist * 255);
			color *= 0x10101;
			if (buff.getRGB(scrX, scrY) < color) {
				buff.setRGB(scrX, scrY, color);
			}
		}
	}

}
