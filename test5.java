package TPP_TEST;

class CompteBanque{
	private int solde;
	
	CompteBanque(int solde){
		this.solde = solde;
	}
	int getSolde() {
		return this.solde;
	}
	void setSolde(int montant) {
		this.solde = montant;
	}
	void retirer(int montant) {
		 this.solde -= montant;
	}
}

class SanjiEtNamiJob implements Runnable{
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
					 System.out.println("Pas assez d’argent pour "+Thread.currentThread().getName());
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



public class test5 {

public static void main(String[] args) {
CompteBanque compte = new CompteBanque(500);
		
		SanjiEtNamiJob Sanji =new SanjiEtNamiJob(compte,100);
		SanjiEtNamiJob Nami = new SanjiEtNamiJob(compte,100);
		
		Thread SanjiT = new Thread(Sanji,"Sanji");
		Thread NamiT = new Thread( Nami,"Nami");

		SanjiT.start();
		NamiT.start();
		
		try {
			SanjiT.join(); 
			NamiT.join();

        } catch (InterruptedException e) {
        	System.out.println(Thread.currentThread().getName() + " interrupted. Exiting.");
            return;
        }
		 System.out.println("* "+Thread.currentThread().getName()+" solde: "+Sanji.totalWithdrawn);
		 System.out.println("* "+Thread.currentThread().getName()+" solde: "+Nami.totalWithdrawn);
		 System.out.println("* compte solde: "+compte.getSolde());

	}

}
