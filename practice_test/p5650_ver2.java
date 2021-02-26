// 39000 KB, 247ms
// Using DFS, N*N time
// which means we use DFS method maximum 10 * 10 * 4 time......

import java.util.StringTokenizer;
import java.io.*;
import java.util.Arrays;


class p5650_ver2{
	static int T,N, B;
	static int[][] board;
	static int[][][] worm;
	static int[] worm_cnt;

	static int ANSWER;

	static int[] dr = {-1,1,0,0};
	static int[] dc = {0,0,-1,1};
	static int[][] changeDir = {{1,3,0,2},{3,0,1,2},{2,0,3,1},{1,2,3,0},{1,0,3,2}};
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder ans = new StringBuilder();
		StringTokenizer st;

		T = Integer.parseInt(br.readLine());
		board = new int[100][100];
		worm = new int[11][2][2]; // first index worm hole number, second worm number, third row and column // how can i get the worm hole with O(1)??
		worm_cnt = new int[11];

		for (int t=1; t<=T; t++) {
			N = Integer.parseInt(br.readLine());

			// initialize board & worm
			initialize();

			for (int r=0; r<N; r++) {
				st = new StringTokenizer(br.readLine());

				for (int c=0; c<N; c++) {
					board[r][c] = Integer.parseInt(st.nextToken());

					if(board[r][c] >= 6 && board[r][c] <= 10){
						int cnt = worm_cnt[ board[r][c] ];
						worm[ board[r][c] ][cnt][0] = r;
						worm[ board[r][c] ][cnt][1] = c;
						worm_cnt[ board[r][c] ]++;
					}
				}
			}// end of getting information

			ANSWER = Integer.MIN_VALUE;
			for (int r=0; r<N; r++) {
				for (int c=0; c<N; c++) {
					if(board[r][c] == 0){
						for (int dir=0; dir<4; dir++) {
							DFS(r,c,r,c,dir,0);
						}
					}
				}
			}// end of DFS all the possible location

			ans.append("#").append(t).append(" ").append(ANSWER).append("\n");
		}// end of Test case 

		System.out.println(ans.toString());
		br.close();
	}// end of main method

	public static void DFS(int sr, int sc, int cr, int cc, int dir, int score){
		// ending condition for DFS method
		int nr = cr + dr[dir];
		int nc = cc + dc[dir];

		//System.out.println("DFS at R"+cr+" C"+cc+" D"+dir+" SCORE"+score+" to R"+sr+" C"+sc);
		if(nr == sr && nc == sc){
			ANSWER = ANSWER < score ? score : ANSWER;
			return;
		}

		// 4 way route.......
		// Out of bounds
		if(nr < 0 || nr >= N || nc < 0 || nc >= N){
			DFS(sr,sc, nr,nc,changeDir[4][dir],score+1);
		}
		// 4. Black Hole
		else if(board[nr][nc] == -1){
			ANSWER = ANSWER < score ? score : ANSWER;
			return;
		}
		// 1. EMpty
		else if(board[nr][nc] == 0){
			DFS(sr,sc, nr, nc, dir, score);
		}
		// 2. BLock
		else if(board[nr][nc] >= 1 && board[nr][nc] <= 5){
			DFS(sr,sc,nr,nc,changeDir[ board[nr][nc]-1 ][dir],score+1);
		}
		// 3. Worm hloe
		else{
			int[] nextLoc = next_worm(board[nr][nc], nr, nc);
			DFS(sr,sc,nextLoc[0],nextLoc[1], dir, score);
		}
		

	}// end of DFS method

	public static int[] next_worm(int worm_num, int wr, int wc){
		int[] result = new int[2];
		if(worm[ worm_num ][0][0] == wr && worm[ worm_num ][0][1] == wc){
			result[0] = worm[ worm_num ][1][0];
			result[1] = worm[ worm_num ][1][1];
		}else{
			result[0] = worm[ worm_num ][0][0];
			result[1] = worm[ worm_num ][0][1];
		}
		return result;
	}// end of next worm method

	public static void initialize(){
		for (int r=0; r<100; r++) {
			Arrays.fill(board[r],0);
		}

		for (int r=0; r<11; r++) {
			for (int c=0; c<2; c++) {
				Arrays.fill(worm[r][c],0);
			}
		}

		Arrays.fill(worm_cnt,0);
	}// end of initialize method
}// end of class 

