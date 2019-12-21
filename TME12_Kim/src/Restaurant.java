import java.util.HashMap;

class Restaurant {

    private int nb_tables_dispo;

    Restaurant(int nb_tables) {

        this.nb_tables_dispo = nb_tables;

    }

    synchronized NumeroReservation reserver(GroupeClients groupe) {

        if (groupe.getReservation() != null) {
            return groupe.getReservation();
        }

        // Each table has 2 sit
        int nb_tables_necessaires = (int) Math.ceil((float)groupe.getTaille() / 2);

        if (nb_tables_necessaires <= this.nb_tables_dispo) {

            System.out.printf("Nouvelle réservation pour le groupe %s.%n", groupe);
            NumeroReservation res = new NumeroReservation();
            this.nb_tables_dispo -= nb_tables_necessaires;
            groupe.setReservation(res);
            return res;

        }

        groupe.propagateInterruptedStatus();
        return null;

    }

}
