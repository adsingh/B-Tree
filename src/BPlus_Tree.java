import java.util.Collections;
import java.util.Stack;

public class BPlus_Tree {

	public Node root;
	static Integer order;

	public BPlus_Tree(int order) {
		root = null;
		BPlus_Tree.order = order;
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
			// or it will be added now
			// Top of this stack will always be a LeafNode
			Stack<Node> searchPath = searchKey(key);

			// Case 2a. Adding the key to the LeafNode might cause imbalance
			if (searchPath.peek().keys.size() + 1 == BPlus_Tree.order) {

				int index = Collections.binarySearch(searchPath.peek().keys, key);

				// Case 2a-i. The key already exists and hence no imbalance
				// created, simply insert the value
				if (index >= 0) {
					searchPath.peek().insert(key, value);
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
					while (!searchPath.empty() && searchPath.peek().keys.size() + 1 == BPlus_Tree.order) {

						Node parent = searchPath.pop();
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
					if (searchPath.empty()) {

						InternalNode newRoot = new InternalNode();
						newRoot.insert(keyMovingUp, partitions[0], partitions[1]);
						this.root = newRoot;

					}
					// An intermediate node in the path towards root can fit the key
					else {
						InternalNode node = (InternalNode) searchPath.peek();
						node.insert(keyMovingUp, partitions[0], partitions[1]);
					}
					// }

				}

			}

			// Case 2b. Adding the key value pair to the LeafNode without any
			// imbalance caused
			else {
				searchPath.peek().insert(key, value);
			}
		}

	}

	// Search function for single key
	public void search(double key) {

		if(root == null){
			System.out.println("Null");
			return;
		}
		Node possibleNode = searchKey(key).peek();
		int index = Collections.binarySearch(possibleNode.keys, key);
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

	public void search(double key1, double key2){
		
		if(root == null){
			System.out.println("Null");
			return;
		}
		
		// Find key1 or key which is just greater than key1 if key1 is not present
		LeafNode possibleNode = (LeafNode)searchKey(key1).peek();
		int index = Collections.binarySearch(possibleNode.keys, key1);
		index = index < 0 ? -index - 1 : index;
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
			possibleNode = possibleNode.getNext();
			index = 0;
		}
		if(sb.length() == 0){
			System.out.println("Null");
			return;
		}
		System.out.println(sb.substring(0, sb.length()-1));
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
			index = index < 0 ? -index - 1 : index + 1;
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
