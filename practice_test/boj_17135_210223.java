// 55 min Success...
// 34680 kb, 512ms

import java.util.StringTokenizer;
import java.io.*;
import java.util.PriorityQueue;
import java.lang.Math;
import java.util.Queue;
import java.util.LinkedList;

class boj_17135_210223{
	static int N,M,D;
	static int[][] original_board;
	static int[][] board;
	static int[] archer;
	static int ENEMY;

	static int ANSWER;

	static Queue<Integer> target;
	static PriorityQueue<Loc> pq;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		D = Integer.parseInt(st.nextToken());

		original_board = new int[N][M];
		board = new int[N][M];
		archer = new int[3];
		target = new LinkedList<>();
		pq = new PriorityQueue<Loc>();

		for (int r=0; r<N; r++) {
			st = new StringTokenizer(br.readLine());
			for (int c=0; c<M; c++) {
				original_board[r][c] = Integer.parseInt(st.nextToken());
				if(original_board[r][c] == 1) ENEMY++;
			}
		}// end of getting information

		ANSWER = Integer.MIN_VALUE;

		// solving algorithm here!
		// placing archer in castle.
		DFS(3,0,0);
		// archer[0] = 0;
		// archer[1] = 2;
		// archer[2] = 4;

		// copyArr(original_board, board);
		// simulation();

		System.out.println(ANSWER);
		br.close();


	}// end of main method

	public static void simulation(){
		int killed = 0;
		int reach_castle = 0;

		
		
		// using archer array and board arr
		while(reach_castle + killed < ENEMY){

			// attack enemy! update killed......
			// 우선순위가 잘못됨!
			for (int i=0; i<3; i++) {
				boolean found = false;
				for (int r=N-1; r>=0; r--) {
					for (int c=0; c<M; c++) {
						if(Math.abs(r - N) + Math.abs(c - archer[i]) > D) continue;

						if(board[r][c] == 0) continue;
						
						if(board[r][c] == 1){
							pq.add(new Loc(r,c,Math.abs(r - N) + Math.abs(c - archer[i])));
						}
					}
				}
				// adding total Queue from priorityQueue
				if(pq.size() == 0) continue;
				Loc target_decided = pq.remove();
				target.add(target_decided.r); target.add(target_decided.c);
				pq.clear();
			}// end of finding enemy

			while(!target.isEmpty()){
				int rr = target.remove(); int rc = target.remove();
				if(board[rr][rc] == 1) killed++;
				board[rr][rc] = 0;
			}

			// enemy moves! update reach_castle....
			// last row
			for (int c=0; c<M; c++) {
				if(board[N-1][c] == 1){
					reach_castle++;
					board[N-1][c] = 0;
				}
			}

			for (int r=N-2; r>=0; r--) {
				for (int c=0; c<M; c++) {
					board[r+1][c] = board[r][c];
				}
			}

			// first row
			for (int c=0; c<M; c++) {
				board[0][c] = 0;
			}

			// System.out.println();
			// System.out.println("Killed"+killed + " reached"+reach_castle);
			// print_board();


		}// end of game!


		// lastly, update the ANSWER
		ANSWER = ANSWER < killed? killed : ANSWER;
	}// end of simulation

	public static void print_board(){
		for (int r=0; r<N; r++) {
			for (int c=0; c<M; c++) {
				System.out.print(board[r][c]+" ");
			}
			System.out.println();
		}
	}// end of print_board method

	public static void DFS(int capa, int round, int start){
		if(capa == round){
			copyArr(original_board, board);
			simulation();
			// for (int i=0; i<3; i++) {
			// 	System.out.print(archer[i]+" ");
			// }
			// System.out.println();
			return;
		}

		// visit next vertex
		for (int n=start; n<M; n++) {
			archer[round] = n;
			DFS(capa, round+1, n+1);
		}
	}// end of DFS method

	public static void copyArr(int[][] arr1, int[][] arr2){
		for (int r=0; r<N; r++) {
			for (int c=0; c<M; c++) {
				arr2[r][c] = arr1[r][c];
			}
		}
	}// end of copyArr method
}// end of class 

class Loc implements Comparable<Loc>{
	int r,c,d;

	Loc(int r, int c, int d){
		this.r = r;
		this.c = c;
		this.d = d;
	}// end of constructor

	@Override
	public int compareTo(Loc loc2){
		if(this.d != loc2.d) return Integer.compare(this.d, loc2.d);
		else return Integer.compare(this.c, loc2.c);
	}
}// end of class Enemy