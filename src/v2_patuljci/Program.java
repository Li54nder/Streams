package v2_patuljci;
import java.io.IOException;
import java.util.stream.Stream;

/**
 * Napisati program koji racuna statistike o patuljcima.
 *
 * Podaci o patuljcima su dati u fajlu "patuljci.txt" za po jednog patuljka u
 * svakom redu. Klasa koja ucitava redove iz fajla, kao i klasa koja predstavlja
 * pautljka, su vec date.
 *
 * Koristeci kolekcije, napraviti klasu koja sadrzi podatke o ucitanim
 * patuljcima i pruza metod void dodaj(Pauljak) za dodavanje novog pautljka.
 *
 * Potom pomocu tokova podataka kreirati patuljke koristeci metod fromString() i
 * ubaciti ih sve u prethodnu klasu.
 * 
 * 
 * Na kraju, takodje pomocu tokova podataka, u datoj klasi implementirati
 * sledece metode i pozvati ih iz glavnog programa:
 *   int brPatuljaka()
 *   double prosecnoIskopaliZlata()
 *   double ukupnoIskopaliZlataOniNaSlovo(char)
 *   Patuljak najbogatiji()
 *   Patuljak drugiNajbogatiji()
 *   int ukupnoUbiliGoblina(Predicate<Patuljak>)
 *   List<Patuljak> opadajuceSortiraniPoBrojuUbijenihGoblina()
 *   Set<Patuljak> patuljciStarijiOd(int)
 *   List<String> imenaPatuljakaRastuceSortiranaPoPrezimenu()
 *   Map<Integer, Set<Patuljak>> patuljciRazvrstaniPoGodiniRodjenja()
 *   Map<Integer, Set<Patuljak>> patuljciRazvrstaniPoVekuRodjenja()
 *   boolean postojiPatuljakSaViseZlataOd(double)
 */
public class Program {
	
	private static Patuljci p = new Patuljci();

	public static void main(String[] args) throws IOException {
		Stream<String> linije = Loader.load();
		parse(linije);
//		linije.forEach(System.out::println);
		System.out.println("Broj patuljaka: " + p.brPatuljaka());
		System.out.println("Prosecno iskopano blaga po patuljku: " + p.prosecnoIskopaliZlata());
		System.out.println("Patuljci na slovo 'A' ukupno ikopali blaga: " + p.ukupnoIskopaliZlataOniNaSlovo('A'));
		System.out.println("Najbogatiji patuljak: " + p.najbogatiji());
		System.out.println("Drugi najbogatiji patuljak: " + p.drugiNajbogatiji());
		System.out.println("Ukupno su ubili goblina: " + p.ukupnoUbiliGoblina());
		System.out.println("Soritirani: " + p.opadajuceSortiraniPoBrojuUbijenihGoblina());
		System.out.println("Patuljci stariji od 800 godina: " + p.patuljciStarijiOd(800));
		System.out.println("Sortirani: " + p.imenaPatuljakaRastuceSortirana());
		System.out.println("Mapirani godini rodjenja: " + p.patuljciRazvrstaniPoGodiniRodjenja());
		System.out.println("Mapirani po veku: " + p.patuljciRazvrstaniPoVekuRodjenja());
		System.out.println("Patuljak sa vise zlata od 100 " + (p.postojiPatuljakSaViseZlataOd(100.20)?"postoji":"ne postoji."));
	}
	
	private static void parse(Stream<String> linije) {
		linije
			.map(Patuljak::fromString)
//			.map(x -> x.fromString(x))
			.forEach(x -> p.addPatuljak(x));
	}
	
}
