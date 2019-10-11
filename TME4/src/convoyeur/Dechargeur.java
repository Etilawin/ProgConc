package convoyeur;

public class Dechargeur implements Runnable {

    private Chariot chariot;
   // private AleaStock stock;

    public Dechargeur(Chariot chariot, AleaStock stock) {
        this.chariot = chariot;
        this.stock = stock;
    }

    @Override
    public void run() {
        while(!this.stock.isEmpty() || this.chariot.isFull()) {
            if(chariot.isFull()) {
                chariot.decharger();
            }
        }
    }
}
