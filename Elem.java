package raum;


public class Elem{
	public String sadrzaj;
	public int ind; //0 za broj, 1 za operaciju, 2 za promenljivu 
	public Elem sledeci;
	
	public Elem(String s, int b) {
		sadrzaj=s;
		ind=b;
		sledeci=null;
	}
	
	
	public Elem(Elem copy) {
		if (copy!=null) { 
		sadrzaj=copy.sadrzaj;
		ind=copy.ind;
		sledeci=null;
		}
	}
}