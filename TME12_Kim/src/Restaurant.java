import java.util.HashMap;

public class Restaurant {

    private int nb_tables_dispo;
    private HashMap<GroupeClients, NumeroReservation> reservations;

    public Restaurant(int nb_tables) {

        this.nb_tables_dispo = nb_tables;
        this.reservations = new HashMap<>();

    }

    public synchronized NumeroReservation reserver(GroupeClients groupe) {

        if (this.reservations.containsKey(groupe)) {

            return this.reservations.get(groupe);

        }

        // Each table has 2 sit
        int nb_tables_necessaires = (int) Math.ceil((float)groupe.getTaille() / 2);

        if (nb_tables_necessaires <= this.nb_tables_dispo) {

            NumeroReservation res = new NumeroReservation();
            this.reservations.put(groupe, res);
            this.nb_tables_dispo -= nb_tables_necessaires;
            return res;

        }

        return null;

    }

}
