package barbier;

import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SalleCoiffure {

    private Random r;
    private Lock lock;
    private Condition clientCoiffe;

    public SalleCoiffure() {
        this.r = new Random();
        this.lock = new ReentrantLock();
        this.clientCoiffe = this.lock.newCondition();
    }

    public void coiffer(Client client) throws InterruptedException {
        this.lock.lock();
        try {
            Thread.sleep(100 + r.nextInt(100));
            client.setCoiffee();
            this.clientCoiffe.signalAll();
        } finally {
            this.lock.unlock();
        }
    }

    public void attendreCoiffer(Client client) throws InterruptedException {
        this.lock.lock();
        try {
            while (!client.estCoiffe()) {
                this.clientCoiffe.await();
            }
        }finally {
            this.lock.unlock();
        }
    }
}
