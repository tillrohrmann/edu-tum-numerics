public class Gleitpunktzahl {

	/********************/
	/* Membervariablen: */
	/********************/

	/* Vorzeichen, Mantisse und Exponent der Gleitpunktzahl */
	public boolean vorzeichen;
	public BitFeld mantisse;
	public BitFeld exponent;

	/*
	 * Anzahl der Bits fuer die Mantisse: einmal gesetzt, soll sie nicht mehr
	 * veraendert werden koennen
	 */
	private static int anzBitsMantisse = 32;
	private static boolean anzBitsMantisseFixed = false;

	/*
	 * Anzahl der Bits fuer dem Exponent: einmal gesetzt, soll sie nicht mehr
	 * veraendert werden koennen
	 */
	private static int anzBitsExponent = 8;
	private static boolean anzBitsExponentFixed = false;

	/*
	 * Aus der Anzahl an Bits fuer den Exponenten laesst sich der maximale
	 * Exponent und der Offset berechnen
	 */
	private static int maxExponent = (int) Math.pow(2, anzBitsExponent) - 1;
	private static int expOffset = (int) Math.pow(2, anzBitsExponent - 1) - 1;

	/**
	 * Falls die Anzahl der Bits der Mantisse noch nicht gesperrt ist, so wird
	 * sie auf abm gesetzt und gesperrt
	 */
	public static void setAnzBitsMantisse(int abm) {
		/*
		 * Falls anzBitsMantisse noch nicht gesetzt und abm > 0 dann setze auf
		 * abm und sperre den Zugriff
		 */
		if (!anzBitsMantisseFixed & (abm > 0)) {
			anzBitsMantisse = abm;
			anzBitsMantisseFixed = true;
		}
	}

	/**
	 * Falls die Anzahl der Bits des Exponenten noch nicht gesperrt ist, so wird
	 * sie auf abe gesetzt und gesperrt. maxExponent und expOffset werden
	 * festgelegt
	 */
	public static void setAnzBitsExponent(int abe) {
		if (!anzBitsExponentFixed & (abe > 0)) {
			anzBitsExponent = abe;
			anzBitsExponentFixed = true;
			maxExponent = (int) Math.pow(2, abe) - 1;
			expOffset = (int) Math.pow(2, abe - 1) - 1;
		}
	}

	/** Liefert die Anzahl der Bits der Mantisse */
	public int getAnzBitsMantisse() {
		return this.mantisse.getSize();
	}

	/** Liefert die Anzahl der Bits des Exponenten */
	public int getAnzBitsExponent() {
		return this.exponent.getSize();
	}
	
	/**
	 * Returns the max exponent
	 * @return
	 */
	public int getMaxExponent()	{
		return maxExponent;
	}
	
	/**
	 * This function return the exponent offset
	 * @return
	 */
	public int getExpOffset(){
		return expOffset;
	}

	/**
	 * erzeugt eine Gleitpunktzahl ohne Anfangswert. Die Bitfelder fuer Mantisse
	 * und Exponent werden angelegt. Ist die Anzahl der Bits noch nicht gesetzt,
	 * wird sie auf 32 bzw. 8 gesetzt und gesperrt
	 */
	Gleitpunktzahl() {
		if (!anzBitsMantisseFixed) {
			anzBitsMantisse = 32;
			anzBitsMantisseFixed = true;
		}
		if (!anzBitsExponentFixed) {
			anzBitsExponent = 8;
			anzBitsExponentFixed = true;
			maxExponent = (int) Math.pow(2, anzBitsExponent) - 1;
			expOffset = (int) Math.pow(2, anzBitsExponent - 1) - 1;
		}
		/*
		 * Erzeuge neue BitFelder fuer die Speicherung von Mantisse und Exponent
		 */
		this.mantisse = new BitFeld(anzBitsMantisse);
		this.exponent = new BitFeld(anzBitsExponent);
	}

	/** erzeugt eine Kopie der reellen Zahl r */
	Gleitpunktzahl(Gleitpunktzahl r) {

		/* Vorzeichen kopieren */
		this.vorzeichen = r.vorzeichen;
		/*
		 * Erzeugt neue BitFelder fuer die Speicherung von Mantisse und Exponent
		 * und kopiert den Inhalt der jeweiligen Felder aus r
		 */
		this.mantisse = new BitFeld(anzBitsMantisse);
		this.exponent = new BitFeld(anzBitsExponent);
		for (int i = 0; i < anzBitsMantisse; i++) {
			this.mantisse.bits[i] = r.mantisse.bits[i];
		}
		for (int i = 0; i < anzBitsExponent; i++) {
			this.exponent.bits[i] = r.exponent.bits[i];
		}
	}

	/**
	 * erzeugt eine reelle Zahl mit der Repraesentation des Double-Wertes d Ist
	 * die Anzahl der Bits fuer Mantisse und Exponent noch nicht gesetzt, wird
	 * sie auf 32 bzw. 8 gesetzt und gesperrt
	 */
	Gleitpunktzahl(double d) {
		/*
		 * Falls anzBitsMantisse noch nicht gesetzt setze Defaultwert 32 und
		 * sperre den Zugriff
		 */
		if (!anzBitsMantisseFixed) {
			anzBitsMantisse = 32;
			anzBitsMantisseFixed = true;
		}
		/*
		 * Falls anzBitsExponent noch nicht gesetzt setze Defaultwert 8 und
		 * sperre den Zugriff
		 */
		if (!anzBitsExponentFixed) {
			anzBitsExponent = 8;
			anzBitsExponentFixed = true;
			maxExponent = (int) Math.pow(2, anzBitsExponent) - 1;
			expOffset = (int) Math.pow(2, anzBitsExponent - 1) - 1;
		}

		/* BitFelder anlegen */
		this.mantisse = new BitFeld(anzBitsMantisse);
		this.exponent = new BitFeld(anzBitsExponent);
		/* Werte inizialisieren fuer d = 0 */
		this.vorzeichen = false;
		this.mantisse.setBits(false);
		this.exponent.setBits(false);

		/* Spezialfall d = 0 */
		if (d == 0)
			return;

		/* negatives Vorzeichen? */
		if (d < 0) {
			this.vorzeichen = true;
			/* positives d fuer weitere Berechnungen */
			d *= (-1);
		}

		/* exponent bestimmen */
		/* kleinste Potenz groesser als d suchen */
		int i = 0;
		double zweiHochI = 1;
		if (d > 1) {
			while (zweiHochI < d) {
				i++;
				zweiHochI *= 2;
			}
			i--;
			zweiHochI /= 2;
		} else {
			while (zweiHochI > d) {
				i--;
				zweiHochI /= 2;
			}
		}

		/* +/- Unendlich */
		if (i + expOffset >= maxExponent) {
			this.exponent.setBits(true);
			this.mantisse.setBits(false);
			return;
		}
		/*
		 * Null (Falls i+expOffset = 0, muss abgewartet werden, ob durch
		 * aufrunden der Wert doch noch groesser wird
		 */
		else if (i + expOffset < 0) {
			this.exponent.setBits(false);
			this.mantisse.setBits(false);
			return;
		}
		/* Rest */
		else {
			this.exponent.setInt(i + expOffset);
		}

		/* mantisse bestimmen */
		double rest = d;
		for (int j = this.mantisse.getSize() - 1; j >= 0; j--) {
			if (rest == 0)
				break;
			if (zweiHochI <= rest) {
				rest -= zweiHochI;
				this.mantisse.bits[j] = true;
			}
			zweiHochI /= 2;
		}

		/* bei Bedarf aufrunden */
		if (zweiHochI <= rest) {
			if (this.mantisse.inc() > 0) {
				this.exponent.inc();
				if (this.exponent.toInt() == maxExponent) {
					this.mantisse.setBits(false);
				}
			} else {
				/*
				 * Falls nicht aufgerundet wird, kann der Exponent (i+offset)
				 * Null sein
				 */
				if (this.exponent.toInt() == 0) {
					this.exponent.setBits(false);
					this.mantisse.setBits(false);
				}
			}
		}
	}

	/** liefert eine String-Repraesentation des Objekts */
	public String toString() {
		StringBuffer s = new StringBuffer();
		if (this.vorzeichen)
			s.append('-');
		for (int i = this.mantisse.getSize() - 1; i >= 0; i--) {
			if (i == this.mantisse.getSize() - 2)
				s.append(',');
			if (this.mantisse.bits[i])
				s.append('1');
			else
				s.append('0');
		}
		s.append(" * 2^(");
		s.append(this.exponent.toString());
		s.append("-Offset)");
		return s.toString();
	}

	/** berechnet den Double-Wert des Objekts */
	public double toDouble() {
		/*
		 * Wenn der Exponent maximal ist, nimmt die Gleitpunktzahl einen der
		 * speziellen Werte an
		 */
		if (this.exponent.toInt() == maxExponent) {
			/*
			 * Wenn die Mantisse Null ist, hat die Zahl den Wert Unendlich oder
			 * -Unendlich
			 */
			if (this.mantisse.isNull()) {
				if (this.vorzeichen)
					return -1.0 / 0.0;
				else
					return 1.0 / 0.0;
			}
			/* Ansonsten ist der Wert NaN */
			else
				return 0.0 / 0.0;
		}
		double m = this.mantisse.toDouble();
		if (this.vorzeichen)
			m *= (-1);
		return m
				* Math.pow(2, (this.exponent.toInt() - expOffset)
						- (this.mantisse.getSize() - 1));
	}

	/**
	 * denormalisiert die betragsmaessig kleinere Zahl, so dass die Exponenten
	 * von a und b gleich sind. Ist eine der beiden Zahlen 0, so wird diese Zahl
	 * denormalisiert (der Exponent wird angepasst). Die "Null" (bzw. "Nullen")
	 * wird (werden) vorsorglich normalisiert.
	 */
	public static void denormalisiere(Gleitpunktzahl a, Gleitpunktzahl b) {
		/* Sind beide Zahlen 0? */
		if (a.mantisse.isNull() & b.mantisse.isNull()) {
			a.normalisiere();
			b.normalisiere();
			return;
		}
		/* NUR eine der Zahlen 0? */
		if (a.mantisse.isNull()) {
			a.vorzeichen = false;
			for (int i = a.exponent.getSize() - 1; i >= 0; i--) {
				a.exponent.bits[i] = b.exponent.bits[i];
			}
			return;
		}
		if (b.mantisse.isNull()) {
			b.vorzeichen = false;
			for (int i = a.exponent.getSize() - 1; i >= 0; i--) {
				b.exponent.bits[i] = a.exponent.bits[i];
			}
			return;
		}
		// haben beide Zahlen bereits den selben Exponenten, so gibt's nichts zu
		// tun
		if (a.exponent.toInt() == b.exponent.toInt())
			return;

		int diff;
		if (a.exponent.toInt() > b.exponent.toInt()) {
			diff = a.exponent.toInt() - b.exponent.toInt();
			for (int i = a.exponent.getSize() - 1; i >= 0; i--) {
				b.exponent.bits[i] = a.exponent.bits[i];
			}
			for (int i = 1; i < diff; i++)
				b.mantisse.shiftRight(false);
			boolean runden = b.mantisse.bits[0];
			b.mantisse.shiftRight(false);
			if (runden)
				b.mantisse.inc();
		} else {
			diff = b.exponent.toInt() - a.exponent.toInt();
			for (int i = a.exponent.getSize() - 1; i >= 0; i--) {
				a.exponent.bits[i] = b.exponent.bits[i];
			}
			for (int i = 1; i < diff; i++)
				a.mantisse.shiftRight(false);
			boolean runden = a.mantisse.bits[0];
			a.mantisse.shiftRight(false);
			if (runden)
				a.mantisse.inc();
		}
	}

	/**
	 * normalisiert das aktuelle Objekt auf die Darstellung r = (-1)^vorzeichen
	 * * 1,r_t-1 r_t-2 ... r_1 r_0 * 2^exponent. Die 0 wird zu (-1)^0 *
	 * 0,00...00 * 2^0 normalisiert WICHTIG: Es kann sein, dass die Anzahl der
	 * Bits nicht mit anzBitsMantisse uebereinstimmt. Das Ergebnis soll aber
	 * eine Mantisse mit anzBitsMantisse Bits haben. Deshalb muss evtl. mit Bits
	 * aufgefuellt oder Bits abgeschnitten werden. Dabei muss das Ergebnis nach
	 * Definition gerundet werden.
	 */
	public void normalisiere() {

		/* Null normalisieren */
		if (mantisse.isNull()) {
			this.mantisse = new BitFeld(anzBitsMantisse, false);
			if (this.exponent.toInt() < maxExponent) {
				this.vorzeichen = false;
				this.exponent.setBits(false);
			}
			return;
		}

		/* Neue Mantisse anlegen mit normalisierter Laenge */
		BitFeld neueMantisse = new BitFeld(anzBitsMantisse, false);

		/* Fuehrende 1 suchen */
		int positionErsteEins = this.mantisse.getSize() - 1;
		while (!(this.mantisse.bits[positionErsteEins]))
			positionErsteEins--;

		/*
		 * Bei negativem neuem Exponenten ist das Ergebnis Null (Bei neuem
		 * Exponent Null kann durch aufrunden wieder ein positiver Exponent
		 * entstehen)
		 */
		if (this.exponent.toInt()
				- (this.mantisse.getSize() - (positionErsteEins + 1)) < 0) {
			this.exponent.setBits(false);
			this.mantisse.setBits(false);
			return;
		} else {
			this.exponent.setInt(this.exponent.toInt()
					- (this.mantisse.getSize() - (positionErsteEins + 1)));
		}

		int anzBitsAlteMantisse = positionErsteEins + 1;

		/* sind weniger Bits vorhanden als im Ergebnis gefordert? */
		if (anzBitsAlteMantisse < anzBitsMantisse) {
			/* vorhandene Bits uebernehmen */
			for (int j = 0; j <= positionErsteEins; j++)
				neueMantisse.bits[anzBitsMantisse - (j + 1)] = this.mantisse.bits[positionErsteEins
						- j];
			/* restliche Bits mit Nullen auffuellen */
			for (int j = anzBitsMantisse - (anzBitsAlteMantisse + 1); j >= 0; j--)
				neueMantisse.bits[j] = false;
		} else {
			/* es sind mehr Bits vorhanden, als im Ergebnis noetig */
			for (int j = 0; j < anzBitsMantisse; j++) {
				neueMantisse.bits[anzBitsMantisse - (j + 1)] = this.mantisse.bits[positionErsteEins
						- j];
			}

			/* gibt es noch eine Stelle, anhand der gerundet werden koennte? */
			if (anzBitsAlteMantisse > anzBitsMantisse) {
				/* evtl. runden und nochmals normalisieren (bei Ueberlauf) */
				if (this.mantisse.bits[positionErsteEins - anzBitsMantisse]) {
					/* Ergebnis uebernehmen */
					this.mantisse = neueMantisse;
					if (this.mantisse.inc() == 1) {
						if (this.exponent.inc() == 1) {
							this.exponent.setBits(true);
						}
					}
					/*
					 * zur Sicherheit (ab hier wird this.mantisse nicht mehr
					 * veraendert)
					 */
					neueMantisse = this.mantisse;
					/* nochmals normalisieren, da ein Uebertrag denormalisiert */
					this.normalisiere();
				}
			}
		}

		/* +/- Unendlich bzw. Null */
		if (this.exponent.toInt() == maxExponent || this.exponent.toInt() == 0) {
			/* this.mantisse auf die richtige Laenge bringen */
			this.mantisse = neueMantisse;
			this.mantisse.setBits(false);
		} else {
			/* Ergebnis uebernehmen */
			this.mantisse = neueMantisse;
		}
	}

	/**
	 * vergleicht betragsmaessig den Wert des aktuellen Objekts mit der reellen
	 * Zahl r
	 */
	public int compareAbsTo(Gleitpunktzahl r) {
		/*
		 * liefert 1, falls |this| > |r| 0, falls |this| = |r| -1, falls |this|
		 * < |r|
		 */

		/* Beide Zahlen 0? */
		if (this.mantisse.isNull() & r.mantisse.isNull())
			return 0;
		/* Nur eine der beiden Zahlen 0? */
		if (this.mantisse.isNull())
			return -1;
		if (r.mantisse.isNull())
			return 1;

		/* Exponenten vergleichen */
		if (this.exponent.toInt() > r.exponent.toInt())
			return 1;
		if (this.exponent.toInt() < r.exponent.toInt())
			return -1;

		/* Bei gleichen Exponenten: Bitweisses Vergleichen der Mantissen */
		for (int i = this.mantisse.getSize() - 1; i >= 0; i--) {
			if (this.mantisse.bits[i] & !(r.mantisse.bits[i]))
				return 1;
			if (!(this.mantisse.bits[i]) & r.mantisse.bits[i])
				return -1;
		}

		/*
		 * Es konnte keine Differenz gefunden werden => Zahlen betragsmaessig
		 * gleich
		 */
		return 0;

	}
	
	/**
	 * This functions compares to Gleitpunktzahlen
	 * @param number
	 * @return
	 */
	public int compareTo(Gleitpunktzahl number)
	{
		if(this.vorzeichen ^ number.vorzeichen)
			return this.vorzeichen? -1 : 1;
		else if(this.exponent.compareTo(number.exponent) != 0)
			return this.exponent.compareTo(number.exponent);
		else
			return this.mantisse.compareTo(number.mantisse);
	}

	/** subtrahiert vom aktuellen Objekt die Gleitpunktzahl r */
	public Gleitpunktzahl sub(Gleitpunktzahl r) {
		/* this - r = this + (-r) */
		Gleitpunktzahl c = new Gleitpunktzahl(r);
		c.vorzeichen = !c.vorzeichen;
		return this.add(c);
	}

	/**
	 * addiert das aktuelle Objekt und die Gleitpunktzahl r. Dabei wird zuerst
	 * die Anzahl der Bits der Mantissen von a und b auf das doppelte erhoeht,
	 * um die Genauigkeit des Ergebnisses zu verbessern. Das Ergebnis wird in
	 * einem neuen Objekt gespeichert und dieses wird zurueckgegeben
	 */
	public Gleitpunktzahl add(Gleitpunktzahl r) {

		/* Spezialfall: mindestens einer der Operanden ist 0 */
		if (this.mantisse.isNull())
			return new Gleitpunktzahl(r);
		if (r.mantisse.isNull())
			return new Gleitpunktzahl(this);

		/* Objekt fuer Ergebnis anlegen */
		Gleitpunktzahl erg = new Gleitpunktzahl();

		/*
		 * Kopien, damit Originalobjekte beim denormalisieren nicht veraendert
		 * werden
		 */
		Gleitpunktzahl c = new Gleitpunktzahl(this);
		Gleitpunktzahl d = new Gleitpunktzahl(r);

		/* Anzahl Bits der Mantissen auf das Doppelte erhoehen */
		c.mantisse.erweitereFeld(c.mantisse.getSize());
		d.mantisse.erweitereFeld(d.mantisse.getSize());

		Gleitpunktzahl.denormalisiere(c, d);

		/* c negativ, d positiv */
		if (c.vorzeichen & !(d.vorzeichen)) {
			switch (c.compareAbsTo(d)) {
			case 1:
				/* |c| > |d| => erg = -(|c| - |d|) */
				erg.vorzeichen = true;
				erg.mantisse = c.mantisse.sub(d.mantisse);
				for (int i = erg.exponent.getSize() - 1; i >= 0; i--) {
					erg.exponent.bits[i] = c.exponent.bits[i];
				}
				break;
			case 0:
				return new Gleitpunktzahl(0);
			case -1:
				/* |c| < |d| => erg = |d| - |c| */
				erg.vorzeichen = false;
				erg.mantisse = d.mantisse.sub(c.mantisse);
				for (int i = erg.exponent.getSize() - 1; i >= 0; i--) {
					erg.exponent.bits[i] = c.exponent.bits[i];
				}
				break;
			}

		} else {

			/* c positiv, d negativ */
			if (!(c.vorzeichen) & d.vorzeichen) {
				switch (c.compareAbsTo(d)) {
				case 1:
					/* |c| > |d| => erg = |c| - |d| */
					erg.vorzeichen = false;
					erg.mantisse = c.mantisse.sub(d.mantisse);
					for (int i = erg.exponent.getSize() - 1; i >= 0; i--) {
						erg.exponent.bits[i] = c.exponent.bits[i];
					}
					break;
				case 0:
					return new Gleitpunktzahl(0);
				case -1:
					/* |c| < |d| => erg = -(|d| - |c|) */
					erg.vorzeichen = true;
					erg.mantisse = d.mantisse.sub(c.mantisse);
					for (int i = erg.exponent.getSize() - 1; i >= 0; i--) {
						erg.exponent.bits[i] = c.exponent.bits[i];
					}
					break;
				}

			} else {

				/* Beide haben gleiches Vorzeichen */
				/* Vorzeichen bleibt gleich */
				erg.vorzeichen = c.vorzeichen;
				/* Mantissen werden addiert */
				erg.mantisse = c.mantisse.add(d.mantisse);
				for (int i = erg.exponent.getSize() - 1; i >= 0; i--) {
					erg.exponent.bits[i] = c.exponent.bits[i];
				}
				/*
				 * Bei der Addition der Mantisse wird zur Sicherheit eine Stelle
				 * dazugenommen, falls es zu einem Ueberlauf kommt => exponent
				 * wird um eins erhoeht Der Exponent muss dazu kleiner als 255
				 * sein (Addition von speziellen Werten nicht unterstuetzt)
				 */
				erg.exponent.inc();
			}
		}

		erg.normalisiere();

		/* Ergebnis wird zurueckgegeben */
		return erg;
	}

	/**
	 * multipliziert das aktuelle Objekt mit der Gleitpunktzahl r. Das Ergebnis
	 * wird in einem neuen Objekt gespeichert und dieses wird zurueckgegeben.
	 * Die zurueckgegebene Gleitpunktzahl muss normalisiert sein und die gleiche
	 * Anzahl an Bits fuer Mantisse und Exponent verwenden wie die beiden
	 * Operanden
	 */
	public Gleitpunktzahl mult(Gleitpunktzahl r) {

		Gleitpunktzahl erg = new Gleitpunktzahl();
		
		erg.vorzeichen = this.vorzeichen ^ r.vorzeichen;
		
		BitFeld newSignificand = this.mantisse.mult(r.mantisse);
		
		BitFeld tempExponent = this.exponent.add(r.exponent);
		
		tempExponent.inc();
		
		BitFeld offset = new BitFeld(tempExponent.getSize());
		offset.setInt(expOffset);
		
		/*
		 * exponent underflow
		 */
		if(offset.compareTo(tempExponent) > 0)
		{
			erg.mantisse.setBits(false);
			erg.exponent.setBits(false);
		}
		else
		{
			tempExponent = tempExponent.sub(offset);
			
			/*
			 * exponent overflow
			 */
			if(tempExponent.toInt() > maxExponent)
			{
				erg.exponent.setInt(maxExponent);
				erg.mantisse.setBits(false);
			}
			else
			{
				erg.mantisse = newSignificand;
				
				for(int i =0; i < erg.exponent.getSize();i++)
					erg.exponent.bits[i] = tempExponent.bits[i];
			}
			
			erg.normalisiere();
		}
		
		return erg;
	}

	/**
	 * dividiert das aktuelle Objekt durch die Gleitpunktzahl r. Das Ergebnis
	 * wird in einem neuen Objekt gespeichert und dieses wird zurueckgegeben.
	 * Die zurueckgegebene Gleitpunktzahl muss normalisiert sein und die gleiche
	 * Anzahl an Bits fuer Mantisse und Exponent verwenden wie die beiden
	 * Operanden
	 */
	public Gleitpunktzahl div(Gleitpunktzahl r) {
		
		Gleitpunktzahl erg = new Gleitpunktzahl();
		
		erg.vorzeichen = this.vorzeichen ^ r.vorzeichen;
		
		/*
		 * NaN
		 */
		if(r.exponent.isNull() && this.exponent.isNull())
		{
			erg.exponent.setInt(maxExponent);
			erg.mantisse.setInt(1);
			return erg;
		}
		/*
		 * infinity
		 */
		else if(r.exponent.isNull())
		{
			erg.mantisse.setBits(false);
			erg.exponent.setBits(true);
			return erg;
		}
		/*
		 * 0/x = 0: x != 0
		 */
		else if(this.exponent.isNull())
		{
			erg.exponent.setBits(false);
			erg.mantisse.setBits(false);
			return erg;
		}
		
		BitFeld newSignificand = this.mantisse.div(r.mantisse);
		
		BitFeld offset = new BitFeld(this.exponent.getSize());
		offset.setInt(expOffset);
		
		BitFeld tempExponent = this.exponent.add(offset);
		BitFeld helper = new BitFeld(tempExponent.getSize());
		helper.setInt(r.exponent.toInt());
		
		/*
		 * exponent underflow
		 */
		if(tempExponent.compareTo(helper) < 0)
		{
			erg.exponent.setBits(false);
			erg.mantisse.setBits(false);
		}
		else
		{
			tempExponent = tempExponent.sub(helper);
			
			int firstOne = newSignificand.getSize()-1;
			
			while(!newSignificand.bits[firstOne] && firstOne > 0)
				firstOne--;
			
			/*
			 * exponent underflow
			 */
			
			if(tempExponent.toInt() - (newSignificand.getSize()-1 - firstOne) <= 0)
			{
				erg.mantisse.setBits(false);
				erg.exponent.setBits(false);
			}
			else if(tempExponent.toInt() - ( newSignificand.getSize()-1 - firstOne) >= maxExponent){
				erg.mantisse.setBits(false);
				erg.exponent.setBits(true);
			}
			else{
			
				erg.exponent.setInt(tempExponent.toInt() - (newSignificand.getSize()-1 - firstOne));
				
				for(int i =0; i < newSignificand.getSize()-1 - firstOne;i++)
					newSignificand.shiftLeft(false);
				
				erg.mantisse = newSignificand;
			}
			
			
			erg.normalisiere();
		}
		
		return erg;
	}
}
