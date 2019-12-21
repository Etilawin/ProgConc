import java.util.ArrayList;
import java.util.Random;

public class TestRestaurant {
    public static void main(String[] args) {

        int nbGroupe = 6;

        Random random = new Random();

        Restaurant restaurant = new Restaurant(10);
        ArrayList<GroupeClients> groupes = new ArrayList<>();

        for (int i = 0; i < nbGroupe; i++) {
            GroupeClients newGroupe = new GroupeClients(i, random.nextInt(4) + 2, restaurant);
            groupes.add(newGroupe);
        }

    }
}
