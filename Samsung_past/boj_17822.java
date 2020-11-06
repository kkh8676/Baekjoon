import java.io.*;
import java.util.Scanner;
import java.util.Arrays;


class boj_17822{
	static int N;
	static int M;

	static int[][] board;

	static int T;

	// index 0 xi number
	// index 1 direction
	// index 2 how many block?
	static int[][] commands;

	static int ANSWER;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

		String[] NMT = br.readLine().split(" ");
		N = Integer.parseInt(NMT[0]);
		M = Integer.parseInt(NMT[1]);
		T = Integer.parseInt(NMT[2]);

		board = new int[N+1][M];

		for (int r=1; r<=N; r++) {
			String[] curRow = br.readLine().split(" ");
			for (int c=0; c<M; c++) {
				board[r][c] = Integer.parseInt(curRow[c]);
			}// end of column board
		}// end of row board

		// getting commands

		commands = new int[T+1][3];

		for (int t=1; t<=T; t++) {
			String[] curCommand = br.readLine().split(" ");
			for (int i=0; i<3; i++) {
				commands[t][i] = Integer.parseInt(curCommand[i]);
			}
		}// end of getting commands information!!

		for (int round=1; round<=T; round++) {
			
			rotate(commands[round][0], commands[round][1], commands[round][2]);
			
			//printMap();

			boolean deleted_ = delete();
			
			
			//printMap();

			if(!deleted_) update_num();

		}

		int sum = 0;

		for (int r=1; r<=N; r++) {
			for (int m=0; m<M; m++) {
				sum = sum + board[r][m];
			}
		}
		//printMap();
		System.out.println(sum);


	}// end of main method

	public static void rotate(int num, int dir, int howMany){
		// rotating row number 
		// num, 2*num, 3*num......rotating
		//dir 0 clockwise, dir 1 anticlockwise

		int k = howMany % M;

		for (int round=1; (num*round) <=N; round++) {
			// clockwise
			
			int cR = num*round;
			int[] used_ary = board[cR];
			int[] new_ary = new int[M+1];
			if(dir==0){
				for (int c=0; c<M; c++) {
					new_ary[(c+k)%M] = used_ary[c];
				}
			} // end of clockwise rotate

			// anticlockwise
			if(dir==1){
				for (int c=0; c<M; c++) {
					new_ary[c] = used_ary[(c+k)%M];
				}
			}// end of anti clock wise rotate

			board[cR] = new_ary;
		} // end of round 

	} // end of rotate method

	public static boolean delete(){
		boolean[][] isDeleted = new boolean[N+1][M];
		boolean result = false;
		
		for (int row=1; row<=N; row++) {
			Arrays.fill(isDeleted[row], false);
		}

		for (int row=1; row<=N; row++) {
			for (int col=0; col<M; col++) {
				// (col+1)%M
				if(board[row][col] == board[row][(col+1)%M] && board[row][col] != 0){
					result = true;
					isDeleted[row][col] = true;
					isDeleted[row][(col+1)%M]  =true;
				}
				if(row+1 > N ) continue;

				if(board[row][col] == board[row+1][col] && board[row][col] != 0){
					result = true;
					isDeleted[row][col] = true;
					isDeleted[row+1][col] = true;
				}
			}
		}// end of for loop

		for (int row=1; row<=N; row++) {
			for (int m=0; m<M; m++) {
				if(isDeleted[row][m]) board[row][m] = 0;
			}
		}

		return result;
	} // end of delete method

	public static void update_num(){
		float avg = -1;
		float count =0;
		float sum = 0;

		for (int r=1; r<=N; r++) {
			for (int m=0; m<M; m++) {
				if(board[r][m] != 0 ){
				 sum = sum + board[r][m];
				 count = count + 1;
				}
			}
		}
		if(count!=0) avg = sum/ count;

		for (int r=1; r<=N; r++) {
			for (int m=0; m<M; m++) {
				if(board[r][m] != 0 && board[r][m] > avg) board[r][m] = board[r][m] -1;
				else if(board[r][m] != 0 && board[r][m] < avg) board[r][m] = board[r][m] + 1;
			}
		}// changing process is done!
	} // end of update_num

	public static void printMap(){
		for (int r=1; r<=N; r++) {
			for (int m=0; m<M; m++) {
				System.out.print(board[r][m]+" ");
			}
			System.out.println();
		}
	}

}// end of class