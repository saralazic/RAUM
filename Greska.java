package raum;

@SuppressWarnings("serial")
public class Greska extends Exception{

	public Greska() {
		
	}
	
	@Override
	public String toString() {
		return "Unos nije korektan";
	}
}
