public class Main_Activity {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BPlus_Tree bT = new BPlus_Tree(3);
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

		bT.insert(17,"17A");
		bT.insert(27, "27A");
		bT.insert(60, "60A");
		
		bT.insert(12, "12A");
		bT.insert(15, "15A");
//        bT.search(17);
        bT.insert(17,"17B");
        bT.search(17);
        
//        bT.printTree(bT.root);
        
        bT.search(2, 5);
        
		System.out.println("");
	}

}
