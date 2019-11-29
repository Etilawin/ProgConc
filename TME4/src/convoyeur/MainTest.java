package convoyeur;

public class MainTest {
    public static void main(String[] args) {
        AleaStock amazon = new AleaStock(500);
        Chariot michel = new Chariot(50, 10);
        Chargeur[] chronopost = new Chargeur[3];
        Thread[] conducteurs = new Thread[3];
        Thread philippe = new Thread(new Dechargeur(michel, amazon));
        for (int i = 0; i < 3; i++) {
            chronopost[i] = new Chargeur(michel, amazon);
            conducteurs[i] = new Thread(chronopost[i]);
            conducteurs[i].start();
        }
        philippe.start();
        try {
            for (int i = 0; i < 3; i++) {
                conducteurs[i].join();
            }
            philippe.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
