import java.util.ArrayList;

public class TestRestaurant {

    public static void main(String[] args) {

        int n = 0;

        try {

            n = Integer.parseInt(args[1]);

        } catch ( NumberFormatException|ArrayIndexOutOfBoundsException e ) {

            System.out.println("Please provide a valid n, put n = 10 by default.");
            n = 10;

        }


        Restaurant chezJiji = new Restaurant(10);

        int nb_groupes = (int) (Math.random() * 10) + 1;

        GroupeClients[] groupes = new GroupeClients[nb_groupes];

        ArrayList<Thread> clients = new ArrayList<>();

        for (int i = 0; i < nb_groupes; i++) {

            int taille = (int) (Math.random() * 5) + 1;
            groupes[i] = new GroupeClients(taille, chezJiji);
            clients.addAll(groupes[i].getClients());

        }

        try {

            for (Thread client : clients) {

                client.join();

            }

        } catch (InterruptedException e) {

            e.printStackTrace();

        }

    }

}
