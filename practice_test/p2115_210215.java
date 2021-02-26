// Took 45 min Success
// 20796 kb, 129ms......

import java.util.StringTokenizer;
import java.io.*;
import java.util.Arrays;

class p2115_210215{
	static int T,N,M,C;
	static int[][] board, isV;

	static int first_profit;
	static int second_profit;

	static int ANSWER;
	public static void main(String[] args) throws IOException{	
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder answer = new StringBuilder();

		board = new int[10][10];
		isV = new int[10][10];

		T = Integer.parseInt(br.readLine());
		for (int t=1; t<=T; t++) {
			// initialize the board arr
			for (int r=0; r<10; r++) {
				Arrays.fill(board[r],0);
				Arrays.fill(isV[r],0);
			}

			st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());
			C = Integer.parseInt(st.nextToken());

			for (int r=0; r<N; r++) {
				st = new StringTokenizer(br.readLine());
				for (int c=0; c<N; c++) {
					board[r][c] = Integer.parseInt(st.nextToken());
				}
			}// end of getting board information

			ANSWER = Integer.MIN_VALUE;

			// Solving algorithm here!!
			// first starting location deciding
			for (int fr=0; fr<N; fr++) {
				for (int fc=0; fc<N; fc++) {
					// initialize isV arr here!
					first_profit = Integer.MIN_VALUE;
					for (int r=0; r<N; r++) {
						Arrays.fill(isV[r],0);
					}

					// last column index is out of bounds, then continue
					if(fc + M-1 >= N) continue;

					// check the isV array & get the largest profit from that bee cave
					for (int m=0; m<M; m++) {
						int cc = fc + m;
						isV[fr][cc] = 1;
					}

					get_largest_profit(0,fr,fc,0,0,0);

					for (int sr=0; sr<N; sr++) {
						for (int sc=0; sc<N; sc++) {
							second_profit = Integer.MIN_VALUE;
							int first = fr*N + fc;
							int second = sr*N + sc;

							if(second <= first) continue;

							// out of bounds condition
							if(sc + M-1 >= N) continue;

							// if already occupied location
							if(isV[sr][sc] == 1 || isV[sr][sc + M-1] == 1) continue;


							get_largest_profit(1,sr,sc,0,0,0);

							int total_profit = first_profit + second_profit;

							ANSWER = ANSWER < total_profit ? total_profit : ANSWER;

						}
					}


				}
			}
			// get_largest_profit(0,0,0,0,0,0);
			// get_largest_profit(1,2,0,0,0,0);
			// System.out.println(first_profit);
			// System.out.println(second_profit);


			// printing process
			answer.append("#").append(t).append(" ").append(ANSWER).append("\n");
		}// end of test case

		System.out.println(answer.toString());
		br.close();
	}// end of main method

	public static void get_largest_profit(int first_or_second, int sr, int sc, int round, int got, int score){
		// came here because not out of bounds
		if(round == M){
			if(first_or_second == 0){
				first_profit = first_profit < score? score : first_profit;
			} else{
				second_profit = second_profit < score? score : second_profit;
			}
			return;
		}// end of ending condition

		if(got + board[sr][sc+round] <= C){
			//System.out.println("Getting Honey in R"+sr+" C"+(sc+round));
			get_largest_profit(first_or_second, sr,sc, round+1, got+board[sr][sc+round], score + (board[sr][sc+round])*(board[sr][sc+round]));	
		} 
		//System.out.println("Not getting Honey in R"+sr+" C"+(sc+round));
		get_largest_profit(first_or_second, sr, sc, round+1, got, score);	
	}// end of get_largest_profit method
}// end of class

/*
10
4 2 13
6 1 9 7
9 8 5 8
3 4 5 3
8 2 6 7
3 3 10
7 2 9
6 6 6
5 5 7
4 1 10
8 1 8 2
4 6 1 6
4 9 6 3
7 4 1 3
4 3 12
8 8 6 5
5 2 7 4
8 5 1 7
7 8 9 4
5 2 11
7 5 9 9 6
7 3 7 9 3
1 7 1 4 5
1 7 9 2 6
6 6 8 3 8
6 3 20
8 5 2 4 3 1
4 3 6 1 1 8
4 4 1 2 3 1
1 7 4 9 6 1
6 5 1 2 8 4
3 1 4 5 1 3
7 2 11
2 8 2 5 2 8 6
2 3 7 4 6 4 8
3 7 8 3 9 4 4
8 8 5 9 3 6 9
9 7 6 2 4 1 3
2 9 2 8 9 7 3
2 1 7 2 7 8 3
8 3 12
9 1 6 7 5 4 6 7
9 5 1 8 8 3 5 8
5 2 6 8 6 9 2 1
9 2 1 8 7 5 2 3
6 5 5 1 4 5 7 2
1 7 1 8 1 9 5 5
6 2 2 9 2 5 1 4
7 1 1 2 5 9 5 7
9 4 20
5 2 4 8 3 7 6 2 1
7 9 8 5 8 2 6 3 6
1 9 4 6 7 5 3 1 1
4 4 7 6 2 2 8 1 7
9 6 8 5 7 3 7 9 5
7 3 1 4 1 1 8 5 3
4 6 8 9 4 5 3 8 8
1 3 4 2 4 1 1 3 6
5 9 2 3 5 2 4 8 5
10 5 30
7 4 7 5 9 3 6 4 6 7
8 9 8 4 5 7 8 9 2 9
6 5 3 4 6 4 7 6 3 2
4 7 4 3 4 7 3 3 4 3
3 5 6 4 8 8 2 1 8 6
3 7 9 7 1 7 6 2 8 9
3 6 1 6 8 9 7 7 5 1
4 3 5 6 2 1 2 6 3 6
3 4 9 2 1 5 9 9 6 3
9 9 7 3 7 5 5 5 8 4
*/