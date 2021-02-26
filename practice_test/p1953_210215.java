// 35min Success....
// 25748 kb, 135ms......
// How can i modulize the BFS method.....?

// in 44min Optimize modulized version Success

import java.util.StringTokenizer;
import java.io.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Arrays;

class p1953_210215{	
	static int T,N,M,R,C, L;
	static int[][] board, isV;

	static int[] dr = {-1,1,0,0};
	static int[] dc = {0,0,-1,1};

	static Queue<Integer> q;

	static int[][] dirType = {{1,2,4,7},{1,2,5,6},{1,3,6,7},{1,3,4,5}};
	static int[][] dirType_opposite = {{1,2,5,6},{1,2,4,7},{1,3,4,5},{1,3,6,7}};

	static int ANSWER;
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder answer = new StringBuilder();
		board = new int[50][50];
		isV = new int[50][50];
		q = new LinkedList<>();

		T = Integer.parseInt(br.readLine());
		for (int t=1; t<=T; t++) {
			// initialize the board array
			for (int r=0; r<50; r++) {
				Arrays.fill(board[r],0);
				Arrays.fill(isV[r],0);
			}

			q.clear();

			st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());
			R = Integer.parseInt(st.nextToken());
			C = Integer.parseInt(st.nextToken());
			L = Integer.parseInt(st.nextToken());

			for (int r=0; r<N; r++) {
				st = new StringTokenizer(br.readLine());
				for (int c=0; c<M; c++) {
					board[r][c] = Integer.parseInt(st.nextToken());
				}
			}// end of getting information

			ANSWER = 0;

			// Solving algorithm
			BFS();
			
			answer.append("#").append(t).append(" ").append(ANSWER).append("\n");
		}// end of test case

		System.out.println(answer.toString());
		br.close();
	}// end of main method

	public static void BFS(){
		q.add(R); q.add(C); q.add(1);
		isV[R][C] = 1;

		ANSWER++;

		while(!q.isEmpty()){
			int cr = q.remove(); int cc = q.remove(); int ct = q.remove();
			if(ct > L -1) continue;

			int type = board[cr][cc];

			// there is total 4 way
			for (int d=0; d<4; d++) {
				int nr = cr + dr[d];
				int nc = cc + dc[d];
				if(nr < 0 || nr >= N || nc < 0 || nc >= M || isV[nr][nc] == 1 || board[nr][nc] == 0) continue;

				int next_type = board[nr][nc];
				if((type == dirType[d][0] || type == dirType[d][1] || type == dirType[d][2] || type == dirType[d][3]) &&  (next_type == dirType_opposite[d][0] || next_type == dirType_opposite[d][1] || next_type == dirType_opposite[d][2] || next_type == dirType_opposite[d][3])){
					q.add(nr); q.add(nc); q.add(ct+1);
					isV[nr][nc] = 1;
					ANSWER++;
				}
			}

		}// end of while loop

	}// end of BFS method

	public static void print_isV(){
		for (int r=0; r<N; r++) {
			for (int c=0; c<M; c++) {
				System.out.print(isV[r][c]+" ");
			}
			System.out.println();
		}
	}// end of print_isV method
}// end of class