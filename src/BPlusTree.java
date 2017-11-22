import java.util.Collections;
import java.util.Stack;

public class BPlusTree {

	public Node root;
	static Integer order;

	public BPlusTree(int order) {
		root = null;
		BPlusTree.order = order;
	}

	public void insert(double key, String value) {

		// Case 1. Root is null

		if (root == null) {
			root = new LeafNode();
			root.insert(key, value);
		}

		// Case 2. Root is not null
		else {

			// Get path from root to the LeafNode that either contains the key
			// or should be added to
			// Top of this stack will always be a LeafNode
			Stack<Node> searchPathStack = getPathToKey(key);

			// Case 2a. Adding the key to the LeafNode might cause imbalance
			if (searchPathStack.peek().keys.size() + 1 == BPlusTree.order) {

				int index = Collections.binarySearch(searchPathStack.peek().keys, key);

				// Case 2a-i. The key already exists and hence no imbalance
				// created, simply insert the value
				if (index >= 0) {
					searchPathStack.peek().insert(key, value);
				}

				// Case 2a-ii. The addition of key creates imbalance
				else {

					Node[] partitions = null;

					// LeafNode is Not root, then the new key needs to be
					// inserted with recursive partitioning of the internal
					// nodes up to the root if required

					double keyMovingUp = 0;

					// Balancing the entire tree until the root is reached or
					// the intermediate node becomes balanced
					while (!searchPathStack.empty() && searchPathStack.peek().keys.size() + 1 == BPlusTree.order) {

						Node parent = searchPathStack.pop();
						if (parent.isLeaf) {
							parent.insert(key, value);
						} else {
							parent.insert(keyMovingUp, partitions[0], partitions[1]);
						}

						partitions = parent.partition();
						keyMovingUp = parent.isLeaf ? partitions[1].keys.get(0)
								: parent.keys.get(parent.keys.size() / 2);

					}

					// Here the root has been reached and it is unbalanced
					if (searchPathStack.empty()) {

						InternalNode newRoot = new InternalNode();
						newRoot.insert(keyMovingUp, partitions[0], partitions[1]);
						this.root = newRoot;

					}
					// An intermediate node in the path towards root can fit the key
					else {
						InternalNode node = (InternalNode) searchPathStack.peek();
						node.insert(keyMovingUp, partitions[0], partitions[1]);
					}
					// }

				}

			}

			// Case 2b. Adding the key value pair to the LeafNode without causing imbalance
			else {
				searchPathStack.peek().insert(key, value);
			}
		}

	}

	// Search method for single key
	public void search(double key) {

		if(root == null){
			System.out.println("Null");
			return;
		}
		
		// searchKey method returns path to the LeafNode that might contain the key
		// in the form of a stack with the LeafNode on the top
		Node possibleNode = getPathToKey(key).peek();
		
		int index = Collections.binarySearch(possibleNode.keys, key);
		// if the key is present the index will be positive or 0
		if (index >= 0){
			StringBuilder sb = new StringBuilder();
			for(String value: ((LeafNode) possibleNode).valueList.get(index)){
				sb.append(value).append(",");
			}
			System.out.println(sb.substring(0,sb.length()-1)); 
		}
		else{
			System.out.println("Null");
		}

	}

	// Search method for a range of keys
	public void search(double key1, double key2){
		
		if(root == null){
			System.out.println("Null");
			return;
		}
		
		// Find key1 or key which is just greater than key1 if key1 is not present
		LeafNode possibleNode = (LeafNode)getPathToKey(key1).peek();
		int index = Collections.binarySearch(possibleNode.keys, key1);
		index = index < 0 ? -index - 1 : index;
		
		// The LeafNode the key1 could be in does not contain the key1 and 
		// all the keys in that node are smaller than key1, so move to next LeafNode 
		// using doubly linked list structure
		if(index == possibleNode.keys.size()){
			possibleNode = possibleNode.getNext();
			index = 0;
		}
		
		double iter = key1;
		StringBuilder sb = new StringBuilder();
		
		// Loop until key2 is reached
		while(iter <= key2 && possibleNode != null){
			
			iter = possibleNode.keys.get(index);
			while(iter <= key2){
				for(String value: possibleNode.valueList.get(index)){
					sb.append("(" + iter + ", " + value + "),");
				}
				index++;
				if(index == possibleNode.keys.size()) break;
				iter = possibleNode.keys.get(index);
			}
			
			// Moving to the next leaf node, with the help of the doubly linked list structure
			possibleNode = possibleNode.getNext();
			index = 0;
		}
		// if there is no key-value pair to display, print NULL and return
		if(sb.length() == 0){
			System.out.println("Null");
			return;
		}
		
		// Print the key-value pairs
		System.out.println(sb.substring(0, sb.length()-1));
	}
	
	// Method to locate the given key and also create a path from root 
	// to the LeafNode that either contains the key or can contain it 
	// Search is performed using PREORDER
	private Stack<Node> getPathToKey(double key) {

		Stack<Node> path = new Stack<>();
		preorder(root, key, path);
		return path;

	}

	private void preorder(Node root, double key, Stack<Node> path) {

		// If root is leaf then the last node is reached,
		// add it to the stack and return
		if (root.isLeaf) {
			path.push(root);
			return;
		} 
		// Else continue moving downwards with based on the value of the key,
		// either move towards leftmost child OR some middle child OR the rightmost child
		else {
			path.push(root);
			int index = Collections.binarySearch(root.keys, key);
			index = index < 0 ? -index - 1 : index + 1;
			Node next = ((InternalNode) root).getChildren().get(index);
			preorder(next, key, path);
		}
	}

	// Extra method to print the entire tree, for debugging and verification purposes
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
