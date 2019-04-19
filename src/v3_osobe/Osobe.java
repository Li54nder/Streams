package v3_osobe;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Comparator;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import formater.TableFromLists;


public final class Osobe {

	private static final String[] IMENA = new String[] {
			"Per", "Mik", "Djok", "Raj", "Gaj", "Vlaj", "Zlaj", "Ars", "Bor",
			"Ac", "Jov", "Kost", "Nikol", "Vlad", "Vukot", "Život", "Siniš", "Puniš",
			"Nad", "Ned", "An", "Tanj", "Jelen", "Marij", "Tamar", "Nin", "Mil", "Mim", "Petr", "Ver",
			"Oliver", "Ivan", "Bojan", "Goran", "Zoran", "Dragan"};

	private static final String[] MESTA = new String[] {
			"Novi Sad", "Beograd", "Zrenjanin", "Sombor", "Subotica", "Kikinda", "Smederevo", "Kovin", "Kragujevac", "Ruma"
	};

	public static final Stream<Osoba> osobeStream(int n) {
		Random r = new Random(0);
		return Stream.generate(() -> osoba(r)).limit(n);
	}

	public static final Stream<String> stringStream(int n) {
		return osobeStream(n).map(o -> Osoba.toString(o));
	}

	private static final LocalDate START = LocalDate.of(1940, 1, 1);
	private static final Osoba osoba(Random r) {
		int index = r.nextInt(IMENA.length);
		String ime = IMENA[index] + "a";
		String prezime = IMENA[r.nextInt(IMENA.length)] + ((r.nextDouble() > 0.4) ? "ić" : r.nextDouble() > 0.5 ? "ović" : "ovski");
		Pol pol = index < IMENA.length / 2 ? Pol.MUSKI : Pol.ZENSKI;
		if (pol == Pol.ZENSKI && r.nextDouble() < 0.1) {
			prezime = prezime + "-" + IMENA[r.nextInt(IMENA.length)] + "ić";
		}
		String mesto1 = MESTA[r.nextInt(MESTA.length)];
		String mesto2 = MESTA[r.nextInt(MESTA.length)];
		LocalDate datum = START.plusDays(r.nextInt(20000));
		int primanja = 10 * (50_00 + (int)(50_00 * r.nextGaussian()));
		if (primanja < 5000) {
			primanja = 0;
		}
		Osoba[] deca = new Osoba[r.nextInt(6)];
		for (int i = 0; i < deca.length; i++) {
			int index2 = (index + i + 1) % IMENA.length;
			String ime2 = IMENA[index2] + "a";
			Pol pol2 = index2 < IMENA.length / 2 ? Pol.MUSKI : Pol.ZENSKI;
			LocalDate datum2 = datum.plusDays(7500 + r.nextInt(7500));
			if (datum2.getYear() > 2018) {
				datum2 = datum2.withYear(2018);
			}
			deca[i] = new Osoba(ime2, prezime, pol2, datum2, mesto2, mesto2, 0);
		}
		return new Osoba(ime, prezime, pol, datum, mesto1, mesto2, primanja, deca);
	}
	
	public static List<Osoba> opadajuceSortiraniPoDatumuRodjenja() {
		return Osobe.osobeStream(5000)
				.sorted(Comparator.comparing(Osoba::getDatumRodjenja).reversed())
				.collect(Collectors.toList());
	}
	
	public static Set<Osoba> saVecimPrimanjimaOd(int y) {
		return Osobe.osobeStream(5000)
				.filter(x -> x.getPrimanja() > y)
				.collect(Collectors.toSet());
	}
	
	public static double prosecnaPrimanjaUMestu(String s) {
		return Osobe.osobeStream(5000)
				.filter(x -> x.getMestoStanovanja().equals(s))
				.mapToDouble(x -> x.getPrimanja())
				.average().orElse(Double.NaN);
	}
	
//	@SuppressWarnings({ "unchecked", "restriction" })
//	public static Map<String, Double> prosecnaPrimanjaPoMestu1() {
//		return (Map<String, Double>) Osobe.osobeStream(5000)
//				.collect(Collectors.toMap(Osoba::getMestoStanovanja, x -> Osobe.prosecnaPrimanjaUMestu(x.getMestoStanovanja())));
//	}
	
