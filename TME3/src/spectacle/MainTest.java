package spectacle;

import java.util.Random;

public class MainTest{
  public static void main(String[] args) {
    Salle s = new Salle(20,10);
    // System.out.println(s.capaciteOk(20));
    // System.out.println(s.capaciteOk(4000));
    // System.out.println(s.capaciteOk(2500));

    // System.out.println(s.nContiguesAuRangI(10, 2));
    // System.out.println(s.nContiguesAuRangI(60, 10));
    // System.out.println(s.nContiguesAuRangI(50, 10));
    Random r = new Random();
    Groupe[] groupes = new Groupe[10];
    Thread[] th = new Thread[10];
    for (int i = 0;i < 10; i++) {
      groupes[i] = new Groupe(r.nextInt(15),s);
      th[i] = new Thread(groupes[i]);
      th[i].run();
      System.out.println(groupes[i]);
    }
    for (int i = 0; i < 3; i++) {
      s.annuler(groupes[i]);
    }
    try {
      for (int i = 0;i < 10; i++) {
        th[i].join();
      }
    } catch (InterruptedException e){
      e.printStackTrace();
    }
    System.out.println(s);
  }
}
