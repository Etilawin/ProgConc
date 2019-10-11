package spectacle;

public class Place{
  private int numero;
  private int rang;

  public Place(int i, int j){
    this.rang = i;
    this.numero = j;
  }

  public int getJ(){
    return this.numero;
  }

  public int getI(){
    return this.rang;
  }
}
