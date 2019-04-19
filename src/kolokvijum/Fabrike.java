package kolokvijum;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Fabrike {

	private static final List<Proizvodjac>lista = new ArrayList<>();

	public static Stream<Proizvodjac> tokProizvodjaca() {
		return lista.stream();
	}

	static {
		try (BufferedReader in = new BufferedReader(new InputStreamReader(
				Fabrike.class.getResourceAsStream("proizvodjaci.txt")))) {
			String linija;
			while ((linija = in.readLine()) != null) {
				String[] delovi = linija.split(",");
				String naziv = delovi[0];
				int brFabrika = Integer.parseInt(delovi[1]);
				boolean domaci = delovi[2].trim().equalsIgnoreCase("domaci");
				lista.add(new Proizvodjac(naziv, brFabrika, domaci));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
