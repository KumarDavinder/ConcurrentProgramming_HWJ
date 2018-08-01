package it.uniroma3.hwj.suppot;

import it.uniroma3.hwj.base.BinaryTreeAdder;
import it.uniroma3.hwj.base.FakeProcessor;
import it.uniroma3.hwj.base.Node;
import it.uniroma3.hwj.base.OnerousProcessor;

public class BTA_serial implements BinaryTreeAdder{
	private final OnerousProcessor fp = new FakeProcessor(3000);

	@Override
	public int computeOnerousSum(Node root) {
		int sum;
		if(root == null)
			sum = 0;
		else{
			sum = fp.onerousFunction(root.getValue());
			if(root.getDx() != null)
				sum += computeOnerousSum(root.getDx());

			if(root.getSx() != null)
				sum += computeOnerousSum(root.getSx());
		}
		return sum;
	}
}
