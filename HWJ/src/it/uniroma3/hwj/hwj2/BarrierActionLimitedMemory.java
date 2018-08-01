package it.uniroma3.hwj.hwj2;

import it.uniroma3.hwj.base.Node;

import java.util.Iterator;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;

public class BarrierActionLimitedMemory implements Runnable{
	private ExecutorService executor;
	private ConcurrentLinkedQueue<BlockingDeque<Node>> poolBuffers;
	private CyclicBarrier barrier = null;
	
	public BarrierActionLimitedMemory(ExecutorService executor, ConcurrentLinkedQueue<BlockingDeque<Node>> pollBuffers) {
		this.executor = executor;
		this.poolBuffers = pollBuffers;
	}

	@Override
	public void run() {
		boolean check = true;
		Iterator <BlockingDeque<Node>> it = this.poolBuffers.iterator();
		while(it.hasNext() && check){
			if(!it.next().isEmpty()){	//non è vuoto
				check = false;
			}
		}
		if (check){
			this.executor.shutdown();	//tutti empty
		}
		else {
			this.barrier.reset();	//almeno uno non empty
		}
	}

	public CyclicBarrier getBarrier() {
		return barrier;
	}

	public void setBarrier(CyclicBarrier barrier) {
		this.barrier = barrier;
	}

	public ExecutorService getExecutor() {
		return executor;
	}

	public void setExecutor(ExecutorService executor) {
		this.executor = executor;
	}
	
	public ConcurrentLinkedQueue<BlockingDeque<Node>> getPollBuffers() {
		return poolBuffers;
	}
	
	public void setPollBuffers(ConcurrentLinkedQueue<BlockingDeque<Node>> pollBuffers) {
		this.poolBuffers = pollBuffers;
	}

}
