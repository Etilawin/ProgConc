package convoyeur;

public class Dechargeur implements Runnable {

    private Chariot chariot;

    /**
     * Créer un nouveau déchargeur pour le chariot
     *
     * @param chariot Chariot - Le chariot à décharger
     */
    public Dechargeur(Chariot chariot) {
        this.chariot = chariot;
    }

    @Override
    public void run() {
        while(!chariot.isStockVide() || this.chariot.getNbObjetTotal() != 0) {
            try {
                chariot.decharger();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
