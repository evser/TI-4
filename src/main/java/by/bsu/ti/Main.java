package by.bsu.ti;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.Map;
import java.util.Map.Entry;

import by.bsu.ti.algo.HuffmanAlgo;
import by.bsu.ti.bean.Node;
import by.bsu.ti.util.FileUtils;

public class Main {

	private static final boolean SPLIT_CODES = true;

	private static final double LOG_2 = Math.log(2);

	private static final Path OUTPUT_FILE = Paths.get("output.txt");

	public static void main(String[] args) throws IOException {
		String inputText = FileUtils.normalizeAndReWriteInputFile(FileUtils.loadNodesFromFile());

		System.out.println(calculateHuffman(FileUtils.loadNodesFromFile(), inputText, 1));
		System.out.println("\n---------- Recalculated text:");
		System.out.println(calculateHuffman(HuffmanAlgo.calculateNodeProbabilitiesByText(inputText, 1), inputText, 1));
		System.out.println("\n---------- Paired text:");
		System.out.println(calculateHuffman(HuffmanAlgo.calculateNodeProbabilitiesByText(inputText, 2), inputText, 2));

		// List<String> outputStrings = new ArrayList<>();
		//
		// outputStrings.add(calculateHuffman(FileUtils.loadNodesFromFile(), inputText, 1));
		// outputStrings.add("\n---------- Recalculated text:");
		// outputStrings.add(calculateHuffman(HuffmanAlgo.calculateNodeProbabilitiesByText(inputText, 1), inputText, 1));
		// outputStrings.add("\n---------- Paired text:");
		// outputStrings.add(calculateHuffman(HuffmanAlgo.calculateNodeProbabilitiesByText(inputText, 2), inputText, 2));
		//
		// Files.write(OUTPUT_FILE, outputStrings);
		//
		// outputStrings.forEach(System.out::println);
	}

	private static String calculateHuffman(Map<String, Node> nodesMap, String inputText, int symCount) {
		HuffmanAlgo.buildHuffmanTree(nodesMap.values());

		double avgLength = 0;
		double avgEntropy = 0;

		for (Entry<String, Node> nodeEntry : nodesMap.entrySet()) {
			String letter = nodeEntry.getKey();
			Node node = nodeEntry.getValue();
			String code = HuffmanAlgo.buildHuffmanCode(node);

			avgLength += node.getValue() * code.length();
			avgEntropy += node.getValue() * Math.log(node.getValue()) / LOG_2;

			System.out.println(MessageFormat.format("{0} ({1}): {2}, Length: {3}", letter, node.getValue(), code, code.length()));
		}

		avgEntropy *= -1;
		System.out.println(MessageFormat.format("\nAvg. Word length: {0}, Entropy: {1}", avgLength, avgEntropy));

		String encodedText = HuffmanAlgo.encodeString(inputText, nodesMap, SPLIT_CODES, symCount);
		return "\nText:\n" + inputText + "\nEncoded text:\n" + encodedText;
	}


}