	public static Map<String, Double> prosecnaPrimanjaPoMestu() {
		return Osobe.osobeStream(5000)
				.collect(Collectors.groupingBy(Osoba::getMestoStanovanja, Collectors.averagingDouble(Osoba::getPrimanja)));
	}
	
	public static Map<String, Osoba> saNajvecimPrimanjimaZaSvakiGrad() {
		return Osobe.osobeStream(5000)
				.collect(Collectors.groupingBy(Osoba::getMestoRodjenja, 
						Collectors.collectingAndThen(Collectors.maxBy(Comparator.comparing(Osoba::getPrimanja)), o-> o.get())));
	}
	
	public static Map<Integer, List<Osoba>> razvrstaniPoBrojuDece() {
		return Osobe.osobeStream(5000)
				.collect(Collectors.groupingBy(Osoba::getBrDece,
						Collectors.toList()));
	}
	
	public static Map<String, Map<Integer, List<Osoba>>> razvrstaniPoMestuIBrojuDece() {
		return Osobe.osobeStream(5000)
				.collect(Collectors.groupingBy(Osoba::getMestoRodjenja, Collectors.groupingBy(Osoba::getBrDece, Collectors.toList())));
	}
	
	public static String gradSaNajviseDoseljenika() {
		return Osobe.osobeStream(5000)
				.filter(x -> !x.getMestoRodjenja().equals(x.getMestoStanovanja()))
				.collect(Collectors.groupingBy(Osoba::getMestoStanovanja, Collectors.counting()))
				.entrySet()
				.stream()
				.max(Map.Entry.comparingByValue())
				.map(Map.Entry::getKey).orElse(null);			
	}
	
	public static String gradSaNajviseStarosedelaca() {
		return Osobe.osobeStream(5000)
				.filter(x -> x.getMestoRodjenja().equals(x.getMestoStanovanja()))
				.collect(Collectors.groupingBy(Osoba::getMestoRodjenja, Collectors.counting()))
				.entrySet()
				.stream()
				.max(Map.Entry.comparingByValue())
				.map(Map.Entry::getKey).orElse("Error!");
	}
	
	public static String najbogatijiGrad() {
		return Osobe.osobeStream(5000)
				.collect(Collectors.groupingBy(Osoba::getMestoStanovanja, Collectors.summingInt(Osoba::getPrimanja)))
				.entrySet()
				.stream()
				.max(Map.Entry.comparingByValue())
				.map(Map.Entry::getKey).orElse("Error!");
	}
	
	public static String najpopularnijeMuskoIme() {
		return Osobe.osobeStream(5000)
				.filter(x -> x.getPol() == Pol.MUSKI)
				.collect(Collectors.groupingBy(Osoba::getIme, Collectors.counting()))
				.entrySet()
				.stream()
				.max(Map.Entry.comparingByValue())
				.map(Map.Entry::getKey).orElse("Error!");
	}
	
	public static String najpopularnijeZenskoIme() {
		return Osobe.osobeStream(5000)
				.filter(x -> x.getPol() != Pol.MUSKI)
				.collect(Collectors.groupingBy(Osoba::getIme, Collectors.counting()))
				.entrySet()
				.stream()
				.max(Map.Entry.comparingByValue())
				.map(Map.Entry::getKey).orElse("Error!");
	}
	
	//reduce();
	public static Osoba najbogatijaOsoba() {
		return Osobe.osobeStream(5000)
				.reduce(null, (o1, o2) -> {
					if(o1 == null) return o2;
					if(o1.getPrimanja() < o2.getPrimanja())
						return o2;
					return o1;
				});
	}

	public static String muskaImena() {
		return Osobe.osobeStream(5000)
				.filter(x -> x.getPol() == Pol.MUSKI)
				.map(x -> x.getIme())
				.distinct()
				.sorted()
				.reduce("", (i1, i2) -> "".equals(i1)? i2 : i1 + ", " + i2);
	}
	
	public static int godinaRodjenjaNajstarijeOsobe() {
		return Osobe.osobeStream(5000)
				.mapToInt(x -> x.getDatumRodjenja().getYear())
				.reduce(0, (x1, x2) -> {
					if(x1 == 0) return x2;
					if(x1 < x2) return x1;
					return x2;
				});
	}
	
