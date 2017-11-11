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
		
//		System.out.println("insert of internal called");
		int index = -Collections.binarySearch(keys, key) - 1;
		
		keys.add(index, key);
		if(children.size() > 0) children.remove(index);
		children.add(index, right);
		children.add(index, left);
		
		
	}

}
