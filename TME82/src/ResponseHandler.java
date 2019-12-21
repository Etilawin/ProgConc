import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Future;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ResponseHandler implements Runnable {

    // ----- Attributes -----

    private boolean running;
    private Server server;
    private ConcurrentLinkedQueue<Future<ResponseRequest>> responses;

    private Lock queueLock;

    // ----- Constructors -----

    public ResponseHandler(Server server) {
        this.running = true;
        this.server = server;
        this.responses = new ConcurrentLinkedQueue<>();

        this.queueLock = new ReentrantLock();
    }

    // ----- Methods -----

    public void stop() {
        this.running = false;
    }

    public void addResponseToHandle(Future<ResponseRequest> response) {
        this.queueLock.lock();
        this.responses.add(response);
        this.queueLock.unlock();
    }

    @Override
    public void run() {
        while (this.running || this.responses.size() > 0) {
            this.queueLock.lock();

            try {
                if(this.responses.size() > 0) {
                    Future<ResponseRequest> future = this.responses.poll();
                    if(future != null && future.isDone()) {
                        this.server.sendResponse(future.get());
                    } else {
                        this.responses.add(future);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                this.queueLock.unlock();
            }
        }
    }

}
