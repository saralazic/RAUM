package raum;


//PAZI DA LI TREBA DA ISKOPIRAS LISTU	

public class funkcija {
	
	public static final int BROJ=0, 
							BIN_OP=1, 
							PROM=2,
							UN_OP=3,
							PI=4,
							ZAGR=5;
				
	 
	//zapis funkcije u vidu liste ciji su elementi operandi i operatori
	protected Elem glava;
	protected Elem rep;
	protected Elem pret;
		
	//stek koji koristimo u pretvaranju infiksa u postfiks
	protected Lista stek;
			
	//postfiksni oblik
	protected Lista postfix;
			
	protected boolean un;
	
			public funkcija() {
				glava=rep=pret=null;
				postfix=new Lista();
				un=false;
			}	
			
			public void printfun() {
				Elem tek=glava;
				while (tek!=null) {
					System.out.print(tek.sadrzaj+" ");
					tek=tek.sledeci;
				}
			}
			
			//dodavanje operatora u listu 
			public void dodajOper(String s, boolean b) throws Greska {
				//Ako je lista prazna
				if(glava==null) 
					if (b==true) throw new Greska();//i ako je binarni, greska je u unosu
					else 
						if (s=="π") 
							glava=rep=new Elem(s, funkcija.PI); //ako je pi, dodajem pi
						else if (s=="(" || s==")")
							glava=rep=new Elem(s, funkcija.ZAGR); //ako je zagrada, dodajem zagradu 
						else {
								un=true;
								glava=rep=new Elem(s, funkcija.UN_OP); //ako je unarni, dodajem unarni
						}
				
				//Ako lista nije prazna, dodajem element na kraj 
				else 
					{
					if (b==true)	 
					{
						rep.sledeci=new Elem(s, funkcija.BIN_OP);
					}
				else {
					if (s=="π") 
						rep.sledeci=new Elem(s, funkcija.PI); //ako je pi, dodajem pi
					else if (s=="(" || s==")")
						rep.sledeci=new Elem(s, funkcija.ZAGR); //ako je zagrada, dodajem zagradu 
					else 
						rep.sledeci=new Elem(s, funkcija.UN_OP); //ako je unarni, dodajem unarni	
				}
					pret=rep;
					rep=rep.sledeci;
			}
			}
			
			
			
			
			//dodavanje cifre (ili u vec postojeci element
			//ili u novi ako je prethodni element operator)
			//u ovoj funkciji se takodje u listu dodaje i promenljiva
			public void dodajCif(String s) {
				if(glava==null) 
					if (s=="x") 
							glava=rep=new Elem(s, funkcija.PROM);
					else 
						glava=rep=new Elem(s, funkcija.BROJ);
				else {
					if (rep.ind==BROJ) rep.sadrzaj+=s;
					else {
						if (s=="x")
							{
								rep.sledeci=new Elem(s, funkcija.PROM);
							}
						else rep.sledeci=new Elem(s, funkcija.BROJ);
						pret=rep;
						rep=rep.sledeci;
					}
				}
			}
			
			
			
			public void dodajEl(Elem e) {
				Elem el=new Elem(e); 
				if(glava==null) glava=rep=el;
				else {
					rep.sledeci=el;
					rep=rep.sledeci;
				}
			}
			
			
			public void uredi() {
				Elem tmp1=glava, tmp2=glava;
				while(tmp2!=null) {
					tmp2=tmp2.sledeci;
					if(tmp1.ind!=BIN_OP && tmp2.ind!=BIN_OP) {
						tmp1.sledeci=new Elem("*", BIN_OP);
						tmp1.sledeci.sledeci=tmp2;
					}
					tmp1=tmp2;
				}
			}
				
