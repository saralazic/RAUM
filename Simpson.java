package prm;


public class Simpson {
	
	private double korak;
	
	private integrator intgr;
	
	
	public Simpson(double korak, integrator i) {
		this.korak=korak;
		this.intgr=i;
	}
	
	public double Racunaj(Funkcija f, double Dole, double Gore) {
		double d1=0;
		int j;
		String s1, s2;
	
		s1="Simpsonova formula aproksimira vrednost integrala na sledeći način:\n";
		s1+="S(h)=(h/3)*(f0+2*(f2+f4+...)+4*(f1+f3+...)+f2m)";
		
		s1+="\n\n\nI=(h/3)*(f0+2*(";
		s2="I="+((korak)/3)+"*("+f.aprox(Dole)+"2*(";
		
		for(int i=1; i<((int)(((Gore-Dole)/korak)/2)); i++) {
			j=2*i;
			if(i>1) { s1+="+"; s2+="+";}
			s1+="f"+j;
			Double fx=f.aprox(Dole+2*i*korak);
			d1+=fx;
			s2+=fx;
		}
		s1+=")\n+4*("; s2+=")+\n4*(";
		
		double d2=0;
		for(int i=1; i<=((int)(((Gore-Dole)/korak)/2)); i++){
			j=2*i-1;
			if(i>1) {s1+="+"; s2+="+";}
			s1+="f"+j;
			Double fx=f.aprox(Dole+(2*i-1)*korak);
			s2+=fx;
			d2+=fx;
		}
		
		s1+=")\n+f2m))";
		s2+=")+\n"+f.aprox(Gore)+"))";
		
		intgr.s1=s1;
		intgr.s2=s2;
		
		return ((korak/3)*(f.aprox(Dole)+2*d1+4*d2+f.aprox(Gore)));
	}

}
