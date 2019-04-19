package kolokvijum;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/*
 * Dat je tok podataka koji sadrzi podatke o proizvodjacima prirodnih
 * vocnih sokova. Za svakog proizvodjaca se zna njegov naziv, broj
 * fabika koje poseduje i da li je poreklom iz Srbije. Pored toga,
 * za svakog proizvodjaca se cuva lista sokova koju on proizvodi.
 * Jedan sok je predstavljen klasom Sok. Za njega se cuvaju podaci
 * o nazivu, njegov ukus, procenat voca koji sadrzi i velicina pakovanja.
 * Pozivom statickog metoda tokProizvodjaca() klase Fabrike dobija se
 * tok podataka koji sadrzi objekte tipa Proizvodjac.
 * 
 * 1) Implementirati metod void domaci() koji ispisuje sve proizvodjace
 * koji su poreklom iz Srbije.
 * 
 * 2) Implementirati metod Proizvodjac najrasprostranjeniji() koji vraca
 * proizvodjaca sa najvecim brojem fabrika. Pozvati metod iz glavnog
 * programa i ispisati rezultat u obliku:
 * 
 *     Najrasprostranjeniji proizvodjac je: Rauch
 * 
 * 3) Implementirati metod Sok najprirodniji(String) koji za datog
 * proizvodjaca pronalazi sok koji sarzi najvecu koncentraciju voca i ima
 * pakovanje najmanje zapremine. U glavnom programu pitati korisnika za
 * ime proizvodjaca, pozvati metod i ispisati rezultat.
 * 
 * 4) Implementirati metod List<Double> pakovanja(String) koji vraca sortiranu
 * listu zapremina pakovanja sokova od jabuke datog proizvodjaca. U glavnom
 * programu pozvati metod za proizvodjaca cije je ime korisnik uneo i ispisati
 * rezultat.
 * 
 * 5) Prikazati tabelarno kolicine sokova. Vrste tabele predstavljaju
 * velicine pakovanja dok kolone predstavlju ukuse. Na odgovarajucem
 * mestu u tabeli se nalazi broj sokova odgovarajuceg ukusa i velicine
 * pakovanja. Ispisati podatke na sledeci nacin:
 * 
 *     Pakovanje | Jabuka | Pomor. | Jagoda | Ostali 
 *     ----------+--------+--------+--------+--------
 *       0.25l   |   19   |   18   |   19   |   57   
 *       0.30l   |   11   |   20   |   16   |   66   
 *       0.33l   |   22   |   23   |   22   |   87   
 *       0.50l   |   19   |   13   |   15   |   66   
 *       0.75l   |   17   |   20   |   19   |   80   
 *       1.00l   |   15   |   14   |   19   |   88   
 *       1.50l   |   20   |   14   |   16   |   97   
 *       1.75l   |   18   |   21   |   22   |   66   
 *       2.00l   |   16   |   22   |   15   |   73   
 * 
 */
public class Program {
    
    private static Scanner sc = new Scanner(System.in);
    
    public static void main(String... args) {

//        Fabrike.tokProizvodjaca()
//            .forEach(System.out::println);

        domaci();
        System.out.println("Najrasprostranjeniji proizvodjac je: " + najrasprostranjeniji());
        System.out.println(najprirodniji("Rauch"));
        System.out.println("Unesite naziv proizvodjaca za pretragu (npr Rauch): ");
        String s = sc.nextLine();
        System.out.println(pakovanja(s));
        napraviTabelu();
    }
    
    private static void domaci() {
        Fabrike.tokProizvodjaca()
            .filter(x -> x.getDomaci())
            .forEach(System.out::println);
    }
    
    private static Proizvodjac najrasprostranjeniji() {
        return Fabrike.tokProizvodjaca()
                .max(Comparator.comparing(Proizvodjac::getBrojFabrika)).orElse(null);
    }
    
    private static Sok najprirodniji(String s) {
        Map<Double, List<Sok>> mapa = Fabrike.tokProizvodjaca()
            .filter(x -> x.getNazivProizvodjaca().equals(s))
            .flatMap(x -> x.sokovi())
            .collect(Collectors.groupingBy(Sok::getProcenatVoca, Collectors.toList()));
        List<Sok> lista = mapa.entrySet().stream().max(Map.Entry.comparingByKey()).get().getValue();
        return lista.stream().min(Comparator.comparing(Sok::getKolicina)).orElse(null);
    }

    private static List<Double> pakovanja(String s) {
        return Fabrike.tokProizvodjaca()
            .filter(x -> x.getNazivProizvodjaca().equals(s))
            .flatMap(x -> x.sokovi())
            .filter(x -> x.getUkus() == Ukus.JABUKA)
            .map(x -> x.getKolicina())
            .sorted()
            .collect(Collectors.toList());
    }
    public static void napraviTabelu() {System.out.println();
        class Brojac {
            int brJabuka;
            int brPomor;
            int brJagoda;
            int brOstalih;
        }
        System.out.println("Pakovanje | Jabuka | Pomor. | Jagoda | Ostali");
        System.out.println("----------+--------+--------+--------+-------");
        Map<Double, Brojac> mapa = Fabrike.tokProizvodjaca()
                .flatMap(x -> x.sokovi())
                .collect(Collectors.groupingBy(Sok::getKolicina,
                        Collector.of(
                                Brojac::new,
                                (b, o) -> {
                                    switch (o.getUkus()) {
                                    case JABUKA:
                                        b.brJabuka++;
                                        break;
                                    case POMORANDZA:
                                        b.brPomor++;
                                        break;
                                    case JAGODA:
                                        b.brJagoda++;
                                        break;
                                    default:
                                        b.brOstalih++;
                                        break;
                                    }
                                },
                                (b1, b2) -> {
                                    b1.brJabuka += b2.brJabuka;
                                    b1.brJagoda += b2.brJagoda;
                                    b1.brPomor += b2.brPomor;
                                    b1.brOstalih += b2.brOstalih;
                                    return b1;
                                })));
        mapa.entrySet().stream()
        .map(e -> String.format(" %6.2fl  |   %-4s |   %-4s |   %-4s |   %-4s",
                e.getKey(),
                e.getValue().brJabuka+"",
                e.getValue().brPomor+"",
                e.getValue().brJagoda+"",
                e.getValue().brOstalih+""))
        .forEach(System.out::println);
    }
}