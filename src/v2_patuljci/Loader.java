package v2_patuljci;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Loader {

	public static Stream<String> load() throws IOException {
		return Files.lines(Paths.get("src/patuljci.txt"));
	}
}
