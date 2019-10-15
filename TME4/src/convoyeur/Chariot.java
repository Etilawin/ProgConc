package convoyeur;

import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Chariot {

    private int poidsMax;
    private int nbObjetsMax;
    private ArrayList<AleaObjet> objets;
    private int nbObjetTotal;
    private int poidsCourant;
    private boolean chariotParti;
    private AleaStock stock;

    private ReentrantLock lockChariot;
    private Condition chariotPlein;
    private Condition chariotNonPlein;

    /**
     * Créer un nouveau chariot avec son poids et son nombre d'objet maximum.
     *
     * @param poidsMax int - Poids maximum que peut transporter le chariot
     * @param nbObjetsMax int - Nombre d'objets maximum que peut transporter le chariot
     */
    public Chariot(AleaStock stock, int poidsMax, int nbObjetsMax) {
        // Initialisation des attributs de classe
        this.poidsMax = poidsMax;
        this.nbObjetsMax = nbObjetsMax;
        this.poidsCourant = 0;
        this.stock = stock;
        this.nbObjetTotal = this.stock.getNbObjet();
        this.objets = new ArrayList<>();
        this.chariotParti = false;

        // Initialisation des condition et des locks
        this.lockChariot = new ReentrantLock();
        this.chariotPlein = this.lockChariot.newCondition();
        this.chariotNonPlein = this.lockChariot.newCondition();
    }

    /**
     * Retourn true si le chariot est plein avec l'objet qu'on essaye de charger ou si la limite d'objet est atteinte
     *
     * @param objet AleaObjet - L'objet que l'on veut charger
     * @return boolean - True si le chariot ne peut plus prendre d'objet
     */
    public boolean isPlein(AleaObjet objet) {
        boolean res = false;
        this.lockChariot.lock();

        try {
            res = (this.poidsCourant + objet.getPoids()) >= this.poidsMax || this.objets.size() == this.nbObjetsMax;
        } finally {
            this.lockChariot.unlock();
        }

        return res;
    }

    /**
     * Retourne si le stock est vide
     *
     * @return boolean - Tru si le stock est vide
     */
    public boolean isStockVide() {
        return this.stock.isVide();
    }

    public int getNbObjetTotal() {
        return nbObjetTotal;
    }

    /**
     * Enlève tous les objets du chariots
     *
     * @throws InterruptedException Si un problème de Thread
     */
    public void decharger() throws InterruptedException {
        this.lockChariot.lock();

        try {
            // Si le chariot n'est pas plein, on attend qu'il se remplisse
            while(!this.chariotParti) {
                System.out.println("Le déchargeur attend");
                this.chariotNonPlein.await();
            }

            // Si on peut décharger les objets on le fait et on sinal au chargeur que le chariot est revenu
            this.objets = new ArrayList<>();
            this.poidsCourant = 0;
            this.chariotParti = false;
            this.chariotPlein.signalAll();
            System.out.println("Déchargement");
        } finally {
            lockChariot.unlock();
        }
    }

    /**
     * Charge un objet dans le chariot
     *
     * @param objet AleaObjet - L'objet à charger
     * @param id int - L'identifiant du chargeur
     * @throws InterruptedException Si problème de Thread
     */
    public void charger(AleaObjet objet, int id) throws InterruptedException {
        this.lockChariot.lock();

        try {
            // Si le chariot est plein, on attend et on signal au déchargeur que le chariot est plein
            while(this.isPlein(objet) || this.chariotParti) {
                this.chariotParti = true;
                System.out.println("Le chargeur n°" + id + " attend que le chariot se vide");
                this.chariotNonPlein.signalAll();
                this.chariotPlein.await();
            }

            // Si on peut déposer l'objet dans le chariot, on met à jour la liste, le poids courrant et si le stock est vide
            this.objets.add(objet);
            this.poidsCourant += objet.getPoids();
            this.nbObjetTotal -= 1;
            System.out.println("Le chargeur n°" + id + " a déposer un objet de " + objet.getPoids() + "kg dans le chariot");
            System.out.println("Poids total : " + this.poidsCourant);
            System.out.println("Nombre d'objets : " + this.objets.size());

            if(this.isStockVide() && this.nbObjetTotal == 0) {
                this.chariotParti = true;
                this.chariotNonPlein.signalAll();
            }
        } finally {
            this.lockChariot.unlock();
        }
    }
}