			//provera da li je operacija prefiksna
			public boolean pref(String s) {
				if (s=="log" || s=="ln" || s=="e^" || s=="√"
				|| s=="sin" || s=="cos" || s=="tg" || s=="ctg"
				|| s=="asin" || s=="acos" || s=="atan" || s=="acot")
					return true;
				return false;
			}
			
			
			//POPRAVI
			public void in2post() throws Greska {
				int rank=0; Elem x;
				stek=new Lista();
				
				TabelaPrioriteta tab=new TabelaPrioriteta();
				Elem next=glava;
				
				while(next!=null) {
					if(next.ind==funkcija.BROJ || next.ind==funkcija.PI ||
							next.ind==funkcija.PROM) { 
						postfix.OUTPUT(next);
						rank++;
					}
					else
						{
							while(!stek.STACK_EMPTY()
									&& (tab.IPR(next)<=tab.SPR(stek.TOP()))) {
								x=stek.POP();
								postfix.OUTPUT(x);
								rank=rank+tab.R(x);
								if(rank<1) throw new Greska(); 
							}
							if(next.sadrzaj!=")")
								stek.PUSH(next);
							else x=stek.POP();
						}
					next=next.sledeci;
				}
				while(!stek.STACK_EMPTY()) {
					x=stek.POP();
					postfix.OUTPUT(x);
					rank=rank+tab.R(x);
				}
				
				if(rank!=1) throw new Greska();
				
				postfix.printList();
			
			}
			
			
		//racunanje prostih operacija	
		public double calc(double a, double b, String op) {
			double rez=0;
			switch(op) {
				case "+": rez=a+b; break;
				case "-": rez=a-b; break;
				case "*": rez=a*b; break;
				case "/": rez=a/b; break;
				case "^": rez=Math.pow(a, b); break;
			}
			return rez;
		}
		
		
		//izrcunavanje postfiksnog izraza
		public double evalExp(double prom) throws Greska {
			Elem x;
			Elem oprnd;
			double rez;
			stek=new Lista();
			
			while(!postfix.END()) {
				x=postfix.INPUT();
				if(x.ind==funkcija.BROJ || x.ind==funkcija.PI
						|| x.ind==funkcija.PROM) stek.PUSH(x);
				else if (x.ind==funkcija.UN_OP)
				{
					oprnd=stek.POP();
					if (oprnd.ind==funkcija.PI) rez=-Math.PI;
					else if(oprnd.ind==funkcija.PROM) rez=-prom;
					else rez=-Double.parseDouble(oprnd.sadrzaj); //kao unarni operand se eventualno moze pojaviti minus
					stek.PUSH(new Elem(Double.toString(rez), funkcija.BROJ));
				}
				else if(x.ind==funkcija.BIN_OP) {
					double o[]=new double[2];
					for(int i=0; i<2; i++) {
						 Elem oper=stek.POP();
						 if (oper.ind==funkcija.BROJ)
							 o[i]=Double.parseDouble(oper.sadrzaj);
						 else if(oper.ind==funkcija.PI)
							 o[i]=Math.PI;
						 else o[i]=prom;
					}
					rez=calc(o[1],o[0], x.sadrzaj);
					stek.PUSH(new Elem(Double.toString(rez), funkcija.BROJ));
					
				}	
			}
			
			Elem el=stek.POP(); 
			if (el==null) throw new Greska();
			if(el.ind==funkcija.BROJ)
				rez=Double.parseDouble(el.sadrzaj);
			else throw new Greska();
			if(stek.STACK_EMPTY()) return rez;
			else throw new Greska();
		}
			
		
		
		
		public void Unarni(Elem p,double x) throws Greska{
			Elem priv=p, sled=priv.sledeci;
			
			//ova petlja prolazi kroz celu listu 
			//i eliminise sve unarne operande
			while(priv!=null) {
				if (priv.ind!=funkcija.UN_OP )
				{
					//ako nije unarni operand, samo ide dalje
					//pret=priv;
					priv=priv.sledeci;
					if(sled!=null) sled=sled.sledeci;
				}
				else {//ako je trenutni element unarni operand
					
					Elem tek=priv.sledeci;
					if (sled.ind==funkcija.BIN_OP) throw new Greska();
					else {
						funkcija podf=new funkcija(); //podfunkcija 
						//Ako posle unarnog operanda sledi "(", za podfunkciju uzimam sve sto je u zagradi
						if (sled.sadrzaj=="(") {
							int bz;
							bz=1; //brojim zagrade
							//podf.dodajEl(sled);
							tek=sled.sledeci;
							while (tek!=null) {
								if (tek.sadrzaj==")") {
									bz--;
									if (bz<0) throw new Greska();
									else if (bz==0) break;
									else podf.dodajEl(tek);
								}
								else{
									if(tek.sadrzaj=="(") bz++;
									podf.dodajEl(tek);
								}
								tek=tek.sledeci;
							}
							if (tek==null && bz!=0) throw new Greska(); //ako zagrada nikad nije zatvorena
						}
						else if (sled.ind==funkcija.UN_OP) {
							Unarni(sled,x);
							
							//PROVERI STA JE U ZAGRADI
							podf.dodajEl(priv.sledeci); 
						}
						else 
							podf.dodajEl(sled);
						
					double d=podf.aprox(x);	
					Elem e=new Elem(d+"", funkcija.BROJ);
					if (tek!=null) e.sledeci=tek.sledeci;	
					else e.sledeci=null;
					priv.sledeci=e;
					} //sad je podfunkcija oblika funkcija(broj)
			
					funkcija f;
					
					switch(priv.sadrzaj) {
					case "log": f=new Log(); break;
					case "ln":  f=new Ln(); break;
					case "e2": f=new e2(); break;
					case "√": f=new Root(); break;
					case "sin": f=new Sin(); break;
					case "cos": f=new Cos(); break;
					case "tan": f=new Tan(); break;
					case "cot": f=new Cot(); break;
					case "asin": f=new Asin(); break;
					case "acos": f=new Acos(); break;
					case "atan": f=new Atan(); break;
					case "acot": f=new Acot(); break;
					default: f=new funkcija(); break;
				}
					double dd=f.aprox(x);
					
					priv.sadrzaj=dd+"";
					priv.ind=funkcija.BROJ;
					priv.sledeci=priv.sledeci.sledeci;
			}
	}//while(priv!=null)
}//Unarni
			
		public double aprox(double x) throws Greska {
			double d;
			if (glava==null) throw new Greska();
			//ako ima samo jedan element, vratice njegovu vrednost
			//(ovo je uslov za izlazak iz rekurzije)
			if (glava.equals(rep)) {
				if(glava.ind==funkcija.PI) return Math.PI;
				if (glava.ind==funkcija.BROJ) return Double.parseDouble(glava.sadrzaj);
				else if (glava.ind==funkcija.PROM) return x;
				else throw new Greska(); //Greska ako je sam operand ili zagrada
			}
			else {//lista ima vise od jednog elementa
				pret=null;
			if (un==true) Unarni(glava, x);
			in2post();	
				//dodaj opciju da ako lista ima jedan element, vrati tu vrednost i ne zove evalExp
				/*if (glava.sledeci==null) {
					if (glava.equals(rep)) {
						if(glava.ind==funkcija.PI) return Math.PI;
						if (glava.ind==funkcija.BROJ) return Double.parseDouble(glava.sadrzaj);
						else if (glava.ind==funkcija.PROM) return x;
				}
			}*/
				
				d=evalExp(x);
				System.out.println("\n d");
				postfix.rst();
			}
		return d;
		}		
}


