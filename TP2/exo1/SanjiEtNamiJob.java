package exo1;

public class SanjiEtNamiJob implements Runnable{
	CompteBanque compte;
	int ammountToWithdraw;
	int totalWithdrawn;
	
	SanjiEtNamiJob(CompteBanque compte,int ammountToWithdraw){
		this.compte = compte;
		this.ammountToWithdraw = ammountToWithdraw;
	}
	
	public void run() {
		 demandeRetrait();
	}
	
	 void demandeRetrait() {
		while(true) {
			
			synchronized (compte) {	
				if(compte.getSolde() >=  ammountToWithdraw) {
					 System.out.println("- "+Thread.currentThread().getName()+" est sur le point de retirer"); 
					 compte.retirer( ammountToWithdraw);
					 totalWithdrawn +=  ammountToWithdraw;
					 System.out.println("- "+Thread.currentThread().getName() + " à compléter le retrait de "+ ammountToWithdraw );
				 }
				 else {
					 System.out.println("Pas assez d'argent pour "+Thread.currentThread().getName());
					 break;
				 }
			}
			
			
	
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
	
		}
	}
	
}