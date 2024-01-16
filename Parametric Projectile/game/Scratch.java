package game;
/**
 * Calculates some probabilities
 *
 * @Alex Schwartz
 * @7/15/2020
 */
import java.util.Scanner;
public class Scratch
{
    public static void main(){
        Scanner scan = new Scanner(System.in);
        System.out.println('\u000C');
        StdDraw.enableDoubleBuffering();
        System.out.printf("How many iterations? ");
        int iterations = scan.nextInt();
        int[] d8 = new int[16];
        int[] d6d10 = new int[16];

        StdDraw.setXscale(0, +32.0);
        StdDraw.setYscale(0, (double) iterations);

        for (int i = 0; i <=iterations; i++){
            d8[(int)((Math.round((Math.random() * 7)) + 1) + (Math.round((Math.random() * 7)) + 1))-1] += 1;
            d6d10[(int)((Math.round((Math.random() * 5)) + 1) + (Math.round((Math.random() * 9)) + 1))-1] +=1;

            if (i % (iterations/1000) == 0){
                StdDraw.clear();
                for (int d = 0; d < 16; d++){
                    StdDraw.setPenColor(255,0,0);
                    StdDraw.filledRectangle(d*2 -.9, 0, .4, d6d10[d]);
                    StdDraw.setPenColor(0,0,255);
                    StdDraw.filledRectangle(d*2 -.1, 0, .4, d8[d]);
                }
                StdDraw.setPenColor(0,0,0);
                StdDraw.filledRectangle(.2, 0, .2, i);
            }
            StdDraw.show();
        }
    }
}