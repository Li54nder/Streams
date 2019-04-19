package v3_osobe;

/*
 * Dat je tok osoba u vidu metoda Osobe::osobeStream. Pomocu datog metoda,
 * lambda izraza i operacija nad tokovima podataka implementirati sledece metode
 * i pozvati ih iz glavnog programa:
 *
 * List<Osoba> opadajuceSortiraniPoDatumuRodjenja()
 * Set<Osoba> saVecimPrimanjimaOd(int)
 * double prosecnaPrimanjaUMestu(String)
 * Map<String, Double> prosecnaPrimanjaPoMestu()
 * Map<String, Osoba> saNajvecimPrimanjimaZaSvakiGrad()
 * Map<Integer, List<Osoba>> razvrstaniPoBrojuDece()
 * Map<String, Map<Integer, List<Osoba>>> razvrstaniPoMestuIBrojuDece()
 * String gradSaNajviseDoseljenika()
 * String gradSaNajviseStarosedelaca()
 * String najbogatijiGrad()
 * String najpopularnijeMuskoIme()
 * String najpopularnijeZenskoIme()
 * 
 * U glavnom programu ispisati sledece podatke koriscenjem redukcija tokova
 * podataka (terminalna operacija .reduce()).
 *
 * Najbogatija osoba
 * -----------------
 * 01234567 Pera Peric
 *
 * Muska imena
 * -----------
 * Aca, Arsa, Bora, Vlada, ...
 *
 * Godina kada je rodjena najstarija osoba
 * ---------------------------------------
 * 1940
 *
 * U glavnom programu ispisati sledece podatke na zadati nacin. Racunanje podataka
 * realizovati pomocu operacije .collect(). Prvo podatke skupiti u mapu a potom
 * iz mape pustiti novi tok podataka i formatirati ispis pomocu metoda String::format.
 * Obratiti paznju na format ispisa, velika i mala slova i broj decimala. 
 *
 * Grad       | Broj ljudi | Prosecna primanja
 * -----------+------------+------------------
 * NOVI SAD   |        234 |          49867.56
 * BEOGRAD    |        322 |          50072.33
 * KRAGUJEVAC |        225 |          49215.45
 * ...
 *						
 * Ime        | Br roditelja | Broj muske dece | Broj zenske dece
 * -----------+--------------+-----------------+-----------------
 * Pera       |          234 |             356 |              297
 * Mika       |          322 |             442 |              443
 * Jelena     |          225 |             295 |              312
 * ...
 *
 * Godine | Primanja
 * zivota | Najnize   | Najvise   | Ukupno     | Prosek    | Devijacija 
 * -------+-----------+-----------+------------+-----------+-----------
 * ...
 * 22     |  12600.00 | 102400.00 | 7652300.00 | 503476.12 |     132.66
 * 23     |  29600.00 |  99700.00 | 6843500.00 | 496456.26 |      98.32
 * 24     |  23400.00 | 123400.00 | 8134800.00 | 512388.43 |     253.01
 * ...  
 */
public class OsobeProgram2 {

	public static void main(String[] args) {
//		svi();
//		System.out.println(Osobe.najbogatijiGrad());
//		System.out.println(Osobe.najpopularnijeMuskoIme());
//		System.out.println(Osobe.najpopularnijeZenskoIme());
//		System.out.println(Osobe.godinaRodjenjaNajstarijeOsobe());
//		Osobe.stampajGradBrLjudiPrimanja();
//		Osobe.stampajImeBrRBrMDeceBrZDece();
		Osobe.tabelaPrimanje();
	}

	private static void svi() {
		Osobe.osobeStream(5000)
			.forEach(System.out::println);
	}
}
