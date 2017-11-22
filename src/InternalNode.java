import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InternalNode extends Node{
	
	private List<Node> children;
	
	public InternalNode(){
		children = new ArrayList<Node>();
		isLeaf = false;
	}
	
	public List<Node> getChildren(){
		return children;
	}
	
	@Override
	public Node[] partition(){
		
		InternalNode[] partitions = new InternalNode[2];
		
		// Creating two new partitions from the original partition
		partitions[0] = new InternalNode();
		partitions[1] = new InternalNode();
		
		int length = this.keys.size();
		
		// Copying the keys, pointers to children for the first partition
		partitions[0].keys = new ArrayList<>(this.keys.subList(0, length/2));
		partitions[0].children = new ArrayList<>(this.children.subList(0, length/2 + 1));
		
		// Copying the keys, pointers to children for the second partition
		partitions[1].keys = new ArrayList<>(this.keys.subList(length/2 + 1, length));
		partitions[1].children = new ArrayList<>(this.children.subList(length/2 + 1, length + 1));
		
		return partitions;
	}
	
	@Override
	public void insert(double key, Node leftPartition, Node rightPartition){
		
		// Find the possible position of the key to be inserted in the node 
		int index = -Collections.binarySearch(keys, key) - 1;
		
		// Insert the key
		keys.add(index, key);
		
		//Removing pointer to the previous child
		if(children.size() > 0) children.remove(index);
		
		// Adding pointers to the new partitions of the child node
		children.add(index, rightPartition);
		children.add(index, leftPartition);
		
	}
	
}