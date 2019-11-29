package spectacle;

import java.util.ArrayList;

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
    s.reserver(this.n);
  }

  public addPlace(Place p){
    this.placesReservees.add(p);
  }
}
