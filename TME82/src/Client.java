public class Client implements Runnable {

    private int nbRequete;
    private Serveur serv;
    private int id;
    private static int idcpt = 0;
    private boolean requestReceived;

    public Client(Serveur serv) {
        this.serv = serv;
        this.id = idcpt++;
        this.requestReceived = true;
    }

    public synchronized void requeteServie(ReponseRequete r) {
        this.requestReceived = true;
        notifyAll();
    }

    public synchronized void attendreReponse() throws InterruptedException{
        while(!this.requestReceived) {
            wait();
        }
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                '}';
    }

    @Override
    public void run() {
        try {

            for (int i = 0; i < 5; i++) {

                if(this.id % 3 == 0) {
                    System.out.println(this.id + " soumet une requete de type " + 2);
                    serv.soumettreRequete(2, this);
                } else {
                    System.out.println(this.id + " soumet une requete de type " + 1);
                    serv.soumettreRequete(1, this);
                }

                this.requestReceived = false;
                System.out.println(this.id + " attend la rep");
                attendreReponse();

            }

            System.out.println("Fin du client " + this.id);

        } catch (InterruptedException e) {

            e.printStackTrace();

        }
    }

}
