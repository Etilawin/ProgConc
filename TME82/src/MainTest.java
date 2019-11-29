public class MainTest {
    public static void main(String[] args) {

        final int NB_CLIENTS = 10;

        Serveur s = new Serveur();
        Thread serv = new Thread(s);
        serv.start();

        Thread[] clients = new Thread[NB_CLIENTS];
        for (int i = 0; i < NB_CLIENTS; i++) {
            clients[i] = new Thread(new Client(s));
            clients[i].start();
        }

        try {
            for (int i = 0; i < NB_CLIENTS; i++) {
                clients[i].join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        serv.interrupt();
    }

}