/*
5
10
0 1 0 3 0 0 0 0 7 0 
0 0 0 0 -1 0 5 0 0 0 
0 4 0 0 0 3 0 0 2 2 
1 0 0 0 1 0 0 3 0 0 
0 0 3 0 0 0 0 0 6 0 
3 0 0 0 2 0 0 1 0 0 
0 0 0 0 0 1 0 0 4 0 
0 5 0 4 1 0 7 0 0 5 
0 0 0 0 0 1 0 0 0 0 
2 0 6 0 0 4 0 0 0 4 
6
1 1 1 1 1 1 
1 1 -1 1 1 1 
1 -1 0 -1 1 1 
1 1 -1 1 1 1 
1 1 1 1 1 1 
1 1 1 1 1 1 
8
0 0 0 3 0 0 0 0 
0 0 2 0 0 5 0 0 
0 0 5 0 3 0 0 0 
0 0 1 1 0 0 0 4 
0 0 0 0 0 0 0 0 
0 0 0 0 0 0 5 0 
0 0 4 0 0 3 1 0 
2 0 0 4 3 4 0 0 
10
0 4 0 0 0 0 4 0 5 0 
0 0 0 0 0 0 0 0 3 0 
0 0 0 5 6 0 0 0 0 2 
3 0 0 1 0 0 1 4 0 2 
2 0 0 0 0 5 0 0 5 0 
0 0 1 0 2 0 0 0 5 0 
0 0 0 0 0 0 6 1 0 0 
0 0 1 0 2 0 2 4 0 0 
0 0 0 0 0 0 2 0 0 0 
2 0 0 0 0 0 0 3 0 0 
20
0 0 1 0 0 0 0 3 0 3 0 0 0 4 0 0 1 0 4 0 
0 1 2 3 3 0 0 0 0 0 0 0 0 5 0 0 0 0 5 0 
0 0 0 0 0 0 0 0 0 5 0 0 0 5 0 4 0 0 0 0 
4 0 0 0 0 0 0 4 5 0 0 0 3 0 0 0 3 0 0 0 
0 0 0 3 0 4 1 0 3 0 0 0 0 4 0 0 0 2 0 3 
2 2 0 0 0 0 0 0 0 0 0 0 4 0 0 0 0 0 0 0 
0 0 0 0 0 0 0 0 0 0 0 0 5 0 5 0 0 0 3 4 
0 0 5 0 -1 5 0 0 5 2 0 0 0 4 2 0 0 3 0 0 
0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0 0 0 0 0 
2 0 0 0 0 3 0 0 3 3 3 0 0 1 0 0 2 0 0 0 
1 5 0 5 0 0 0 0 5 4 5 0 0 0 0 4 2 4 0 0 
0 4 0 0 0 1 3 0 0 0 0 0 1 0 3 0 0 2 0 0 
0 0 0 0 0 0 3 0 1 0 0 1 0 0 0 0 0 3 4 0 
0 4 0 4 0 0 0 0 0 0 0 2 0 0 0 3 0 0 0 2 
0 5 0 0 0 4 1 5 0 0 0 2 0 0 0 0 0 0 0 0 
0 0 0 5 0 0 1 2 0 0 0 3 1 2 5 0 0 0 0 0 
0 4 2 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 
0 0 0 4 0 0 0 0 4 0 0 0 0 0 0 1 4 0 2 0 
0 0 1 0 0 0 0 0 3 0 0 0 0 0 0 0 5 0 0 0 
0 0 0 0 0 0 0 5 0 4 0 0 0 0 0 2 0 0 2 0 



*/
