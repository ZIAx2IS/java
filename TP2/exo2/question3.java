
class Banque {
	double[] comptes ;
	
	Banque (int n, double d){
		comptes = new double[n];
		for(int i =0; i< n ;i++) {
			comptes[i] = d;
		}
		
	}
	
	int size() {
		return comptes.length;
	}
	
	synchronized double soldeTotal() {
		double somme=0;
		for (int i=0; i < size(); i++) { 
			somme += comptes[i];
		}
		return somme; 
	}
	
	synchronized void transferer(int from,int to,double amount){

		 while (comptes[from] < amount) {
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
		}
		comptes[from] = comptes[from] - amount;
	    comptes[to]   = comptes[to] + amount; 
	    notifyAll();
	    System.out.printf("Transferred %f from %d to %d %n",amount,from,to); 
        System.out.printf("Total: %f%n ",soldeTotal()); 
	}
}

class Transfert implements Runnable{
	  Banque banque;
	  int from;
	  double amount;
	 
	  Transfert(Banque banque,int from,double amount){
		  this.banque = banque;
		  this.from = from;
		  this.amount = amount;
	  }
	  
	  public void run() {
	        while(true) {
	            int to = (int) (Math.random() * banque.size());
	            banque.transferer(from, to, amount);
	        }
	  }
}

public class test6 {
	public static void main(String[] args) {
	
		Banque banque = new Banque(5, 1000);
		Thread[] threads = new Thread[banque.size()];

		for (int i = 0; i < banque.size(); i ++) {
			double ammount = Math.random()*1000;
			Runnable r = new Transfert(banque,i,ammount);
			threads[i] = new Thread(r);
			threads[i].start();
		}
		
		for (int i = 0; i < banque.size(); i ++) {
			try {
				threads[i].join();
			}catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}

}
