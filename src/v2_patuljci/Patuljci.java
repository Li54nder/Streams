package v2_patuljci;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Patuljci {

	private List<Patuljak> patuljci = new ArrayList<Patuljak>();
	
	
	
	public void addPatuljak(Patuljak p) {
		if(p == null) return;
		patuljci.add(p);
	}
	
	public int brPatuljaka() {
		int br = (int) patuljci.stream()
			.count();
		return br;
	}
	
	public double prosecnoIskopaliZlata() {
		double y = patuljci.stream()
			.mapToDouble(x -> x.getIskopaoZlata())
			.average() //.ifPresent(x -> System.out.printf("%2.3f%%", x));
			.orElse(Double.NaN);
		return y;
	}
	
	public double ukupnoIskopaliZlataOniNaSlovo(char c) {
		double y = patuljci.stream()
			.filter(x -> x.getIme().charAt(0) == c)
			.mapToDouble(x -> x.getIskopaoZlata())
			.sum();
		return y;
	}
	
	public Patuljak najbogatiji() {
//		List<Patuljak> p = patuljci.stream()
//			.sorted((x1 ,x2) -> Double.compare(x2.getIskopaoZlata(), x1.getIskopaoZlata()))
//			.limit(1)
//			.collect(Collectors.toList());
//		return p.get(0);
		
		return patuljci.stream()
				.max((x1 ,x2) -> Double.compare(x2.getIskopaoZlata(), x1.getIskopaoZlata())).get();	
	}
	
	public Patuljak drugiNajbogatiji() {
		return patuljci.stream()
			.sorted((x1 ,x2) -> Double.compare(x2.getIskopaoZlata(), x1.getIskopaoZlata()))
			.skip(1)
			.limit(1)
			.findFirst().get();
	}
	
	public int ukupnoUbiliGoblina() {
		int i = (int)patuljci.stream()
			.mapToInt(x -> x.getUbioGoblina())
			.sum();
		return i;
	}
	
	public List<Patuljak> opadajuceSortiraniPoBrojuUbijenihGoblina() {
		return patuljci.stream()
			.sorted((x1, x2) -> Integer.compare(x2.getUbioGoblina(), x1.getUbioGoblina()))
			.collect(Collectors.toList());
	}
	
	public Set<Patuljak> patuljciStarijiOd(int godina) {
		return patuljci.stream()
			.filter(x -> LocalDate.now().getYear() - x.getGodinaRodjenja() > godina)
			.collect(Collectors.toSet());
	}
	
	/**-------------------------------------------------*/
	public List<String> imenaPatuljakaRastuceSortirana() {
		return patuljci.stream()
			.sorted(Comparator.comparing(Patuljak::getIme))
			.map(x -> x.getIme())
			.collect(Collectors.toList());
	}
	
	public Map<Integer, Set<Patuljak>> patuljciRazvrstaniPoGodiniRodjenja() {
		 return patuljci.stream()
			.collect(Collectors.groupingBy(x -> x.getGodinaRodjenja(), 
							Collectors.toSet()));
	}
	
	public Map<Integer, Set<Patuljak>> patuljciRazvrstaniPoVekuRodjenja() {
		return patuljci.stream()
			.collect(Collectors.groupingBy(x -> x.getGodinaRodjenja() / 100 + 1, 
											Collectors.mapping(x -> x, Collectors.toSet())));
	}
		
	public boolean postojiPatuljakSaViseZlataOd(double zlata) {
		return patuljci.stream()
			.filter(x -> x.getIskopaoZlata() > zlata)
			.findAny().isPresent();
	}
}
