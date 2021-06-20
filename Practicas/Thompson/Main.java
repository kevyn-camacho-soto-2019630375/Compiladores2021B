package Thompson;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
   public static void main(String[] args) throws FileNotFoundException {
      Scanner s = new Scanner(System.in);
      System.out.println("Ingresa la expresion regular: ");
      String expresion_regular = s.nextLine();
      // String expresion_regular = "a(a|b)*b";
      Thompson t = new Thompson(expresion_regular);// manda al constructor la expresion regular
      t.imprimir_afn();// imprime el afn
   }
}
