package garage;

public class Loco implements Runnable{

    private SegAccueil sAccueil;
    private SegTournant sTournant;
    private PoolHangars pHangars;
    private int id;
    private static int idInc = 0;

    public Loco(SegAccueil sAccueil, SegTournant sTournant, PoolHangars pHangars) {
        this.sAccueil = sAccueil;
        this.sTournant = sTournant;
        this.pHangars = pHangars;
        this.id = idInc++;
    }

    public void run() {
        try {
            System.out.println("La locomotive " + id + " réserve le segment d'accueil");
            sAccueil.reserver();

            System.out.println("La locomotive " + id + " appel le segment tournant au segment d'accueil");
            sTournant.appeler(0);

            System.out.println("La locomotive " + id + " attends que la position soit ok");
            sTournant.attendrePositionOK();

            System.out.println("La locomotive " + id + " rentre sur le segment tournant");
            sTournant.entrer(this);

            System.out.println("La locomotive " + id + " libère le segment d'accueil");
            sAccueil.liberer();

            System.out.println("La locomotive " + id + " attends que la position soit ok");
            sTournant.attendrePositionOK();

            System.out.println("La locomotive " + id + " entre dans le hangar " + sTournant.getPosition());
            pHangars.getHangar(sTournant.getPosition()).entrer(this);

            System.out.println("La locomotive " + id + " sort du segment tournant");
            sTournant.sortir(this);
        } catch (InterruptedException e) {
            System.out.println("Loco " + id + " interrompue (ne devrait pas arriver)");
        }
    }
}
