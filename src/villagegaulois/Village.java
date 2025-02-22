package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche;

	public Village(String nom, int nbVillageoisMaximum, int nbEtals) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		marche = new Marche(nbEtals);
	}


	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}

	public Gaulois trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			if (gaulois.getNom().equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}

	public String afficherVillageois() throws VillageSansChefException {
		if(chef == null) {
			throw new VillageSansChefException("Pas de chef dans le village.");
		}
		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef "
					+ chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom()
					+ " vivent les légendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}
	
	
	private static class Marche{
		private Etal[] etals;
		
		private Marche(int nbEtals) {
			etals = new Etal[nbEtals];
			for (int i = 0; i < etals.length; i++) {
				etals[i] = new Etal();
			}
		}
		
		private void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
			// TO DO: try catch
			if(! etals[indiceEtal].isEtalOccupe()) {
				etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
			}
		}
		
		private int trouverEtalLibre() {
			for (int i = 0; i < etals.length; i++) {
				if(!etals[i].isEtalOccupe()) {
					return i;
				}
			}
			 return -1;
		}
		
		private Etal[] trouverEtals(String produit) {
			int nbEtalsPrdt = 0;
			for (int i = 0; i < etals.length; i++) {
				if(etals[i].isEtalOccupe() && etals[i].contientProduit(produit)) {
					nbEtalsPrdt ++;
				}
			}
			
			Etal[] etalsPrdt = new Etal[nbEtalsPrdt];
			int cpt = 0;
			
			for (int i = 0; i < etals.length && cpt < nbEtalsPrdt; i++) {
				if(etals[i] != null && etals[i].contientProduit(produit)) {
					etalsPrdt[cpt] = etals[i];
					cpt ++;
				}
			}
			
			return etalsPrdt;
		}
		
		private Etal trouverVendeur(Gaulois gaulois) {
			for (int i = 0; i < etals.length; i++) {
				if(etals[i].isEtalOccupe() && etals[i].getVendeur() == gaulois) {
					return etals[i];
				}
			}
			
			return null;
		}
		
		private String afficherMarche() {
			String retour = "";
			int nbEtalVide = 0;
			for (int i = 0; i < etals.length; i++) {
				if(etals[i].isEtalOccupe()) {
					retour += etals[i].afficherEtal();
				}else {
					nbEtalVide ++;
				}
			}
			
			return retour + "Il reste " +nbEtalVide + " �tals non utilis�s dans le march�.\n";
			
		}
	}
	
	public String installerVendeur(Gaulois vendeur, String produit,int nbProduit) {
		StringBuilder retour = new StringBuilder();
		retour.append (vendeur.getNom() + "cherche un endroit pour vendre " + nbProduit + " " + produit + ".\n");
		int indiceEtal = marche.trouverEtalLibre();
		if(indiceEtal >=0 ) {
			marche.utiliserEtal(indiceEtal, vendeur, produit, nbProduit);
			retour.append("Le vendeur "+ vendeur.getNom() + " vend des " + produit + " � l'�tal n�" +( indiceEtal + 1) +"\n");
		}
		else {
			retour.append( "Plus de place pour le vendeur " + vendeur.getNom() + "\n");
		}
		
		return retour.toString();
	}
	
	public String rechercherVendeursProduit(String produit) {
		StringBuilder retour = new StringBuilder();
		Etal[] etalsPrdt = marche.trouverEtals(produit);
		if(etalsPrdt.length == 0) {
			retour.append( "Il n'y a pas de vendeur qui propose des " +produit+" au march�.");
		}else {
			retour.append("Les vendeurs qui proposent des " + produit +" sont :\n");
			for (int i = 0; i < etalsPrdt.length; i++) {
				retour.append("- " + etalsPrdt[i].getVendeur().getNom() + "\n");
			}
		}
		
		return retour.toString();
		
	}
	
	public Etal rechercherEtal(Gaulois vendeur) {
		return marche.trouverVendeur(vendeur);
	}
	
	
	public String partirVendeur(Gaulois vendeur) {
		String retour;
		Etal etalVdr = marche.trouverVendeur(vendeur);
		if(etalVdr != null) {
			retour = etalVdr.libererEtal() + "\n";
		}else {
			retour = "Le vendeur " + vendeur.getNom() + " n'�tait pas au march� aujourd'hui !\n";
		}
		
		return retour;
	}
	
	public String afficherMarche() {
		return "Le march� du village \" "+ nom + "\" poss�de plusieurs �tals :\n" + marche.afficherMarche() + "\n";
	}
	
}