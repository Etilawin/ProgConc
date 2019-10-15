package convoyeur;

import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

public class AleaStock {

    private ArrayList<AleaObjet> stock;
    private ReentrantLock stockLock;

    /**
     * Créer un nouveau stock d'objet au poids aléatoires
     *
     * @param taille int - Nombres d'objets dans le stock
     * @param poidsMin int - Poids minimum des objets
     * @param poidsMax int - Poids maximum des objets
     */
    public AleaStock(int taille, int poidsMin, int poidsMax) {
        // Initialisation du lock de consultation du stock
        this.stockLock = new ReentrantLock();

        // Initialisation du stock d'objets
        this.stock = new ArrayList<>();
        for(int i = 0; i < taille; i++) {
            this.stock.add(new AleaObjet(poidsMin, poidsMax));
        }
    }

    public int getNbObjet() {
        return this.stock.size();
    }

    /**
     * Retourne true si le stock est vide, false sinon
     *
     * @return boolean - True si le stock est vide
     */
    public boolean isVide() {
        boolean res = false;
        this.stockLock.lock();

        try {
            res =  this.stock.size() == 0;
        } finally {
            this.stockLock.unlock();
        }

        return res;
    }

    /**
     * Récupère le premier élément de la liste et le retourne aprèes l'avoir supprimé du stock, si le stock est vide la méthode renvoie null
     *
     * @return - AleaObjet - Le premier élément du stock
     */
    public AleaObjet prendreElement() {
        AleaObjet res = null;
        this.stockLock.lock();

        try {
            if(!this.isVide()) {
                res = this.stock.remove(0);
            }
        } finally {
            this.stockLock.unlock();
        }

        return res;
    }

}
