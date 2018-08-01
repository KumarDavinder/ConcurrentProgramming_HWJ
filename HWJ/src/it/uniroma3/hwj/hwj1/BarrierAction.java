package it.uniroma3.hwj.hwj1;

import it.uniroma3.hwj.base.Node;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;

public class BarrierAction implements Runnable{
	private ExecutorService executor;
	private LinkedBlockingDeque<Node> buffer;
	private CyclicBarrier barrier = null;

	public BarrierAction(ExecutorService executor, LinkedBlockingDeque<Node> buffer) {
		this.executor = executor;
		this.buffer = buffer;
	}
	@Override
	public void run() {
		if(this.buffer.isEmpty()){
			//System.out.println("shutdown executor"+ Thread.currentThread().getId());
			this.executor.shutdown();
		}
		else{
			//System.out.println("reset Barrier");
			this.barrier.reset();	//in TaskUnlimitedBuffer viene eseguito catch (BrokenBarrierException) -> uscita dal await()
		}
	}

	public void setBarrier(CyclicBarrier barrier) {
		this.barrier = barrier;
	}
	
	public void setBuffer(LinkedBlockingDeque<Node> buffer) {
		this.buffer = buffer;
	}
	
	public void setExecutor(ExecutorService executor) {
		this.executor = executor;
	}
	
	public CyclicBarrier getBarrier() {
		return barrier;
	}
	
	public LinkedBlockingDeque<Node> getBuffer() {
		return buffer;
	}
	
	public ExecutorService getExecutor() {
		return executor;
	}
	
}
