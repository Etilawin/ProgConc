package matrice;

import java.io.*;
import java.util.Scanner;

/**
 * A class to implement a Matrix
 * @author Kim Vallée
 * @version 1.0
 */
public class MatriceEntiere {

  private int nbrows;
  private int nbcols;
  private int[][] matrice;

  /**
   * The constructor with lines and columns
   * @param lignes The number of lines in your matrix
   * @param colonnes The number of columns in your matrix
   */
  public MatriceEntiere(int lignes, int colonnes) {
    this.nbrows = lignes;
    this.nbcols = colonnes;
    this.matrice = new int[this.nbrows][this.nbcols];
  }

  /**
   * The constructor with a file
   * @param fichier The file which contains the number of lines and columns
   */
  public MatriceEntiere(File fichier) throws IOException {
      BufferedReader in = new BufferedReader (new FileReader (fichier));
      this.nbrows = Integer.parseInt( in.readLine() );
      this.nbcols = Integer.parseInt( in.readLine() );
      this.matrice = new int[this.nbrows][this.nbcols];
      String[] line = new String[this.nbrows];
      for (int i = 0; i < this.nbrows; i++) {
        line = in.readLine().split(" ");
        for (int j = 0; j < this.nbcols; j++) {
          this.matrice[i][j] = Integer.parseInt(line[j]);
        }
      }
  }

  public String toString() {
    String ret = "";
    for (int i = 0; i < this.nbrows; i++) {
      for (int j = 0; j < this.nbcols; j++) {
        ret += Integer.toString(this.matrice[i][j]) + " ";
      }
      ret += "\n";
    }
    return ret;
  }

  public void afficher() {
    System.out.println(this.toString());
  }

  public void initZero() {
    for (int i = 0; i < this.nbrows; i++) {
      for (int j = 0; j < this.nbcols; j++) {
        this.matrice[i][j] = 0;
      }
    }
  }

  public MatriceEntiere transposee(){
    MatriceEntiere tr = new MatriceEntiere(this.nbcols, this.nbrows);
    for (int i = 0; i < this.nbrows; i++) {
      for (int j = 0; j < this.nbcols; j++) {
        tr.setElem(j, i, this.matrice[i][j]);
      }
    }
    return tr;
  }

  public static MatriceEntiere somme(MatriceEntiere a, MatriceEntiere b) throws Exception{
    if (a.getRows() != b.getRows() || a.getCols() != b.getCols()){
      throw new Exception("Matrix don't have the same size");
    }
    MatriceEntiere ret = new MatriceEntiere(a.getRows(), a.getCols());
    for (int i = 0; i < a.getRows(); i++) {
      for (int j = 0; j < a.getCols(); j++) {
        ret.setElem(i, j, a.getElem(i,j) + b.getElem(i,j));
      }
    }

    return ret;
  }

  public MatriceEntiere multiplyByScalar(int a) {
    MatriceEntiere ret = new MatriceEntiere(this.nbrows, this.nbcols);
    for (int i = 0; i < this.nbrows; i++) {
      for (int j = 0; j < this.nbcols; j++) {
        ret.setElem(i, j, this.matrice[i][j] * a);
      }
    }

    return ret;
  }

  public static MatriceEntiere matricProduct(MatriceEntiere a, MatriceEntiere b) throws Exception {
    if (a.getCols() != b.getRows()){
      throw new Exception("The number of cols of A doesn't matche with the number of cols of B");
    }
    MatriceEntiere ret = new MatriceEntiere(a.getRows(), a.getCols());
    for (int i = 0; i < a.getRows(); i++) {
      for (int j = 0; j < a.getCols(); j++) {
        ret.setElem(i, j, a.getElem(i,j) + b.getElem(i,j));
      }
    }

    return ret;
  }

  public int getElem(int i, int j) {
    return this.matrice[i][j];
  }

  public void setElem(int i, int j, int val) {
    this.matrice[i][j] = val;
  }

  public int getRows(){
    return this.nbrows;
  }

  public int getCols(){
    return this.nbcols;
  }

  public static void main(String[] args) throws IOException{
    System.out.println("Hello, World !");
    MatriceEntiere hey = new MatriceEntiere(new File("../data/donnees_produit1"));
    hey.afficher();
  }
}
