package it.uniroma3.hwj.hwj2;

import java.util.concurrent.*;
import it.uniroma3.hwj.base.BinaryTreeAdder;
import it.uniroma3.hwj.base.Node;


public class BTA_limitedMemory implements BinaryTreeAdder{
	private int depthTree;

	public BTA_limitedMemory(int depth) {
		this.depthTree = depth;
	}

	@Override
	public int computeOnerousSum(Node root){
		int sum = 0;
		int cores = Runtime.getRuntime().availableProcessors();
		ExecutorService executor = Executors.newFixedThreadPool(cores);
		//O(N ·D) dove N il numero di thread visitatori e D `e la profondità massima dell’al- bero visitato 
		if(this.depthTree == 0) this.depthTree = 1; 
		int size_buffer = cores* this.depthTree;
		ConcurrentLinkedQueue<BlockingDeque<Node>> poolBuffers = new ConcurrentLinkedQueue<BlockingDeque<Node>>();

		BarrierActionLimitedMemory actionBarrier = new BarrierActionLimitedMemory(executor, poolBuffers);
		CyclicBarrier barrier = new CyclicBarrier(cores , actionBarrier);
		actionBarrier.setBarrier(barrier);

		CompletionService<Integer> ecs = new ExecutorCompletionService<Integer>(executor);
		try{
			BlockingDeque<Node> buff = new LinkedBlockingDeque<Node>(size_buffer);
			buff.offerFirst(root);
			poolBuffers.add(buff);
			ecs.submit(new TaskLimitedBuffer(buff, barrier, poolBuffers));

			for(int i = 1;i < cores;i++) {
				BlockingDeque<Node> buffer = new LinkedBlockingDeque<Node>(size_buffer);
				poolBuffers.add(buffer);
				ecs.submit(new TaskLimitedBuffer(buffer, barrier, poolBuffers));
			}

			for(int j = 0; j < cores; j++){
				sum = sum + ecs.take().get();

			}
		}
		catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
		return sum;
	}
	
	public void setDepthTree(int depthTree) {
		this.depthTree = depthTree;
	}
}
