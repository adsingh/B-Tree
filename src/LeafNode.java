import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LeafNode extends Node{

	public List<List<String>> valueList;
	Node prev, next;
	
	public LeafNode(){
		valueList = new ArrayList<>();
		isLeaf = true;
	}
	
	@Override
	public void insert(double key, String value){
		
		int index = Collections.binarySearch(keys, key);
		if(index < 0){
			int actualIndex = -index - 1;
			keys.add(actualIndex, key);
			
			List<String> values = new ArrayList<>();
			values.add(value);
			valueList.add(actualIndex, values);
		}
		else{
			valueList.get(index).add(value);
		}
		
	}
	
	public void setNext(Node next){
		this.next = next;
	}
	
	public Node getNext(){
		return next;
	}
	
	public void setPrev(Node prev){
		this.prev = prev;
	}
	
	public Node getPrev(){
		return prev;
	}
}
