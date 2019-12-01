public class MainTest {

    public final static int NB_CLIENTS = 10;

    public static void main(String[] args) {

        Server server = new Server();
        server.start();
        Thread[] clients = new Thread[MainTest.NB_CLIENTS];

        try {

            for (int i = 0; i < MainTest.NB_CLIENTS; i++) {
                clients[i] = new Thread(new Client(server));
                clients[i].start();
            }

            for (int i = 0; i < MainTest.NB_CLIENTS; i++) {
                clients[i].join();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
