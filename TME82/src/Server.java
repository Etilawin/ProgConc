import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Server {

    // ----- Attributes -----

    private boolean running;

    private boolean handleClient;
    private boolean handleResponse;

    private ResponseHandler handler;
    private Thread handlerThread;

    private ExecutorCompletionService<ResponseRequest> completionService;

    private Lock clientLock;
    private Condition handleClientCondition;
    private Lock responseLock;
    private Condition handleResponseCondition;

    // ----- Constructors -----

    public Server() {
        this.running = true;

        this.handleClient = false;
        this.handleResponse = false;

        this.handler = new ResponseHandler(this);
        this.handlerThread = new Thread(this.handler);

        Executor executor = Executors.newFixedThreadPool(50);
//        Executor executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        this.completionService = new ExecutorCompletionService<>(executor);

        this.clientLock = new ReentrantLock();
        this.handleClientCondition = this.clientLock.newCondition();
        this.responseLock = new ReentrantLock();
        this.handleResponseCondition = this.responseLock.newCondition();
    }

    // ----- Getter -----



    // ----- Methods -----

    public void sendRequest(Request request) throws InterruptedException {
        this.clientLock.lock();

        try {
            while(this.handleClient) {
                this.handleClientCondition.await();
            }

            this.handleClient = true;
            System.out.println("Le serveur a reçu une requête " + request.getContent() + " du client " + request.getClient().getId());
            this.handler.addResponseToHandle(this.completionService.submit(new Slave(request, this)));
            this.handleClient = false;
        } finally {
            this.clientLock.unlock();
        }
    }

    public void sendResponse(ResponseRequest responseRequest) throws InterruptedException {
        this.responseLock.lock();

        try {
            while (this.handleResponse) {
                this.handleResponseCondition.await();
            }

            this.handleResponse = true;
            responseRequest.getClient().sendResponse(responseRequest);
            this.handleResponse = false;
        } finally {
            this.responseLock.unlock();
        }
    }

    public void start() {
        this.handlerThread.start();
    }

    public void stop() {
        this.running = false;
        this.handler.stop();
    }

}
