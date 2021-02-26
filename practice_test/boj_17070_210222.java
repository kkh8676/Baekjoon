// 26min........13224kb, 180ms

import java.util.StringTokenizer;
import java.io.*;
import java.util.LinkedList;
import java.util.Queue;

class boj_17070_210222{
	static int N;
	static int[][] board;

	// 0 horizontal move
	// 1 vertical move
	// 2 diagonal move

	static int ANSWER;
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		N = Integer.parseInt(br.readLine());
		board = new int[N][N];

		for (int r=0; r<N; r++) {
			st = new StringTokenizer(br.readLine());
			for (int c=0; c<N; c++) {
				board[r][c] = Integer.parseInt(st.nextToken());
			}
		}// end of getting board information

		ANSWER = 0;
		DFS(0,0,0,1,0);

		System.out.println(ANSWER);
	}// end of main method

	public static void DFS(int r1, int c1, int r2, int c2, int dir){
		if(r2 == N-1 && c2 == N-1){
			ANSWER++;
			return;
		}// ending condition

		// out of bounds ending condition
		//if(r2 >= N || c2 >= N) return;

		// 3 way each
		if(dir==0){
			// current position is horizontal
			// 1. to horizontal move
			if(inbound(r2,c2+1) && board[r2][c2+1] == 0) DFS(r1,c1+1,r2,c2+1,0);

			// 2. to diagonal move
			if(inbound(r2+1,c2+1) && board[r2+1][c2] == 0 && board[r2][c2+1] == 0 && board[r2+1][c2+1] == 0) DFS(r1,c1+1,r2+1,c2+1,2);

		} else if(dir==1){
			// current position is vertical
			// 1. to vertical move
			if(inbound(r2+1,c2) && board[r2+1][c2] == 0) DFS(r1+1,c1, r2+1,c2, 1);

			// 2. to diagonal move
			if(inbound(r2+1,c2+1) && board[r2+1][c2] == 0 && board[r2][c2+1] == 0 && board[r2+1][c2+1] == 0) DFS(r1+1,c1, r2+1,c2+1, 2);

		} else{
			// current position is diagonal
			// 1. to horizontal move
			if(inbound(r2,c2+1) && board[r2][c2+1] == 0) DFS(r1+1,c1+1, r2, c2+1, 0);

			// 2. to vertical move
			if(inbound(r2+1,c2) && board[r2+1][c2] == 0) DFS(r1+1,c1+1, r2+1, c2, 1);

			// 3. to horizontal move
			if(inbound(r2+1,c2+1) && board[r2+1][c2] == 0 && board[r2][c2+1] == 0 && board[r2+1][c2+1] == 0) DFS(r1+1,c1+1, r2+1,c2+1, 2);

		}
	}// end of DFS method

	public static boolean inbound(int r, int c){
		if(r < 0 || r >= N || c < 0 || c >= N) return false;

		return true;
	}// end of inbound method

}// end of class