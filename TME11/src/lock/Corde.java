package lock;

import utils.Position;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Corde {

    private Position sensUtilisation = null;
    private int surLePont = 0;

    private boolean kongHasArrived = false;

    private Lock lock = new ReentrantLock();
    private Condition estLibre = lock.newCondition();
    private Condition estPasRempli = lock.newCondition();
    private Condition pontPourKong = lock.newCondition();

    public void acceder(Position p) throws InterruptedException {
        lock.lock();
        try {

            while(sensUtilisation != p) {
                if (sensUtilisation == null) {
                    sensUtilisation = p;
                } else {
                    estLibre.await();
                }
            }

            while(surLePont >= 5) {
                estPasRempli.await();
            }

            surLePont++;


        } finally {
            lock.unlock();
        }
    }

    public void lacher(Position p) {
        lock.lock();
        try {

            surLePont--;

            estPasRempli.signalAll();

            if ( surLePont == 0 ) {
                if (kongHasArrived) {
                    pontPourKong.signalAll();
                } else {
                    sensUtilisation = null;
                    estLibre.signalAll();
                }
            }

        } finally {
            lock.unlock();
        }




    }

    public void accederKong() throws InterruptedException {
        lock.lock();
        try {
            kongHasArrived = true;
            pontPourKong.await();
        } finally {
            lock.unlock();
        }
    }

    public void lacherKong() {
        lock.lock();
        try {
            sensUtilisation = null;
            kongHasArrived = false;
            estLibre.signalAll();
        } finally {
            lock.unlock();
        }
    }



}
