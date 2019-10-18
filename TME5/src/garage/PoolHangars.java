package garage;

import java.util.ArrayList;

public class PoolHangars {
    private ArrayList<Hangar> hangars;

    public PoolHangars(int nbHangars) {
        this.hangars = new ArrayList<>();
        for (int i = 0; i < nbHangars; i++) {
            this.hangars.add(new Hangar());
        }
    }

    public Hangar getHangar(int position) {
        return hangars.get(position);
    }

    public synchronized int getFreeHangar() {
        for (int i = 0; i < this.hangars.size(); i++) {
            if (this.hangars.get(i).isEmpty())
                return i;
        }
        return -1;
    }
}
