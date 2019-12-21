package lock;

import utils.Position;

public class Babouin implements Runnable {

    protected Corde laCorde;
    protected Position position;
    private int id;
    private static int idcpt = 0;
    private final static Object m = new Object();

    public Babouin(Corde laCorde, Position position) {
        this.laCorde = laCorde;
        this.position = position;
        synchronized (m) {
            this.id = ++idcpt;
        }
    }

    public Babouin() {
    }

    protected void batifoler() throws InterruptedException{
        Thread.sleep((int) (Math.random() * 500) + 100);
    }

    protected void traverser() throws InterruptedException{
        Thread.sleep((int) (Math.random() * 400) + 100);
    }

    @Override
    public String toString() {
        return "Le babouin " + this.id + " depuis la position " + this.position;
    }

    @Override
    public void run() {
        try {
            batifoler();
            laCorde.acceder(position);
            System.out.println(this.toString() + " a pris la corde.");
            traverser();
            System.out.println(this.toString() + " est arrivé.");
            laCorde.lacher(position);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
