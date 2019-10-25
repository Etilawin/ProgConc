package barbier;

public class Client implements Runnable {

    private String name;
    private SalleAttente salleAttente;
    private SalleCoiffure salleCoiffure;
    private boolean estCoiffee;

    public Client(String name, SalleAttente salleAttente, SalleCoiffure salleCoiffure) {
        this.name = name;
        this.salleAttente = salleAttente;
        this.salleCoiffure = salleCoiffure;
        this.estCoiffee = false;
    }

    public void finirCoiffure() {
        this.estCoiffee = true;
    }

    public void setCoiffee() {
        this.estCoiffee = true;
    }

    public boolean estCoiffe() {
        return this.estCoiffee;
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public void run() {
        try{
            System.out.println(this.name + " essaye d'entrer dans le salon...");
            if(salleAttente.entrer(this)){
                System.out.println(this.name + " a réussi à entrer et attend...");
                salleCoiffure.attendreCoiffer(this);
                System.out.println(this.name + " sort avec une super nouvelle coiffure !");
            } else {
                System.out.println(this.name + " a échoué à rentrer dans le salon...");
            }

        } catch (InterruptedException e) {

            e.printStackTrace();

        }
    }

}
