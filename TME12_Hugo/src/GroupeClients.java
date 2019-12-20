import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class GroupeClients {

    // ----- Attributes -----

    private boolean rejected;
    private int id;
    private int nbClients;
    private Integer numReservation;

    private Restaurant restaurant;

    private ArrayList<Client> clients;
    private ArrayList<Thread> clientsThreads;
    private ArrayList<Client> clientsArrives;

    private Lock reservationLock;

    // ----- Constructors -----

    public GroupeClients(int id, int nbClients, Restaurant restaurant) {
        this.rejected = false;
        this.id = id;
        this.nbClients = nbClients;
        this.numReservation = null;

        this.restaurant = restaurant;

        this.clients = new ArrayList<>();
        this.clientsThreads = new ArrayList<>();
        this.clientsArrives = new ArrayList<>();

        this.reservationLock = new ReentrantLock();

        // Construct all new clients
        for (int i = 0; i < this.nbClients; i++) {
            Client newClient = new Client(i, this);
            Thread newClientThread = new Thread(newClient);

            this.clients.add(newClient);
            this.clientsThreads.add(newClientThread);
        }

        // Launch all the threads
        for (Thread thread : this.clientsThreads) {
            thread.start();
        }
    }

    // ----- Getters -----

    public int getId() {
        return id;
    }

    public int getNbClients() {
        return this.nbClients;
    }

    public Integer getNumReservation() {
        return numReservation;
    }

    // ----- Class methods -----

    public void reserver(Client client) throws InterruptedException {
        try {
            this.reservationLock.lock();

            if(this.clientsThreads.get(client.getId()).isInterrupted() || this.rejected) {
                throw new InterruptedException();
            }

            if(this.numReservation == null) {

                System.out.println("Le client " + client.getId() + " essaye de réserver pour le groupe " + this.id);
                Integer numReservation = this.restaurant.reserver(this);
                if(numReservation != null) {

                    System.out.println("Le groupe " + this.id + " a trouvé de la place au numéro de réservation " + numReservation);
                    this.numReservation = numReservation;

                } else {

                    System.out.println("Le groupe " + this.id + " s'est fait rejeté...");
                    this.rejected = true;
                    for (Thread thread : this.clientsThreads) {
                        thread.interrupt();
                    }
                    throw new InterruptedException();

                }

            }

        } finally {

            this.reservationLock.unlock();

        }
    }

    public synchronized void estArrive(Client client) {
        this.clientsArrives.add(client);

        if(this.clientsArrives.size() == this.nbClients) {
            System.out.println("Tous les clients du gorupe " + this.id + " sont arrivées, ils peuvent passer commande !");
        }
    }

}
