// 40 min 40/50 success.........
// example???
// 49 min Success......... visiting process...

import java.io.*;
import java.util.StringTokenizer;
import java.util.Arrays;

class p4014{
	static int T,N,X;
	static int[][] board;
	static int[] built;

	static int ANSWER;
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder answer = new StringBuilder();
		StringTokenizer st;
		T = Integer.parseInt(br.readLine());

		board = new int[20][20];
		built = new int[20]; // should be 2 dimension???

		for (int t=1; t<=T; t++) {
			st = new StringTokenizer(br.readLine());

			// initialize board array
			for (int r=0; r<20; r++) {
				Arrays.fill(board[r],0);
			}

			N = Integer.parseInt(st.nextToken());
			X = Integer.parseInt(st.nextToken());

			for (int r=0; r<N; r++) {
				st = new StringTokenizer(br.readLine());
				for (int c=0; c<N; c++) {
					board[r][c] = Integer.parseInt(st.nextToken());
				}
			}// end of getting information
			ANSWER = 0;

			for (int i=0; i<N; i++) {
				ANSWER += check_r(i);
				//System.out.println();
				ANSWER += check_c(i);
				//System.out.println();
			}

			answer.append("#").append(t).append(" ").append(ANSWER).append("\n");
		}// end of test case

		System.out.println(answer.toString());
		br.close();
	}// end of main method

	public static int check_r(int checkingR){
		//System.out.println("Checking R"+checkingR);
		int[] floor = board[checkingR];

		// initializing built
		Arrays.fill(built,0);

		int seq = 1;
		int prev = floor[0];
		for (int l=1; l<N; l++) {
			if(prev == floor[l]){
				seq++;
				continue;
			} else if(prev == floor[l]-1){
				// upStair case 
				if(seq < X){
					//System.out.println("Previous Sequence is less than X return 0");
					return 0;
				}


				// came here because seq larger than X
				seq = 1;
				prev = floor[l];
				continue;

			} else if(prev == floor[l]+1){
				// downStair case
				for (int i=1; i<X; i++) {
					if(l+i >= N || floor[l] != floor[l+i]){
						//System.out.println("Next sequence is less than X return 0");
						return 0;
					} 
				}
				//came here because there's X sequence
				l = l+X-1;
				prev = floor[l];
				seq = 0;
			} else{
				//System.out.println("Height difference is larger than 1 return 0");
				return 0;	
			} 
		}// end of for loop
		//System.out.println("Returning 1");
		return 1;
	}// end of check_r method

	public static int check_c(int checkingC){
		//System.out.println("Checking C"+checkingC);
		int[] floor = new int[N];
		for (int l=0; l<N; l++) {
			floor[l] = board[l][checkingC];
		}

		// initializing built
		Arrays.fill(built,0);

		int seq = 1;
		int prev = floor[0];
		for (int l=1; l<N; l++) {
			if(prev == floor[l]){
				seq++;
				continue;
			} else if(prev == floor[l]-1){
				// upStair case 
				if(seq < X){
					//System.out.println("Previous Sequence is less than X return 0");
					return 0;
				}


				// came here because seq larger than X
				seq = 1;
				prev = floor[l];
				continue;

			} else if(prev == floor[l]+1){
				// downStair case
				for (int i=1; i<X; i++) {
					if(l+i >= N || floor[l] != floor[l+i]){
						//System.out.println("Next sequence is less than X return 0");
						return 0;
					} 
				}
				//came here because there's X sequence
				l = l+X-1;
				prev = floor[l];
				seq = 0;
			} else{
				//System.out.println("Height difference is larger than 1 return 0");
				return 0;	
			} 
		}// end of for loop
		//System.out.println("Returning 1");
		return 1;
	}// end of check_c method


}// end of class

