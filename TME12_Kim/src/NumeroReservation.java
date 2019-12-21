public class NumeroReservation {

    public final static Object mutex = new Object();
    public static int cpt = 0;
    public int id;

    public NumeroReservation() {
        synchronized (mutex) {
            id = cpt++;
        }
    }

    public int getId() {
        return id;
    }
}
