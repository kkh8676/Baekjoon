// 1hr 30min failed.........
// Using regular expression.......

import java.util.StringTokenizer;
import java.io.*;
import java.util.Queue;
import java.util.LinkedList;
import java.util.HashSet;
import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

class boj_9328{
	static int T,H,W;
	static String[][] board;
	static int[][] isV;
	static Queue<Pair> starts;
	static Queue<Pair> q;
	static StringBuilder sb;
	static HashSet<String> key;

	static int[] dr = {-1,1,0,0};
	static int[] dc = {0,0,-1,1};
	
	static Queue<Pair> candKey;
	static Queue<Pair> docs;

	static int DOCUMENT;

	static String lower = "[a-z]";
	static String upper = "[A-Z]";
	

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		sb = new StringBuilder();

		T = Integer.parseInt(st.nextToken());

		for (int t=0; t<T; t++) {
			st = new StringTokenizer(br.readLine());
			H = Integer.parseInt(st.nextToken());
			W = Integer.parseInt(st.nextToken());

			board = new String[H][W];
			isV = new int[H][W];
			starts = new LinkedList<>();
			key = new HashSet<>();
			
			candKey = new LinkedList<>();
			docs = new LinkedList<>();

			
			
			

			for (int h=0; h<H; h++) {
				String[] str = br.readLine().split("");
				for (int w=0; w<W; w++) {
					board[h][w] = str[w];
					if(board[h][w].matches(lower)) candKey.add(new Pair(h,w));
					if(board[h][w].equals("$")) docs.add(new Pair(h,w));
					
				}
			}// end of getting information

			String[] gotKeys = br.readLine().split("");
			for (int i=0; i<gotKeys.length; i++) {
				key.add(gotKeys[i]);
			}// end of getting got keys;.....

			q = new LinkedList<>();

			while(true){
				getStarts();

				boolean gotNew = false;
				//System.out.println("Hello!");
				for (Pair keyPair : candKey ) {
					String keyStr = board[ keyPair.r ][ keyPair.c ];
					//System.out.println("Current key is "+keyStr);
					if(key.contains(keyStr)) continue;

					//System.out.println("Came here?");
					if(getNewKey(keyPair)) gotNew = true;
				}
				//System.out.println("Hello");
				if(!gotNew) break;
			} // came here with no more adding key
			//System.out.println("Hello!");
			DOCUMENT = 0;
			getStarts();
			//System.out.println("Hello");
			
			getDocuments();

			sb.append(DOCUMENT).append("\n");

		}// end of test case 

