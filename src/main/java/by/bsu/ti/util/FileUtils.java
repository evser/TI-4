package by.bsu.ti.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import by.bsu.ti.bean.Node;

public class FileUtils {

	private static final String DELIMITER = ":";

	private static final Path LETTER_PROBABILITY_FILE = Paths.get("letter-probability.txt");

	private static final Path INPUT_FILE = Paths.get("input.txt");

	public static String normalizeAndReWriteInputFile(final Map<String, Node> nodesMap) throws IOException {
		final List<String> inputFileLines = Files.readAllLines(INPUT_FILE);

		final List<String> normalizedLines = inputFileLines.stream().map(String::toLowerCase).map(line -> {
			return line.chars().map(charSym -> {
				if (charSym == '¸') {
					return 'å';
				} else if (charSym == 'ú') {
					return 'ü';
				} else {
					return charSym;
				}
			}).mapToObj(charSym -> {
				final String symbol = String.valueOf((char) charSym);
				return nodesMap.containsKey(symbol) ? symbol : "";
			}).collect(Collectors.joining());
		}).collect(Collectors.toList());

		String inputText = normalizedLines.stream().collect(Collectors.joining());

		if (inputText.length() % 2 != 0) {
			inputText += " ";
			final int lastIdx = normalizedLines.size() - 1;
			normalizedLines.set(lastIdx, normalizedLines.get(lastIdx) + " ");
		}

		try {
			Files.write(INPUT_FILE, normalizedLines);
		} catch (final IOException ex) {
			ex.printStackTrace();
		}

		return inputText;
	}

	public static Map<String, Node> loadNodesFromFile() throws IOException {
		final List<String> probabilityFileLines = Files.readAllLines(LETTER_PROBABILITY_FILE);

		return probabilityFileLines.stream()
				.map(line -> line.split(DELIMITER))
				.collect(Collectors.toMap(pair -> pair[0], pair -> new Node(Double.parseDouble(pair[1])), (u, v) -> v, TreeMap::new));
	}
}
