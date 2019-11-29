import javax.annotation.processing.Completions;
import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Serveur implements Runnable {

    private boolean possedeRequete;
    private int req;
    private Client c;
    private CompletionService<ReponseRequete> completionService;
    private Lock clientLock;
    private Condition aRequete;
    private ConcurrentLinkedQueue<Future<ReponseRequete>> queue;

    public Serveur() {

        Executor executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        this.completionService = new ExecutorCompletionService<>(executor);
        this.clientLock = new ReentrantLock();
        this.queue = new ConcurrentLinkedQueue<>();

    }


    public void soumettreRequete(int i, Client c) throws InterruptedException {

        this.clientLock.lock();

        try {

            while (this.possedeRequete) {
                this.aRequete.await();
            }

            System.out.println("Le serveur à reçu la requête de " + c);
            this.possedeRequete = true;
            this.c = c;
            this.req = i;

        } finally {

            this.clientLock.unlock();

        }

    }

    private void traiterRequete() throws InterruptedException {

        this.clientLock.lock();

        try {

            Esclave e = new Esclave(this.req, this.c);
            // TODO
            this.possedeRequete = false;
            this.aRequete.signalAll();

        } finally {

            this.clientLock.unlock();

        }

    }

//    private void attendreRequete() throws InterruptedException{
//        while(!possedeRequete) {
//            this.aRequete.await();
//        }
//    }

    @Override
    public void run() {

        try {

            while (true) {
                if (this.possedeRequete) {
                    traiterRequete();
                }
                if (!this.queue.isEmpty() && this.queue.peek().isDone()) {
                    ReponseRequete r = this.queue.remove().get();
                    r.getC().requeteServie(r);
                }


            }

        } catch (InterruptedException e) {

            System.out.println("Serveur interrompu !");
            System.out.println("Attente pour vider la queue ... ");
            try {
                while(!this.queue.isEmpty()) {
                    ReponseRequete r = this.queue.remove().get();
                    r.getC().requeteServie(r);
                    Thread.sleep(100);
                }
            } catch (InterruptedException | ExecutionException f) {
                f.printStackTrace();
            }

        } catch (ExecutionException e) {

            System.out.println("Unexpected exception !");

        }

    }

}
