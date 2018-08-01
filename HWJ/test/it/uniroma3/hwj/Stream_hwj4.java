package it.uniroma3.hwj;

import static org.junit.Assert.assertTrue;
import it.uniroma3.hwj.base.BinaryNode;
import it.uniroma3.hwj.base.Node;
import it.uniroma3.hwj.hwj4.BTA_stream;
import it.uniroma3.hwj.suppot.BTA_serial;
import it.uniroma3.hwj.suppot.BinaryTree;

import org.junit.Before;
import org.junit.Test;

public class Stream_hwj4 {
	private Node root;
	private BinaryNode node;
	private BTA_stream bta_stream;

	@Before
	public void setUp(){
		this.root = BinaryTree.genereateTree(10);
		this.node = new BinaryNode(null, null, 2);
		this.bta_stream = new BTA_stream();
	}

	@Test
	public void onlyOneNode(){
		assertTrue(this.bta_stream.computeOnerousSum(this.node) == 2);
	}
	
	@Test
	public void onlyRootAndLeftNode(){
		this.node.setSx(new BinaryNode(null, null, 3));
		assertTrue(this.bta_stream.computeOnerousSum(this.node) == 5);
	}
	
	@Test
	public void onlyRootAndRightNode(){
		this.node.setDx(new BinaryNode(null, null, 31));
		assertTrue(this.bta_stream.computeOnerousSum(this.node) == 33);
	}
	
	@Test
	public void balancedTreeDepth_1(){
		this.node.setSx(new BinaryNode(null, null, 31));
		this.node.setDx(new BinaryNode(null, null, 1));
		assertTrue(this.bta_stream.computeOnerousSum(this.node) == 34);
	}
	
	@Test
	public void unbalancedTreeDepth_2Sx(){
		BinaryNode s = new BinaryNode(null, null, 31);
		this.node.setSx(s);
		this.node.setDx(new BinaryNode(null, null, 1));
		s.setSx(new BinaryNode(null, null, 1));
		assertTrue(this.bta_stream.computeOnerousSum(this.node) == 35);
	}
	
	@Test
	public void unbalancedTreeDepth_2Dx(){
		BinaryNode d = new BinaryNode(null, null, 31);
		this.node.setDx(d);
		this.node.setSx(new BinaryNode(null, null, 1));
		d.setDx(new BinaryNode(null, null, 1));
		assertTrue(this.bta_stream.computeOnerousSum(this.node) == 35);
	}
	
	@Test
	public void sumSerialParallel(){
		BTA_serial bta_serial = new BTA_serial();
		assertTrue(bta_serial.computeOnerousSum(root) == this.bta_stream.computeOnerousSum(root));
	}
}
