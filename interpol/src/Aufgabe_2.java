class Aufgabe_3 {

	/*
	 * Dieses Testprogramm erstellt Objekte der verschiedenen Klassen und wendet
	 * die Methoden der verschiedenen Klassen bzw. Objekte an. Es werden nur
	 * sehr wenige Faelle getestet, d.h. Sie sollten das Programm erweitern, um
	 * weitere Faelle zu testen.
	 */

	public static void test(String name, double exp, double is) {
		if (exp != is)
			System.out.printf("%s: expected %f but received %f\n", name, exp,
					is);
		else
			System.out.printf("%s passed\n", name);
	}

	public static void main(String args[]) {

		/* Festlegen, welche Klassen getestet werden sollen */
		boolean test_polynom = false;
		boolean test_lagrange = false;
		boolean test_derivative = false;
		boolean test_aitken_real = false;
		boolean test_shamir_real = true;
		boolean test_aitken_int = true;
		boolean test_shamir_int = true;

		/***************************/
		/* Test der Klasse Polynom */
		/***************************/

		if (test_polynom) {
			System.out.println("Test der Klasse Polynom:");

			/* erstellen des Polynoms 2*x+1 */
			Polynom testpol = new Polynom(2);
			testpol.coefficients[0] = 1.0;
			testpol.coefficients[1] = 2.0;

			/*
			 * Polynom an der Stelle 5.0 auswerten richtiger Wert: testpol(5.0)
			 * = 220.5
			 */
			System.out.println("  Test der Methode evaluate:");
			if (testpol.evaluate(5.0) == 11.0) {
				System.out.println("    Ergebnis korrekt (11.0)");
			} else {
				System.out.println("    Falsche Ausgabe für das Polynom: "
						+ testpol.toString());
				System.out.println("    FEHLER: falsches Ergebnis: "
						+ testpol.evaluate(5.0));
				System.out
						.println("    FEHLER: korrektes Ergebnis lautet: 11.0");
			}

			testpol = new Polynom(4);
			testpol.coefficients[0] = 1;
			testpol.coefficients[1] = 1;
			testpol.coefficients[2] = 1;
			testpol.coefficients[3] = 1;
			testpol.multiply(testpol);

			System.out.println(testpol);

		}

		/****************************/
		/* Test der Klasse Lagrange */
		/****************************/

		if (test_lagrange) {
			System.out.println("-----------------------------------------");
			System.out.println("Test der Klasse Lagrange:");

			Lagrange l = new Lagrange(new double[] { 1, 2, 3 }, new double[] {
					1, 4, 9 });
			l.assemble_polynoms();

			System.out.println(l.pol);

			test("Lag1", 1, l.pol.evaluate(1));
			test("Lag2", 4, l.pol.evaluate(2));
			test("Lag3", 9, l.pol.evaluate(3));

			l = new Lagrange(new double[] { 1, 2, 3 }, new double[] { 1, 2, 3 });
			l.assemble_polynoms();

			System.out.println(l.pol);

			test("Lag4", 1, l.pol.evaluate(1));
			test("Lag5", 2, l.pol.evaluate(2));
			test("Lag6", 3, l.pol.evaluate(3));
		}

		/******************************/
		/* Test der Klasse Derivative */
		/******************************/

		if (test_derivative) {
			Polynom pol = new Polynom(3); // 3x^2 + 2x + 1
			pol.coefficients[0] = 1;
			pol.coefficients[1] = 2;
			pol.coefficients[2] = 3;

			System.out.println("-----------------------------------------");
			System.out.println("Test der Klasse Derivative:");

			test("Deriv1", 20, Derivative.analytic_derivative(pol, 1).evaluate(
					3));
			test("Deriv2", 6, Derivative.analytic_derivative(pol, 2)
					.evaluate(3));
			test("Deriv3", 0, Derivative.analytic_derivative(pol, 3)
					.evaluate(3));
		}

		/******************************/
		/* Test der Klasse Aitken_real */
		/******************************/

		if (test_aitken_real) {
			System.out.println("-----------------------------------------");
			System.out.println("Test der Klasse Aitken_real:");

			Aitken_real a = new Aitken_real(new double[] { 1, 2, 3 },
					new double[] { 1, 4, 9 });
			test("Aitken1", 16, a.evaluate(4)[0]);
			test("Aitken2", 25, a.evaluate(5)[0]);

			a = new Aitken_real(new double[] { 1, 2, 3 }, new double[] { 1, 2,
					3 });
			test("Aitken3", 4, a.evaluate(4)[0]);
			test("Aitken4", 5, a.evaluate(5)[0]);
		}

		/*******************************/
		/* Test der Klasse Shamir_real */
		/*******************************/

		if (test_shamir_real) {
			System.out.println("-----------------------------------------");
			System.out.println("Test der Klasse Shamir_real");

			Shamir_real s = new Shamir_real(new double[] { 1, 2, 3, 4 },
					new double[] { 2, 9, 28, 65 }, new double[] { 5, 6, 7, 8 });
			System.out.printf("Key: %f\n", s.get_full_key());

			double key = s.create_key(
					new double[] { 100, 110, 120, 130 }, new double[] {
								   100 * 100 * 100 + 1,
								   110 * 110 * 110 + 1,
								   120 * 120 * 120 + 1,
								   130 * 130 * 130 + 1 });
			System.out.printf("Generated key: %f\n", key);

			key = s.create_key(
					new double[] { 300,  310, 320, 330 }, new double[] {
								   300 * 300 * 300 + 1,
								   310 * 310 * 310 + 1,
								   320 * 320 * 320 + 1,
								   330 * 330 * 330 + 1 });
			System.out.printf("Generated key: %f\n", key);
	
			key = s.create_key(	
					new double[] { 1300.0,  1310, 1320, 1330 }, new double[] {
								   1300.0 * 1300 * 1300 + 1,
								   1310.0 * 1310 * 1310 + 1,
								   1320.0 * 1320 * 1320 + 1,
								   1330.0 * 1330 * 1330 + 1 });
			System.out.printf("Generated key: %f\n", key);
		}

		/******************************/
		/* Test der Klasse Aitken_int */
		/******************************/

		if (test_aitken_int) {
			System.out.println("-----------------------------------------");
			System.out.println("Test der Klasse Aitken_int");

			test("AitkenI1", 9, Aitken_int.pow_mod(3, 10, 20));
			test("AitkenI1", 4, Aitken_int.pow_mod(2, 50, 7));
			test("AitkenI1", 7, Aitken_int.pow_mod(7, 15, 12));
		}

		/******************************/
		/* Test der Klasse Shamir_int */
		/******************************/

		if (test_shamir_int) {
			System.out.println("-----------------------------------------");
			System.out.println("Test der Klasse Shamir_int");

			Shamir_int s = new Shamir_int(new int[] { 1, 2, 3, 4 },
					new int[] { 2, 9, 28, 65 }, new int[] { 5, 6, 7, 8 }, 39119);
			System.out.printf("Key: %d\n", s.get_full_key());

			int key = s.create_key(
					new int[] { 100, 110, 120, 130 }, new int[] {
								   100 * 100 * 100 + 1,
								   110 * 110 * 110 + 1,
								   120 * 120 * 120 + 1,
								   130 * 130 * 130 + 1 });
			System.out.printf("Generated key: %d\n", key);

			key = s.create_key(
					new int[] { 300,  310, 320, 330 }, new int[] {
								   300 * 300 * 300 + 1,
								   310 * 310 * 310 + 1,
								   320 * 320 * 320 + 1,
								   330 * 330 * 330 + 1 });
			System.out.printf("Generated key: %d\n", key);
	
			key = s.create_key(	
					new int[] { 1300,  1310, 1320, 1330 }, new int[] {
								   1300 * 1300 * 1300 + 1,
								   1310 * 1310 * 1310 + 1,
								   1320 * 1320 * 1320 + 1,
								   1330 * 1330 * 1330 + 1 });
			System.out.printf("Generated key: %d\n", key);
		}
	}
}
