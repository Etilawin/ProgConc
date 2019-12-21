import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Client implements Runnable {

    private final Restaurant restaurant;
    private final GroupeClients groupe;
    private boolean interruptedStatus;

    private static int cpt = 0;
    private final int id;

    private static Lock lock = new ReentrantLock();

    Client(Restaurant chezJiji, GroupeClients groupeClients) {

        lock.lock();
        this.id = cpt++;
        lock.unlock();

        this.restaurant = chezJiji;
        this.groupe = groupeClients;
        this.interruptedStatus = false;
    }

    @Override
    public void run() {
        //            Thread.sleep((long) (Math.random()*200));

        boolean dernier;

        try {

            System.out.printf("Un des clients(%d) du groupe %s arrive au resto.%n", this.id, this.groupe);

            if (this.groupe.getReservation() == null) {

                if (!this.interruptedStatus) {

                    System.out.printf("Le client(%d) essaye de réserver pour son groupe %s%n", this.id, this.groupe);
                    NumeroReservation reservation = this.restaurant.reserver(this.groupe);

                    if (reservation != null) {

                        dernier = this.groupe.nouveauClientArrive();
                        Thread.sleep((long) (Math.random() * 500));
                        if (dernier) {

                            System.out.printf("Dernier arrivé(%d) ! Aller hop go manger !(groupe = %s)(taille = %d)(reservation = %d)%n", this.id, this.groupe, this.groupe.getTaille(), reservation.getId());

                        } else {

                            System.out.printf("Yes le client(%d) à pu rentrer dans le restaurant, il attend les autres. (groupe = %s) !%n", this.id, this.groupe);

                        }


                    } else {

                            System.out.printf("Dommage pour le client %d son groupe(%s) n'a pas eu de place %n", this.id, this.groupe);

                    }



                } else {

                    System.out.printf("Dommage pour le client %d son groupe(%s) n'a pas eu de place %n", this.id, this.groupe);

                }

            } else {

                dernier = this.groupe.nouveauClientArrive();
                Thread.sleep((long) (Math.random() * 500));

                if (dernier) {

                    System.out.printf("Dernier arrivé(%d) ! Aller hop go manger !(groupe = %s)(taille = %d)(reservation = %d)%n", this.id, this.groupe, this.groupe.getTaille(), this.groupe.getReservation().getId());

                } else {

                    System.out.printf("Yes le client(%d) à pu rentrer dans le restaurant, il attend les autres. (groupe = %s) !%n", this.id, this.groupe);

                }

            }

        } catch ( InterruptedException e ) {
            e.printStackTrace();
        }

    }

    void setInterruptedStatus() {

        this.interruptedStatus = true;

    }
}
