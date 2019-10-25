import garage.*;

public class MainTest {
    public static void main(String[] args) {
        int N  = 20;
        PoolHangars pool = new PoolHangars(N);
        SegAccueil accueil = new SegAccueil();
        SegTournant tournant = new SegTournant(pool);
        Loco[] locomotives = new Loco[N];
        for (int i = 0; i < N; i++) {
            locomotives[i] = new Loco(accueil, tournant, pool);
        }
        Thread[] threads = new Thread[N];
        Thread tournantThread = new Thread(tournant);
        tournantThread.start();
        for (int i = 0; i < N; i++) {
            threads[i] = new Thread(locomotives[i]);
            threads[i].start();
        }
        try {
            for (int i = 0; i < N; i++) {
                threads[i].join();
            }
            tournantThread.join();
        } catch(InterruptedException e) {
            e.printStackTrace();
        }

    }
}
