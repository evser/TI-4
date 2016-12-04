package by.bsu.ti.algo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.TreeMap;
import java.util.stream.Collectors;

import by.bsu.ti.bean.Node;

public class HuffmanAlgo {

	public static void buildHuffmanTree(Collection<Node> nodes) {
		Queue<Node> nodeQueue = new PriorityQueue<>(nodes);
		while (nodeQueue.size() > 1) {
			Node leftNode = nodeQueue.remove();
			Node rightNode = nodeQueue.remove();
			Node newNode = new Node(leftNode.getValue() + rightNode.getValue());
			leftNode.setParent(newNode);
			rightNode.setParent(newNode);
			newNode.setLeftChild(leftNode);
			newNode.setRightChild(rightNode);
			nodeQueue.add(newNode);
		}
	}

	public static String buildHuffmanCode(Node node) {
		List<Byte> code = new ArrayList<>();
		Node parent = node.getParent();
		while (parent != null) {
			code.add((byte) (parent.getLeftChild().equals(node) ? 0 : 1));
			node = parent;
			parent = parent.getParent();
		}
		Collections.reverse(code);
		return code.stream().map(String::valueOf).collect(Collectors.joining());
	}

	public static String encodeString(String text, Map<String, Node> nodeMap, boolean splitCodes, int symCount) {
		StringBuilder encodedStr = new StringBuilder();

		String[] symbols = text.split("");
		for (int i = 0; i < symbols.length; i++) {
			String symbolSet = ""; // 1 or symCount
			for (int j = 0; j < symCount; j++) {
				symbolSet += symbols[i + j];
			}
			i += symCount - 1;

			encodedStr.append(buildHuffmanCode(nodeMap.get(symbolSet)));
			if (splitCodes) {
				encodedStr.append(" ");
			}
		}

		return encodedStr.toString();
	}

	public static Map<String, Node> calculateNodeProbabilitiesByText(String text, int symCount) {
		Map<String, Node> nodesMap = new TreeMap<>();
		double symbolProbability = 1.0 / text.length();
		String[] symbols = text.split("");
		
		for (int i = 0; i < symbols.length - symCount + 1; i++) {
			String symbolSet = ""; // 1 or symCount
			for (int j = 0; j < symCount; j++) {
				symbolSet += symbols[i + j];
			}

			Node node = nodesMap.get(symbolSet);
			if (node == null) {
				nodesMap.put(symbolSet, new Node(symbolProbability));
			} else {
				node.setValue(node.getValue() + symbolProbability);
			}
		}
		
		return nodesMap;
	}

}
