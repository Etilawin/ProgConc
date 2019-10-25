package barbier;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SalleAttente {
    private Chaise[] chaises;
    private Lock chaiseLock;
    private Condition nonVide;
    private Queue<Chaise> ordreArrivee;

    public SalleAttente(int nbChaises) {
        this.chaises = new Chaise[nbChaises];
        this.chaiseLock = new ReentrantLock();
        this.nonVide = chaiseLock.newCondition();
        this.ordreArrivee = new ConcurrentLinkedQueue<>();
        for (int i = 0; i < nbChaises; i++) {
            this.chaises[i] = new Chaise();
        }
    }

    public boolean entrer(Client client) throws InterruptedException {
        this.chaiseLock.lock();
        try{
            for (Chaise chaise : chaises) {
                if (chaise.estLibre()) {
                    chaise.prendre(client);
                    this.ordreArrivee.add(chaise);
                    this.nonVide.signalAll();
                    return true;
                }
            }
        } finally {
            this.chaiseLock.unlock();
        }
        return false;
    }

    public Client chercherClient() throws InterruptedException {
        this.chaiseLock.lock();
        try {
            while (this.ordreArrivee.size() == 0) {
                System.out.println("Joel attend qu'il y ai des clients...");
                this.nonVide.await();
            }
            return this.ordreArrivee.remove().laisser();
        } finally {
            this.chaiseLock.unlock();
        }
    }
}
