import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class Main_Activity {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		FileInputStream inputStream;
		Scanner sc;
		boolean isOrder = true;
		BPlus_Tree bPlusTree = null;
		try{
			inputStream= new FileInputStream("C:/Gainesville/Fall 2017/ADS/Project/src/input.txt");
			sc = new Scanner(inputStream);
			while(sc.hasNextLine()){
				String line = sc.nextLine();
				if(isOrder){
					isOrder = false;
					int order = Integer.parseInt(line);
					bPlusTree = new BPlus_Tree(order);
				}
				else{
					String[] cmdParts = line.split("\\(");
					String cmd = cmdParts[0].toLowerCase();
					String[] params = cmdParts[1].split(",");
					//System.out.println(params.length);
					
					switch(cmd){
					case "insert":
						
						bPlusTree.insert(Double.parseDouble(params[0]), params[1].substring(0, params[1].length()-1));
						break;
						
					case "search":
						
						if(params.length == 1){
							bPlusTree.search(Double.parseDouble(params[0].substring(0, params[0].length()-1)));
						}
						else{
							bPlusTree.search(Double.parseDouble(params[0]), Double.parseDouble(params[1].substring(0,params[1].length()-1)));
						}
						break;
					}
				}
			}
		}
		catch(IOException e){
			e.printStackTrace();
		}
		//bPlusTree.printTree(bPlusTree.root);
		bPlusTree.search(100, 400);
//		BPlus_Tree bT = new BPlus_Tree(3);
//		bT.insert(5,"5A");
//		bT.insert(3,"3A");
//		bT.insert(8,"8A");
//		bT.insert(5, "5B");
//		bT.insert(2,"2A");
//		bT.insert(3,"3B");
//		bT.insert(4,"4A");
//		bT.insert(1,"1A");
//		bT.insert(0,"0A");
//		bT.insert(-1,"-1A");
//		bT.insert(6,"6A");
//		bT.insert(7,"7A");
//		bT.insert(7, "7B");
//		bT.insert(6, "6B");
//
//		bT.insert(17,"17A");
//		bT.insert(27, "27A");
//		bT.insert(60, "60A");
//		
//		bT.insert(12, "12A");
//		bT.insert(15, "15A");
//        bT.search(17);
//        bT.insert(17,"17B");
//        bT.search(60);
//        //bT.printTree(bT.root);
//        bT.search(2, 100);
//        
//		System.out.println("");
	}

}
