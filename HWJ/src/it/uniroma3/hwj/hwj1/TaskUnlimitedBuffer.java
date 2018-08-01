package it.uniroma3.hwj.hwj1;

import it.uniroma3.hwj.base.FakeProcessor;
import it.uniroma3.hwj.base.Node;
import it.uniroma3.hwj.base.OnerousProcessor;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.Callable;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.LinkedBlockingDeque;

public class TaskUnlimitedBuffer implements Callable<Integer> {
	private LinkedBlockingDeque<Node> buffer;
	private CyclicBarrier barrier;
	private final OnerousProcessor fp = new FakeProcessor(3000);
	private int sum;

	public TaskUnlimitedBuffer(LinkedBlockingDeque<Node> buffer ,CyclicBarrier barrier) {
		this.barrier = barrier;
		this.buffer = buffer;
		this.sum = 0;
	}

	@Override
	public Integer call() throws Exception {		
		Node poll = this.buffer.pollFirst();
		while(poll != null){
			if(poll.getSx() != null)
				this.buffer.addLast(poll.getSx());
			if(poll.getDx() != null)
				this.buffer.addLast(poll.getDx());
			if(this.barrier.getNumberWaiting() > 0){	
				//System.out.println("reset await"+ Thread.currentThread().getId());
				this.barrier.reset();
			}
			this.sum += fp.onerousFunction(poll.getValue());
			poll = this.buffer.pollFirst();

		}
		try {
			//System.out.println("await"+ Thread.currentThread().getId() +" "+ this.sum);
			this.barrier.await(); 
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (BrokenBarrierException e) {
			//System.out.println("Rottura Barrira EXECPTION ");
			call();
		}
		return this.sum;
	}
}
