package guichet.physique;

import guichet.Session;

import java.awt.Toolkit;
import java.util.Timer;
import java.util.TimerTask;

public class MecanismeTemporisation {
	Toolkit beeper;
	Timer timer;
	Session session;
	Boolean tempsEcoule = false;
	
	public MecanismeTemporisation(Session s){
		beeper = Toolkit.getDefaultToolkit();
		timer = new Timer();
		session = s;
	}
	
	class Delais extends TimerTask {
		public void run(){
			for (int i=0; i<3; i++){
				beeper.beep();
				try{
					Thread.sleep(1000);
				} catch(InterruptedException e){ 	//incertains de InterruptedException 
					Thread.currentThread().interrupt();
				}
			}
		tempsEcoule = true; 	
		}	
	}
	
	public int compter(){
		timer.schedule(new Delais(), 5*1000);
		if (tempsEcoule){
			return 5;	//ne semble pas retourner quoi que ce soit
		}
		else {
			return session.state;
		}
	}
	
	public void arret(){
		timer.cancel();
	}
}
