// Failed ........because of Memory Overflow.....
// Don't Understand Bull shit!!

import java.util.HashMap;
import java.util.Queue;
import java.util.LinkedList;
import java.io.*;
import java.util.StringTokenizer;
import java.lang.Runtime;

class boj_1525{
	static int[][] board;

	static String ANSWERKEY = "123456780";

	static int[] dr = {-1,1,0,0};
	static int[] dc = {0,0,-1,1};

	static HashMap<String, Integer> map;

	static int[][] tempBoard;

	static int curbr;
	static int curbc;
	static int curd;

	static String tempKey;
	static String curKey;

	public static void main(String[] args) throws IOException{
		System.gc();
		long before = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

		BufferedReader BR = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		board = new int[4][4];
		tempBoard = new int[4][4];

		
		for (int r=1; r<=3; r++) {
			st = new StringTokenizer(BR.readLine());
			for (int c=1; c<=3; c++) {
				board[r][c] = Integer.parseInt(st.nextToken());
			}
		}// end of getting information

		BFS();
		int ANSWER = map.containsKey(ANSWERKEY)? map.get(ANSWERKEY) : -1;
		System.out.println(ANSWER);

		System.gc();
		long after = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

		long used = (before - after)/1024/1024;
		System.out.println(used);
		

	}// end of main method

	public static void BFS(){
		//Queue<Integer> nq = new LinkedList<>();
		Queue<String> sq = new LinkedList<>();
		map = new HashMap<>();

		curKey = new String();
		tempKey = new String();

		curKey = makeKey(board);
		//System.out.println(curKey);
		//nq.add(curKey);
		// nq.add(br);
		// nq.add(bc);
		sq.add(curKey);

		map.put(curKey, 0);

		while(!sq.isEmpty() && !map.containsKey(ANSWERKEY)){
			//int cKey = nq.remove();
			// int curbr = nq.remove();
			// int curbc = nq.remove();
			String cKey = sq.remove();
			curbr = extractR(cKey);
			curbc = extractC(cKey);
			//System.out.println("Current BR"+curbr+" BC"+curbc);
			curd = map.get(cKey);
			makeBoard(cKey);

			for (int i=0; i<4; i++) {
				int nr = curbr + dr[i];
				int nc = curbc + dc[i];

				if(nr < 1 || nr > 3 || nc < 1 || nc > 3) continue;

				//int[][] tempBoard = new int[4][4];
				//copyBoard(curBoard, tempBoard);

				swap(tempBoard, curbr, curbc, nr, nc);

				tempKey = makeKey(tempBoard);

				if(!map.containsKey(tempKey)){
					//nq.add(tempKey);
					//nq.add(nr);
					//nq.add(nc);
					sq.add(tempKey);

					map.put(tempKey, curd+1);
				}

				swap(tempBoard, curbr, curbc, nr, nc);
			}


		}




	}// end of BFS method

	public static void swap(int[][] board, int r, int c, int nr, int nc){

		int temp = board[r][c];
		int temp2 = board[nr][nc];
		board[r][c] = temp2;
		board[nr][nc] = temp;

	}// end of swap method



	public static String makeKey(int[][] board){
		String key = "";

		for (int r=1; r<=3; r++) {
			for (int c=1; c<=3; c++) {
				key = key + board[r][c];
			}
		}
		return key;
	}// end of makeKey method

	public static void makeBoard(String key){
		
		String[] splited = key.split("");

		

		for (int r=1; r<=3; r++) {
			for (int c=1; c<=3; c++) {
				tempBoard[r][c] = Integer.parseInt(splited[(r-1)*3+c - 1]);
			}
		}
	}// end of makeBoard method

	public static int extractR(String key){
		int index = -1;
		String[] splited = key.split("");
		for (int i=0; i<splited.length; i++) {
			if(splited[i].equals("0")){
				index = i;
			}
		}

		int r = index / 3 + 1;

		return r;
	}// end of extractR

	public static int extractC(String key){
		int index = -1;
		String[] splited = key.split("");
		for (int i=0; i<splited.length; i++) {
			if(splited[i].equals("0")){
				index = i;
			}
		}

		int r = index / 3 + 1;
		int c = index - (r-1)*3 + 1;

		return c;
	}// end of extractC
}// end of class 