package it.uniroma3.hwj.hwj2;

import it.uniroma3.hwj.base.FakeProcessor;
import it.uniroma3.hwj.base.Node;
import it.uniroma3.hwj.base.OnerousProcessor;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CyclicBarrier;

public class TaskLimitedBuffer implements Callable<Integer> {
	BlockingDeque<Node> buffer;
	CyclicBarrier barrier;
	ConcurrentLinkedQueue<BlockingDeque<Node>> poolBuffers;
	private final OnerousProcessor fp = new FakeProcessor(3000);
	private int sum;
	
	public TaskLimitedBuffer(BlockingDeque<Node> buffer, CyclicBarrier barrier, ConcurrentLinkedQueue<BlockingDeque<Node>> poolBuffers) {
		this.buffer = buffer;
		this.barrier = barrier;
		this.poolBuffers = poolBuffers;
		this.sum = 0;
	}
	
	@Override
	public Integer call() throws Exception {
		boolean k = true;
		while(k){ //return true if all is empty
			if(this.buffer.isEmpty()){ //vai a prendere i nodi da altri buffer
				Node node = stealNode(this.poolBuffers);
				if (node != null){
					//System.out.println("th "+ Thread.currentThread().getId());
					this.buffer.putFirst(node);
				}
				else {
					//System.out.println("th "+ Thread.currentThread().getId());
					k = false;	//tutti i buffer sono vuoti -> ritorno "sum"
				}
			}
			if (k){
				this.sum += calculateValue(this.buffer.pollFirst());
			}
		}
		try {
			//System.out.println("await"+ Thread.currentThread().getId() +" "+ this.sum);
			this.barrier.await(); 
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (BrokenBarrierException e) {
			//System.out.println("Rottura Barrira EXECPTION "+ Thread.currentThread().getId());
			call();
		}
		return this.sum;
	}

	private Node stealNode(ConcurrentLinkedQueue<BlockingDeque<Node>> poolBuffers) {
		for (BlockingDeque<Node> blockingDeque : poolBuffers) {
			if(!blockingDeque.isEmpty()){
				Node node =  blockingDeque.pollLast();
				//System.out.println("RUBO un nodo ad altezza "+ BinaryTree.getDepth(node)+ " Nodi "+BinaryTree.countNodes(node) + " thread "+Thread.currentThread().getId());
				return node;
			}
		}
		//System.out.println("NON RUBO "+" th "+Thread.currentThread().getId());
		return null;
	}

	private int calculateValue(Node node) {
		int sumPartial = 0;
		while(node != null){
			if(node.getSx() != null){
					this.buffer.addFirst(node.getSx());
			}
			if(node.getDx() != null){
				this.buffer.addFirst(node.getDx());
			}
			if(this.barrier.getNumberWaiting() > 0){	
				this.barrier.reset();
			}
			sumPartial += fp.onerousFunction(node.getValue());
			node = this.buffer.pollFirst();
		}
		return sumPartial;
	}
}