		System.out.println(sb.toString());
	}// end of main method

	public static boolean getNewKey(Pair spair){
		initialize(isV);

		q.add(spair);
		int sr = spair.r; int sc = spair.c;
		//System.out.println("Getting key "+board[sr][sc]);
		isV[sr][sc] = 1;

		while(!q.isEmpty()){
			Pair cur = q.remove();
			int cr = cur.r; int cc = cur.c;
			

			for (int i=0; i<4; i++) {
				int nr = cr + dr[i]; int nc = cc + dc[i];

				// Out of bounds
				if(nr < 0 || nr >= H || nc < 0 || nc >= W || isV[nr][nc] == 1) continue;

				if(board[nr][nc].equals(".") || board[nr][nc].matches(lower) || (board[nr][nc].matches(upper) && key.contains(board[nr][nc].toLowerCase()))){
					// 
					q.add(new Pair(nr,nc));
					isV[nr][nc] = 1;
				}
			}
		}
		boolean result = checkOut();
		if(result) {
			//System.out.println("Got Key "+board[sr][sc]);
			key.add(board[sr][sc]);
		}
		return result;
	}// end of getNewKey method// using BFS

	public static boolean checkOut(){
		for (Pair candOut : starts ) {
			int cr = candOut.r;
			int cc = candOut.c;
			//System.out.println("Checking R"+cr+" C"+cc+" is reachable");
			if(isV[cr][cc] == 1) return true;
		}

		return false;
	}// end of checkOut

	public static void getDocuments(){
		for(Pair doc : docs){
			if(BFS(doc)) DOCUMENT++;
		}
	}// end of getDocuments 

	public static boolean BFS(Pair start){
		initialize(isV);

		q.add(start);
		int sr = start.r; int sc = start.c;
		//System.out.println("R"+sr+" C"+sc);
		isV[sr][sc] = 1;

		while(!q.isEmpty()){
			Pair cur = q.remove();
			int cr = cur.r; int cc = cur.c;

			

			for (int i=0; i<4; i++) {
				int nr = cr + dr[i]; int nc = cc + dc[i];	

				// Out of bound
				if(nr < 0 || nr >= H || nc < 0 || nc >= W || isV[nr][nc] == 1) continue;			

				if(board[nr][nc].equals(".") || board[nr][nc].matches(lower) || (board[nr][nc].matches(upper) && key.contains(board[nr][nc].toLowerCase()))){
					q.add(new Pair(nr,nc));
					isV[nr][nc] = 1;
				}
			}
		}// end of while loop
		boolean result = checkOut();
		//System.out.println(result);
		return result;
	}

	public static void getStarts(){
		// 4 point!
		// 0,0 & 0,W-1 & H-1,0 & H-1,W-1
		starts = new LinkedList<>();

		if((board[0][0].matches(lower)) || board[0][0].equals(".") || board[0][0].equals("$") || (board[0][0].matches(upper) && key.contains(board[0][0].toLowerCase()))) starts.add(new Pair(0,0));
		if((board[0][W-1].matches(lower)) || board[0][W-1].equals(".") || board[0][W-1].equals("$")|| (board[0][W-1].matches(upper) && key.contains(board[0][W-1].toLowerCase()))) starts.add(new Pair(0,W-1));
		if((board[H-1][0].matches(lower)) || board[H-1][0].equals(".") || board[H-1][0].equals("$")|| (board[H-1][0].matches(upper) && key.contains(board[H-1][0].toLowerCase()))) starts.add(new Pair(H-1,0));
		if((board[H-1][W-1].matches(lower)) || board[H-1][W-1].equals(".") || board[H-1][W-1].equals("$")|| (board[H-1][W-1].matches(upper) && key.contains(board[H-1][W-1].toLowerCase()))) starts.add(new Pair(H-1,W-1));

		for (int r=1; r<H-1; r++) {
			if((board[r][0].matches(lower)) || board[r][0].equals(".") || board[r][0].equals("$")|| (board[r][0].matches(upper) && key.contains(board[r][0].toLowerCase()))) starts.add(new Pair(r,0));
			if((board[r][W-1].matches(lower)) || board[r][W-1].equals(".") || board[r][W-1].equals("$")|| (board[r][W-1].matches(upper) && key.contains(board[r][W-1].toLowerCase()))) starts.add(new Pair(r,W-1));
		}

		for (int c=1; c<W-1; c++) {
			if((board[0][c].matches(lower)) || board[0][c].equals(".") || board[0][c].equals("$") || (board[0][c].matches(upper) && key.contains(board[0][c].toLowerCase()))) starts.add(new Pair(0,c));
			if((board[H-1][c].matches(lower)) || board[H-1][c].equals(".") || board[H-1][c].equals("$") || (board[H-1][c].matches(upper) && key.contains(board[H-1][c].toLowerCase()))) starts.add(new Pair(H-1,c));
		}

	}// end of getStarts.......

	public static void initialize(int[][] arr){
		for (int r=0; r<arr.length; r++) {
			Arrays.fill(arr[r],0);
		}
	}// end of initialize method

}// end of class 

class Pair{
	int r;
	int c;

	public Pair(int r, int c){
		this.r = r;
		this.c = c;
	}
}// end of pair class 



/*
2 2
$$
$$
0
5 5
***b*
***A*
***$*
.a***
*****
0
*/