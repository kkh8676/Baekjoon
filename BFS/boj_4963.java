// 30 min
// 14360 KB, 140ms

import java.io.*;
import java.util.StringTokenizer;
import java.util.Queue;
import java.util.LinkedList;


class boj_4963{
	static int W,H;
	static int[][] board;
	static int[][] isV;
	static StringBuilder sb;

	static int[] dr = {-1,-1,0,1,1,1,0,-1};
	static int[] dc = {0,1,1,1,0,-1,-1,-1};

	static int ANSWER;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		sb = new StringBuilder();

		while(true){
			st = new StringTokenizer(br.readLine());
			W = Integer.parseInt(st.nextToken());
			H = Integer.parseInt(st.nextToken());

			if(W == 0 && H == 0) break;

			board = new int[H][W];
			isV   = new int[H][W];

			Queue<Integer> lq = new LinkedList<>();

			for (int r=0; r<H; r++) {
				st = new StringTokenizer(br.readLine());
				for (int c=0; c<W; c++) {
					board[r][c] = Integer.parseInt(st.nextToken());
					if(board[r][c] == 1){
						lq.add(r); lq.add(c);
					}
				}
			}// end of getting information

			// now lq has the all the land location
			ANSWER = 0;

			while(!lq.isEmpty()){
				int cr = lq.remove();
				int cc = lq.remove();
				//System.out.println("CR"+cr+" CC"+cc);
				if(isV[cr][cc] == 1) continue;

				BFS(cr,cc);
				ANSWER++;
				//printArr(isV);
				//System.out.println();
			}

			sb.append(ANSWER).append("\n");


		}// end of while loop

		System.out.println(sb.toString());
		br.close();

	}// end of main method

	public static void BFS(int r, int c){

		//System.out.println("BFS started at R"+r+" C"+c);
		Queue<Integer> q = new LinkedList<>();

		q.add(r); q.add(c);
		isV[r][c] = 1;

		while(!q.isEmpty()){
			int cr = q.remove();
			int cc = q.remove();

			for (int i=0; i<8; i++) {
				int nr = cr + dr[i];
				int nc = cc + dc[i];

				if(nr < 0 || nr >= H || nc < 0 || nc >= W || isV[nr][nc] == 1) continue;

				if(board[nr][nc] == 1 && isV[nr][nc] == 0){
					q.add(nr); q.add(nc);
					isV[nr][nc] = 1;	
				}
			}
		}// end of while loop

	}// end of BFS method

	public static void printArr(int[][] arr){
		for (int r=0; r<H; r++) {
			for (int c=0; c<W; c++) {
				System.out.print(arr[r][c]+" ");
			}
			System.out.println();
		}
	}// end of printArr method
}// end of class 