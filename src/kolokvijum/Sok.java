package kolokvijum;

public class Sok {

	private final String naziv;
	private final Ukus ukus;
	private final double procenatVoca;
	private final double kolicina;

	public Sok(String naziv, Ukus ukus, double procenatVoca, double kolicina) {
		this.naziv = naziv;
		this.ukus = ukus;
		this.procenatVoca = procenatVoca;
		this.kolicina = kolicina;
	}

	public String getNaziv() {
		return naziv;
	}

	public Ukus getUkus() {
		return ukus;
	}

	public double getProcenatVoca() {
		return procenatVoca;
	}

	public double getKolicina() {
		return kolicina;
	}

	@Override
	public String toString() {
		return String.format("%s %s %.0f%%", naziv, ukus.toString().toLowerCase(), procenatVoca * 100);
	}
}
