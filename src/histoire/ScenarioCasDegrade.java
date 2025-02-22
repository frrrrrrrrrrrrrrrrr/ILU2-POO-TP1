package histoire;
import personnages.Chef;
import personnages.Druide;
import personnages.Gaulois;
import villagegaulois.Village;
import villagegaulois.Etal;


public class ScenarioCasDegrade {
	public static void main(String[] args) {
		Etal etal = new Etal();
		
		//1
		etal.libererEtal();
		
		Gaulois vendeur = new Gaulois("Vendeur", 10);
		etal.occuperEtal(vendeur, "Produit", 15);
		
		//2a
		etal.acheterProduit(1, null);
		
		//2b
		Gaulois acheteur = new Gaulois("Acheteur", 10);
		try {
			etal.acheterProduit(-1, acheteur);
		}catch(IllegalArgumentException | IllegalStateException e) {
			e.printStackTrace();
		}
		
		etal.libererEtal();
		
		try {
			etal.acheterProduit(8, acheteur);
		}catch(IllegalStateException e) {
			e.printStackTrace();
		}

		
		System.out.println("Fin du test");
	}
}
