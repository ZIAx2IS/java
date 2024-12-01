package exo1;

public class Main {
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
