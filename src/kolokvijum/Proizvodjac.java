package kolokvijum;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Proizvodjac {

	private final String nazivProizvodjaca;
	private final int brojFabrika;
	private final boolean domaci;
	private final List<Sok> sokovi;

	public Proizvodjac(String nazivProizvodjaca, int brojFabrika, boolean domaci) {
		this.nazivProizvodjaca = nazivProizvodjaca;
		this.brojFabrika = brojFabrika;
		this.domaci = domaci;
		this.sokovi = random();
	}

	private static final Random random = new Random(0);
	private List<Sok> random() {
		String[] naziv = { "Life", "Ice Tea", "Vitamin", "Happy Day", "Juice Bar", "Yippy", "Hello", "Vocna dolina", "Juice" };
		Ukus[] ukusi = Ukus.values();
		double[] procenti = {0.1, 0.12, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.75, 0.8, 0.9, 1};
		double[] kolicine = {0.25, 0.3, 0.33, 0.5, 0.75, 1, 1.5, 1.75, 2};
		return Stream.generate(() -> {
			return new Sok (naziv[random.nextInt(naziv.length)], ukusi[random.nextInt(ukusi.length)], procenti[random.nextInt(procenti.length)], kolicine[random.nextInt(kolicine.length)]);
		}).limit(100).collect(Collectors.toList());
	}

	public String getNazivProizvodjaca() {
		return nazivProizvodjaca;
	}

	public int getBrojFabrika() {
		return brojFabrika;
	}

	public boolean getDomaci() {
		return domaci;
	}

	public Stream<Sok> sokovi() {
		return sokovi.parallelStream();
	}

	@Override
	public String toString() {
		return nazivProizvodjaca;
	}
}
