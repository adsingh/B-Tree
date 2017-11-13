import java.util.ArrayList;
import java.util.List;

public class Node {
	
	List<Double> keys;
	boolean isLeaf;
	
	public Node(){
		keys = new ArrayList<>();
	}
	
	// Dummy methods used for runtime polymorphism
	public void insert(double key, String value){
		
	}
	
	public void insert(double key, Node left, Node right){
		
	}
	
	public Node[] partition(){
		
		return new Node[2];
	}
}
