package hexapod.visualizer;

import hexapod.geometry.Base;
import hexapod.geometry.Cone;
import hexapod.geometry.ProjectionPlane;
import hexapod.parts.Construct;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

public abstract class Visualizer {

	public Frame fr;
	public BufferedImage buff;
	protected int width, height;
	protected ProjectionPlane proj;
	private double focusDist;
	private boolean spaceToggle;
	
	public Visualizer(int width, int height, double focusDist, boolean visible) {
		this.width = width;
		this.height = height;
		this.focusDist = focusDist;
		setBase(Base.DEFAULT);
		buff = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
		fr = new Frame("Quadropod") {
			private static final long serialVersionUID = 1L;
			@Override
			public void paint(Graphics g) {
				g.drawImage(buff, 0, 0, null);
			}
		};
		fr.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		fr.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					System.exit(0);
				}
				else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
					spaceToggle = !spaceToggle;
				}
			}
		});
		fr.setBounds((Toolkit.getDefaultToolkit().getScreenSize().width - width) / 2, (Toolkit.getDefaultToolkit().getScreenSize().height - height) / 2, width, height);
		fr.setVisible(visible);
	}
	
	public void setTitle(String title) {
		fr.setTitle(title);
	}
	
	public Base getBase() {
		return proj.b;
	}
	
	public void setBase(Base b) {
		proj = new ProjectionPlane(b, focusDist, width, height);
	}
	
	public boolean getSpaceToggle() {
		return spaceToggle;
	}

	public boolean clearBuffOnRedraw() {
		return true;
	}
	
	public void visualize(Scene s) {
		if (clearBuffOnRedraw()) {
			Graphics g = buff.getGraphics();
			g.setColor(Color.black);
			g.fillRect(0, 0, width, height);
		}
		draw(s);
		fr.paint(fr.getGraphics());
	}
	
	public void draw(Scene s) {
		for (Construct c: s.objects) {
			draw(c);
		}
	}
	
	public void draw(Construct c) {
		for (int i = 0; i < c.numberOfConstructs(); i++) {
			draw(c.getConstruct(i));
		}
		for (int i =0 ; i < c.numberOfCones(); i++) {
			draw(c.getCone(i));
		}
	}
	
	public abstract void draw(Cone c);
	
}
