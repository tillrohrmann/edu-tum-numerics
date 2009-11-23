class Aitken_real {

	double x[];
	double y[];

	/*
	 * Der Konstruktor belegt die Arrays x und y mit den Werten aus den
	 * uebergebenen Arrays
	 */
	Aitken_real(double xin[], double yin[]) {
		this.x = new double[xin.length];
		y = new double[xin.length];
		for (int i = 0; i < yin.length; i++) {
			y[i] = yin[i];
			this.x[i] = xin[i];
		}
	}

	/* gibt eine Kopie des Stuetzstellen-Arrays zurueck */
	public double[] get_x() {
		double xcopy[] = new double[this.x.length];
		for (int i = 0; i < this.x.length; i++) {
			xcopy[i] = this.x[i];
		}
		return xcopy;
	}

	/* gibt eine Kopie des Stuetzwerte-Arrays zurueck */
	public double[] get_y() {
		double ycopy[] = new double[y.length];
		for (int i = 0; i < y.length; i++) {
			ycopy[i] = y[i];
		}
		return ycopy;
	}

	/* gibt die Anzahl an Stuetzpunkten zurueck */
	public int get_number_of_nodes() {
		return this.x.length;
	}

	/*
	 * Wertet das Aitken-Neville-Schema an einer gegebenen Stelle aus. Bei dem
	 * aus der Vorlesung bekannten Algorithmus wird ein Speicherplatz von O(n^2)
	 * (2D-Array p[i,k]) benoetigt, wobei n die Anzahl an Stuetzstellen ist.
	 * Diese zu implementierende Methode soll dagegen mit O(n) Speicherplatz
	 * (1D-Array p_k[i]) auskommen. Man muss also zunaechst eine Kopie p_k des
	 * Werte-Arrays y erstellen und dann stets direkt auf p_k arbeiten. Nach
	 * Ablauf des Algorithmus steht dann automatisch der gesuchte
	 * Interpolationswert in p_k[0]. Das gesamte Array p_k wird zurueckgegeben.
	 */
	public double[] evaluate(double xeval) {
		double p_k[] = get_y();

		for(int k = 1; k < p_k.length; k++) {
			double opk = p_k[p_k.length-k];
			for(int i = p_k.length - k - 1; i >= 0; i--) {
				double nopk = p_k[i];
				p_k[i] = p_k[i] + (xeval-x[i])/(x[i+k]-x[i])*(opk - p_k[i]);
				opk = nopk;
			}
		}
		
		return p_k;
	}

}
