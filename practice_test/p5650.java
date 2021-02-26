// DP + DFS ???
// 2hr failed.......Shit..........TOO HARD......;;;;;
// 2hr Failed....
// 2hr 18min Success......Why??????????
// No Optimized version.......

import java.util.StringTokenizer;
import java.io.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

class p5650{
	static int T,N,W,B;
	static int[][] board;
	static int[][][] isV;
	static int[][][] answer;
	static int[][][] worm;
	static int[][] black;
	static int[] worm_cnt;
	static int[][] stop;

	static int ANSWER;

	static int[] dr = {-1,1,0,0};
	static int[] dc = {0,0,-1,1};

	// first index block number
	static int[][] changeDir = {{0,0,0,0},{1,3,0,2},{3,0,1,2},{2,0,3,1},{1,2,3,0},{1,0,3,2}};

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder ans = new StringBuilder();
		StringTokenizer st;
		T = Integer.parseInt(br.readLine());

		board = new int[100][100]; // starts with index 0
		//isV = new int[100][100][4]; // starts with index 0
		worm = new int[11][2][2]; // starts with index 6 .. 10
		worm_cnt = new int[11]; // 
		black = new int[5][2]; // starts with index 0
		//stop = new int[100][100];
		//answer = new int[100][100][4];

		for (int t=1; t<=T; t++) {
			N = Integer.parseInt(br.readLine());


			initialize();

			B = 0;
			

			for (int r=0; r<N; r++) {
				st = new StringTokenizer(br.readLine());
				for (int c=0; c<N; c++) {
					board[r][c] = Integer.parseInt(st.nextToken());

					if(board[r][c] == 1 || board[r][c] == 2 || board[r][c] == 3 || board[r][c] == 4 || board[r][c] == 5 ) continue;

					if(board[r][c] == -1){
						black[B][0] = r;
						black[B][1] = c;
						B++;
					}

					else if(board[r][c] != 0){
						int curCnt = worm_cnt[ board[r][c] ];
						
						worm[ board[r][c] ][curCnt][0] = r;
						worm[ board[r][c] ][curCnt][1] = c;
						worm_cnt[board[r][c]]++;
					}
				}
			}// end of getting information

			ANSWER = Integer.MIN_VALUE;

			for (int r=0; r<N; r++) {
				for (int c=0; c<N; c++) {
					for (int i=0; i<4; i++) {
						// No starts at worm black and block....
						if(board[r][c] ==0) BFS(r,c,i);
					}
				}
			}// end of getting all the way DFS
			
			ans.append("#").append(t).append(" ").append(ANSWER).append("\n");
		}// end of test case

		System.out.println(ans.toString());
		br.close();
	}// end of main method

	public static void initialize(){
		// 1 dimension
		Arrays.fill(worm_cnt, 0);

		// 2 dimension
		for (int r=0; r<100; r++) {
			Arrays.fill(board[r],0);
			// for (int c=0; c<100; c++) {
			// 	Arrays.fill(isV[r][c],0);
			// }
		}

		for (int r=0; r<5; r++) {
			Arrays.fill(black[r],0);
		}

		// 3 dimension last

		for (int r=0; r<11; r++) {
			for (int c=0; c<2; c++) {
				Arrays.fill(worm[r][c],0);
			}
		}
	}// end of initialize method

	public static int[] next_worm(int worm_num, int cr, int cc){
		int[] result = new int[2];
		if( worm[ worm_num ][0][0] == cr && worm[ worm_num ][0][1] == cc){
			result[0] = worm[ worm_num ][1][0];
			result[1] = worm[ worm_num ][1][1];
		} else{
			result[0] = worm[ worm_num ][0][0];
			result[1] = worm[ worm_num ][0][1];
		}

		return result;
	}// end of next worm method

	public static void BFS(int sr, int sc, int dir){
		// starts at sr sc
		// which that row and column is not visitied yet.
		// initialize stop arr
		// for (int r=0; r<100; r++) {
		// 	Arrays.fill(stop[r],0);
		// }
		//System.out.println("BFS at SR"+sr+" SC"+sc+ " D"+dir);
		Queue<Integer> q = new LinkedList<>();
		q.add(sr);
		q.add(sc);
		q.add(dir);
		q.add(0); // wall or block cnt


		// check that location is end!!
		//isV[sr][sc][dir] = 1;
		//stop[sr][sc]++;
		int stop_cnt = 0;

		while(!q.isEmpty()){
			int cr = q.remove();
			int cc = q.remove();
			int cd = q.remove();
			int score = q.remove();

			if(cr == sr && cc == sc) stop_cnt++;

			if(stop_cnt >= 2){
				ANSWER = ANSWER < score ? score : ANSWER;
				//answer[sr][sc][dir] = ANSWER;
				//System.out.println("ENDS SR"+sr+" SC"+sc+" STOP CNT"+stop_cnt);
				return;
			} 

			int nr = cr + dr[cd];
			int nc = cc + dc[cd];

			//System.out.println("Next R"+nr+" C"+nc+" with SCORE"+score+ " STOP CNT"+stop_cnt);

			// Out of bound
			if(nr < 0 || nr >= N || nc < 0 || nc >= N){
				q.add(nr);
				q.add(nc);
				int nd = changeDir[5][cd];
				q.add(nd);
				q.add(score+1);
				continue;
			}

			// already been there........
			// if(isV[nr][nc][cd] == 1){
			// 	ANSWER = ANSWER < score*2 + answer[nr][nc][cd]? score*2 + answer[nr][nc][cd] : ANSWER;
			// 	answer[sr][sc][dir] = ANSWER;
			// 	System.out.println("HERE! with ANSWER"+ANSWER+ " at R"+sr+" C"+sc);
			// 	return;
			// }

			// if next location is black hole, then return the method
			// 4. Black hole
			int next_type = board[nr][nc];
			if(next_type == -1){
				//isV[nr][nc][cd] = 1;
				ANSWER = ANSWER < score ? score : ANSWER;
				//answer[sr][sc][dir] = ANSWER;
				return;
			}

			// 4way
			// 1. Empty case
			// came here there not visited and not black hole
			if(next_type == 0){
				//isV[nr][nc][cd] = 1;
				q.add(nr);
				q.add(nc);
				q.add(cd);
				q.add(score);
				continue;
			}

			// 2. Block
			if(next_type == 1 || next_type == 2 || next_type == 3 || next_type == 4 || next_type == 5){
				int nd = changeDir[next_type][cd];
				q.add(nr);
				q.add(nc);
				q.add(nd);
				q.add(score+1);
				//isV[nr][nc][cd] = 1;
				continue;
			} else{
				// came here because 
				// -1 0 1 2 3 4 5 none
				// 6 7 8 9 10
				// 3. Worm hole

				int[] nextLoc = next_worm(next_type, nr,nc);
				q.add(nextLoc[0]);
				q.add(nextLoc[1]);
				q.add(cd);
				q.add(score);
				//isV[nr][nc][cd] = 1;
				continue;
			}


			
			


		}// end of while loop


	}// end of BFS method
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
