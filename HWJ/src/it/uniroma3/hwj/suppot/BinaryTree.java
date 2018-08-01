 package it.uniroma3.hwj.suppot;

import it.uniroma3.hwj.base.BinaryNode;
import it.uniroma3.hwj.base.Node;

public class BinaryTree {
	private static java.util.Random random = new java.util.Random();

	/*=============================================*/
	public static Node genereateTree(int level) {
		if(level == -1)
			return null;
		int value = random.nextInt(10);
		Node sx = genereateTree(level-1);
		Node dx = genereateTree(level-1);
		return new BinaryNode(sx, dx, value);
	}
	/*=============================================*/
	public static int countNodes(Node root) {
		if (root == null)
			return 0;
		else {
			int sx = countNodes(root.getSx());
			int dx = countNodes(root.getDx());
			return 1 + sx + dx;
		}
	}
	/*=============================================*/

	public static int getDepth(Node node){
		int depth = 0;
		if (node == null)
			return -1;
		else { 
			int depthSx = getDepth(node.getSx());
			int	depthDx = getDepth(node.getDx());
			if (depthSx > depthDx)
				depth = 1 + depthSx;
			else depth = 1 + depthDx;
		}
		return depth;
	}
}