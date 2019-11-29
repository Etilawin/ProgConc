import java.util.Random;

public class ReponseRequete {
    private Client c;
    private int reqId;
    private Random r = new Random();
    private int val;

    public ReponseRequete(Client c, int reqId) {
        this.c = c;
        this.reqId = reqId;
        this.val = r.nextInt(42);
    }

    public Client getC() {
        return c;
    }

    @Override
    public String toString() {
        return "ReponseRequete{" +
                "c=" + c +
                ", reqId=" + reqId +
                ", val=" + val +
                '}';
    }
}
