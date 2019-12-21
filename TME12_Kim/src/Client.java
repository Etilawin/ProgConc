public class Client implements Runnable {

    private final Restaurant restaurant;
    private final GroupeClients groupe;
    private boolean interruptedStatus;

    private static int cpt = 0;
    private final int id;

    private static final Object m = new Object();

    public Client(Restaurant chezJiji, GroupeClients groupeClients) {

        synchronized (m) {

            this.id = cpt++;

        }

        this.restaurant = chezJiji;
        this.groupe = groupeClients;
        this.interruptedStatus = false;
    }

    @Override
    public void run() {
        //            Thread.sleep((long) (Math.random()*200));

        System.out.printf("Un des clients(%d) du groupe %s arrive au resto.%n", this.id, this.groupe);
        if ( !this.interruptedStatus ) {

            if (this.groupe.getReservation() == null) {

                System.out.printf("Le client(%d) essaye de réserver pour son groupe %s%n", this.id, this.groupe);
                NumeroReservation reservation = this.restaurant.reserver(this.groupe);

                if ( reservation == null ) {
                    System.out.printf("Arf impossible de réserver pour le groupe %s par le client %d%n", this.groupe, this.id);
                    this.groupe.propagateInterruptedStatus();

                } else {
                    this.groupe.setReservation(reservation);
                    System.out.printf("Yes le client(%d) à pu réserver pour son groupe(%s) !%n", this.id, this.groupe);
                }
            }

        } else {
            System.out.printf("Dommage pour le client %d son groupe(%s) n'a pas eu de place %n", this.id, this.groupe);
        }


    }

    public void setInterruptedStatus() {
        this.interruptedStatus = true;
    }
}
