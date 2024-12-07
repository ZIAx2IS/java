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
		 if(comptes[from] >=  amount) {
		    
			comptes[from] = comptes[from] - amount;
			
			 // comptes[to]   = comptes[to] +  amount;  is a non-atomic operation
			 // it consists of multiple low-level operations
			 // read val of comptes[to] 
			 // Perform the addition
			 // Write the result back to comptes[to] 
			 // a thread can be interrupted or preempted while executing comptes[to]   = comptes[to] +  amount; 
			 // To illustrate the issue I split it 
			 // Thread.sleep() to illustrate interruption in the process :)
			 double temp = comptes[to] +  amount;
			
			 try {
				Thread.sleep(1000);
			 } catch (InterruptedException e) {
			
				e.printStackTrace();
			 }
			 comptes[to]  = temp;
			 System.out.println("Transferred " + amount + " from " + from + " to " + to);
			 System.out.println("Total: "+soldeTotal()); 
		 } 
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
			int to = (int) ( Math.random()*banque.size() ); 
			banque.transferer(from, to, amount);    
		  }
	  }
}

public class test6{
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
