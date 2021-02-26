import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.io.*;
import java.util.StringTokenizer;
// library which is in java.lang work without importing!


class boj_1525_re{
	static String ANSWERKEY = "123456789";

	static int[] dr = {-1,1,0,0};
	static int[] dc = {0,0,-1,1};

	static Queue<String> sq;
	static HashMap<String, Integer> map;

	public static void main(String[] args) throws IOException{
		BufferedReader BR = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb  = new StringBuilder();

		for (int r=0; r<3; r++) {
			st = new StringTokenizer(BR.readLine());
			for (int c=0; c<3; c++) {
				String next = st.nextToken();
				String appending = next.equals("0")? "9" : next;
				sb.append(appending);
			}
		}// end of getting first String


		String start = sb.toString();
		String swapped = swap(start, 3, 8);

		
		BFS(start);

		int ANSWER = map.containsKey(ANSWERKEY)? map.get(ANSWERKEY) : -1;

		System.out.println(ANSWER);


	}// end of main method

	public static void BFS(String start){
		sq = new LinkedList<>();
		map = new HashMap<>();

		sq.add(start);
		map.put(start,0);

		while(!sq.isEmpty() && !map.containsKey(ANSWERKEY)){
			String cur = sq.remove();
			int br = getR(cur);
			int bc = getC(cur);
			int d = map.get(cur);

			for (int i=0; i<4; i++) {
				int nbr = br + dr[i];
				int nbc = bc + dc[i];

				if(nbr < 0 || nbr >= 3 || nbc < 0 || nbc >= 3) continue;
				//System.out.println(br+" "+bc+" "+nbr+" "+nbc+" "+getIndex(nbr,nbc));
				String next = swap(cur, getIndex(br,bc), getIndex(nbr,nbc));
				if(!map.containsKey(next)){
					sq.add(next);
					map.put(next, d+1);
				}
			}



		}


	}// end of BFS method

	public static String swap(String str, int index1, int index2){
		StringBuilder sbb = new StringBuilder();

		for (int i=0; i<9; i++) {
			if(i == index1){
				sbb.append(str.charAt(index2));
			}
			else if(i == index2){
				sbb.append(str.charAt(index1));
			} else{
				sbb.append(str.charAt(i));
			}
		}

		return sbb.toString();
	}// end of swap method

	public static int getR(String str){
		int index = -1;

		for (int i=0; i<9; i++) {
			if(str.charAt(i) == '9'){
				index = i;
				break;
			}
		}

		int r = index / 3;

		return r;
	}// end of getR method

	public static int getC(String str){
		int index = -1;

		for (int i=0; i<9; i++) {
			if(str.charAt(i) == '9'){
				index = i;
				break;
			}
		}

		
		int c = index % 3;

		return c;
	}// end of getC method

	public static int getIndex(int r, int c){
		return r*3 + c;
	}


}// end of class 