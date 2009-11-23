class Lagrange {

	Polynom L[]; /* Array fuer die Lagrange-Polynome */
	double x[]; /* Array der Stuetzstellen */
	double y[]; /* Array der Stuetzwerte */
	Polynom pol /* Interpolationspolynom */;

	/*
	 * Lagrange-Konstruktor x und y werden mit den Stuetzstellen und
	 * Stuetzwerten belegt. Die Laenge von x und y muss gleich sein. Das Array
	 * fuer die Lagrange-Polynome wird initialisiert
	 */
	Lagrange(double xin[], double yin[]) {
		int order = xin.length - 1;
		this.L = new Polynom[order + 1];
		this.x = new double[order + 1];
		this.y = new double[order + 1];
		for (int i = 0; i < order + 1; i++) {
			this.x[i] = xin[i];
			this.y[i] = yin[i];
		}
	}

	/*
	 * Kopie des Interpolationspolynoms pol erstellen und zurueckgeben. Dabei
	 * muss pol natuerlich vorher schon mit assemble_polynoms erzeugt worden
	 * sein
	 */
	public Polynom get_polynom() {
		Polynom erg = new Polynom(this.pol.get_length());
		for (int i = 0; i < erg.get_length(); i++) {
			erg.coefficients[i] = this.pol.coefficients[i];
		}
		return erg;
	}

	/*
	 * Kopie eines Lagrangepolynoms L[k] erstellen und zurueckgeben. Dabei muss
	 * das entsprechende Polynom natuerlich vorher schon mit
	 * create_lagrange_base erzeugt worden sein
	 */
	public Polynom get_lagrange_polynom(int k) {
		Polynom erg = new Polynom(L[k].get_length());
		for (int i = 0; i < erg.get_length(); i++) {
			erg.coefficients[i] = L[k].coefficients[i];
		}
		return erg;
	}

	/*
	 * Basisfunktion L[k] fuer gegebenes k erstellen Die genaue Formel steht in
	 * Gl. 1 auf dem Angabenblatt. Fuer k soll das k-te Lagrange-Polynom
	 * aufgestellt werden und in L[k] gespeichert werden
	 */
	public void create_lagrange_base(int k) {
		final Polynom r = new Polynom(1);
		r.coefficients[0] = 1;
		final Polynom f = new Polynom(2);
		
		final double xk = x[k];
		for(int i = 0; i < x.length; i++) {
			if(i != k) {
				final double xi = x[i];
				final double h = 1.0 / (xk - xi);
				f.coefficients[1] = h;
				f.coefficients[0] = -xi * h;

				r.multiply(f);
			}
		}
		
		L[k] = r;
	}

	/*
	 * Diese Methode baut aus den Lagrange-Polynomen das Interpolationspolynom
	 * zusammen. Dazu werden zunaechst alle Lagrange-Basisfunktionen durch
	 * Aufruf von "create_lagrange_base" erstellt und in L gespeichert. L[i] ist
	 * dabei das zum Stuetzpunkt i gehoerige Polynom. Bei den einzelnen
	 * Lagrange-Polynomen ist der Grad durch die Anzahl der Stuetzpunkte exakt
	 * festgelegt. Beim Zusammensetzten kann der Grad jedoch verringert werden.
	 * Dies muss bei der Implementierung beruecksichtigt werden. Die Laenge des
	 * zurueckgegebenen Polynoms muss genau Grad+1 sein (z.B. hat ein lineares
	 * Polynom (Grad 1) zwei Koeffizienten, naemlich fuer x^1 und x^0).
	 */
	public void assemble_polynoms() {
		pol = new Polynom(0);
		
		for(int i = 0; i < x.length; i++) {
			create_lagrange_base(i);
			L[i].multiply(y[i]);
			pol.add(L[i]);
		}
		
		pol.normalize();
	}

}
