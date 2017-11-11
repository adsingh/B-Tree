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
	
	public Node[] partition(){
		
		LeafNode[] partitions = new LeafNode[2];
		
		partitions[0] = new LeafNode();
		partitions[1] = new LeafNode();
		
		partitions[0].keys = new ArrayList<>(this.keys.subList(0, this.keys.size() / 2));
		partitions[0].valueList = new ArrayList<>(this.valueList.subList(0, this.keys.size() / 2));
		partitions[0].next = partitions[1];
		
		partitions[1].keys = new ArrayList<>(this.keys.subList(this.keys.size() / 2, this.keys.size()));
		partitions[1].valueList = new ArrayList<>(this.valueList.subList(this.keys.size() / 2, this.keys.size()));
		partitions[1].prev = partitions[0];
		
		return partitions;
	}
	
}
