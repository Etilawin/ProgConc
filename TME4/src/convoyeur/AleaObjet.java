package convoyeur;

import java.util.Random;

public class AleaObjet {

    private int poids;

    /**
     * Créer un nouvel objet avec un poids aléatoire compris entre les arguments
     *
     * @param poidsMin int - Poids minimum de l'objet
     * @param poidsMax int - Poids maximum de l'objet
     */
    public AleaObjet(int poidsMin, int poidsMax) {
        // Initialisation de l'objet avec un poids aléatoire
        Random rand = new Random();
        this.poids = poidsMin + rand.nextInt(poidsMax-poidsMin);
    }

    /**
     * Récupération du poids de l'objet
     *
     * @return int - Le poids de l'objet
     */
    public int getPoids() {
        return poids;
    }

}
