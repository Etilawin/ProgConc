package barbier;

public class Chaise {

    private Client client;

    public Chaise() {
        client = null;
    }

    public boolean estLibre(){
        return client == null;
    }

    public void prendre(Client client) {
        this.client = client;
    }

    public Client laisser() {
        Client c = this.client;
        this.client = null;
        return c;
    }


}
