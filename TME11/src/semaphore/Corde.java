package semaphore;

import utils.Position;

import java.util.concurrent.Semaphore;

public class Corde {

    private Position sensUtilisation = null;
    private int[] cpt = {0, 0};

    private boolean kongIsHere = false;

    private final Semaphore kong = new Semaphore(0);
    private final Semaphore kongBlocker = new Semaphore(0);
    private final Semaphore maxPont = new Semaphore(5);
    private final Semaphore mutex = new Semaphore(1);
    private final Semaphore[] semAccess = {new Semaphore(0), new Semaphore(0)};

    public void acceder(Position p) throws InterruptedException {

        this.mutex.acquire();
        this.cpt[p.index()]++;

        if(sensUtilisation == null) {
            sensUtilisation = p;
        }


        if (sensUtilisation != p) {
            mutex.release();
            semAccess[p.index()].acquire();
        } else {
            mutex.release();
        }
        maxPont.acquire();

    }

    public void lacher(Position p) throws InterruptedException {
        int p_inverse = (p.index() + 1) % 2;

        mutex.acquire();
        cpt[p.index()]--;
        maxPont.release();

        if(cpt[p.index()] == 0) {
            if (kongIsHere) {

                kong.release();

            } else if (cpt[p_inverse] > 0) {

                sensUtilisation = Position.values()[p_inverse];
                semAccess[p_inverse].release(cpt[p_inverse]);

            } else {

                sensUtilisation = null;

            }

        }
        mutex.release();
    }

    public void accederKong() throws InterruptedException {
        mutex.acquire();
        kongIsHere = true;
        mutex.release();
        kong.acquire();
    }

    public void lacherKong() throws InterruptedException {
        mutex.acquire();
        kongIsHere = false;
        if (cpt[0] > 0) {

            sensUtilisation = Position.values()[0];
            semAccess[0].release(cpt[0]);

        } else if (cpt[1] > 0) {

            sensUtilisation = Position.values()[1];
            semAccess[1].release(cpt[1]);

        } else {

            sensUtilisation = null;

        }
        mutex.release();
    }
}
