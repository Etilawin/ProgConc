package convoyeur;

public class Chargeur implements Runnable {
    Chariot chariot;
    AleaStock stock;

    private static int id = 0;
    private int myid;

    public Chargeur(Chariot chariot, AleaStock stock) {
        this.chariot = chariot;
        this.stock = stock;
        synchronized (this) {
            this.myid = Chargeur.id++;
        }
    }

    @Override
    public void run() {
        while(!this.stock.isEmpty()) {
            AleaObjet obj = this.stock.prendreElement();
            try {
                this.chariot.charger(obj, this.myid);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
