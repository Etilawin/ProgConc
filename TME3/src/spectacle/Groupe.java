package spectacle;

import java.util.ArrayList;
import java.util.Random;

public class Groupe implements Runnable{
  private int n;
  private static int idinc = 0;
  private int id;
  private Salle s;
  private ArrayList<Place> placesReservees;

  public Groupe(int n, Salle s){
    this.n = n;
    this.id = Groupe.idinc++;
    this.s = s;
    this.placesReservees = new ArrayList<>();
  }

  public String toString(){
    return "Groupe : " + String.valueOf(this.id) + " avec " + String.valueOf(this.n) + " personnes";
  }

  public void run(){
    this.s.reserver(this);
    Random r = new Random();
    if(r.nextInt(2) > 0) {
        this.s.annuler(this);
        System.out.println("Le groupe " + this.id + " à annulé.");
    }
  }

  public void addPlace(Place p){
    this.placesReservees.add(p);
  }

  public int getNumberOfPlace() {
      return this.n;
  }

  public ArrayList<Place> getPlacesReservees() {
      return this.placesReservees;
  }
}
