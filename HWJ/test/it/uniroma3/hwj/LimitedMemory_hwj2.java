package it.uniroma3.hwj;

import static org.junit.Assert.assertTrue;
import it.uniroma3.hwj.base.BinaryNode;
import it.uniroma3.hwj.base.Node;
import it.uniroma3.hwj.hwj2.BTA_limitedMemory;
import it.uniroma3.hwj.suppot.BTA_serial;
import it.uniroma3.hwj.suppot.BinaryTree;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class LimitedMemory_hwj2 {
	private Node root;
	private BinaryNode node;
	private int warm_up = 3;
	private int sumSerial;
	private int sumParallel;
	private Long serialTime;
	private Long parallelTime;
	private BTA_limitedMemory bta_limitedMemory;

	
	@Before
	public void setUp(){
		this.root = BinaryTree.genereateTree(10);
		this.node = new BinaryNode(null, null, 2);
		this.bta_limitedMemory = new BTA_limitedMemory(1);
	}
	
	@Ignore
	public void warm_up_serial(){
		BTA_serial bta_serial = new BTA_serial();
		for(int i = 0; i < warm_up; i++){
			bta_serial.computeOnerousSum(this.root);
		}
		Long startSerialTime = System.currentTimeMillis();
		this.sumSerial = bta_serial.computeOnerousSum(this.root);
		this.serialTime = System.currentTimeMillis() - startSerialTime;
	}
	
	@Ignore
	public void warm_up_limitedMemory(){
		this.bta_limitedMemory.setDepthTree(BinaryTree.getDepth(this.root));
		for(int i = 0; i < warm_up; i++){
			this.bta_limitedMemory.computeOnerousSum(this.root);
		}
		Long startParallelTime = System.currentTimeMillis();
		this.sumParallel = this.bta_limitedMemory.computeOnerousSum(this.root);
		this.parallelTime = System.currentTimeMillis() - startParallelTime;
	}
	
	@Test
	public void onlyOneNode(){
		this.bta_limitedMemory.setDepthTree(0);
		assertTrue(this.bta_limitedMemory.computeOnerousSum(this.node) == 2);
	}
	
	@Test
	public void onlyRootAndLeftNode(){
		this.node.setSx(new BinaryNode(null, null, 3));
		assertTrue(this.bta_limitedMemory.computeOnerousSum(this.node) == 5);
	}
	
	@Test
	public void onlyRootAndRightNode(){
		this.node.setDx(new BinaryNode(null, null, 31));
		assertTrue(this.bta_limitedMemory.computeOnerousSum(this.node) == 33);
	}
	
	@Test
	public void balancedTreeDepth_1(){
		this.node.setSx(new BinaryNode(null, null, 31));
		this.node.setDx(new BinaryNode(null, null, 1));
		assertTrue(this.bta_limitedMemory.computeOnerousSum(this.node) == 34);
	}
	
	@Test
	public void unbalancedTreeDepth_2Sx(){
		BinaryNode s = new BinaryNode(null, null, 31);
		this.node.setSx(s);
		this.node.setDx(new BinaryNode(null, null, 1));
		s.setSx(new BinaryNode(null, null, 1));
		assertTrue(this.bta_limitedMemory.computeOnerousSum(this.node) == 35);
	}
	
	@Test
	public void unbalancedTreeDepth_2Dx(){
		BinaryNode d = new BinaryNode(null, null, 31);
		this.node.setDx(d);
		this.node.setSx(new BinaryNode(null, null, 1));
		d.setDx(new BinaryNode(null, null, 1));
		assertTrue(this.bta_limitedMemory.computeOnerousSum(this.node) == 35);
	}
	
	@Test
	public void sumSerialParallel(){
		assertTrue(this.sumSerial == this.sumParallel);
	}
	
	@Test
	public void serialParallelTime(){
		warm_up_serial();
		warm_up_limitedMemory();
		assertTrue(this.serialTime > this.parallelTime);
	}
	
	@Test
	public void speedUp(){
//		warm_up_serial();
//		warm_up_limitedMemory();
//		double speedUp = (double)serialTime/(double)parallelTime;
//		System.out.println("Serial time = "+this.serialTime+" Limited Memory time = "+this.parallelTime);
//		System.out.println("Limited Memory: SpeedUp = "+speedUp);
	}
}
