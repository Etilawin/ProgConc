package garage;

public class Loco {

    private SegAccueil sAccueil;
    private SegTournant sTournant;
    private PoolHangars pHangars;
    private int id;
    private static int idInc = 0;

    public Loco() {

    }

    public void run() {
        try {
            sAccueil.reserver();
            sTournant.appeler(0);
            sTournant.attendrePositionOK();
            sTournant.entrer(this);
            sAccueil.liberer();
            sTournant.attendrePositionOK();
            pHangars.getHangar(sTournant.getPosition()).entrer(this);
            sTournant.sortir(id);
        } catch (InterruptedException e) {
            System.out.println("Loco " + id + " interrompue (ne devrait pas arriver)");
        }
    }
}
