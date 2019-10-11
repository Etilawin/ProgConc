package convoyeur;

import java.util.ArrayList;
import java.util.Collections;

public class AleaStock {

    private ArrayList<AleaObjet> stock;

    public AleaStock(int taille) {
        this.stock = new ArrayList<>();
        for(int i = 0; i < taille; i++) {
            this.stock.add(new AleaObjet(1, 10));
        }
    }

    public synchronized boolean isEmpty() {
        return this.stock.size() == 0;
    }

    public synchronized AleaObjet prendreElement() {
        return !this.isEmpty() ? this.stock.remove(this.stock.size()-1) : null;
    }

}
