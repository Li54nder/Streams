package v1_osobe;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import v1_osobe.Osoba.Pol;

/*
 * Dat je tok osoba u vidu metoda Osobe.osobeStream(). Pomocu datog metoda,
 * lambda izraza i operacija nad tokovima podataka implementirati sledece metode:
 * 
 * svi() - Stampa id, ime i prezime za sve osobe
 * 
 * punoDece() - Stampa ime i prezime svih osoba sa vise od dvoje dece
 *
 * istoMesto() - Stampa, sortirano po imenu mesta, podatke za sve osobe koje zive
 *      u istom mestu u kojem su rodjeni, na sledeci nacin:
 *      Ime Prezime (Mesto)
 * 
 * bogateBezDece() - Stampa podatke o zenama sa platom preko 75.000 koje nemaju
 *      dece, sortirano opadajuce po plati, na sledeci nacin:
 * 
 * rodjendani() - Stampa podatke o osobama koje slave rodjendan ovog meseca.
 *      Stampati dan i mesec, ime i prezime, kao i koliko godina osoba puni, na
 *      sledeci nacin:
 *      DD. MM. Ime Prezime (Puni)
 * 
 * odrasli(String prezime) - Stampa ukupan broj osoba sa datim prezimenom.
 * 
 * ukupnoDece() - Stampa ukupan broj dece svih osoba.
 * 
 * zaPaketice() - Stampa imena, prezimena i starost dece koja su dobila paketice
 *      2010. godine. Uslov za dobijanje paketica je da dete ne bude starije od
 *      10 godina.
 * 
 * imenaPenzionera() - Stampa sva razlicita imena penzionera, sortirana abecedno.
 *      Penzioneri su osobe koje imaju preko 65 godina.
 * 
 * procenat() - Stampa procenat muskih osoba, ukljucujuci i decu.
 * 
 * trecaPoRedu() - Stampa osobu koja je treca po broju muske dece.
 * 
 * najbogatiji(String grad) - Stampa ime (bez prezimena), visinu primanja i mesto
 *      rodjenja osobe sa najvecim primanjima za zadato mesto stanovanja.
 * 
 * josBogatiji(String grad) - Stampa podatke osoba koje imaju veca primanja od
 *      najbogatije osobe iz zadatog mesta, na sledeci nacin:
 *      Ime (primanja) Mesto stanovanja
 * 
 * Implementirati metod Osoba::fromString i prepraviti sve metode da koriste tok
 * stringova Osobe.stringStream().
 */
public class OsobeProgram {

	public static void main(String[] args) {
		//svi();
		//punoDece();
		//istoMesto();
		//bogateBezDece();
		//rodjendani();
		//odrasli("Gajić");
		//ukupnoDece();
		//zaPaketice();
		//imenaPenzionera();
		//procenat();
		//trecaPoRedu();
		//najbogatiji("Ruma");
		josBogatiji("Ruma");
	}

	private static void svi() {
		Osobe.osobeStream(100)
			.forEach(System.out::println);
	}
	
	private static void punoDece() {
		Osobe.osobeStream(100)
			.filter(o -> o.getDeca().size() > 2)
			.forEach(System.out::println);
	}
	
	private static void istoMesto() {
		Osobe.osobeStream(100)
			.filter(x -> x.getMestoRodjenja().equals(x.getMestoStanovanja()))
			.map(x -> x.getIme() + " " + x.getPrezime() + " " + x.getMestoStanovanja())
			.forEach(System.out::println);
	}
	
	private static void bogateBezDece() {
		Osobe.osobeStream(100)
			.filter(x -> x.getPol().equals(Pol.ZENSKI))
			.filter(x -> x.getPrimanja() > 75_000)
			.filter(x -> x.getDeca().size() == 0)
			.sorted((x1, x2) -> Integer.compare(x1.getPrimanja(), x2.getPrimanja()))
			.map(x -> x.getIme() + " " + x.getPrezime() + " " + x.getPrimanja())
			.forEach(System.out::println);
	}
	
