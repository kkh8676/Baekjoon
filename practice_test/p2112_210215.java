// First Time Overflow.........

import java.util.StringTokenizer;
import java.io.*;
import java.util.Arrays;

class p2112_210215{
	static int T,D,W,K;
	static int[][] board;

	static int[] seq;

	static int ANSWER;
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder answer = new StringBuilder();

		board = new int[13][20];
		//seq = new int[13];

		T = Integer.parseInt(br.readLine());
		for (int t=1; t<=T; t++) {
			// initialize board arr
			for (int r=0; r<13; r++) {
				Arrays.fill(board[r],0);
			}

			st = new StringTokenizer(br.readLine());
			D = Integer.parseInt(st.nextToken());
			W = Integer.parseInt(st.nextToken());
			K = Integer.parseInt(st.nextToken());

			for (int r=0; r<D; r++) {
				st = new StringTokenizer(br.readLine());
				for (int c=0; c<W; c++) {
					board[r][c] = Integer.parseInt(st.nextToken());
				}
			}// end of getting board information

			ANSWER = -1;
			for (int capa=0; capa<=D; capa++) {
				DFS(capa, 0, 0);

				if(ANSWER != -1) break;
			}
			// check(0);

			answer.append("#").append(t).append(" ").append(ANSWER).append("\n");
		}// end of test case

		System.out.println(answer.toString());
		br.close();
	}// end of main method

	public static void DFS(int capa, int round, int start){
		if(ANSWER != -1) return;

		if(capa == round){
			check(capa);
			// for (int i=0; i<capa; i++) {
			// 	System.out.print(seq[i]+" ");
			// }
			// System.out.println();
			return;
		}

		int[][] prev_state = new int[D][W];
		copyArr(board,prev_state);

		for (int n=start; n<D; n++) {
			// check it to A DFS process
			// check that row to A
			// for (int c=0; c<W; c++) {
			// 	board[n][c] = 0;
			// }
			Arrays.fill(board[n],0);
			//seq[round] = n;
			DFS(capa, round+1, n+1);

			// BackTracking process
			copyArr_row(prev_state, board,n);

			// check it to B DFS process
			// for (int c=0; c<W; c++) {
			// 	board[n][c] = 1;
			// }
			Arrays.fill(board[n],1);
			//seq[round] = n;
			DFS(capa, round+1, n+1);


			// BackTracking process
			copyArr_row(prev_state, board,n);
		}
	}// end of DFS method

	public static void check(int capa){

		for (int c=0; c<W; c++) {
			int seq = 1;
			int prev = board[0][c];
			for (int r=1; r<D; r++) {
				if(prev == board[r][c]){
					seq++;
				} else{
					seq = 1;
					prev = board[r][c];
				}

				if(seq >= K){
					//System.out.println("Current Column"+ c+" Checked!");
					break;	
				} 
			}
			if(seq < K){
				//System.out.println("Current Column"+c+ " Not checked!");
				return;
			}
		}// current column
		//System.out.println("checked!");
		ANSWER = capa;

	}// end of check method

	public static void copyArr_row(int[][] arr1, int[][] arr2, int r){
		
			for (int c=0; c<W; c++) {
				arr2[r][c] = arr1[r][c];
			}
		
	}// end of copyArr_row method

	public static void copyArr(int[][] arr1, int[][] arr2){
		for(int r=0; r<D; r++){
			for (int c=0; c<W; c++) {
				arr2[r][c] = arr1[r][c];
			}
		}
	}// end of copyArr_row method
}// end of class

