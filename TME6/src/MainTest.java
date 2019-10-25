import barbier.*;

import java.io.*;
import java.util.Random;


public class MainTest {

    public static void main(String[] args) {
        try {
            Random r = new Random();
            String fichierComplet = (new BufferedReader(new FileReader(new File("./data/prenoms.json")))).readLine();

            int nbClients = 10 + r.nextInt(100);
            Client[] clients = new Client[nbClients];
            Thread[] threads = new Thread[nbClients];

            SalleAttente leSalonDeJoel = new SalleAttente(10);
            SalleCoiffure leCoinDeJoel = new SalleCoiffure();

            Barbier joel = new Barbier(leSalonDeJoel, leCoinDeJoel);
            Thread th = new Thread(joel);


            th.start();
            for (int i = 0; i < nbClients; i++) {
                clients[i] = new Client(String.valueOf(i), leSalonDeJoel, leCoinDeJoel);
                threads[i] = new Thread(clients[i]);
                Thread.sleep(r.nextInt(100));
                threads[i].start();
            }
            for (int i = 0; i < nbClients; i++) {
                threads[i].join();
            }
            th.interrupt();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
