package garage;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SegTournant implements Runnable {

    private Lock lock;
    private Condition estAppele;
    private Condition estDisponible;
    private Condition estEnDeplacement;
    private Condition estRentre;
    private Condition estVide;
    private int posAppel;
    private boolean dispo;
    private boolean deplacement;
    private boolean entre;
    private PoolHangars pool;
    private Loco loco;

    public SegTournant(PoolHangars pool) {
        this.lock = new ReentrantLock();
        this.estAppele = this.lock.newCondition();
        this.estDisponible = this.lock.newCondition();
        this.estEnDeplacement = this.lock.newCondition();
        this.estRentre = this.lock.newCondition();
        this.estVide = this.lock.newCondition();
        this.posAppel = -1;
        this.pool = pool;
        this.dispo = true;
        this.entre = false;
        this.loco = null;
        this.deplacement = false;
    }

    void appeler(int pos) throws InterruptedException{
        this.lock.lock();
        try {
            while(!this.dispo)
                this.estDisponible.await();
            this.dispo = false;
            this.posAppel = pos;
            this.estAppele.signalAll();
        } finally {
            this.lock.unlock();
        }
    }

    private void attendreAppel() throws InterruptedException {
        this.lock.lock();
        try {
            while(this.posAppel == -1)
                this.estAppele.await();
            this.deplacement = true;
        } finally {
            this.lock.unlock();
        }
    }

    private void seDeplacer() throws InterruptedException {
        this.lock.lock();
        try {
            Thread.sleep(this.posAppel * 100);
            this.deplacement = false; // Fini de se déplacer, on signalAll
            this.estEnDeplacement.signalAll();
        } finally {
            this.lock.unlock();
        }
    }

    void attendrePositionOK() throws InterruptedException {
        this.lock.lock();
        try {
            while (this.deplacement) // Dès qu'on est plus en déplacement on passe à l'action suivante
                this.estEnDeplacement.await();
        } finally {
            this.lock.unlock();
        }
    }

    void sortir(Loco loco) {
        this.lock.lock();
        try {
            this.loco = null;
            this.estVide.signalAll();
        } finally {
            this.lock.unlock();
        }
    }

    private void attendreVide() throws InterruptedException{
        this.lock.lock();
        try {
            while(this.loco != null)
                this.estVide.await();
            this.posAppel = -1;
            this.dispo = true;
            this.entre = false;
            this.estDisponible.signalAll();
        } finally {
            this.lock.unlock();
        }
    }

    void entrer(Loco loco) {
        this.lock.lock();
        try {
            this.loco = loco;
            this.posAppel = pool.getFreeHangar();
            this.entre = true; // C'est bon le train est entré on peut continuer
            this.estRentre.signalAll();
        } finally {
            this.lock.unlock();
        }
    }

    synchronized int getPosition() {
        return posAppel;
    }

    private void attendreEntree() throws InterruptedException{
        this.lock.lock();
        try {
            while(!this.entre)
                this.estRentre.await();
        } finally {
            this.lock.unlock();
        }
    }

    public void run() {
        try {
            while (true) {
                attendreAppel();
                seDeplacer();
                attendreEntree();
                seDeplacer();
                attendreVide();
            }
        }
        catch (InterruptedException e) {
            System.out.println("Terminaison sur interruption du segment tournant");
        }
    }
}
