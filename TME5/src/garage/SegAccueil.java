package garage;

import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SegAccueil {
    private boolean OQP;
    private Lock lock;
    private Condition dispo;

    public SegAccueil() {
        this.lock = new ReentrantLock();
        this.dispo = this.lock.newCondition();
        this.OQP = false;
    }

    public void reserver() throws InterruptedException{
        this.lock.lock();
        try {
            while(this.OQP)
                this.dispo.await();
            this.OQP = true;
        } finally {
            lock.unlock();
        }
    }

    public void liberer() {
        this.lock.lock();
        try {
            this.OQP = false;
            this.dispo.signalAll();
        } finally {
            this.lock.unlock();
        }
    }
}
