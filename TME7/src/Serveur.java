public class Serveur implements Runnable {

    private boolean possedeRequete;
    private int req;
    private Client c;


    public synchronized void soumettre(int i, Client c) throws InterruptedException {
        while (this.possedeRequete) {
            wait();
        }
        System.out.println("Le serveur à reçu la requête de " + c);
        this.possedeRequete = true;
        this.c = c;
        this.req = i;
        notifyAll();
    }

    private synchronized void traiterRequete() throws InterruptedException {
        Esclave e = new Esclave(this.req, this.c);
        new Thread(e).start();
        this.possedeRequete = false;
        notifyAll();
    }

    private synchronized void attendreRequete() throws InterruptedException{
        while(!possedeRequete) {
            wait();
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                attendreRequete();
                traiterRequete();
            }
        }
        catch (InterruptedException e) {
            System.out.println("Serveur interrompu");
        }
    }
}
