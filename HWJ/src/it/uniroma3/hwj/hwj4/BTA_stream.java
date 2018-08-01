package it.uniroma3.hwj.hwj4;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import it.uniroma3.hwj.base.BinaryTreeAdder;
import it.uniroma3.hwj.base.Node;
import it.uniroma3.hwj.base.FakeProcessor;

public class BTA_stream implements BinaryTreeAdder{

	@Override
	public int computeOnerousSum(Node root) {
		List<CompletableFuture<Integer>> poolResult = getStream(root)
				.map(node -> CompletableFuture.supplyAsync(() -> (new FakeProcessor(3000).onerousFunction(node.getValue()))))
				.collect(Collectors.toList());
		return poolResult.stream().map(CompletableFuture::join).reduce(0, (a,b) -> a+b);
	}

	public static Stream<Node> getStream(Node root){
		Stream<Node> concatenation;
		if(root == null)
			concatenation = null;
		else {
			concatenation = Stream.of(root);
			if(root.getSx() != null)
				concatenation = Stream.concat(concatenation, getStream(root.getSx()));
			if(root.getDx() != null)
				concatenation = Stream.concat(concatenation, getStream(root.getDx()));
		}
		return concatenation;
	}
}