	 private static void rodjendani() {
		Osobe.osobeStream(100)
			.filter(x -> x.getDatumRodjenja().getMonth().equals(LocalDate.now().getMonth()))
			.map(x -> x.getDatumRodjenja().getDayOfMonth() + ". " + x.getDatumRodjenja().getMonthValue() +". "+  x.getIme() + " " + x.getPrezime() + " ("
					+ (LocalDate.now().getYear() - x.getDatumRodjenja().getYear()) + ")")
			.forEach(System.out::println);
	}
	private static void odrasli(String prez) {
		 int y = (int) Osobe.osobeStream(100)
		 	.filter(x -> x.getPrezime().equals(prez))
		 	.count();
		 System.out.println("Broj sa tim prezimenom: " + y);
	 }
	 
	 private static void ukupnoDece() {
		 int y = (int) Osobe.osobeStream(100)
		 	.flatMap(x -> x.getDeca().stream())
		 	.count();
		 System.out.println("Ukupno dece: " + y);
	 }
	 
	 private static void zaPaketice() {
		 Osobe.osobeStream(1000)
		 	.filter(x -> (2010 - x.getDatumRodjenja().getYear()) <= 10)
		 	.map(x -> x.getIme() + " " + x.getPrezime() + " " + (LocalDate.now().getYear()-x.getDatumRodjenja().getYear()))
		 	.forEach(System.out::println);
	 }
	 
	 private static void imenaPenzionera() {
		 Osobe.osobeStream(100)
		 	.filter(x -> LocalDate.now().getYear() - x.getDatumRodjenja().getYear() >= 65)
		 	.map(x -> x.getIme() + " " + (LocalDate.now().getYear() - x.getDatumRodjenja().getYear()))
		 	.distinct()
		 	.forEach(System.out::println);
	 }
	 
	 private static void procenat() {
		 Osobe.osobeStream(100)
		 	.flatMap(x -> Stream.concat(Stream.of(x), x.getDeca().stream()))
		 	.mapToDouble(x -> x.getPol().equals(Pol.MUSKI)?100:0)
		 	.average().ifPresent(x -> System.out.printf("%2.3f%%", x));
	 }
	 
	 private static void trecaPoRedu() {
		 Osobe.osobeStream(100)
		 	.sorted((x1, x2) -> Integer.compare(x1.getDeca().size(), x2.getDeca().size()))
		 	.limit(3)
		 	.skip(2)
		 	.forEach(System.out::println);
	 }
	 
	 private static void najbogatiji(String grad) {
		 Osobe.osobeStream(100)
		 	.filter(x -> x.getMestoStanovanja().equals(grad))
		 	.sorted((x1, x2) -> Integer.compare(x2.getPrimanja(), x1.getPrimanja()))
		 	.limit(1)
		 	.map(x -> x.getIme() + " " + x.getPrimanja() + " " + x.getMestoRodjenja())
		 	.forEach(System.out::println);
	 }
	 
	 private static void josBogatiji(String grad) {
		 Osoba o = Osobe.osobeStream(100)
		 	.filter(x -> x.getMestoStanovanja().equals(grad))
		 	.sorted((x1, x2) -> Integer.compare(x2.getPrimanja(), x1.getPrimanja()))
		 	.limit(1)
		 	.collect(Collectors.toList()).get(0);
		 Osobe.osobeStream(100)
		 	.filter(x -> x.getPrimanja() > o.getPrimanja())
		 	.map(x -> x.getIme() + " (" + x.getPrimanja() + ") " + x.getMestoStanovanja())
		 	.forEach(System.out::println);
	 }
	 /* Implementirati metod Osoba::fromString i prepraviti sve metode da koriste tok
	  * stringova Osobe.stringStream().
	  */
}
