public class Client implements Runnable {

    // ----- Attributes -----

    private int id;

    private GroupeClients groupe;

    // ----- Constructors -----

    public Client(int id, GroupeClients groupe) {
        this.id = id;
        this.groupe = groupe;
    }

    // ----- Getters -----

    public int getId() {
        return id;
    }

    public GroupeClients getGroupe() {
        return groupe;
    }

    // ----- Class methods -----

    @Override
    public void run() {
        try {

            this.groupe.reserver(this);

            if(this.groupe.getNumReservation() != null) {
                System.out.println("Le client " + this.id + " du groupe " + this.groupe.getId() + " se rend au restaurant !");
                Thread.sleep((int) (Math.random() * 500) + 200);
                System.out.println("Le client " + this.id + " du groupe " + this.groupe.getId() + " est arrivé au restaurant !");
                this.groupe.estArrive(this);
            }

        } catch (InterruptedException e) {

            System.out.println("Le client " + this.id + " abandonne avec le reste de son groupe " + this.groupe.getId() + "...");

        }
    }
}
