package convoyeur;

import java.util.Random;

public class AleaObjet implements Comparable{

    private Random rand = new Random();
    private int weight;

    public AleaObjet(int min, int max) {
        this.weight = min + rand.nextInt(max-min);
    }

    public int getWeight() {
        return weight;
    }

    @Override
    public int compareTo(Object o) {
        if(o instanceof AleaObjet) {
            AleaObjet obj = (AleaObjet) o;
            return this.getWeight() - obj.getWeight();
        }
        return -1;
    }
}
