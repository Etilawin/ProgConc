public class Esclave implements Runnable {

    private int req;
    private Client c;

    public Esclave(int req, Client c) {
        this.req = req;
        this.c = c;
    }

    @Override
    public void run() {
        try {
            if(this.req == 1) {
                Thread.sleep((int)(Math.random()*100));
            } else {
                while (true);
            }
            this.c.requeteServie(new ReponseRequete(this.c, (int) (Math.random()*100)));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
