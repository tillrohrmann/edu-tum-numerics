public class Aufgabe1 {

	public static void main (String[] argv) {
		switch (argv.length ) {
		case 0: 
			/* Run tests */
			test();
			break;
		case 5:
			/* Konsoleneingabe */

			Gleitpunktzahl.setAnzBitsMantisse(Integer.parseInt(argv[0]));
			Gleitpunktzahl.setAnzBitsExponent(Integer.parseInt(argv[1]));
			Gleitpunktzahl a = new Gleitpunktzahl(Double.parseDouble(argv[2]));
			Gleitpunktzahl b = new Gleitpunktzahl(Double.parseDouble(argv[4]));

			StringBuffer sb = new StringBuffer();
			for (int i=0; i<a.getAnzBitsMantisse()+a.getAnzBitsExponent()+8; i++) sb.append('-');
			sb.append("\n");
			String Trennstrich = sb.toString(); 

			if (argv[3].equals("+")){
				Gleitpunktzahl erg = a.add(b);
				System.out.print(a + " +\n" + b + " =\n"+ Trennstrich + erg + "\n");
				System.out.print(a.toDouble() + " + " + b.toDouble() + " = " + erg.toDouble() + "\n");
			}
			if (argv[3].equals("-")){
				Gleitpunktzahl erg = a.sub(b);
				System.out.print(a + " -\n" + b + " =\n"+ Trennstrich + erg + "\n");
				System.out.print(a.toDouble() + " - " + b.toDouble() + " = " + erg.toDouble() + "\n");
			}
			if (argv[3].equals("*")){
				Gleitpunktzahl erg = a.mult(b);
				System.out.print(a + " *\n" + b + " =\n"+ Trennstrich + erg + "\n");
				System.out.print(a.toDouble() + " * " + b.toDouble() + " = " + erg.toDouble() + "\n");
			}
			if (argv[3].equals("/")){
				Gleitpunktzahl erg = a.div(b);
				System.out.print(a + " /\n" + b + " =\n"+ Trennstrich + erg + "\n");
				System.out.print(a.toDouble() + " / " + b.toDouble() + " = " + erg.toDouble() + "\n");
			}
			break;

		default:
			System.out.println("Aufrufsyntax: java Aufgabe1 <Anzahl Bits Mantisse> <Anzahl Bits Exponent> <1.Zahl> '<+|-|*|/>' <2.Zahl>");
			System.exit(1);
		}
	}

	private static void test() {
		try {
			/*****************************/
			/* Test der Klasse BitFeld   */
			/*****************************/
			
		    System.out.println("-----------------------------------------");
		    System.out.println("Test der Klasse BitFeld");
		    
			BitFeld a;
			BitFeld b;
			BitFeld checkref;
			BitFeld erg;

			// 1. Test: Bitweise Multiplikation 
			//          -fuehrende 0

		    System.out.println("    1. Test: Bitweise Multiplikation - fuehrende 0");
			a = new BitFeld(8);
			b = new BitFeld(8);
			a.setInt(148);
			b.setInt(158);

			// Referenzwerte setzen
			checkref = new BitFeld(16);
			checkref.setInt(23384);

			// Berechnung mit der neu implementierten Methode durchfuehren
			erg = a.mult(b);

			// Test, ob Ergebnis korrekt
			if(erg.compareTo(checkref)!=0) {
				System.out.println("    FEHLER!");
				System.out.println("        erg: "+erg);
				System.out.println("        ref: "+checkref+"\n");
			} else {
				System.out.println("    Richtiges Ergebnis\n");
			}


			// 2. Test: Bitweise Multiplikation 
			//          -fuehrende 1

		    System.out.println("    2. Test: Bitweise Multiplikation - fuehrende 1");
			a = new BitFeld(5);
			b = new BitFeld(5);
			a.setInt(30);
			b.setInt(25);

			// Referenzwerte setzen
			checkref = new BitFeld(10);
			checkref.setInt(750);

			// Berechnung mit der Methode des Studenten durchfuehren
			erg = a.mult(b);

			// Test, ob Ergebnis korrekt
			if(erg.compareTo(checkref)!=0) {
				System.out.println("    FEHLER!");
				System.out.println("        erg: "+erg);
				System.out.println("        ref: "+checkref+"\n");
			} else {
				System.out.println("    Richtiges Ergebnis\n");
			}
			
			// 3. Test: Bitweise Multiplikation 
			//          0*0
		    System.out.println("    3. Test: Bitweise Multiplikation - 0*0");
			a = new BitFeld(11);
			b = new BitFeld(11);
			a.setInt(0);
			b.setInt(0);

			// Referenzwerte setzen
			checkref = new BitFeld(22);
			checkref.setInt(0);

			// Berechnung mit der Methode des Studenten durchfuehren
			erg = a.mult(b);

			// Test, ob Ergebnis korrekt
			if(erg.compareTo(checkref)!=0) {
				System.out.println("    FEHLER!");
				System.out.println("        erg: "+erg);
				System.out.println("        ref: "+checkref+"\n");
			} else {
				System.out.println("    Richtiges Ergebnis\n");
			} 


			// 4. Test: Bitweise Multiplikation 
			//          0*x
		    System.out.println("    4. Test: Bitweise Multiplikation - 0*x");
			a = new BitFeld(9);
			b = new BitFeld(9);
			a.setInt(0);
			b.setInt(10);

			// Referenzwerte setzen
			checkref = new BitFeld(18);
			checkref.setInt(0);

			// Berechnung mit der Methode des Studenten durchfuehren
			erg = a.mult(b);

			// Test, ob Ergebnis korrekt
			if(erg.compareTo(checkref)!=0) {
				System.out.println("    FEHLER!");
				System.out.println("        erg: "+erg);
				System.out.println("        ref: "+checkref+"\n");
			} else {
				System.out.println("    Richtiges Ergebnis\n");
			} 

			// 5. Test: Bitweise Multiplikation 
			//          1*x
		    System.out.println("    5. Test: Bitweise Multiplikation - 1*x");
			a = new BitFeld(10);
			b = new BitFeld(10);
			a.setInt(1);
			b.setInt(22);

			// Referenzwerte setzen
			checkref = new BitFeld(20);
			checkref.setInt(22);

			// Berechnung mit der Methode des Studenten durchfuehren
			erg = a.mult(b);

			// Test, ob Ergebnis korrekt
			if(erg.compareTo(checkref)!=0) {
				System.out.println("    FEHLER!");
				System.out.println("        erg: "+erg);
				System.out.println("        ref: "+checkref+"\n");
			} else {
				System.out.println("    Richtiges Ergebnis\n");
			} 
			
			// 6. Test: Bitweise Division 
			//          a/b : a = b both leading 0
		    System.out.println("    6. Test: Bitweise Division - a/b: a=b both leading 0");
			a = new BitFeld(10);
			b = new BitFeld(10);
			a.setInt(23);
			b.setInt(23);

			// Referenzwerte setzen
			checkref = new BitFeld(12,false);
			checkref.bits[checkref.getSize() -1 ] = true;

			// Berechnung mit der Methode des Studenten durchfuehren
			erg = a.div(b);

			// Test, ob Ergebnis korrekt
			if(erg.compareTo(checkref)!=0) {
				System.out.println("    FEHLER!");
				System.out.println("        erg: "+erg);
				System.out.println("        ref: "+checkref+"\n");
			} else {
				System.out.println("    Richtiges Ergebnis\n");
			}
			
			
			// 7. Test: Bitweise Division 
			//          a/b : a > b both leading 0
		    System.out.println("    7. Test: Bitweise Division - a/b: a>b both leading 0");
			a = new BitFeld(10);
			b = new BitFeld(10);
			a.setInt(49);
			b.setInt(7);

			// Referenzwerte setzen
			checkref = new BitFeld(12,false);
			checkref.setInt(7);
			while(!checkref.bits[checkref.getSize()-1])
				checkref.shiftLeft(false);

			// Berechnung mit der Methode des Studenten durchfuehren
			erg = a.div(b);

			// Test, ob Ergebnis korrekt
			if(erg.compareTo(checkref)!=0) {
				System.out.println("    FEHLER!");
				System.out.println("        erg: "+erg);
				System.out.println("        ref: "+checkref+"\n");
			} else {
				System.out.println("    Richtiges Ergebnis\n");
			}
			
			// 8. Test: Bitweise Division 
			//          a/b : a < b both leading 0
		    System.out.println("    8. Test: Bitweise Division - a/b: a<b both leading 0");
			a = new BitFeld(10);
			b = new BitFeld(10);
			a.setInt(6);
			b.setInt(12);

			// Referenzwerte setzen
			checkref = new BitFeld(12,false);
			checkref.setInt(1);
			while(!checkref.bits[checkref.getSize()-2])
				checkref.shiftLeft(false);

			// Berechnung mit der Methode des Studenten durchfuehren
			erg = a.div(b);

			// Test, ob Ergebnis korrekt
			if(erg.compareTo(checkref)!=0) {
				System.out.println("    FEHLER!");
				System.out.println("        erg: "+erg);
				System.out.println("        ref: "+checkref+"\n");
			} else {
				System.out.println("    Richtiges Ergebnis\n");
			}
			
		} catch (Exception e) {
			System.out.print("Exception bei der Auswertung des Ergebnis!!\n");
			e.printStackTrace();
		}
		
		
		
		
		
		
		
		
		
		System.out.print("\nTests für die Klasse Gleitpunktzahl sind ggf. selbst zu erstellen!!\n");
		
		/**
		 *  optional TODO Die Tests an Klasse Gleitpunktzahl sind ggf. selbst zu erstellen.
		 **/
	}

}
