package convoyeur;

import java.util.ArrayList;

public class Chariot {

    private int poidsMax;
    private int nbMax;
    private ArrayList<AleaObjet> objets;
    private int poidsCourant;
    private boolean full;

    public Chariot(int poidsMax, int nbMax) {
        this.poidsMax = poidsMax;
        this.nbMax = nbMax;
        this.poidsCourant = 0;
        this.objets = new ArrayList<>();
    }

    public boolean isFull() {
        return (this.full || this.poidsCourant >= this.poidsMax || this.objets.size() == this.nbMax);
    }

    public synchronized void decharger() {
        this.full = false;
        this.objets = new ArrayList<>();
        this.poidsCourant = 0;
        System.out.println("Déchargement");
        notifyAll();
    }

    public synchronized void charger(AleaObjet o, int id) throws InterruptedException{
        while (this.isFull() || o.getWeight() + this.poidsCourant > this.poidsMax) {
            System.out.println("Le chargeur " + id + " attend de la place !");
            System.out.println(this.poidsCourant);
            System.out.println(o.getWeight());
            this.full = true;
            wait();
        }
        this.objets.add(o);
        this.poidsCourant += o.getWeight();
        System.out.println("Le chariot a reçu un objet de " + o.getWeight() + " kg");
    }
}
