package garage;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SegTournant implements Runnable {

    private Lock lock;
    private Condition estAppele;
    private Condition estDisponible;
    private Condition estEnDeplacement;
    private Condition estRentrant;
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
        this.estRentrant = this.lock.newCondition();
        this.estVide = this.lock.newCondition();
        this.posAppel = -1;
        this.pool = pool;
        this.dispo = false;
        this.entre = false;
        this.loco = null;
        this.deplacement = false;
    }

    private void attendreAppel() throws InterruptedException {
        this.lock.lock();
        try {
            while(this.posAppel == -1) {
                this.estAppele.await();
            }
        } finally {
            this.lock.unlock();
        }
    }

    private void seDeplacer() throws InterruptedException {
        this.lock.lock();
        try {
            Thread.sleep(this.posAppel * 100);
            this.estEnDeplacement.signalAll();
        } finally {
            this.lock.unlock();
        }
    }

    public void appeler(int id) throws InterruptedException{
        this.lock.lock();
        try {
            while(!this.dispo) {
                this.estDisponible.await();
            }
            this.posAppel = id;
            this.estAppele.signalAll();
        } finally {
            this.lock.unlock();
        }
    }

    public void attendrePositionOK() throws InterruptedException {
        while(this.deplacement)
            this.estEnDeplacement.await();
    }

    public void entrer(Loco loco) {
        this.lock.lock();
        try {
            this.loco = loco;
            this.entre = false;
            this.estRentrant.signalAll();
        } finally {
            this.lock.unlock();
        }
    }

    private void attendreEntree() throws InterruptedException{
        this.lock.lock();
        try {
            while(this.entre)
                this.estRentrant.await();
        } finally {
            this.lock.unlock();
        }
    }

    public void sortir(Loco loco) {
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
