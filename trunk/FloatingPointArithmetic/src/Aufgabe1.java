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
	
	private static void test_BitFeld()
	{
		test_BitFeld_mul();
		test_BitFeld_div();
	}
	
	private static void test_BitFeld_mul()
	{
		try {
			/*****************************/
			/* Test der Klasse BitFeld mul  */
			/*****************************/
			
		    System.out.println("-----------------------------------------");
		    System.out.println("Test der Klasse BitFeld mul");
		    
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
			
		}catch (Exception e) {
			System.out.print("Exception bei der Auswertung des Ergebnis!!\n");
			e.printStackTrace();
		}
	}
	
	
	private static void test_BitFeld_div()
	{
		BitFeld a;
		BitFeld b;
		BitFeld checkref;
		BitFeld erg;
		
		try{
			/*****************************/
			/* Test der Klasse BitFeld div  */
			/*****************************/
			
		    System.out.println("-----------------------------------------");
		    System.out.println("Test der Klasse BitFeld div");
			
			// 1. Test: Bitweise Division 
			//          a/b : a = b both leading 0
		    System.out.println("    1. Test: Bitweise Division - a/b: a=b both leading 0");
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
			
			
			// 2. Test: Bitweise Division 
			//          a/b : a > b both leading 0
		    System.out.println("    2. Test: Bitweise Division - a/b: a>b both leading 0");
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
			
			// 3. Test: Bitweise Division 
			//          a/b : a < b both leading 0
		    System.out.println("    3. Test: Bitweise Division - a/b: a<b both leading 0");
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
		
	}
	
	
	
	private static void test_Gleitpunktzahl()
	{
		test_Gleitpunktzahl_mul();
		test_Gleitpunktzahl_div();
	}
	
	private static void test_Gleitpunktzahl_mul()
	{
		Gleitpunktzahl a;
		Gleitpunktzahl b;
		Gleitpunktzahl checkref;
		Gleitpunktzahl erg;
		
		try{
			/*****************************/
			/* Test der Klasse Gleitpunktzahl mult  */
			/*****************************/
			
		    System.out.println("-----------------------------------------");
		    System.out.println("Test der Klasse Gleitpunktzahl mult");
			
			// 1. Test: Multiplikation 0*0 = 0
		    System.out.println("    1. Test: Multiplikation 0*0 = 0");
			a = new Gleitpunktzahl(0);
			b = new Gleitpunktzahl(0);

			// Referenzwerte setzen
			checkref = new Gleitpunktzahl(0);

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
			
			// 2. Test: Multiplikation 0*x = 0
			System.out.println("	2. Test: Multiplikation 0*x = 0");
			a = new Gleitpunktzahl(0);
			b = new Gleitpunktzahl(12.34);
			
			checkref = new Gleitpunktzahl(0);
			
			erg = a.mult(b);
			
			// Test, ob Ergebnis korrekt
			if(erg.compareTo(checkref)!=0) {
				System.out.println("    FEHLER!");
				System.out.println("        erg: "+erg);
				System.out.println("        ref: "+checkref+"\n");
			} else {
				System.out.println("    Richtiges Ergebnis\n");
			}
			
			
			// 3. Test: Multiplikation x*0 = 0
			System.out.println("	3. Test: Multiplikation x*0 = 0");
			b = new Gleitpunktzahl(0);
			a = new Gleitpunktzahl(12.34);
			
			checkref = new Gleitpunktzahl(0);
			
			erg = a.mult(b);
			
			// Test, ob Ergebnis korrekt
			if(erg.compareTo(checkref)!=0) {
				System.out.println("    FEHLER!");
				System.out.println("        erg: "+erg);
				System.out.println("        ref: "+checkref+"\n");
			} else {
				System.out.println("    Richtiges Ergebnis\n");
			}
			
			// 4. Test: Multiplikation x*y = z
			System.out.println("	4. Test: Multiplikation x*y = z");
			a = new Gleitpunktzahl(12.34);
			b = new Gleitpunktzahl(34.56);
			
			checkref = new Gleitpunktzahl(a.toDouble()*b.toDouble());
			
			erg = a.mult(b);
			
			// Test, ob Ergebnis korrekt
			if(erg.compareTo(checkref)!=0) {
				System.out.println("    FEHLER!");
				System.out.println("        erg: "+erg);
				System.out.println("        ref: "+checkref+"\n");
			} else {
				System.out.println("    Richtiges Ergebnis\n");
			}
			
			// 5. Test: Multiplikation -x*y = -z
			System.out.println("	5. Test: Multiplikation -x*y = -z");
			a = new Gleitpunktzahl(-12.34);
			b = new Gleitpunktzahl(34.56);
			
			checkref = new Gleitpunktzahl(a.toDouble()*b.toDouble());
			
			erg = a.mult(b);
			
			// Test, ob Ergebnis korrekt
			if(erg.compareTo(checkref)!=0) {
				System.out.println("    FEHLER!");
				System.out.println("        erg: "+erg);
				System.out.println("        ref: "+checkref+"\n");
			} else {
				System.out.println("    Richtiges Ergebnis\n");
			}
			
			// 6. Test: Multiplikation x*y = infinity
			System.out.println("	6. Test: Multiplikation x*y = +infinity");
			a = new Gleitpunktzahl();
			b = new Gleitpunktzahl();
			
			a.mantisse.setBits(true);
			a.exponent.setInt((a.getMaxExponent()- a.getExpOffset())/2 + a.getExpOffset());
			b.mantisse.setBits(true);
			b.exponent.setInt((a.getMaxExponent()- a.getExpOffset())/2-1 + a.getExpOffset());
			
			
			checkref = new Gleitpunktzahl();
			checkref.mantisse.setBits(false);
			checkref.exponent.setBits(true);
			
			erg = a.mult(b);
			
			// Test, ob Ergebnis korrekt
			if(erg.compareTo(checkref)!=0) {
				System.out.println("    FEHLER!");
				System.out.println("        erg: "+erg);
				System.out.println("        ref: "+checkref+"\n");
			} else {
				System.out.println("    Richtiges Ergebnis\n");
			}
			
			
			// 7. Test: Multiplikation x*y = z
			System.out.println("	7. Test: Multiplikation x*y = z");
			a = new Gleitpunktzahl();
			b = new Gleitpunktzahl();
			
			a.mantisse.setInt(1<< a.getAnzBitsMantisse()-1);
			a.exponent.setInt((a.getMaxExponent()- a.getExpOffset())/2 + a.getExpOffset());
			b.mantisse.setInt(1<< a.getAnzBitsMantisse()-1);
			b.exponent.setInt((a.getMaxExponent()- a.getExpOffset())/2-1 + a.getExpOffset());
			
			
			checkref = new Gleitpunktzahl(a.toDouble()*b.toDouble());
			
			erg = a.mult(b);
			
			// Test, ob Ergebnis korrekt
			if(erg.compareTo(checkref)!=0) {
				System.out.println("    FEHLER!");
				System.out.println("        erg: "+erg);
				System.out.println("        ref: "+checkref+"\n");
			} else {
				System.out.println("    Richtiges Ergebnis\n");
			}
			
			// 8. Test: Multiplikation x*y = 0
			System.out.println("	8. Test: Multiplikation x*y = 0");
			a = new Gleitpunktzahl(1);
			b = new Gleitpunktzahl(1);
			
			a.exponent.setInt(a.getExpOffset()/2);
			b.exponent.setInt(a.getExpOffset()/2+1);
			
			checkref = new Gleitpunktzahl(0);
			
			erg = a.mult(b);
			
			// Test, ob Ergebnis korrekt
			if(erg.compareTo(checkref)!=0) {
				System.out.println("    FEHLER!");
				System.out.println("        erg: "+erg);
				System.out.println("        ref: "+checkref+"\n");
			} else {
				System.out.println("    Richtiges Ergebnis\n");
			}
			
			// 9. Test: Multiplikation x*y != 0
			System.out.println("	9. Test: Multiplikation x*y != 0");
			a = new Gleitpunktzahl(1);
			b = new Gleitpunktzahl(1);
			
			a.exponent.setInt(a.getExpOffset()/2+1);
			b.exponent.setInt(a.getExpOffset()/2+1);
			
			checkref = new Gleitpunktzahl(a.toDouble() * b.toDouble());
			
			erg = a.mult(b);
			
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
		
		
	}
	
	private static void test_Gleitpunktzahl_div()
	{
		Gleitpunktzahl a;
		Gleitpunktzahl b;
		Gleitpunktzahl erg;
		Gleitpunktzahl checkref;
		
		try{
			/*
			 * Tests the Gleitpunkt division
			 */
			
			 System.out.println("-----------------------------------------");
			 System.out.println("Test der Klasse Gleitpunktzahl div");
			
			// 1. Test: Multiplikation 0/x =0
			System.out.println("	1. Test: Division 0/x = 0");
			a = new Gleitpunktzahl(0);
			b = new Gleitpunktzahl(12.34);

			checkref = new Gleitpunktzahl(a.toDouble() / b.toDouble());
			
			erg = a.div(b);
			
			// Test, ob Ergebnis korrekt
			if(erg.compareTo(checkref)!=0) {
				System.out.println("    FEHLER!");
				System.out.println("        erg: "+erg);
				System.out.println("        ref: "+checkref+"\n");
			} else {
				System.out.println("    Richtiges Ergebnis\n");
			}
			
			// 2. Test: Multiplikation 0/0 =NaN
			System.out.println("	2. Test: Division 0/0 = NaN");
			a = new Gleitpunktzahl(0);
			b = new Gleitpunktzahl(0);
		
			checkref = new Gleitpunktzahl();
			
			checkref.mantisse.setInt(1);
			checkref.exponent.setBits(true);
			
			erg = a.div(b);
			
			// Test, ob Ergebnis korrekt
			if(erg.compareTo(checkref)!=0) {
				System.out.println("    FEHLER!");
				System.out.println("        erg: "+erg);
				System.out.println("        ref: "+checkref+"\n");
			} else {
				System.out.println("    Richtiges Ergebnis\n");
			}
			
			// 3. Test: Multiplikation x/y = z
			System.out.println("	3. Test: Division x/y = z");
			a = new Gleitpunktzahl(12.213462561);
			b = new Gleitpunktzahl(57.2345176764);
		
			checkref = new Gleitpunktzahl(a.toDouble() / b.toDouble());
			
			erg = a.div(b);
			
			// Test, ob Ergebnis korrekt
			if(erg.compareTo(checkref)!=0) {
				System.out.println("    FEHLER!");
				System.out.println("        erg: "+erg);
				System.out.println("        ref: "+checkref+"\n");
			} else {
				System.out.println("    Richtiges Ergebnis\n");
			}
			
			// 4. Test: Multiplikation x/0 = infinity
			System.out.println("	4. Test: Division x/0 = infinty");
			a = new Gleitpunktzahl(12.213462561);
			b = new Gleitpunktzahl(0);
		
			checkref = new Gleitpunktzahl();
			checkref.mantisse.setBits(false);
			checkref.exponent.setBits(true);
			
			erg = a.div(b);
			
			// Test, ob Ergebnis korrekt
			if(erg.compareTo(checkref)!=0) {
				System.out.println("    FEHLER!");
				System.out.println("        erg: "+erg);
				System.out.println("        ref: "+checkref+"\n");
			} else {
				System.out.println("    Richtiges Ergebnis\n");
			}
			
			// 5. Test: Multiplikation x/y = infinity
			System.out.println("	5. Test: Division x/y = infinty");
			a = new Gleitpunktzahl(1);
			b = new Gleitpunktzahl(1);
			
			a.exponent.setInt((a.getMaxExponent()+a.getExpOffset())/2);
			b.exponent.setInt((a.getMaxExponent()-a.getExpOffset())/2 -1);
			
			checkref = new Gleitpunktzahl();
			checkref.mantisse.setBits(false);
			checkref.exponent.setBits(true);
			
			erg = a.div(b);
			
			// Test, ob Ergebnis korrekt
			if(erg.compareTo(checkref)!=0) {
				System.out.println("    FEHLER!");
				System.out.println("        erg: "+erg);
				System.out.println("        ref: "+checkref+"\n");
			} else {
				System.out.println("    Richtiges Ergebnis\n");
			}
			
			// 6. Test: Multiplikation x/y = infinity
			System.out.println("	6. Test: Division x/y =0");
			a = new Gleitpunktzahl(1);
			b = new Gleitpunktzahl(1);
			
			a.exponent.setInt(a.getExpOffset());
			b.exponent.setInt(a.exponent.toInt() + a.getExpOffset());
			
			checkref = new Gleitpunktzahl();
			checkref.mantisse.setBits(false);
			checkref.exponent.setBits(false);
			
			erg = a.div(b);
			
			// Test, ob Ergebnis korrekt
			if(erg.compareTo(checkref)!=0) {
				System.out.println("    FEHLER!");
				System.out.println("        erg: "+erg);
				System.out.println("        ref: "+checkref+"\n");
			} else {
				System.out.println("    Richtiges Ergebnis\n");
			}
			
		}catch(Exception e){
			System.out.println("Exception bei der Auswertung des Ergebnis!!");
			e.printStackTrace();
		}
	}

	private static void test() {
		
		test_BitFeld();
		test_Gleitpunktzahl();
	}

}
