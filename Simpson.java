package raum;

public class Simpson {
	
	private double korak;
	
	public Simpson(double korak) {
		this.korak=korak;
	}



double Racunaj(funkcija f, double a, double b) throws Greska {
	double parni = 0;
	for (int i = 1; i < ((int) (((b - a) / korak) / 2)); i++) {
		parni += f.aprox(a + 2 * i * korak);
	}
	double neparni = 0;
	for (int i = 1; i <= ((int) (((b - a) / korak) / 2)); i++) {
		neparni += f.aprox(a + (2 * i - 1) * korak);
	}
	return ((korak / 3)
			* (f.aprox(a) + 2 * parni + 4 * neparni
					+ f.aprox(b)));
}

};

