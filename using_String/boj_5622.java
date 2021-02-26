import java.util.Scanner;
import java.io.*;
import java.util.StringTokenizer;
import java.util.HashMap;

class boj_5622{
	public static void main(String[] args){
		Scanner scan = new Scanner(System.in);

		

		int[] time = {0,2,3,4,5,6,7,8,9,10};
		HashMap<String, Integer> map = new HashMap<>();
		map.put("A",2);
		map.put("B",2);
		map.put("C",2);

		map.put("D",3);
		map.put("E",3);
		map.put("F",3);

		map.put("G",4);
		map.put("H",4);
		map.put("I",4);

		map.put("J",5);
		map.put("K",5);
		map.put("L",5);

		map.put("M",6);
		map.put("N",6);
		map.put("O",6);

		map.put("P",7);
		map.put("Q",7);
		map.put("R",7);
		map.put("S",7);

		map.put("T",8);
		map.put("U",8);
		map.put("V",8);

		map.put("W",9);
		map.put("X",9);
		map.put("Y",9);
		map.put("Z",9);

		String[] s = scan.nextLine().split("");

		int TIME = 0;
		for (int i=0; i<s.length; i++) {
			String cur = s[i];
			//System.out.println(cur);
			TIME += time[map.get(cur)];
		}

		System.out.println(TIME);
	}// end of main method
}// end of class