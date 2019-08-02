package malhaDeTriangulo;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;


import minibiblioteca.Vertice;

public class Desenhar extends Canvas{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Vertice[] v;
	private int W;
	private int H;
	private Color[][] c;
	
	public Desenhar (Vertice[] v, int W, int H, Color[][] c) {
		this.v = v;
		this.W = W;
		this.H = H;
		this.c = c;
	}
	
	@Override
	public void paint(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, W, H);
		for(int i = 0; i < this.v.length; i++) {
			int x = (int) this.v[i].getX();
			int y = (int) this.v[i].getY();
			g.setColor(this.c[x][y]);
			g.fillRect(x, y, 1, 1);
		}
		
	}
	
	public void setV(Vertice[] vertices) {
		this.v = vertices;
	}
	
	public Vertice[] getV() {
		return this.v;
	}

	public int getW() {
		return W;
	}

	public void setW(int w) {
		W = w;
	}

	public int getH() {
		return H;
	}

	public void setH(int h) {
		H = h;
	}
	
	

}