/*
10
6 2
3 3 3 2 1 1
3 3 3 2 2 1
3 3 3 3 3 2
2 2 3 2 2 2
2 2 3 2 2 2
2 2 2 2 2 2
6 4
3 2 2 2 1 2 
3 2 2 2 1 2 
3 3 3 3 2 2 
3 3 3 3 2 2 
3 2 2 2 2 2 
3 2 2 2 2 2 
7 2
1 1 1 1 2 1 1 
1 1 1 2 2 2 1 
2 2 2 2 2 2 2 
2 2 2 2 2 2 2 
2 2 2 2 2 2 2 
2 2 2 2 2 2 2 
2 2 2 2 2 2 2 
8 3
2 2 2 3 3 4 2 2 
2 2 2 3 3 4 2 2 
2 2 2 2 2 2 2 2 
2 2 2 2 2 2 2 2 
2 2 2 2 1 1 2 2 
2 1 1 1 1 1 1 1 
2 1 1 1 1 1 1 1 
2 1 1 1 1 1 1 1 
8 4
1 1 1 1 1 1 1 1 
1 1 1 1 1 1 1 1 
1 1 1 1 1 1 1 1 
2 1 1 1 1 1 1 1 
2 1 1 1 1 1 1 1 
2 1 1 1 1 1 1 1 
1 1 1 1 1 1 1 2 
1 1 1 1 1 1 1 2 
9 4
4 4 3 3 3 3 2 2 2 
4 4 3 3 1 1 2 2 3 
3 3 2 2 1 1 1 1 2 
1 1 1 1 1 1 1 1 1 
1 1 1 1 1 1 1 1 1 
2 2 1 1 1 1 1 1 1 
2 2 1 1 1 1 1 1 1 
2 2 2 2 2 2 1 1 1 
3 3 3 3 2 2 2 2 1 
10 2
2 2 3 5 3 1 1 1 1 1 
2 2 3 5 3 1 1 1 1 1 
3 3 4 5 4 3 2 1 1 2 
3 3 4 5 4 3 2 1 1 2 
5 5 5 5 5 5 3 1 1 3 
4 4 4 5 5 5 4 3 3 3 
4 4 4 5 5 5 5 5 5 3 
4 4 4 4 4 5 5 5 5 3 
4 4 4 4 4 5 5 5 5 3 
5 5 4 4 4 5 5 5 5 4 
12 4
4 4 4 5 5 4 4 4 4 4 4 4 
5 5 5 5 5 5 5 5 5 5 5 4 
4 4 4 5 5 4 4 4 5 5 5 4 
3 3 4 5 5 4 3 4 5 5 5 4 
3 3 4 5 5 4 3 4 5 5 5 4 
2 2 3 4 4 4 4 4 4 4 4 5 
2 2 3 4 4 4 4 4 4 4 4 5 
2 2 3 3 3 4 5 3 2 2 4 4 
3 3 3 4 4 4 5 4 3 3 4 4 
3 3 4 5 5 5 5 5 5 5 5 4 
3 3 4 5 5 5 5 5 5 5 5 4 
4 4 4 4 4 4 5 5 5 5 5 4 
15 3
5 4 4 3 3 3 2 2 2 3 4 4 4 4 4 
5 4 4 3 3 3 2 2 2 3 4 4 4 4 4 
5 5 5 5 4 4 2 2 3 4 4 4 4 4 5 
5 4 4 3 3 3 2 2 3 4 4 4 4 4 4 
5 3 3 1 2 2 3 3 3 4 4 4 4 4 4 
3 3 3 3 3 3 3 3 3 3 4 4 4 4 4 
3 3 3 3 3 3 3 3 3 3 4 4 4 4 4 
2 3 3 4 3 3 3 3 3 3 3 4 4 4 3 
2 3 3 4 3 3 3 3 3 3 3 4 4 4 3 
3 4 4 4 4 4 3 3 3 3 3 3 4 4 4 
5 5 5 4 4 4 4 4 3 3 3 3 4 4 5 
5 5 5 4 4 4 4 4 3 3 3 3 4 4 5 
5 5 5 5 4 4 4 4 3 3 2 2 3 3 4 
5 5 5 5 5 5 4 4 3 3 2 1 2 2 3 
5 5 5 5 5 5 4 4 3 3 2 1 2 2 3 
20 3
3 3 3 2 2 2 2 3 3 3 4 4 4 4 4 4 5 5 5 5 
3 3 3 2 2 2 2 3 3 3 4 4 4 4 4 4 5 5 5 5 
5 3 3 2 2 2 2 2 3 3 4 4 4 4 5 5 5 5 5 5 
4 3 3 1 1 1 1 1 2 3 4 4 4 5 5 5 5 5 5 5 
4 2 2 1 1 1 1 1 2 3 4 5 5 5 5 5 5 5 5 5 
4 3 3 2 2 2 2 1 2 3 4 5 5 5 5 5 5 5 5 5 
4 4 4 4 4 3 3 2 3 4 5 5 5 5 5 5 5 5 5 5 
4 3 3 3 3 3 3 3 4 4 4 5 5 5 5 5 5 4 4 4 
4 3 3 3 3 3 3 3 4 4 4 5 5 5 5 5 5 4 4 4 
4 3 3 3 3 4 4 4 4 4 5 5 5 5 5 5 5 5 5 5 
3 3 3 3 3 4 4 4 4 4 5 5 5 5 5 5 5 5 5 5 
3 3 3 3 3 4 4 4 4 4 4 4 5 5 5 5 5 5 5 5 
3 3 3 3 4 4 4 4 5 5 5 5 5 5 5 5 5 5 5 5 
4 4 4 4 4 4 4 5 5 5 5 5 5 5 5 5 5 4 4 4 
4 4 4 4 4 4 4 5 5 5 5 5 5 5 5 5 5 4 4 4 
5 5 5 5 5 5 5 5 5 5 5 5 4 4 4 4 4 4 4 4 
5 5 5 5 5 5 5 5 5 5 5 5 3 3 3 3 4 4 4 4 
5 5 5 5 5 5 5 5 5 5 5 5 3 3 2 2 3 3 4 4 
5 5 5 5 5 5 5 5 5 5 5 5 3 3 2 2 3 3 4 4 
5 5 5 5 5 5 5 5 4 4 4 4 3 3 3 3 4 4 4 4 
*/
