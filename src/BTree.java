import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

public class BTree {

	public Node root;
	static Integer order;

	public BTree(int order) {
		root = null;
		BTree.order = order;
	}

	public void insert(double key, String value) {
		
		
		// Case 1. Root is null
		
		if (root == null) {
			root = new LeafNode();
			root.insert(key, value);
		} 
		
		// Case 2. Root is not null
		else {

			// Get path from root to the LeafNode that either contains the key or it will be added now
			// Top of this stack will always be a LeafNode
			Stack<Node> searchPath = searchKey(key);

			// Case 2a. Adding the key to the LeafNode might cause imbalance 
			if (searchPath.peek().keys.size() + 1 == BTree.order) {
				
				int index = Collections.binarySearch(searchPath.peek().keys, key);
				
				// Case 2a-i. The key already exists and hence no imbalance created, simply insert the value
				if (index >= 0) {
					searchPath.peek().insert(key, value);
				} 
				
				// Case 2a-ii. The addition of key creates imbalance
				else {

					// First partitioning is always for LeafNode

					LeafNode leafToPartition = (LeafNode) searchPath.pop();
					leafToPartition.insert(key, value);

					Node leftPartition = new LeafNode();
					Node rightPartition = new LeafNode();
					

					leftPartition.isLeaf = true;
					leftPartition.keys = new ArrayList<>(
							leafToPartition.keys.subList(0, leafToPartition.keys.size() / 2));
					((LeafNode)leftPartition).valueList = new ArrayList<>(
							leafToPartition.valueList.subList(0, leafToPartition.keys.size() / 2));
					

					rightPartition.isLeaf = true;
					rightPartition.keys = new ArrayList<>(
							leafToPartition.keys.subList(leafToPartition.keys.size() / 2, leafToPartition.keys.size()));
					((LeafNode)rightPartition).valueList = new ArrayList<>(leafToPartition.valueList
							.subList(leafToPartition.keys.size() / 2, leafToPartition.keys.size()));

					// 1. LeafNode just partitioned is root Node, then simply create a new
					// InternalNode which will be the new root and make the 2 partitions as its children
					if (searchPath.empty()) {
						InternalNode newRoot = new InternalNode();
						newRoot.insert(rightPartition.keys.get(0) , leftPartition, rightPartition);
						this.root = newRoot;
					}
					
					// 2. LeafNode is Not root, then the new key needs to be
					// inserted with recursive partitioning of the internal nodes up to the root if required
					else {
						
						double keyMovingUp = rightPartition.keys.get(0);
						
						// Balancing the entire tree until the root is reached or the intermediate node becomes balanced
						while (!searchPath.empty() && searchPath.peek().keys.size() + 1 == BTree.order) {
							
							InternalNode parent = (InternalNode)searchPath.pop();
							
							parent.insert(keyMovingUp , leftPartition, rightPartition);
							
							int length = parent.keys.size();
							leftPartition = new InternalNode();
							rightPartition = new InternalNode();
							
							leftPartition.keys = new ArrayList<>(parent.keys.subList(0, length/2));
							((InternalNode)leftPartition).children = new ArrayList<>(parent.children.subList(0, length/2 + 1));
							
							rightPartition.keys = new ArrayList<>(parent.keys.subList(length/2+1, length));
							((InternalNode)rightPartition).children = new ArrayList<>(parent.children.subList(length/2 + 1, length + 1));
							
							keyMovingUp = parent.keys.get(length/2);
							
						}

						// Here the root has been reached and it is unbalanced
						if (searchPath.empty()) {
							
							InternalNode newRoot = new InternalNode();
							newRoot.insert(keyMovingUp, leftPartition, rightPartition);
							this.root = newRoot;

						} 
						// An intermediate node in the path towards root can fit the key
						else {
							InternalNode node = (InternalNode) searchPath.peek();
							node.insert(keyMovingUp, leftPartition, rightPartition);
						}
					}

				}

			} 
			
			// Case 2b. Adding the key value pair to the LeafNode without any imbalance caused
			else {
				searchPath.peek().insert(key, value);
			}
		}

	}

	// Search function for single key
	public void search(double key) {

		Node possibleNode = searchKey(key).peek();
		int index = Collections.binarySearch(possibleNode.keys, key);
		if (index >= 0)
			System.out.println(((LeafNode) possibleNode).valueList.get(index));

	}

	private Stack<Node> searchKey(double key) {

		Stack<Node> path = new Stack<>();
		helper(root, key, path);
		return path;

	}

	private void helper(Node root, double key, Stack<Node> path) {

		if (root.isLeaf) {
			path.push(root);
			return;
		} else {
			path.push(root);
			int index = Collections.binarySearch(root.keys, key);
			index = index < 0 ? -index - 1 : index+1;
			Node next = ((InternalNode) root).getChildren().get(index);
			helper(next, key, path);
		}
	}

	public void printTree(Node root) {

		if (root == null)
			return;

		if (root.isLeaf) {
			for (int i = 0; i < root.keys.size(); i++) {
				System.out.print(root.keys.get(i));
				System.out.print("---");
				System.out.print(((LeafNode) root).valueList.get(i) + " ; ");
			}
			System.out.print(" \n");
			return;
			
		}
		
		System.out.println("\nInternal node:" + root.keys);
		for (Node node : ((InternalNode) root).getChildren()) {
			printTree(node);
		}

	}
}
