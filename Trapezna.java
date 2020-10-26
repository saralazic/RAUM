package prm;

public class Trapezna {
	
	private double korak;
	private integrator intgr;
	
	public Trapezna(double korak, integrator i) {
		this.korak=korak;
		this.intgr=i;
	}
	
	
	double Racunaj(Funkcija f, double Dole, double Gore) {
		double d=0;
		
		String s1, s2;
		
		s1="Trapezna formula aproksimira vrednost integrala na sledeći način: \n";
		s1+="T=(h/2)*(f(a)+f(b)+2*∑f(a+i*h0))\n";
		
		s2="";
		s1+="\n\nT(h)=(h/2)*(f0+2*(";
		s2+="T("+korak+")=("+korak/2+")*("+f.aprox(Dole)+"+2*(";
		
		int i;Double fx;
		for(i=1; i<=((int)((Gore-Dole)/korak)-1); i++) {
			if(i>1) { s1+="+"; s2+="+"; }
			s1+="f"+i;
			fx=f.aprox(Dole+i*korak);
			d+=fx;
			s2+=fx;
		}
		s1+=")+f"+(i+1)+")";
		s2+=")+"+f.aprox(Gore)+")";
		
		intgr.s1=s1;
		intgr.s2=s2;
	
	//	System.out.println(((korak/2)*(f.aprox(Dole)+2*d+f.aprox(Gore))));
		
		return ((korak/2)*(f.aprox(Dole)+2*d+f.aprox(Gore)));
		
	
	}
	
}
