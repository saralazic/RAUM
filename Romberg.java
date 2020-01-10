package raum;
import java.math.*;


@SuppressWarnings("unused")
public class Romberg
{

	private int maxBrKoraka;
	private double preciznost;
	
	public static int MAX_BR_K=50;
	
	public Romberg(double preciznost, int mbk){
		this.preciznost=preciznost;
		this.maxBrKoraka=mbk;
	}
	
	
	public Romberg(double preciznost) {
		this.preciznost=preciznost;
		this.maxBrKoraka=MAX_BR_K;
	}
	
		

	
	double Racunaj(funkcija f, double Gore, double Dole) throws Greska {
		double[][] r= new double[maxBrKoraka][maxBrKoraka];
		
		double h = Dole - Gore; // step size
		otf TrapezoidnaFla=new otf(h);
		r[0][0] = TrapezoidnaFla.osnovnaTrapezna(f, Gore, Dole);
		int i = 1;
		do {
			otf trap=new otf(h / (double) Math.pow(2, i));
			r[i][0] = trap.osnovnaTrapezna(f, Gore, Dole);
			for (int j = 1; j <= i; j++) {
				r[i][j] = ((double) Math.pow(2, 2.0 * j) * r[i][j - 1] - r[i - 1][j - 1])
						/ ((double) Math.pow(2, 2.0 * j) - 1.0);
			}
			i++;
		} while ((Math.abs(r[i-1][i-1]-r[i-2][i-2])>preciznost)
				&& (i<maxBrKoraka));
		return r[i-1][i-1];
	}
	
	


};
	/*
	
	public void Romberg (int fcnNum)
	{
	    double[][] T = new double [20][20];
	    
	    System.out.print ("\nEnter the interval endpoints a and b: ");
	    double a = Keyboard.readDouble();
	    double b = Keyboard.readDouble();
	    System.out.print ("Enter the tolerance: ");
	    double tol = Keyboard.readDouble();

	    boolean done = false;
	    int k = 0;
	    
	    System.out.println ("\nRomberg: ");
	    int n = 1;   // n is the number of subdivisions of [a,b]
	                 // it starts at 1 and doubles at each row
	                 // -- that is it equals 2^k
	    while (k < 20 && !done)
		{
		    // Compute and print the entry for the first column

		    T[k][0] =  ___________________________________________;

		    System.out.print (fmt2.format(T[k][0]));

		    // Compute and print the remainder of the row
		    for (int j = _____; _________; j++)
			{
			    double FourJ = Math.pow(4, j);

			    T[k][j] = ____________________________________________;


			    System.out.print ("\t" + fmt2.format(T[k][j]));
			}
		    System.out.println ();

		    n = n * 2;  // number of subdivisions for next row

		    if (k !=0 && _____________________________________ < tol)
			done = true;
		    k++;
		}
	        
	}
}
*/