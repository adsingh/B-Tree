import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LeafNode extends Node{

	public List<List<String>> valueList;
	private Node prev, next;
	
	public LeafNode(){
		valueList = new ArrayList<>();
		isLeaf = true;
	}
	
	// getter method for next pointer
	public LeafNode getNext(){
		return (LeafNode)next;
	}	
	
	@Override
	public Node[] partition(){
		
		LeafNode[] partitions = new LeafNode[2];
		
		// Creating two new partitions from the original partition
		partitions[0] = new LeafNode();
		partitions[1] = new LeafNode();
		
		// Copying the keys, values for the first partition 
		partitions[0].keys = new ArrayList<>(this.keys.subList(0, this.keys.size() / 2));
		partitions[0].valueList = new ArrayList<>(this.valueList.subList(0, this.keys.size() / 2));
		
		// restructuring the doubly linked list
		partitions[0].next = partitions[1];
		partitions[0].prev = this.prev;
		if(this.prev != null)
			((LeafNode)this.prev).next = partitions[0];

		// Copying the keys, values for the second partition
		partitions[1].keys = new ArrayList<>(this.keys.subList(this.keys.size() / 2, this.keys.size()));
		partitions[1].valueList = new ArrayList<>(this.valueList.subList(this.keys.size() / 2, this.keys.size()));

		// restructuring the doubly linked list
		partitions[1].prev = partitions[0];
		partitions[1].next = this.next;
		if(this.next != null)
			((LeafNode)this.next).prev = partitions[1];
		
		return partitions;
	}
	
	@Override
	public void insert(double key, String value){
		
		// Search for the index of the key
		int index = Collections.binarySearch(keys, key);
		// if index is negative that means the key is not present
		if(index < 0){
			int actualIndex = -index - 1;
			keys.add(actualIndex, key);
			
			List<String> values = new ArrayList<>();
			values.add(value);
			valueList.add(actualIndex, values);
		}
		// the key is present already, simply add the value
		else{
			valueList.get(index).add(value);
		}
		
	}
	
}
