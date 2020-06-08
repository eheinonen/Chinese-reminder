// This program can be used to solve a system of linear congruence equations
// of the form x = b (mod n). It is based on the Chinese Reminder Theorem
// (see e.g. https://en.wikipedia.org/wiki/Chinese_remainder_theorem#Theorem_statement)
// and therefore it is required that all the moduli are pairwise coprime.

import java.util.ArrayList;
import java.util.Scanner;

public class ChReminder {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        // We will need 4 lists to store all the necessary information.
        ArrayList<Integer> bI = new ArrayList<>();
        ArrayList<Integer> nI = new ArrayList<>();
        ArrayList<Integer> cI = new ArrayList<>();
        ArrayList<Integer> dI = new ArrayList<>();

        System.out.println("Equations have to be in form x = b (mod n).\n"
                + "The program asks for the numbers b and n for one equation at time. \n"
                + "Empty insert for b will stop the reading and the program will compute the solution.");
        System.out.println("");

        // This while loop collects the system of equations by saving
        // the coefficients into the two lists bI and nI.
        // We assume that the equations are inserted correctly and that
        // all the modulos n_i are pairwise coprimes (this is necessary
        // in order to apply the Chinese reminder theorem).
        while (true) {
            System.out.println("For x = b_i (mod n_i), give b_i:");
            String cmd = scan.nextLine();
            if (cmd.equals("")) {
                break;
            }
            int bi = Integer.valueOf(cmd);
            System.out.println("For x = b_i (mod n_i), give n_i:");
            int ni = Integer.valueOf(scan.nextLine());
            bI.add(bi);
            nI.add(ni);
        }

        // To solve the system we need to compute the product n of all of the modulos.
        int n = 1;
        for (int i = 0; i < nI.size(); i++) {
            n = n * nI.get(i);
        }

        // We also need to define coefficients c_i = n/n_i that satisfy
        // gcd(c_i,n_i) = 1.
        for (int i = 0; i < nI.size(); i++) {
            int ci = n / nI.get(i);
            cI.add(ci);
        }

        // Then we compute the inverse elements of each c_i modulo n_i and
        // store them into the list dI.
        for (int i = 0; i < cI.size(); i++) {
            int di = inverse(cI.get(i), nI.get(i));
            dI.add(di);
        }

        // Now the solution can be computed with the formula
        // x0 = sum_{i=1}^k c_i*b_i*d_i.
        int x0 = 0;
        for (int i = 0; i < cI.size(); i++) {
            x0 = x0 + cI.get(i) * bI.get(i) * dI.get(i);
        }

        // Compute the final solution as an element in Z_n.
        int x = x0 % n;

        System.out.println("The solution for the system of linear equations is " + x + " (mod " + n + ").");

    }

    // Method to compute the inverse of c modulo n, i.e. a number d that is a solution to 
    // the equation cx = 1 (mod n).
    public static int inverse(int c, int n) {
        int d = 0;
        for (int i = 1; i < n; i++) {
            int x = (i * c) % n;
            if (x == 1) {
                d = i;
                break;
            }
        }
        return d;
    }
}