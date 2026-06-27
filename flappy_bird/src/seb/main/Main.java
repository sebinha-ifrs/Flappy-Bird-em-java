package seb.main;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JFrame;

public class Main extends Canvas implements Runnable, KeyListener {

	// Objetos
	
	private Thread thread;
	private JFrame frame;
	private BufferedImage bimage;
	
	public Priquito jogador;
	public ArrayList<Pinto> pintos;
	public Spawner spawn;
	
	/* Atributosn */
	
	// Thread
	private boolean rodando = false;
	private final double gps = 1000000000 / 90; // 30 g steps
	private final double sps = 1000000000 / 60; // 60 steps
	
	// Canvas
	private static final String frameTitulo = "Flappy Bird";
	public static final int sprAvgSize = 16;
	public static final int frameW = 24*9; // 216
	public static final int frameH = 24*16; // 384
	private static final int frameZ = 2;
	
	
	
	
	// Métodos
	
	
	public Main() {
		
		bimage = new BufferedImage(frameW, frameH, BufferedImage.TYPE_INT_RGB);
		frame = new JFrame(frameTitulo);
		criarFrame();
		
		thread = new Thread(this);
		startThread();
		
		jogador = new Priquito((frameW /2) - (sprAvgSize/2), sprAvgSize, sprAvgSize, 1, 3.5);
		
		pintos = new ArrayList<>();
		
		spawn = new Spawner();
		this.addKeyListener(this);
		
		
	}
	
	private void startThread() {
		if (rodando) return;
		rodando = true;
		thread.start();
	}
	
	private void stopThread() {
		if (!rodando) return;
		rodando = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}	
	}
	
	private void criarFrame() {
		this.setPreferredSize(new Dimension(frameW*frameZ, frameH*frameZ));
		
		frame.add(this);
		frame.pack();
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		
		frame.setVisible(true);
		
		this.requestFocus();
	}
	
	
	public static void main(String[] args) {
		Main main = new Main();
		
	}
	
	public void step() {
		jogador.step();
		
		for (int i = 0; i < pintos.size(); i++) {
			pintos.get(i).step(jogador, pintos);
		}
		
		spawn.step(pintos);
	}
	
	public void gstep() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = bimage.getGraphics();
		
		g.setColor(Color.black); // Repintar
		g.fillRect(0, 0, frameW, frameH);
		
		
		// Pintar objetos
		
		jogador.gstep(g);
		
		for (int i = 0; i < pintos.size(); i++) {
			pintos.get(i).gstep(g);
		}
		
		
		// Interface
		
		g.setFont(new Font("Arial", Font.BOLD, 10));
		g.setColor(Color.white);
		g.drawString("Pontos: " + Integer.toString(jogador.PONTOS), frameW-60, 24);
		
		g.dispose();
		
		// Colocar bimage 
		
		g = bs.getDrawGraphics();
		g.drawImage(bimage, 0,0, frameW * frameZ, frameH * frameZ, null);
		g.dispose();
		
		bs.show();
		
	}

	@Override
	public void run() {
		long lastT = System.nanoTime();
		long nowT, elapsedT;
		
		double accS = 0, accG = 0; // Acumulador
		int countS = 0, countG = 0; // Contador
		
		long gameClock = System.currentTimeMillis();
		//long segundos = 0;
		while(rodando) {
			nowT = System.nanoTime();
			elapsedT = nowT - lastT;
			lastT += elapsedT;
			
			accS+= elapsedT / sps;
			accG += elapsedT / gps;
			
			while(accS >= 1) {
				step();
				accS--;
				countS++;
			}
			
			if (accG >=1) {
				gstep();
				accG--;
				countG++;
			}
			
			if (System.currentTimeMillis() - gameClock >= 1000) {
				//segundos++;
				System.out.printf("S : 	STEPS :  %d	FRAMES :  %d\n\n", 
						 countS, countG);
				
				countS = 0;
				countG = 0;
				gameClock += 1000;
			}
		}
		stopThread();
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			if (jogador.podePular == true) {
				jogador.podePular = false;
			}
		}
	}

}
