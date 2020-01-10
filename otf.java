package raum;

public class otf { //osnovna trapezna formula

private double korak;

	public otf(double korak) {
		this.setKorak(korak);
	}

	
	public double osnovnaTrapezna(funkcija f, double a, double b) throws Greska {
	
		double delta = 0;
		for (int i = 1; i <= ((int) ((b - a) / korak) - 1); i++) {
			delta += f.aprox(a + i * korak);
		}
		return ((korak/ 2)
				* (f.aprox(a) + 2 * delta + f.aprox(b)));
		
	}


	public double getKorak() {
		return korak;
	}


	public void setKorak(double korak) {
		this.korak = korak;
	}
	
	
	
}
