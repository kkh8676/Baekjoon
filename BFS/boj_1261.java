// 20 min
// 44716 KB, 344 ms

import java.io.*;
import java.util.StringTokenizer;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Arrays;

class boj_1261{
	static int R,C;
	static int[][] board;
	static int[][] d;

	static int[] dr = {-1,1,0,0};
	static int[] dc = {0,0,-1,1};

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		C = Integer.parseInt(st.nextToken());
		R = Integer.parseInt(st.nextToken());

		board = new int[R][C];
		d     = new int[R][C];

		for (int r=0; r<R; r++) {
			String[] str = br.readLine().split("");
			for (int c=0; c<C; c++) {
				board[r][c] = Integer.parseInt(str[c]);
			}
		}// end of getting information

		BFS(0,0);

		System.out.println(d[R-1][C-1]);
	}// end of main method

	public static void BFS(int r, int c){
		// Initializing the distance information
		for (int row=0; row<R; row++) {
			Arrays.fill(d[row], Integer.MAX_VALUE);
		}

		Queue<Integer> q = new LinkedList<>();

		q.add(r); q.add(c); q.add(0);
		d[r][c] = 0;

		while(!q.isEmpty()){
			int cr = q.remove();
			int cc = q.remove();
			int ct = q.remove();

			for (int i=0; i<4; i++) {
				int nr = cr + dr[i];
				int nc = cc + dc[i];

				if(nr < 0 || nr >= R || nc < 0 || nc >= C ) continue;

				int nt;
				if(board[nr][nc] == 1) nt = ct + 1;
				else nt = ct;

				if(d[nr][nc] <= nt) continue;

				q.add(nr); q.add(nc); q.add(nt);
				d[nr][nc] = nt;
			}
		}// end of while loop


	}// end of BFS method
}// end of class 
