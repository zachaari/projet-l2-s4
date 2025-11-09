package tools;

import java.util.Scanner;

/**
 * tool class for inputs (String or int)
 *
 */
public class Input {

   private static Scanner scanner = new Scanner(System.in);

   /**
    * reads a string from standard input
    * 
    * @return the read string
    */
   public static String readString() {
      return Input.scanner.next();
   }

   /**
    * reads an int from standard input
    * 
    * @return the read int
    * @exception java.io.IOException if input does not correspond to an int
    */
   public static int readInt() throws java.io.IOException {
      try {
         return Input.scanner.nextInt();
      } catch (Exception e) {
         Input.scanner.skip(".*");
         throw new java.io.IOException();
      }
   }

   /**
    * lit un booléen depuis l'entrée standard
    * @return le booléen lu (vrai si l'utilisateur entre "true", faux sinon)
    */
   public static boolean readBoolean() {
      String input = readString().toLowerCase();
      return input.equals("true");
  }

}
