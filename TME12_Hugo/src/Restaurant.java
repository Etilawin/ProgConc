import java.util.concurrent.Semaphore;

public class Restaurant {

    // ----- Attributes -----

    private int nbTables;
    private int nextNumReservation;

    private Semaphore semaphoreTables;

    // ----- Constructors -----

    public Restaurant(int nbTables) {
        this.nbTables = nbTables;
        this.nextNumReservation = 1;

        this.semaphoreTables = new Semaphore(this.nbTables, true);
    }

    // ----- Getters -----

    public int getNbTables() {
        return nbTables;
    }

    // ----- Class methods -----

    public Integer reserver(GroupeClients groupe) throws InterruptedException {
        Integer res = null;

        // Round the number of clients to know the number of wanted table
        int nbTableVoulu = (groupe.getNbClients() % 2 == 0 ? groupe.getNbClients() : groupe.getNbClients() + 1) / 2;

        if(this.semaphoreTables.availablePermits() >= nbTableVoulu) {

            System.out.println("Le groupe " + groupe.getId() + " obtient une réservation pour " + nbTableVoulu + " tables !");
            this.semaphoreTables.acquire(nbTableVoulu);
            res = this.nextNumReservation++;

        } else {

            System.out.println("Le groupe " + groupe.getId() + " n'a pas put trouver de place pour " + nbTableVoulu + " tables...");

        }

        return res;
    }

}
