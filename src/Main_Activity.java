public class Main_Activity {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BTree bT = new BTree(4);
		bT.insert(5,"5A");
		bT.insert(3,"3A");
		bT.insert(8,"8A");
		bT.insert(5, "5B");
		bT.insert(2,"2A");
		bT.insert(3,"3B");
		bT.insert(4,"4A");
		bT.insert(1,"1A");
		bT.insert(0,"0A");
		bT.insert(-1,"-1A");
		bT.insert(6,"6A");
		bT.insert(7,"7A");
		bT.insert(7, "7B");
		bT.insert(6, "6B");
//		
//        bT.search(10);
		
		bT.printTree(bT.root);
	}

}
