import java.util.ArrayList;
import java.util.List;

public class Node {
	
	List<Double> keys;
	boolean isLeaf;
	
	public Node(){
		keys = new ArrayList<>();
	}
	
	// Dummy methods used for runtime polymorphism
	// Used for LeafNode
	public void insert(double key, String value){
		
	}
	
	//Used for Internal Node
	public void insert(double key, Node leftPartition, Node rightPartition){
		
	}
	
	public Node[] partition(){
		
		return new Node[2];
	}
}
