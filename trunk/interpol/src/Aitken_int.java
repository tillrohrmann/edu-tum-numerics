class Aitken_int {

	private int x[]; /* Array fuer die Stuetzstellen */
	private int y[]; /* Array der Stuetzwerte */

	/*
	 * Der Konstruktor belegt die Arrays x und y mit den Werten aus den
	 * uebergebenen Arrays
	 */
	Aitken_int(int xin[], int yin[]) {
		this.x = new int[xin.length];
		this.y = new int[yin.length];
		for (int i = 0; i < yin.length; i++) {
			this.y[i] = yin[i];
			this.x[i] = xin[i];
		}
	}

	/* gibt eine Kopie des Stuetzstellen-Arrays zurueck */
	public int[] get_x() {
		int xcopy[] = new int[this.x.length];
		for (int i = 0; i < this.x.length; i++) {
			xcopy[i] = this.x[i];
		}
		return xcopy;
	}

	/* gibt eine Kopie des Stuetzwerte-Arrays zurueck */
	public int[] get_y() {
		int ycopy[] = new int[this.y.length];
		for (int i = 0; i < this.y.length; i++) {
			ycopy[i] = this.y[i];
		}
		return ycopy;
	}

	/* gibt die Anzahl an Stuetzpunkten zurueck */
	public int get_number_of_nodes() {
		return this.x.length;
	}

	/*
	 * Berechnet zahl % modulo Beruecksichtigen Sie, dass immer gelten muss
	 * (also inbesondere auch fuer negative Zahlen) : (zahl + k*modulo) % modulo
	 * = zahl % modulo d.h. z.B.: -2 % 5 = (-2+5) % 5 = 3 % 5 = 3
	 */
	public static long mod(long zahl, int modulo) {
		return (zahl % modulo + (zahl >= 0 ? 0 : modulo));
	}

	/*
	 * Berechnet die Modulo-Potenz, d.h. das der Rueckgabewert der Methode
	 * entspricht (b^e) % modulo Es darf allerding nicht zuerst b^e ausgerechnet
	 * werden, da z.B. bereits 10^10 den Wertebereich von Integer sprengt. Fuer
	 * die Verschluesselungs-Anwendung ist eine Auswahl aus insgesamt 10
	 * Schluesseln allerdings sehr unbefriedigend. Die groesste mit int
	 * darstellbare Zahl ist 2^31-1 Es wird fuer die Implementierung verlangt,
	 * dass zumindest fuer eine Basis b <= 2^15 korrekte Ergebnisse produziert
	 * werden. Dies ist zwar aus kryptographischer Sicht immer noch viel zu
	 * wenig, soll aber fuer diese Implementierung genuegen. Um dieses Ziel zu
	 * erreichen, muss auch schon bei Zwischenschritten der Berechung der
	 * modulo-Operator angewendet werden, und nicht erst am Ende
	 */
	public static long pow_mod(long b, int e, int modulo) {
		if(e==0)
			return 1;
		else
			if((e & 1) == 1) {
				return mod(pow_mod(b, e-1, modulo) * b, modulo);
			} else {
				final long tmp = pow_mod(b, e >> 1, modulo);
				return mod(tmp * tmp, modulo);
			}
	}

	/*
	 * Wertet das Aitken-Neville-Schema an einer gegebenen Stelle aus. Bei dem
	 * aus der Vorlesung bekannten Algorithmus wird ein Speicherplatz von O(n^2)
	 * (2D-Array p[i,k]) benoetigt, wobei n die Anzahl an Stuetzstellen ist.
	 * Diese zu implementierende Methode soll dagegen mit O(n) Speicherplatz
	 * (1D-Array p_k[i]) auskommen. Man muss also zunaechst eine Kopie p_k des
	 * Werte-Arrays y erstellen und dann stets direkt auf p_k arbeiten. Nach
	 * Ablauf des Algorithmus steht dann automatisch der gesuchte
	 * Interpolationswert in p_k[0]. Das gesamte Array p_k muss zurueckgegeben
	 * werden.
	 * 
	 * Beachten Sie bei der Implementierung die modifizierten Rechenoperationen
	 * auf dem Koerper.
	 */
	public int[] evaluate(int xeval, int modulo) {
		int p_k[] = get_y();

		for(int k = 1; k < p_k.length; k++) {
			int opk = p_k[p_k.length-k];
			for(int i = p_k.length - k - 1; i >= 0; i--) {
				int nopk = p_k[i];
				long a = pow_mod(x[i+k]-x[i], modulo-2, modulo);
				a = mod(a * (opk - p_k[i]), modulo);
				
				p_k[i] = (int) mod((p_k[i] + mod((xeval-x[i]) * a, modulo)), modulo);
				opk = nopk;
			}
		}
		
		return p_k;
	}

}
