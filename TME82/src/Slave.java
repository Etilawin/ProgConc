import java.util.concurrent.Callable;

public class Slave implements Callable<ResponseRequest> {

    // ----- Attributes -----

    private Request request;
    private Server server;

    // ----- Constructors -----

    public Slave(Request request, Server server) {
        this.request = request;
        this.server = server;
    }

    // ----- Methods -----

    @Override
    public ResponseRequest call() {
        try {

            ResponseRequest result = null;

            switch (this.request.getType()) {

                case (0):
                    Thread.sleep((long) (Math.random() * 500));
                    result = new ResponseRequest(this.request.getClient(), 0, "SIMPLE_RESPONSE");
                    break;
                case (1):
                    while (true);
                case (-1):
                    this.server.stop();
                    result = new ResponseRequest(this.request.getClient(), -1, "STOP_RESPONSE");
                    break;

            }

            return result;

        } catch (InterruptedException e) {

            e.printStackTrace();
            return null;

        }
    }

}
