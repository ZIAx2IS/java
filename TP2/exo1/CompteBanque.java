package exo1;

public class CompteBanque{
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