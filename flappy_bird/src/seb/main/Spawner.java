package seb.main;

import java.util.ArrayList;
import java.util.Random;

public class Spawner {
	
	
	private final int frames = 180;
	private int frameAtual = 0;
	private final int buraco = 80;
	
	public void step(ArrayList<Pinto> pintos) {
		if (frameAtual < frames) {
			frameAtual++;
		}else {
			
			int tamanho1 = new Random().nextInt(100);
			int tamanho2 = Main.frameW - (tamanho1 + buraco );
			pintos.add(new Pinto(Main.frameW-tamanho1, 50, tamanho1, 24, true));
			pintos.add(new Pinto(Main.frameW-(tamanho1+buraco+tamanho2), 50, tamanho2, 24, false));
			frameAtual = 0;
		}
	}

}
