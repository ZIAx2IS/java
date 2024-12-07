
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


class Banque {
	double[] comptes ;
	
	private Lock bankLock = new ReentrantLock();
	private Condition soldeSuffisant = bankLock.newCondition();
	
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
		bankLock.lock();
		try{
			double somme=0;
			for (int i=0; i < size(); i++) { 
				somme += comptes[i];
			}
			return somme; 
		} finally{
			   bankLock.unlock();
		}
	}
	
	void transferer(int from,int to,double amount){
		 bankLock.lock();
		 try {
			while (comptes[from] < amount) {
				soldeSuffisant.await(1, TimeUnit.SECONDS);
			}
		        comptes[from] = comptes[from] - amount;
		        comptes[to]   = comptes[to] + amount; 
		        // after each transfer to the random acc wakeup all waiting threads to check 
		        // weither or not the acc we transfered to is the one they were wiating for 
		        soldeSuffisant.signalAll();
                System.out.printf("Transferred %f from %d to %d %n",amount,from,to); 
	            System.out.printf("Total: %f%n ",soldeTotal()); 
		    } 
		 catch(InterruptedException e){
			 e.printStackTrace();
		 }
		 finally {
		        bankLock.unlock();
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
	            int to = (int) (Math.random() * banque.size());
	            banque.transferer(from, to, amount);
	        }
	  }
}

public class question2 {
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
