package seb.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

public class Pinto {
	
	// Objetos
	
	Rectangle colisao;
	
	// Atributos
	
	double posX, posY;
	int width, height;
	
	boolean podePontuar;
	
	// Métodos
	
	public Pinto(double pX, double pY, int wi, int he, boolean pode) {
		posX = pX;
		width = wi;
		height = he;
		posY = Main.frameH+he;
		podePontuar = pode;
		
		colisao = new Rectangle((int) posX,(int) posY, width, height);
	}
	
	public void step(Priquito p, ArrayList<Pinto> pints) {
		posY--;
		colisao.x = (int) posX;
		colisao.y = (int) posY;
		
		if (colisao.intersects(p.colisao)){ // Perde o jogo
			pints.clear();
			p.morrer();
			p.PONTOS = 0;
			
		}
		
		if (posY+ height < 0) {
			pints.remove(this);
		}
		
		if (podePontuar && this.posY < p.posY) {
			podePontuar = false;
			p.PONTOS++;
		}
		
	}
	
	public void gstep(Graphics g) {
		g.setColor(Color.GREEN);
		g.fillRect((int)posX, (int)posY, width, height);
		
		//g.dispose();
	}
	
	

}
