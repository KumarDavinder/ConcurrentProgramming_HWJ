package it.uniroma3.hwj.hwj1;

import it.uniroma3.hwj.base.BinaryTreeAdder;
import it.uniroma3.hwj.base.Node;

import java.util.concurrent.*;

public class BTA_unlimitedMemory implements BinaryTreeAdder{

	@Override
	public int computeOnerousSum(Node root) {
		int sum = 0;
		int cores = Runtime.getRuntime().availableProcessors();
		ExecutorService executor = Executors.newFixedThreadPool(cores);
		LinkedBlockingDeque<Node> buffer = new LinkedBlockingDeque<Node>();
		//barriera
		BarrierAction barrierAction = new BarrierAction(executor, buffer);
		CyclicBarrier barrier = new CyclicBarrier(cores, barrierAction);
		barrierAction.setBarrier(barrier);
		
		BlockingDeque<Future<Integer>> poolResults = new LinkedBlockingDeque<Future<Integer>>();
		ExecutorCompletionService<Integer> ecs = new ExecutorCompletionService<Integer>(executor, poolResults);
		try{
			buffer.putLast(root); //add in buffer di intero albero
			for (int i=0; i < cores; i++) {
				ecs.submit(new TaskUnlimitedBuffer(buffer, barrier));
			}

			for(int j = 0; j < cores; j++){
				sum = sum + poolResults.take().get();
			}
		}
		catch (Exception e){
			e.printStackTrace();
			System.exit(0);
		}
		return sum;
	}
}
