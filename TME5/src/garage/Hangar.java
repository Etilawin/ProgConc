package garage;

public class Hangar {
    private Loco loco;

    public Hangar(Loco loco) {
        this.loco = loco;
    }

    public Hangar() {
        this.loco = null;
    }

    public synchronized void entrer(Loco loco) {
        this.loco = loco;
    }

    public boolean isEmpty() {
        return this.loco == null;
    }

}
