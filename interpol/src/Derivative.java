class Derivative {

	/* alle Methoden dieser Klasse sind statische Methoden !!! */

	/*
	 * Diese statische Methode berechnet numerisch die erste Ableitung des
	 * Polynoms pol an einer gegebenen Stelle xzero mit Hilfe des
	 * vorwaerts-Differenzenquotienen fuer das gegebene h
	 */
	static double numeric_forward_deriv(Polynom pol, double xzero, double h) {
		return (pol.evaluate(xzero + h) - pol.evaluate(xzero)) / h;
	}

	/*
	 * Diese statische Methode berechnet numerisch die erste Ableitung des
	 * Polynoms pol an einer gegebenen Stelle xzero mit Hilfe des zentralen
	 * Differenzenquotienen fuer das gegebene h
	 */
	static double numeric_central_deriv(Polynom pol, double xzero, double h) {
		return (pol.evaluate(xzero + h / 2) - pol.evaluate(xzero - h / 2)) / h;
	}

	/*
	 * Diese statische Methode ermittelt die numb_deriv-te analytische Ableitung
	 * eines Polynoms. Es kann davon ausgegangen werden, dass fuer den Grad g
	 * des Polynoms pol gilt: g = pol.get_length()-1 Zurueckgegeben werden soll
	 * ein Polynom pol_deriv, das der numb_deriv-ten Ableitung von pol
	 * entspricht. Fuer den Grad g_deriv dieses Polynoms muss gelten: g_deriv =
	 * pol_deriv.get_length()-1 Das uebergebene Polynom pol darf durch die
	 * Methode nicht veraendert werden.
	 */
	public static Polynom analytic_derivative(Polynom pol, int numb_deriv) {
		if (numb_deriv <= 0)
			return pol;
		else {
			Polynom result = new Polynom(pol.get_length() - 1);
			for (int i = 0; i < pol.get_length() - 1; i++)
				result.coefficients[i] = pol.coefficients[i + 1] * (i + 1);			
			return analytic_derivative(result, numb_deriv-1);
		}
	}

	/*
	 * Diese statische Methode soll das optimale h fuer die Berechnung des
	 * vorwaerts-Differenzenquotienen ermitteln. Dazu wird fuer verschiedene h
	 * (h=2^-i, i von 0 bis 100) die numerische Ableitung mit
	 * "numeric_forward_deriv" berechnet und der Fehler durch Vergleich mit der
	 * analytischen Ableitung ermittelt. (Dazu wertet man das analytisch
	 * abgeleitet Polynom an der Stelle xzero aus, bildet die Differenz zur
	 * numerischen Ableitung und nimmt davon den Absolutbetrag.) Dasjenige i,
	 * bei dem der Fehler am kleinsten ist, soll zurueckgegeben werden. Tritt
	 * der minimale Fehler bei mehreren i (bzw. h) auf, so ist das zum groessten
	 * h gehoerende i (d.h. das kleinste i) zurueckzugeben.
	 */
	static int compare_deriv_forward(Polynom pol, double xzero) {
		double h = 1;
		double exact = analytic_derivative(pol, 1).evaluate(xzero);
		double best = Double.MAX_VALUE;
		int besti = Integer.MAX_VALUE;
		for (int i = 0; i <= 100; i++) {
			double diff = Math
					.abs(exact - numeric_forward_deriv(pol, xzero, h));
			if (diff < best) {
				best = diff;
				besti = i;
			}
			h *= 0.5;
		}
		return besti;
	}

	/*
	 * Diese statische Methode soll das optimale h fuer die Berechnung des
	 * zentralen Differenzenquotienen ermitteln. Dazu wird fuer verschiedene h
	 * (h=2^-i, i von 0 bis 100) die numerische Ableitung mit
	 * "numeric_central_deriv" berechnet und der Fehler durch Vergleich mit der
	 * analytischen Ableitung ermittelt. (Dazu wertet man das analytisch
	 * abgeleitet Polynom an der Stelle xzero aus, bildet die Differenz zur
	 * numerischen Ableitung und nimmt davon den Absolutbetrag.) Dasjenige i,
	 * bei dem der Fehler am kleinsten ist, soll zurueckgegeben werden. Tritt
	 * der minimale Fehler bei mehreren i (bzw. h) auf, so ist das zum groessten
	 * h gehoerende i (d.h. das kleinste i) zurueckzugeben.
	 */
	static int compare_deriv_central(Polynom pol, double xzero) {
		double h = 1;
		double exact = analytic_derivative(pol, 1).evaluate(xzero);
		double best = Double.MAX_VALUE;
		int besti = Integer.MAX_VALUE;
		for (int i = 0; i <= 100; i++) {
			double diff = Math
					.abs(exact - numeric_central_deriv(pol, xzero, h));
			if (diff < best) {
				best = diff;
				besti = i;
			}
			h *= 0.5;
		}
		return besti;
	}
}
