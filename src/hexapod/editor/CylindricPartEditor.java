package hexapod.editor;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class CylindricPartEditor implements MouseListener, MouseMotionListener {

	private static final String DEFAULT_TEXT = "Click to add point. Right-click to remove point. Drag to draw line. Press S to save. Press Ctrl-Esc to exit";
	
	private BufferedImage buff;
	private Graphics g, editorG;
	private Frame editor;
	private String filename;
	private Label label;

	public CylindricPartEditor(String title, String filename) {
		this.filename = filename;
		buff = new BufferedImage(640, 480, BufferedImage.TYPE_3BYTE_BGR);
		g = buff.getGraphics();
		editor = new Frame(title + " -- " + filename) {
			private static final long serialVersionUID = 1L;
			@Override
			public void paint(Graphics g) {
				g.drawImage(buff, 0, 0, null);
			}
		};
		editor.setBounds((Toolkit.getDefaultToolkit().getScreenSize().width - buff.getWidth()) / 2, (Toolkit.getDefaultToolkit().getScreenSize().height - buff.getHeight()) / 2, buff.getWidth(), buff.getHeight() + 20);
		editor.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		editor.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					if (KeyEvent.getKeyModifiersText(e.getModifiers()).equals("Ctrl")) {
						System.exit(0); // Ctrl-Esc = exit
					}
					else {
						cancelEvent = true; // Otherwise cancel current event, e.g. drawing a line.
						dragged = -1;
						refresh(-1, -1);
					}
				}
			}
			@Override
			public void keyTyped(KeyEvent e) {
				if (Character.toLowerCase(e.getKeyChar()) == 's') {
					save();
				}
			}
		});
		editor.addMouseListener(this);
		editor.addMouseMotionListener(this);

		editor.setLayout(null);
		label = new Label(DEFAULT_TEXT);
		label.setBounds(0, editor.getHeight() - 20, editor.getWidth(), 20);
		label.setFocusable(false);
		label.setForeground(Color.gray);
		label.setBackground(Color.darkGray);
		label.setAlignment(Label.CENTER);
		editor.add(label);

		editor.setVisible(true);
		editorG = editor.getGraphics();
	}

	private int focused = -1;
	private List<Point> points = new ArrayList<Point>();
	private List<Rectangle> edges = new ArrayList<Rectangle>();
	private boolean cancelEvent;

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) { // Left button -- add point
			if (focused < 0) { // We don't want to add a point while holding another one.
				points.add(new Point(e.getX(), e.getY()));
				refresh(e.getX(), e.getY());
			}
		}
		else if (e.getButton() == MouseEvent.BUTTON3) { // Right button -- delete point
			if (focused >= 0) { // We can only delete a focused point.
				Point p = points.remove(focused);
				for (int i = 0; i < edges.size(); i++) {
					Rectangle edge = edges.get(i);
					if ((edge.x == p.x && edge.y == p.y) || (edge.width ==  p.x && edge.height == p.y)) {
						edges.remove(i--);
					}
				}
				refresh(e.getX(), e.getY());
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		try {
			if (cancelEvent) {
				return;
			}
			int x = e.getX();
			int y = e.getY();
			if (x < 0 || x >= buff.getWidth() || y < 0 || y >= buff.getHeight()) {
				dragged = -1;
				refresh(x, y);
				return;
			}
			if (dragged >= 0) {
				if (focused >= 0) {
					edges.add(new Rectangle(points.get(dragged).x, points.get(dragged).y, points.get(focused).x, points.get(focused).y));
				}
				else if (x != points.get(dragged).x || y != points.get(dragged).y) {
					points.add(new Point(x, y));
					edges.add(new Rectangle(points.get(dragged).x, points.get(dragged).y, x, y));
				}
				refresh(x, y);
				dragged = -1;
			}
		}
		finally {
			cancelEvent = false;
		}
	}

	private int dragged;

	@Override
	public void mouseDragged(MouseEvent e) {
		if (cancelEvent) {
			return;
		}
		int x = e.getX();
		int y = e.getY();
		if (e.getButton() == MouseEvent.BUTTON2) { // Middle button -- moving a point.
			if (focused >= 0) {
				points.get(focused).x = x;
				points.get(focused).y = y;
				refresh(x, y);
			}
			return;
		}
		if (dragged < 0) {
			if (focused >= 0) {
				dragged = focused;
			}
			else if (x >= 0 && x < buff.getWidth() && y >= 0 && y < buff.getHeight()) {
				points.add(new Point(x, y));
				dragged = points.size() - 1;
			}
		}
		else {
			refresh(x, y);
			editorG.setColor(Color.red);
			editorG.drawLine(points.get(dragged).x, points.get(dragged).y, x, y);
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		if (focused >= 0 &&
				(Math.abs(points.get(focused).x - x) > 3 ||
						Math.abs(points.get(focused).y - y) > 3)) {
			focused = -1;
			refresh(x, y);
		}
		else if (focused < 0) {
			for (int i = 0; i < points.size(); i++) {
				Point p = points.get(i);
				if (Math.abs(x - p.x) <= 3 && Math.abs(y - p.y) <= 3) {
					refresh(x, y);
					break;
				}
			}
		}
	}

	private void refresh(int mouseX, int mouseY) {
		g.setColor(Color.black);
		g.fillRect(0, 0, buff.getWidth(), buff.getHeight());
		g.setColor(Color.blue);
		focused = -1;
		for (int i = 0; i < edges.size(); i++) {
			Rectangle edge = edges.get(i);
			g.drawLine(edge.x, edge.y, edge.width, edge.height);
		}
		g.setColor(Color.yellow);
		for (int i = 0; i < points.size(); i++) {
			Point p = points.get(i);
			g.fillRect(p.x - 2, p.y - 2, 5, 5);
			if (Math.abs(mouseX - p.x) <= 3 && Math.abs(mouseY - p.y) <= 3) {
				focused = i;
				g.drawRect(p.x - 4, p.y - 4, 8, 8);
			}
		}
		editor.paint(editor.getGraphics());
	}

	private void save() {
		try {
			String javaClassName = filename + "_Plot";
			File f = new File("src/hexapod/parts/" + javaClassName + ".java");
			PrintWriter out = new PrintWriter(f);
			out.println("package hexapod.parts;\n");
			out.println("import hexapod.geometry.Point;\n");
			out.println("public class " + javaClassName + " {\n");
			out.println("  public static final Point[] points = new Point[] {");
			for (int i = 0; i < points.size(); i++) {
				Point p = points.get(i);
				out.println("    new Point(" + p.x + ", " + p.y + ", 0),");
			}
			out.println("  };\n");
			out.println("  public static final int[] edges = new int[] {");
			for (int i = 0; i < edges.size(); i++) {
				out.print("    ");
				Rectangle edge = edges.get(i);
				for (int j = 0; j < points.size(); j++) {
					Point p = points.get(j);
					if (edge.x == p.x && edge.y == p.y) {
						out.print(j);
						break;
					}
				}
				out.print(", ");
				for (int j = 0; j < points.size(); j++) {
					Point p = points.get(j);
					if (edge.width == p.x && edge.height == p.y) {
						out.print(j);
						break;
					}
				}
				out.println(", ");
			}
			out.println("  };\n");
			out.println("}");
			out.close();
			label.setText("Saved");
			Thread.sleep(500);
			label.setText(DEFAULT_TEXT);
		}
		catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
	
	//-------------------------------------------------------------------------------------------------
	// M A I N
	//-------------------------------------------------------------------------------------------------

	public static void main(String[] args) throws Exception {
		if (args.length == 0) {
			System.out.println("Usage: java hexapod.editor.CylindricPartEditor <part_name>");
			System.out.println("Edits the java file in hexapod.parts corresponding to the given name");
			return;
		}
		new CylindricPartEditor("Cylindric Part Editor", args[0]);
	}

}
