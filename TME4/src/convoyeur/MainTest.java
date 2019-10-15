package convoyeur;

public class MainTest {
    public static void main(String[] args) {
        AleaStock amazon = new AleaStock(50, 3, 30);

        Chariot michel = new Chariot(amazon ,50, 10);

        Chargeur[] transpalette = new Chargeur[3];
        Thread[] conducteurs = new Thread[3];

        for (int i = 0; i < 3; i++) {
            transpalette[i] = new Chargeur(michel, amazon, i);
            conducteurs[i] = new Thread(transpalette[i]);
            conducteurs[i].start();
        }

        Thread philippe = new Thread(new Dechargeur(michel));

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
