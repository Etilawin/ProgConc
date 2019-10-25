package barbier;

public class Barbier implements Runnable {
    private SalleAttente salleAttente;
    private SalleCoiffure salleCoiffure;

    public Barbier(SalleAttente salleAttente, SalleCoiffure salleCoiffure) {
        this.salleAttente = salleAttente;
        this.salleCoiffure = salleCoiffure;
    }

    @Override
    public void run() {
        Client dernierClient;
        try {
            while(true) {
                dernierClient = this.salleAttente.chercherClient();
                System.out.println("Le coiffeur décide de prendre " + dernierClient + " parce que c'est le moins moche.");
                this.salleCoiffure.coiffer(dernierClient);
                System.out.println("Fini de coiffer " + dernierClient + " et c'était long !");
            }
        } catch (InterruptedException e) {
            System.out.println("C'est une fin de journée !");
        }
    }
}