/*
10
6 8 3
0 0 1 0 1 0 0 1
0 1 0 0 0 1 1 1
0 1 1 1 0 0 0 0
1 1 1 1 0 0 0 1
0 1 1 0 1 0 0 1
1 0 1 0 1 1 0 1
6 8 3
1 1 1 1 0 0 1 0
0 0 1 1 0 1 0 1
1 1 1 1 0 0 1 0
1 1 1 0 0 1 1 0
1 1 0 1 1 1 1 0
1 1 1 0 0 1 1 0
6 8 4
1 1 0 0 0 1 1 0
1 0 1 0 0 1 1 1
0 1 0 0 1 1 0 0
1 0 1 0 0 0 0 0
1 1 0 0 0 0 0 0
1 0 0 0 1 1 1 1
6 4 4
1 1 0 0
0 1 0 1
0 0 0 1
1 1 1 1
1 1 0 1
1 0 1 0
6 10 3
0 1 0 0 0 1 0 0 1 1
0 1 1 0 0 1 0 0 1 0
0 1 0 0 1 0 1 1 1 1
0 0 0 0 0 1 1 1 1 0
0 1 0 0 1 1 1 1 1 1
1 0 0 0 1 1 0 0 1 1
6 6 5
0 0 0 0 0 0
0 0 0 0 0 0
0 0 0 0 0 0
0 0 0 0 0 0
0 0 0 0 0 0
0 0 0 0 0 0
6 6 4
1 1 1 1 1 1
0 0 0 0 0 1
0 1 1 1 0 1
0 1 0 1 0 1
0 1 0 0 0 1
0 1 1 1 1 1
8 15 3
0 1 1 0 0 1 1 0 1 1 0 0 0 0 0
1 0 0 0 1 1 0 0 0 0 0 1 0 1 1
1 1 0 1 0 1 0 1 0 1 0 1 0 0 0
0 1 1 1 0 0 1 0 0 0 0 1 0 0 0
0 0 0 0 0 0 1 0 0 0 1 1 0 0 1
1 0 1 0 0 1 0 1 1 1 1 0 1 1 1
0 0 0 0 0 1 1 1 0 0 0 0 0 1 0
0 0 1 0 1 1 0 1 1 0 0 0 1 0 0
10 20 4
1 0 1 1 1 1 1 1 1 1 0 0 1 1 1 0 1 1 0 1
1 1 0 1 1 1 0 0 1 0 0 0 1 1 1 1 0 0 1 0
1 1 0 1 1 0 0 0 1 1 1 1 1 0 0 1 1 0 1 0
0 0 0 1 1 0 0 0 0 1 0 0 1 0 1 1 1 0 1 0
0 1 1 0 1 0 1 0 1 0 0 1 0 0 0 0 1 1 1 1
1 0 1 0 1 0 1 1 0 0 0 0 1 1 1 0 0 0 0 0
0 1 0 0 1 1 0 0 0 0 0 1 1 0 0 1 1 0 1 1
1 0 0 0 0 1 0 1 1 0 1 1 0 1 0 0 1 1 1 0
0 1 1 0 0 1 0 1 0 0 0 0 0 0 0 1 1 1 0 1
0 0 0 0 0 0 1 1 0 0 1 1 0 0 0 0 0 0 1 0
13 20 5
1 1 0 1 0 0 0 1 1 1 1 0 0 0 1 1 1 0 0 0
1 1 1 1 0 1 0 1 0 0 0 0 1 0 0 0 0 1 0 0
1 0 1 0 1 1 0 1 0 1 1 0 0 0 0 1 1 0 1 0
0 0 1 1 0 1 1 0 1 0 0 1 1 0 0 0 1 1 1 1
0 0 1 0 0 1 0 0 1 0 0 0 0 1 0 0 0 0 1 1
0 0 1 0 0 0 0 0 0 0 0 0 1 1 1 0 0 1 0 1
0 0 0 1 0 0 0 0 0 0 1 1 0 0 0 1 0 0 1 0
1 1 1 0 0 0 1 0 0 1 1 1 0 1 0 1 0 0 1 1
0 1 1 1 1 0 0 0 1 1 0 1 0 0 0 0 1 0 0 1
0 0 0 0 1 0 1 0 0 0 1 0 0 0 0 1 1 1 1 1
0 1 0 0 1 1 0 0 1 0 0 0 0 1 0 1 0 0 1 0
0 0 1 1 0 0 1 0 0 0 1 0 1 1 0 1 1 1 0 0
0 0 0 1 0 0 1 0 0 0 1 0 1 1 0 0 1 0 1 0
*/