	public void stampajGradBrLjudiPrimanja() {
		List<String> linija = Arrays.asList("Grad", "Broj ljudi", "Prosecna primanja");
		System.out.println(TableFromLists.rowFormater(linija, 17));
		linija = Arrays.asList("-", "-", "-");
		System.out.println(TableFromLists.rowFormater(linija, 17));
		Map<String, IntSummaryStatistics> mapa = Osobe.osobeStream(5000)
			.collect(Collectors.groupingBy(Osoba::getMestoStanovanja, Collectors.summarizingInt(Osoba::getPrimanja)));
		mapa.entrySet().stream()
			.map(e -> Arrays.asList(e.getKey().toUpperCase(), ""+e.getValue().getCount(), ""+e.getValue().getAverage()))
			.forEach(e -> TableFromLists.formaterRowPrinter(e, 17));
	}
	
	public static void stampajImeBrRBrMDeceBrZDece() {
		class Br{
			int brRod = 0;
			int brMDece = 0;
			int brZDece = 0;
		}
		TableFromLists.formaterRowPrinter(Arrays.asList("Ime", "Br roditelja", "Br muske dece", "Br zenske dece"));
		TableFromLists.formaterRowPrinter(Arrays.asList("-", "-", "-", "-"));
		Map<String, Br> mapa = Osobe.osobeStream(5000)
				.collect(Collectors.groupingBy(Osoba::getIme, Collector.of(Br::new, 
																(b, o) -> {
																	b.brRod++;
																	b.brMDece += o.getBrDece(Pol.MUSKI);
																	b.brZDece += o.getBrDece(Pol.ZENSKI);
																}, 
																(b1, b2) -> {
																	b1.brRod += b2.brRod;
																	b1.brMDece += b2.brZDece;
																	b1.brZDece += b2.brZDece;
																	return b1;
																})));
		mapa.entrySet().stream()
			.map(x -> Arrays.asList(x.getKey(), x.getValue().brRod+"", x.getValue().brMDece+"", x.getValue().brZDece+""))
			.forEach(TableFromLists::formaterRowPrinter);
	}
	
/**godine zivota | primanje Najnize   | Najvise   | Ukupno     | Prosek    | Devijacija 
 * --------------+--------------------+-----------+------------+-----------+-----------
 * 22            |           12600.00 | 102400.00 | 7652300.00 | 503476.12 |     132.66*/	
	public static void tabelaPrimanje() {
		TableFromLists.formaterRowPrinter(Arrays.asList("Goine zivota", "Najniza primanja", "Najvise primanje", "Ukupno primanje", "Prosecno primanje", "Devijacija"), 18);
		TableFromLists.formaterRowPrinter(Arrays.asList("-", "-", "-", "-", "-", "-"), 18);
		class Pom {
			int br;
			int min;
			int max;
			int suma;
			double devijacija;
			public double prosek() { return (double) suma / br;}
			public void dodaj(int x) {
				br++;
				suma += x;
				min = Math.min(min, x);
				max = Math.max(max, x);
			}
			public Pom spoji(Pom p2) {
				br += p2.br;
				suma += p2.suma;
				min = Math.min(min, p2.min);
				max = Math.max(max, p2.max);
				return this;
			}
			public void devijacija(Stream<Integer> vrednosti) {
				double prosek = prosek();
				double v2n = vrednosti
						.mapToDouble(x -> x - prosek)
						.map(x -> x * x)
						.sum();
				devijacija = Math.sqrt(v2n / br);
			}
		}
		Map<Integer, Set<Integer>> mapa = Osobe.osobeStream(5000)
				.collect(Collectors.groupingBy(
						x -> x.getDatumRodjenja().until(LocalDate.now()).getYears(),
						Collectors.mapping(
								Osoba::getPrimanja,
								Collectors.toSet())));
		Map<Integer, Pom> mapa2 = mapa.entrySet().stream()
				.collect(Collectors.toMap(Map.Entry::getKey, 
										 e -> e.getValue().stream().collect(Collector.of(Pom::new, Pom::dodaj, Pom::spoji))));
		mapa.entrySet().stream()
				.forEach(x -> {
					Pom podaci = mapa2.get(x.getKey());
					podaci.devijacija(x.getValue().stream());
				});
		mapa2.entrySet().stream()
				.map(x -> Arrays.asList(x.getKey()+"", (double)x.getValue().min+"", (double)x.getValue().max+"", (double)x.getValue().suma+"", x.getValue().prosek()+"", x.getValue().devijacija+""))
				.forEach(x -> TableFromLists.formaterRowPrinter(x, 18));
	}
	
}