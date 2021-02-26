import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.HashMap;
import java.util.PriorityQueue;


class boj_1157{

	public static void main(String[] args){
		Scanner scan = new Scanner(System.in);
		HashMap<Character, Integer> map = new HashMap<>();
		HashMap<Integer, Character> map2 = new HashMap<>();
		
		int[] count = new int[26];

		int code = 'A';
		for (int i=0; i<26; i++) {

			// System.out.println((char)code+ " "+ i);
			map.put((char)code,i);
			map2.put(i, (char)code);
			code += 1;
		}
		
		// toUpperCase, toLowerCase applied in total String
		String str = scan.nextLine().toUpperCase();

		for (int i=0; i<str.length(); i++) {
			char cc = str.charAt(i);

			int index = map.get(cc);

			count[index]++;
		}// end of for loop

		// for (int i=0; i<count.length; i++) {
		// 	System.out.print(count[i] +" ");
		// }
		// System.out.println();

		PriorityQueue<Code> pq = new PriorityQueue<>();
		for (int i=0; i<count.length; i++) {
			pq.add(new Code(map2.get(i), count[i]));
		}

		Code first = pq.remove();
		Code second = pq.remove();

		// System.out.println(first.count);
		// System.out.println(second.count);
		if(first.count == second.count) System.out.println("?");
		else System.out.println(first.code);



		
	}// end of main method
}// end of class

class Code implements Comparable<Code>{
	int count;
	char code;

	public Code(char code, int count){
		this.count = count;
		this.code = code;
		return;
	}

	@Override
	public int compareTo(Code code2){
		return Integer.compare(this.count, code2.count) * (-1);
	}
}
