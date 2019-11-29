import java.util.concurrent.Callable;

public class Esclave implements Callable<ReponseRequete> {

    private int req;
    private Client c;

    public Esclave(int req, Client c) {
        this.req = req;
        this.c = c;
    }

    @Override
    public ReponseRequete call() {
        try {

            if(this.req == 1) {
                Thread.sleep((int)(Math.random()*100));
            } else {
                while (true);
            }
            return new ReponseRequete(this.c, (int) (Math.random()*100));

        } catch (InterruptedException e) {

            e.printStackTrace();
            return null;

        }
    }

}
