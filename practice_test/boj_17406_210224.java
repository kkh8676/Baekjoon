import java.util.StringTokenizer;
import java.io.*;
import java.lang.Math;
import java.util.Queue;
import java.util.LinkedList;

class boj_17406_210224{
	static int N,M,K;
	static int[][] board;
	static int[] isV;
	static Pair[] pairs;

	static int[] dr = {-1,0,1,0};
	static int[] dc = {0,1,0,-1};

	static Queue<Integer> q;

	static int ANSWER;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());

		board = new int[N+1][M+1];
		pairs = new Pair[K+1];
		isV = new int[K+1];
		q = new LinkedList<>();

		for (int r=1; r<=N; r++) {
			st = new StringTokenizer(br.readLine());
			for (int c=1; c<=M; c++) {
				board[r][c] = Integer.parseInt(st.nextToken());
			}
		}// end of getting information

		for (int k=1; k<=K; k++) {
			st = new StringTokenizer(br.readLine());

			int r = Integer.parseInt(st.nextToken());
			int c = Integer.parseInt(st.nextToken());
			int s = Integer.parseInt(st.nextToken());

			Pair adding = new Pair(r,c,s);
			pairs[k] = adding;
		}// end of getting pair information

		ANSWER = Integer.MAX_VALUE;

		// Solving algorithm here.....
		DFS(K,0);
		// System.out.println();
		// rotate(3,4,2);
		// print_board();

		// System.out.println();
		// rotate(4,2,1);
		// print_board();


		System.out.println(ANSWER);
		br.close();
	}// end of main method

	public static void print_board(){
		for (int r=1; r<=N; r++) {
			for (int c=1; c<=M; c++) {
				System.out.print(board[r][c]+" ");
			}
			System.out.println();
		}
	}

	public static void DFS(int capa, int round){
		if(capa == round){
			// ANSWER update process in that method
			check_array();
			return;
		}

		// saving previous board status
		int[][] prev = new int[N+1][M+1];
		copyArr(board, prev);

		// visiting next vertex using backtracking and check isV array
		for (int i=1; i<=K; i++) {
			if(isV[i]==1) continue;

			// came here because that index is not visited!
			isV[i] = 1;
			Pair curPair = pairs[i];
			rotate(curPair.r, curPair.c, curPair.s);
			DFS(capa, round+1);

			// BackTracking process
			isV[i] = 0;
			copyArr(prev, board);
		}
	}// end of DFS method

	public static void rotate(int r, int c, int s){
		int[][] rotating = new int[2*s+1][2*s+1];

		// // copying rotating part into rotating arr
		// for (int rr=0; rr<2*s+1; rr++) {
		// 	for (int cc=0; cc<2*s+1; cc++) {
		// 		rotating[rr][cc] = board[r-s+rr][c-s+cc];
		// 	}
		// }

		// // rotating in clockwise 
		// for (int rr=0; rr<2*s+1; rr++) {
		// 	for (int cc=0; cc<2*s+1; cc++) {
		// 		board[r-s+cc][c+s-rr] = rotating[rr][cc];
		// 	}
		// }

		for (int ss=1; ss<=s; ss++) {
			int sr = r-ss; int sc = c-ss;
			int[] qArr = new int[8*ss];
			int d = 1;
			int nr = sr;
			int nc = sc;
			q.clear();
			for (int sss=0; sss<8*ss; sss++) {
				nr = nr + dr[d];
				nc = nc + dc[d];

				if(Math.abs(nr - r) > ss || Math.abs(nc - c) > ss){
					sss--;
					nr -= dr[d];
					nc -= dc[d];
					d = (d+1)%4;
					continue;
				}

				qArr[sss] = board[nr][nc];
				q.add(nr); q.add(nc);
			}

			// one step forward!
			int index = 0;
			while(!q.isEmpty()){
				int cr = q.remove();
				int cc = q.remove();

				board[cr][cc] = qArr[(index+(8*ss)-1)%(8*ss)];
				index++;
			}

		}// end of ss 
	}// end of rotate method

	public static void check_array(){
		int min_sum = Integer.MAX_VALUE;
		for (int r=1; r<=N; r++) {
			int r_sum = 0;
			for (int c=1; c<=M; c++) {
				r_sum += board[r][c];
			}
			min_sum = min_sum > r_sum ? r_sum : min_sum;
		}// end og getting min_num 

		// update ANSWER
		ANSWER = ANSWER > min_sum? min_sum : ANSWER;
	}// end of check array method

	public static void copyArr(int[][] arr1, int[][] arr2){
		for (int r=1; r<=N; r++) {
			for (int c=1; c<=M; c++) {
				arr2[r][c] = arr1[r][c];
			}
		}
	}// end of copyArr method
}// end of class



class Pair{
	int r,c,s;

	Pair(int r, int c, int s){
		this.r = r;
		this.c = c;
		this.s = s;
	}// end of constructor
}// end of class Pair