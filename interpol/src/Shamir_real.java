class Shamir_real {

	private Aitken_real ait;
	private double x_missing_keys[];
	private double y_missing_keys[];
	private double full_key;

	/*
	 * Der Konstruktor erstellt aus den uebergebenen Stuetzpunkten (Arrays x und
	 * y) eine Instanz der Klasse Aitken und speichert sie in ait. Er weist
	 * x_missing_keys das uebergebene Array x_missing zu. Dann werden fuer die
	 * Stuetzstellen x_missing_keys mit dem Aitken-Neville-Schema die
	 * zugehoerigen Stuetzwerte ermittelt und in y_missing_keys gespeichert. Der
	 * Stuetzstelle x_missing_keys[i] ist dabei der Stuetzwert y_missing_keys[i]
	 * zugeordnet. Der Gesamtschluessel enspricht dem auswerten des
	 * Interpolationspolynoms an der Stelle Null. Dieser Gesamtschluessel wird
	 * in full_key gespeichert. Nach Aufruf des Konstruktors (nach dem
	 * instanziieren eines Objekts der Klasse Shamir_real) muessen saemtliche
	 * Membervariablen mit den korrekten Werten belegt sein.
	 */
	Shamir_real(double x[], double y[], double x_missing[]) {
		ait = new Aitken_real(x, y);
		x_missing_keys = x_missing;
		y_missing_keys = new double[x_missing.length];
		for(int i = 0; i < x_missing.length; i++) {
			y_missing_keys[i] = ait.evaluate(x_missing_keys[i])[0];
		}
		full_key = ait.evaluate(0)[0];
	}

	/*
	 * Es gibt insgesamt n Teilschluessel. Zu jedem Teilschluessel gibt es eine
	 * Stuetzstelle. Die ersten k Stuetzstellen sind die Stuetzstellen des
	 * Objekts ait, die restlichen n-k Stuetzstellen sind in x_missing_keys
	 * gespeichert. Diese Methode erstellt ein neues Array das saemtliche
	 * Stuetzstellen enthaelt, beginnend mit den ersten k Stuetzstellen des
	 * Objekts ait in der urspruenglichen Reihenfolge, gefolgt von den
	 * restlichen Stuetzstellen aus x_missing_keys (ebenfalls in der
	 * urspruenglichen Reihenfolge Das erstellte Array wird zurueckgegeben
	 */
	public double[] get_all_xkeys() {
		double xkeys[] = this.ait.get_x();
		double all_xkeys[] = new double[this.ait.get_number_of_nodes()
				+ this.x_missing_keys.length];
		for (int i = 0; i < this.ait.get_number_of_nodes(); i++) {
			all_xkeys[i] = xkeys[i];
		}
		int offset = this.ait.get_number_of_nodes();
		for (int i = 0; i < this.x_missing_keys.length; i++) {
			all_xkeys[i + offset] = this.x_missing_keys[i];
		}
		return all_xkeys;
	}

	/*
	 * Es gibt insgesamt n Teilschluessel. Die ersten k Teilschluessel sind die
	 * Stuetzwerte des Objekts ait, die restlichen n-k Teilschluessel sind in
	 * y_missing_keys gespeichert. Diese Methode erstellt ein neues Array das
	 * saemtliche Teilschluessel enthaelt, beginnend mit den ersten k
	 * Teilschluesseln des Objekts ait, gefolgt von den restlichen
	 * Teilschluesseln aus y_missing_keys. Die urspruengliche Reihenfolge aus
	 * den beiden Arrays darf nicht veraendert werden. Das erstellte Array wird
	 * zurueckgegeben
	 */
	public double[] get_all_ykeys() {
		double ykeys[] = this.ait.get_y();
		double all_ykeys[] = new double[this.ait.get_number_of_nodes()
				+ this.y_missing_keys.length];
		for (int i = 0; i < this.ait.get_number_of_nodes(); i++) {
			all_ykeys[i] = ykeys[i];
		}
		int offset = this.ait.get_number_of_nodes();
		for (int i = 0; i < this.y_missing_keys.length; i++) {
			all_ykeys[i + offset] = this.y_missing_keys[i];
		}
		return all_ykeys;
	}

	/* Gibt den Gesamtschluessel zurueck */
	public double get_full_key() {
		return this.full_key;
	}

	/*
	 * Mit Hilfe der gegebenen Stuetzstellen check_xkeys und ihrer zugehoerigen
	 * Stuetzwerte check_ykeys wird ein neuer Gesamtschluessel erzeugt
	 * (Enspricht der Interpolation des zu den uebergebenen Stuetzstellen
	 * gehoerenden Polynoms mit Aitken_Neville an der Stelle 0). Dieser
	 * Schluessel muss zurueckgegeben werden
	 */
	public double create_key(double check_xkeys[], double check_ykeys[]) {
		return new Aitken_real(check_xkeys, check_ykeys).evaluate(0)[0];
	}
}
