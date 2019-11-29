package spectacle;

public class Salle{

  private boolean[][] places;

  public Salle(int w, int h) {
    this.places = new boolean[w][h];
  }

  private boolean capaciteOk(int n) {
    int placesLibres = 0;
    for (boolean[] rang: this.places){
      for (boolean place: rang){
        if(!place){
          placesLibres++;
        }
      }
    }
    return (placesLibres >= n);
  }

  private int nContiguesAuRangI(int n, int i) {
    int placesLibresContigues = 0;
    int numeroPlace = 0;
    if(n > this.places[0].length) return -1;
    while (placesLibresContigues < n && numeroPlace < this.places[i].length) {
      if (!this.places[i][numeroPlace]) placesLibresContigues++;
      else placesLibresContigues = 0;
      numeroPlace++;
    }
    if (placesLibresContigues == n) return numeroPlace - placesLibresContigues;
    return -1;
  }

  private boolean reserverContigues(int n, Groupe g) {
    int placement = -1;
    int rang = 0;
    for(; rang < this.places.length && placement == -1; rang++) {
      placement = this.nContiguesAuRangI(n, rang);
    }
    rang--;
    if (placement > -1) {
      for(int place = 0; place < n; place++){
        g.addPlace(new Place(rang,place));
        this.places[rang][placement + place] = true;
      }
      return true;
    }
    return false;
  }

  public synchronized boolean reserver(int n, Groupe g) {
    if (!this.capaciteOk(n)) return false;
    if (reserverContigues(n, g)) return true;

    for(int i = 0; i < this.places.length && n != 0; i++){
      for (int j = 0; j < this.places[0].length && n != 0; j++) {
        if(!this.places[i][j]) {
          g.addPlace(new Place(i,j));
          this.places[i][j] = true;
          n--;
        }
      }
    }
    return true;
  }

  @Override
  public String toString(){
    StringBuilder res = new StringBuilder();
    for(boolean[] rang : this.places) {
      for(boolean place : rang) {
        res.append(String.valueOf(place));
        res.append("\t");
      }
      res.append("\n");
    }
    return res.toString();
  }
}
