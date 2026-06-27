package seb.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Priquito {
	// Objetos
	
	public Rectangle colisao;
	
	
	// Atributos
	public double posX; // Posição
	public final int posY = Main.frameH/8+12;
	
	public double gravidade; // Gravidde
	private double gravidadeInf = 0; // Gravidade
	
	public double jumpForce; // Pular
	public int width, height; // Tamanho
	
	private final int jumpFrames = 10; // Pular
	private int atualjumpFrame = 0; // Pular 
	public boolean podePular = true; // Pular
	
	
	public int PONTOS = 0;
	
	// Métodos
	
	public Priquito(int x, int w, int h, double grav, double jumpf){
		posX = x;
		width = w;
		height = h;
		gravidade = grav;
		jumpForce = jumpf;
		
		colisao = new Rectangle((int) posX, posY, width, height);
	}
	
	public void morrer() {
		posX = (Main.frameW /2) - (Main.sprAvgSize/2);
	}
	
	private void pular() {
		if (!podePular) { 
			if (atualjumpFrame < jumpFrames) {
				atualjumpFrame++;
				posX-=jumpForce; // Aceleração? 
			}else {
				atualjumpFrame = 0;
				podePular = true;
				gravidadeInf = 0;
			}
		}
		
	}
	
	public void step() {
		colisao.x = (int)posX;
		
		if (podePular) {
			gravidadeInf+=0.1;
			posX+=gravidade*(1+gravidadeInf); // Gravidade
		}
		
		
		
		pular();
		
		
		
		if(posX + width >= Main.frameW) { // Colisão com o canto direito
			posX = Main.frameW - width;
		}
		
		if (posX < 0) { // Colisão com o canto esquerdo
			posX = 0;
		}
	}
	
	public void gstep(Graphics g) {
		g.setColor(Color.yellow);
		g.fillRect((int) posX, posY, width, height);
		//g.dispose();
	}
	
	
}
