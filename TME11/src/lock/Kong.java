package lock;

public class Kong extends Babouin {

    public Kong(Corde laCorde) {
        this.laCorde = laCorde;
    }

    @Override
    public String toString() {
        return "semaphore.Kong";
    }

    @Override
    public void run() {
        try {
            batifoler();
            laCorde.accederKong();
            System.out.println(this.toString() + " a pris la corde.");
            traverser();
            System.out.println(this.toString() + " est arrivé.");
            laCorde.lacherKong();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
