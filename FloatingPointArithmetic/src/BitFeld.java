public class BitFeld{

  /* Array zur Speicherung der Bits*/
  public boolean[] bits;
  /* Laenge des BitFelds*/
  private int size;

  /** erzeugt ein Bitfeld der Laenge psize */
  BitFeld(int psize){
    size = psize;
    bits = new boolean[psize];
  }

  /** erzeugt ein Bitfeld der Laenge psize und belegt
    * alle Bits mit wert */
  BitFeld(int psize, boolean wert){
    size = psize;
    bits = new boolean[psize];
    for (int i=0; i<psize; i++) bits[i] = wert;
  }

  /** haengt anzahlNeueStellen an das Bitfeld an.
    * Ist anzahlNeueStellen <= 0, so geschieht nichts */
  public void erweitereFeld(int anzahlNeueStellen){

    if (anzahlNeueStellen > 0) {
      int newSize = size + anzahlNeueStellen;
      boolean[] newBits = new boolean[newSize];
      /* Das alte Feld uebernehmen ... */
      int i=1;
      for (; i <= size; i++)
        newBits[newSize-i] = bits[size-i];
      /* ... und den Rest mit false auffuellen */
      for (; i <= newSize; i++)
        newBits[newSize-i] = false;
      /* Variablen uebernehemen */
      size = newSize;
      bits = newBits;
    }
  }
  
  /**
   * extends the current field with numberNewDigits on the left of the field
   * @param numberNewDigits
   */
  public void extendFieldLeft(int numberNewDigits){
	  
	  if(numberNewDigits <= 0 )
		  return;
	  
	  int newSize = numberNewDigits+size;
	  boolean[] newBits = new boolean[newSize];
	  
	  for(int i =0; i< size;i++)
		  newBits[i] = bits[i];
	  
	  for(int i = size; i < newSize; i++)
		  newBits[i] = false;
	  
	  size = newSize;
	  bits = newBits;
	  
  }

  /** liefert die Laenge des Bitfeldes */
  public int getSize(){
    return size;
  }

  /** setzt alle Bits des Bitfeldes auf wert */
  public void setBits(boolean wert){
    for (int i=0; i<size; i++)
      bits[i] = wert;
  }

  /** setzt die Bits entsprechend dem int wert.
    * Uebersteigt wert den Zahlenbereich des
    * Bitfeldes, so werden nicht darstellbare
    * Bits einfach weggelassen (entspricht einer modulo-
    * Rechnung mit 2^anzBitsMantisse). */
  public void setInt(int wert){
    setBits(false);
    for (int i=0; i<size; i++){
      if (wert == 0) break;
      if ((wert & 1) == 1) bits[i] = true;
      wert = wert >>> 1;
    }
  }


  /** interpretiert das Bitfeld als natuerliche Zahl
    * und ermittelt den repraesentierten int-Wert.
    * Funktioniert nur bis 31 Bit, danach
    * bricht die Berechnung zusammen, da der Zahlenbereich
    * von int zu "klein" wird */
  public int toInt(){
    int erg = 0;
    int zweiHochI = 1;
    for (int i=0; i < size; i++){
      if (bits[i]) erg += zweiHochI;
      zweiHochI *= 2;
    }
    return erg;
  }


  /** interpretiert das Bitfeld als natuerliche Zahl
    * und ermittelt den repraesentierten Double-Wert.
    * Double deshalb, da Long nur bis 63 Bit funktioniert,
    * Double aber, mit evtl. Ungenauigkeiten, sehr viel
    * groessere Zahlen darstellen kann */
  public double toDouble(){
    double erg = 0;
    double zweiHochI = 1;
    for (int i=0; i < size; i++){
      if (bits[i]) erg += zweiHochI;
      zweiHochI *= 2;
    }
    return erg;
  }

  /** schiebt das gesamte Bitfeld nach rechts. Das Bit an der
    * Stelle 0 geht verloren, das Bit an der hoechsten Stelle
    * erhaelt den Wert von fillIn */
  public void shiftRight(boolean fillIn){
    for (int i=1; i<size; i++)
      bits[i-1] = bits[i];
    bits[size-1] = fillIn;
  }

  /** schiebt das gesamte Bitfeld nach links. Das Bit an der
    * Stelle 0 erhaelt den Wert von fillIn, das Bit an der
    * hoechsten Stelle geht verloren */
  public void shiftLeft(boolean fillIn){
    for (int i=size-2; i>=0; i--)
      bits[i+1] = bits[i];
    bits[0] = fillIn;
  }

  /** prueft, ob ALLE Bits 0 sind (entspricht der Abfrage, ob
    * das BitFeld, interpretiert als natuerliche Zahl,
    * den Wert 0 hat). */
  public boolean isNull(){
    for (int i=0; i<size; i++)
      if (bits[i] == true) return false;
    return true;
  }

  /** liefert eine String-Repraesentation des Bitfelds */
  public String toString(){
    StringBuffer s = new StringBuffer();
    for (int i=size-1; i>=0; i--){
      if (bits[i])
        s.append('1');
      else
        s.append('0');
    }
    return s.toString();
  }

  /** erhoeht den Wert des als natuerliche Zahl interpretierten
    * Bitfeldes um 1. Tritt dabei ein Ueberlauf auf, so wird die Zahl
    * um eine Stelle nach rechts geschoben. Ist das Bit, das
    * rechts rausfaellt 1, so wird rekursiv nochmal 1
    * dazuaddiert. Zurueckgeliefert wird die Anzahl, wie oft
    * nach rechts geschoben wurde. Diese Methode dient zum
    * Aufrunden im Zusammenhang mit reellen Zahlen */
  public int inc(){
    boolean uebertrag = true;
    for (int i=0; i < size; i++){
      if (uebertrag ^ bits[i]){
        bits[i] = true;
        uebertrag = false;
      } else {
        /* bits[i] und uebertrag sind gleich:
         * wenn beide erfuellt, bleibt uebertrag true, bits[i] wird zu false
         * wenn beide nicht erfuellt, bleiben beide auf false
         */
        if (bits[i]) bits[i] = false;
      }
    }
    if (uebertrag) {
      if (bits[0]) {
        shiftRight(uebertrag);
        return 1+inc();
      } else {
        shiftRight(uebertrag);
        return 1;
      }
    } else {
      return 0;
    }
  }

  /** addiert zum als natuerliche Zahl interpretierten
    * Bitfeld das Bitfeld b. Beide Bitfelder muessen gleich
    * lang sein. Das Ergebnis wird
    * in einem um eine Stelle laengeren Bitfeld zurueckgeliefert,
    * damit ein eventueller Uebertrag gefasst werden kann. */
  public BitFeld add(BitFeld b){

    /* Eregebnisfeld anlegen mit Platz fuer evtl. Ueberlauf */
    BitFeld erg = new BitFeld(this.size+1);
    
    boolean uebertrag = false;
    /* bitweise vom niederwertigsten Bit an durchrechnen,
     * Ueberlauf stets mitfuehren */
    for (int i=0; i<this.size; i++){
      if (this.bits[i] & b.bits[i] & uebertrag){
        erg.bits[i] = true;
        uebertrag = true;
      } else
      if (!(this.bits[i] | b.bits[i] | uebertrag)){
        erg.bits[i] = false;
        uebertrag = false;
      } else
      if ((this.bits[i] & b.bits[i]) |
          (this.bits[i] & uebertrag) |
          (uebertrag & b.bits[i])){
        erg.bits[i] = false;
        uebertrag = true;
      } else
      if (this.bits[i] | b.bits[i] | uebertrag){
        erg.bits[i] = true;
        uebertrag = false;
      }
    }
    /* Uebertrag ins hoechste Ergebnisbit eintragen */
    erg.bits[this.size] = uebertrag;

    return erg;
  }

  /** subtrahiert vom als natuerliche Zahl interpretierten
    * Bitfeld das Bitfeld b. Beide Bitfelder muessen gleich lang sein.
    * Das Ergebnis wird in einem neuen Bitfeld zurueckgeliefert,
    * das ebenfalls die gleiche Laenge besitzt.
    * Es kann davon ausgegangen werden, dass b betragsmaessig stets
    * kleiner oder gleich dem aktuellen Objekt ist. */
  public BitFeld sub(BitFeld b){

    /* Eregebnisfeld anlegen */
    BitFeld erg = new BitFeld(size);

    boolean uebertrag = false;
    /* bitweise vom niederwertigsten Bit an durchrechnen,
     * Ueberlauf stets mitfuehren */
    for (int i=0; i<size; i++){
      if ((!bits[i] & !b.bits[i] & !uebertrag) |
          (bits[i] & (b.bits[i] ^ uebertrag))) {
        erg.bits[i] = false;
        uebertrag = false;
      } else
      if ((bits[i] & b.bits[i] & uebertrag) |
          (!bits[i] & (b.bits[i] ^ uebertrag))){
        erg.bits[i] = true;
        uebertrag = true;
      } else
      if (bits[i] & !b.bits[i] & !uebertrag){
        erg.bits[i] = true;
        uebertrag = false;
      } else
      if (!bits[i] & b.bits[i] & uebertrag){
        erg.bits[i] = false;
        uebertrag = true;
      }
    }
    /* Uebertrag nicht moeglich, da b stets kleiner als a!! */

    return erg;
  }

  /** addiert zum als natuerliche Zahl interpretierten
    * Bitfeld das Bitfeld b. Beide Bitfelder muessen die gleiche
    * Laenge haben. Das Ergebnisbitfeld hat ebenfalls die gleiche
    * Laenge, der Uebertrag wird daher verworfen */
  public BitFeld addwithoutcarry(BitFeld b){

    /* Eregebnisfeld anlegen */
    BitFeld erg = new BitFeld(this.size);

    boolean uebertrag = false;
    /* bitweise vom niederwertigsten Bit an durchrechnen,
     * Ueberlauf stets mitfuehren */
    for (int i=0; i<this.size; i++){
      if (this.bits[i] & b.bits[i] & uebertrag){
        erg.bits[i] = true;
        uebertrag = true;
      } else
      if (!(this.bits[i] | b.bits[i] | uebertrag)){
        erg.bits[i] = false;
        uebertrag = false;
      } else
      if ((this.bits[i] & b.bits[i]) |
          (this.bits[i] & uebertrag) |
          (uebertrag & b.bits[i])){
        erg.bits[i] = false;
        uebertrag = true;
      } else
      if (this.bits[i] | b.bits[i] | uebertrag){
        erg.bits[i] = true;
        uebertrag = false;
      }
    }

    return erg;
  }


  /* vergleicht den Wert zweier gleich langer Bitfelder */
  public int compareTo(BitFeld b){
    /* liefert 1, falls |this| > |b|
     *         0, falls |this| = |b|
     *        -1, falls |this| < |b| */

    /* Beide Zahlen 0? */
    if (this.isNull() & b.isNull()) return 0;
    /* NUR eine der beiden Zahlen 0? */
    if (this.isNull()) return -1;
    if (b.isNull()) return 1;

    /* Arrays Bitweise vergleichen */
    for(int i=this.size-1; i>=0;i--){
      if (this.bits[i] & !(b.bits[i])) return 1;
      if (!(this.bits[i]) & b.bits[i]) return -1;
    }
    
    /* Es konnte keine Differenz gefunden werden
     * => Bitfelder sind gleich */
    return 0;

  }
  
  /**
   * This method copies the bitfield of b if the size is less or equal
   * @param b
   */
  public void copy(BitFeld b)
  {
	  if(this.size < b.size)
		  return;
	  
	  for(int i =0; i< b.size; i++)
		  bits[i] = b.bits[i];
	  
	  for(int i = b.size; i < size;i++)
		  bits[i] = false;
  }

  /* Berechnet das Produkt zweier gleich langer Bitfelder.
   * Das zurueckgegebene Bitfeld hat die doppelte Laenge 
   * Wenn es einen Uebertrag gibt, ist das hoechtwertigste Bit
   * des Ergebnisses 1, sonst 0 */
  public BitFeld mult(BitFeld b){
	  
	  assert b.size == this.size;
	  
	  BitFeld erg = new BitFeld(2*this.size,false);
	  BitFeld temp = new BitFeld(2*this.size,false);
	  
	  temp.copy(this);
	  
	  for(int i = 0; i< b.size;i++)
	  {
		  if(b.bits[i])
		  {
			  erg = erg.addwithoutcarry(temp);
		  }
		  
		  temp.shiftLeft(false);
	  }
	  
	  return erg;
  }

  /* Berechnet den Quotient zweier gleich langer Bitfelder.
   * Das zurueckgegebene Bitfeld ist um 2 laenger
   * Ist der Divident groesser oder gleich dem Divisor so ist das hoechstwertigste 
   * Bit des Ergebnisses 1, sonst 0. Das niederwertigste Bit des 
   * Ergebnisses wird fuer evtl. noetige Rundung verwendet.
   * Die Rundung wird nicht durch diese Methode, sondern durch die
   * Methode normalisierung in der Klasse Gleitpunktzahl vorgenommen. */
  public BitFeld div(BitFeld b) {
	  assert b.size == this.size;
	  
	  //if b is null should be checked in the caller
	  assert !b.isNull();
	  
	  BitFeld erg = new BitFeld(size+2,false);
	  BitFeld divisor = new BitFeld(size,false);
	  BitFeld dividend = new BitFeld(size,false);
	  int currentDigit = -1;
	  
	  divisor.copy(b);
	  
	  if(this.compareTo(divisor) < 0)
		  dividend.copy(this);
	  else
		  for(currentDigit = size-1; currentDigit >= 0 && dividend.compareTo(divisor) < 0; currentDigit--)
			  dividend.shiftLeft(this.bits[currentDigit]);
	  
	  divisor.extendFieldLeft(1);
	  dividend.extendFieldLeft(1);
	  
	  for(int i = erg.size-1; i>=0;i--)
	  {
		  if(dividend.compareTo(divisor) < 0)
			  erg.bits[i] = false;
		  else{
			  erg.bits[i] = true;
			  dividend = dividend.sub(divisor);
		  }
		  
		  dividend.shiftLeft(currentDigit >= 0 ? this.bits[currentDigit]:false);
		  currentDigit--;
	  }
	  
	  
	  
	  return erg;

  }
}

