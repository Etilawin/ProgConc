package convoyeur;

public class Chargeur implements Runnable {

    private Chariot chariot;
    private AleaStock stock;
    private int id;

    /**
     * Initialise unn nouveau chargeur qui fait le lien entre un stock et un chariot
     *
     * @param chariot Chariot - Le chariot à charger
     * @param stock AleaStock - Le stock dans lequel prendre les objets
     * @param id int - L'identifiant du chargeur pour l'affichage
     */
    public Chargeur(Chariot chariot, AleaStock stock, int id) {
        this.chariot = chariot;
        this.stock = stock;
        this.id = id;
    }

    @Override
    public void run() {

        // Boucle de chargement du chariot, tant que le stock n'est pas vide
        while(!this.stock.isVide()) {
            AleaObjet objet = this.stock.prendreElement();
            if(objet != null) {
                try {
                    this.chariot.charger(objet, this.id);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
