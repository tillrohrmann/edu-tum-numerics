class Polynom {

	/*
	 * Array fuer die Koeffizienten des Polynoms coefficients[i] ist hierbei der
	 * Koeffizient vor x^i
	 */
	double coefficients[];

	/*
	 * Der Konstruktor legt ein neues Array der gewuenschten Laenge (Grad+1)
	 * fuer die Koeffizienten an und initialisiert alle Koeffizienten mit 0.0
	 */
	Polynom(int length) {
		this.coefficients = new double[length];
		for (int i = 0; i < length; i++) {
			this.coefficients[i] = 0.0;
		}
	}

	/* Gibt die Laenge des Koeffizienten-Arrays zurueck */
	public int get_length() {
		return this.coefficients.length;
	}

	/* liefert eine String-Darstellung des Polynoms */
	public String toString() {
		StringBuffer s = new StringBuffer();
		s.append("" + this.coefficients[this.get_length() - 1]);
		for (int i = this.get_length() - 2; i >= 0; i--) {
			if (this.coefficients[i] >= 0) {
				s.append("*x^" + (i + 1) + " + ");
				s.append("" + this.coefficients[i]);
			} else {
				s.append("*x^" + (i + 1) + " - ");
				s.append("" + Math.abs(this.coefficients[i]));
			}
		}
		return s.toString();
	}

	/*
	 * Diese Methode wertet das Polynom mit Hilfe des Horner-Schemas an der
	 * gegebenen Auswertestelle "xeval" aus und gibt den Funktionswert zurueck.
	 */
	public double evaluate(double xeval) {
		double result = 0;
		for (int i = coefficients.length - 1; i >= 0; i--) {
			result = result * xeval + coefficients[i];
		}
		return result;
	}

	public void multiply(Polynom factor) {
		Polynom result = new Polynom(coefficients.length + factor.get_length()
				- 1);
		for (int i = 0; i < get_length(); i++) {
			double c = coefficients[i];
			for (int j = 0; j < factor.get_length(); j++) {
				result.coefficients[i + j] += c * factor.coefficients[j];
			}
		}
		coefficients = result.coefficients;
	}

	public void multiply(double factor) {
		for (int i = 0; i < get_length(); i++)
			coefficients[i] *= factor;
	}

	public void normalize() {
		int i = coefficients.length - 1;
		while (i > 0 && coefficients[i] == 0)
			i--;
		double nc[] = new double[i+1];
		System.arraycopy(coefficients, 0, nc, 0, i+1);
		coefficients = nc;
	}

	public void add(Polynom summand) {
		double[] nc = coefficients;
		if (summand.get_length() > get_length()) {
			nc = new double[summand.get_length()];
		}
		for (int i = 0; i < nc.length; i++) {
			nc[i] = (i < summand.get_length() ? summand.coefficients[i]
					: 0) + (i < get_length() ? coefficients[i] : 0);
		}
		coefficients = nc;
	}
}
