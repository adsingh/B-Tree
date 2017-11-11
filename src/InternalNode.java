import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InternalNode extends Node{
	
	List<Node> children;
	
	public InternalNode(){
		children = new ArrayList<Node>();
		isLeaf = false;
	}
	
	public List<Node> getChildren(){
		return children;
	}
	
	public void insert(double key, Node left, Node right){
		
		int index = -Collections.binarySearch(keys, key) - 1;
		keys.add(index, key);
		if(children.size() > 0) children.remove(index);
		children.add(index, right);
		children.add(index, left);
		
	}
	
	public Node[] partition(){
		
		InternalNode[] partitions = new InternalNode[2];
		partitions[0] = new InternalNode();
		partitions[1] = new InternalNode();
		
		int length = this.keys.size();
		
		partitions[0].keys = new ArrayList<>(this.keys.subList(0, length/2));
		partitions[0].children = new ArrayList<>(this.children.subList(0, length/2 + 1));
		
		partitions[1].keys = new ArrayList<>(this.keys.subList(length/2 + 1, length));
		partitions[1].children = new ArrayList<>(this.children.subList(length/2 + 1, length + 1));
		
		return partitions;
	}

}
