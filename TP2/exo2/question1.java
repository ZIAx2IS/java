
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
	
	double soldeTotal() {
		double total = 0;
		for(int i = 0; i < size() ; i++) {
			total += comptes[i];
		}
		return total;
	}
	void transferer(int from,int to,double amount) {
		 if( exist(from,amount)) {
		    // reads notes to understand what am I doing here 
			 double temp = comptes[from] - amount;
			 try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			
				e.printStackTrace();
			}
			 comptes[from] = temp;
			 temp = comptes[to] +  amount;
			 comptes[to]  = temp;
			
		 } 
	}
	boolean exist(int index,double amount) {
		    if( comptes[index] >=  amount)
		    return true;
		    return false;
	}
	
}

class Transfert implements Runnable{
	  Banque banque;
	  int from;
	  double amount;
	  Transfert(Banque banque,int from,int amount){
		  this.banque = banque;
		  this.from = from;
		  this.amount = amount;
	  }
	  public void run() {
		  while(true) {
			  if (banque.exist(from,amount)) {
				  int to = (int) ( Math.random()*banque.size() ); 
				  banque.transferer(from, to, amount);  
				  System.out.println("Transferred " + amount + " from " + from + " to " + to);
			  }
			  else break;
		  }
	  }
}

public class question1 {
	public static void main(String[] args) {
		Banque banque = new Banque(5, 1000);
		Thread[] threads = new Thread[10];
		
		for (int i = 0; i < banque.size(); i ++) {
			Runnable r = new Transfert(banque,i,500);
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
		System.out.println(banque.soldeTotal()); 
	}

}
