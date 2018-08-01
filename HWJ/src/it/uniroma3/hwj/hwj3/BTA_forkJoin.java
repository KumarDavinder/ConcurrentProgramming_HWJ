package it.uniroma3.hwj.hwj3;

import java.util.concurrent.ForkJoinPool;

import it.uniroma3.hwj.base.BinaryTreeAdder;
import it.uniroma3.hwj.base.Node;

public class BTA_forkJoin implements BinaryTreeAdder{

	@Override
	public int computeOnerousSum(Node root) {
		final ForkJoinPool fjpool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
		final TaskForkJoin task = new TaskForkJoin(root);
		return fjpool.invoke(task);
	}
}
