package lock;

import utils.Position;

public class MainTest {
    public static void main(String[] args) {

        int nbBabouins = 20;

        Babouin[] babs = new Babouin[nbBabouins];
        Thread[] babouins = new Thread[nbBabouins];
        Corde corde = new Corde();

        for (int i = 0; i < nbBabouins; i++) {
            Position pos = Position.SUD;
            if (Math.random() < 0.5) {
                pos = Position.NORD;
            }
            babs[i] = new Babouin(corde, pos);
            babouins[i] = new Thread(babs[i]);
            babouins[i].start();
        }

        Kong kong = new Kong(corde);
        Thread k = new Thread(kong);
        k.start();

        try {
            for (int i = 0; i < nbBabouins; i++) {
                babouins[i].join();
            }
            k.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
