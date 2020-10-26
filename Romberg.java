package prm;
import java.math.*;


@SuppressWarnings("unused")
public class Romberg
{

	private int maxBrKoraka;
	private double preciznost;
	private integrator intgr;
	
	public static int MAX_BR_K=20;
	
	
	public Romberg(double preciznost, integrator i, int mbk){
		this.preciznost=preciznost;
		this.intgr=i;
		this.maxBrKoraka=mbk;
	}
	
	
	public Romberg(double preciznost, integrator i) {
		this.intgr=i;
		this.preciznost=preciznost;
		String s=intgr.brk.getText();
		if (s.length()>0)
			this.maxBrKoraka=Integer.parseInt(s);
		else this.maxBrKoraka=MAX_BR_K;
	}
	
		
	
	double Racunaj(Funkcija f, double Dole, double Gore){
		double[][] r= new double[maxBrKoraka][maxBrKoraka];
		
		String s1="", s2="";
		
		s1+="Rombergova integracija koristi sledeće formule: \n";
		s1+="Trapezna formula: T=(h/2)*(f(a)+f(b)+2*∑f(a+i*h0))\n";
		s1+="Formula Ričardsonove ekstrapolacije: T[k][j]=(4^j T[k][j-1]-T[k-1][j-1])/(4^j-1), j=1...k\n";
		
		s1+="\n\nh0=b-a"+"="+(Gore-Dole);
		double h = Gore - Dole; // step size
		Trapezna trap=new Trapezna(h, intgr);
		s1+="\nT[0][0]=(h0/2)*(f(a)+f(b)+2*∑f(a+i*h0))";
		r[0][0] = trap.Racunaj(f, Dole, Gore);
		s1+="T[0][0]="+r[0][0]+", izracunato po trapeznoj formuli";
		int i = 1;
		do {
			s1+="\n\nk="+i;
			s1+="\nh"+i+"=h0/2^"+i+"="+(h/(double) Math.pow(2, (double) i))+"\n";
			Trapezna t=new Trapezna(h/(double) Math.pow(2, (double) i), intgr);
			r[i][0] = t.Racunaj(f, Dole, Gore);
			s1+="T["+i+"][0]="+r[i][0]+", izracunato po trapeznoj formuli \n";
			for (int j = 1; j <= i; j++) {
				r[i][j] = ((double) Math.pow(2, 2.0 * j) * r[i][j - 1] - r[i - 1][j - 1])
						/ ((double) Math.pow(2, 2.0 * j) - 1.0);
			s1+="T["+i+"]["+j+"]="+r[i][j]+", dobijeno Ričardsonovom ekstrapolacijom \n";	
			}
			i++;
		} while ((Math.abs(r[i-1][i-1]-r[i-2][i-2])>preciznost)
				&& (i<maxBrKoraka));

		
		
		
		intgr.s1=s1;
		intgr.s2="";
		return r[i-1][i-1];
	}


};


	