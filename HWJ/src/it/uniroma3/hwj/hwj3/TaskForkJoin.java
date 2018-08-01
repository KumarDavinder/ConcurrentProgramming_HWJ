package it.uniroma3.hwj.hwj3;

import it.uniroma3.hwj.base.FakeProcessor;
import it.uniroma3.hwj.base.Node;
import it.uniroma3.hwj.base.OnerousProcessor;
import it.uniroma3.hwj.suppot.BinaryTree;

import java.util.concurrent.RecursiveTask;

public class TaskForkJoin extends RecursiveTask<Integer>{
	
	private static final long serialVersionUID = 1L;
	public static final int SEQUENTIAL_THRESHOLD = 5000;
	private final OnerousProcessor fp = new FakeProcessor(3000);
	private Node root;
	
	public TaskForkJoin(Node root) {
		this.root = root;
	}

	@Override
	protected Integer compute() {
	//	System.out.println("th "+Thread.currentThread().getId());
		if(BinaryTree.countNodes(this.root) < SEQUENTIAL_THRESHOLD){
			return calculateSum(this.root);
		}
		else{
			final TaskForkJoin left = new TaskForkJoin(this.root.getSx());
			final TaskForkJoin right = new TaskForkJoin(this.root.getDx());
			left.fork();
			Integer rightAns = right.compute();
			Integer leftAns = left.join();
			return leftAns + rightAns + fp.onerousFunction(this.root.getValue());
		}
	}
	
	public int calculateSum(Node node) {
		int sum;
		if(node == null){
			sum = 0;
		}
		
		else{
			sum = this.fp.onerousFunction(node.getValue());
			if(node.getSx() != null)			
				sum += calculateSum(node.getSx());
			if(node.getDx() != null)
				sum += calculateSum(node.getDx());
		}
		return sum;
	}
}
