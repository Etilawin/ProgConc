import java.util.Arrays;
import java.util.Collection;

public class GroupeClients {

    private final int taille;
    private Client[] clients;
    private Thread[] threads;
    private NumeroReservation reservation;

    private static int cpt = 0;
    private final int id;

    private static final Object m = new Object();


    public GroupeClients(int taille, Restaurant chezJiji) {

        synchronized (m) {

            this.id = cpt++;

        }

        this.reservation = null;
        this.taille = taille;
        this.clients = new Client[taille];
        this.threads = new Thread[taille];

        for (int i = 0; i < taille; i++) {

            this.clients[i] = new Client(chezJiji, this);
            this.threads[i] = new Thread(this.clients[i]);
            this.threads[i].start();

        }

    }

    @Override
    public String toString() {
        return String.valueOf(this.id);
    }

    public void propagateInterruptedStatus() {
        for (Client client :
                this.clients) {
            client.setInterruptedStatus();
        }
    }

    public Collection<? extends Thread> getClients() {

        return Arrays.asList(this.threads);

    }

    public NumeroReservation getReservation() {
        return reservation;
    }

    public void setReservation(NumeroReservation reservation) {
        this.reservation = reservation;
    }

    public int getTaille() {
        return taille;
    }
}
