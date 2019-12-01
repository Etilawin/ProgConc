public class Client implements Runnable {

    // ----- Attributes -----

    private boolean haveToWait;
    private Server server;
    private int id;
    private static int idCpt = 0;

    // ----- Constructors -----

    public Client(Server server) {
        this.haveToWait = false;
        this.server = server;
        this.id = ++ Client.idCpt;
    }

    // ----- Getter -----

    public int getId() {
        return this.id;
    }

    // ----- Methods -----

    public synchronized void sendResponse(ResponseRequest responseRequest) {
        System.out.println("Le client " + this.id + " a reçu la réponse " + responseRequest.getContent());
        this.haveToWait = false;
        this.notifyAll();
    }

    private synchronized void waitResponse() throws InterruptedException {
        while (this.haveToWait) {
            this.wait();
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
                    System.out.println("Le client " + this.id + " a envoyé une requête IMPOSSIBLE");
                    this.server.sendRequest(new Request(this, 1, "IMPOSSIBLE"));
                } else {
                    System.out.println("Le client " + this.id + " a envoyé une requête SIMPLE");
                    this.server.sendRequest(new Request(this, 0, "SIMPLE"));
                }

                this.haveToWait = true;
                this.waitResponse();

            }

            if(this.id == MainTest.NB_CLIENTS) {
                System.out.println("Le client " + this.id + " a envoyé une requête STOP");
                this.server.sendRequest(new Request(this, -1, "STOP"));
            }

        } catch (InterruptedException e) {

            e.printStackTrace();

        }
    }
}
