// 1 18min / 3hr 00 Failed with some cases.......
// Failed example?
// 40 / 50 Success.......

// Success in 57 min // 3hr 00 
// 110664 KB, 2005 ms........
// Optimizing process should be done here..........


import java.util.StringTokenizer;
import java.util.Queue;;
import java.util.LinkedList;
import java.io.*;
import java.util.Arrays;

class p5656_v2{
	static int T,W,H,N;
	static int[] seq;
	static int[][] board;
	static int[][] cBoard;

	static int[] dr = {-1,1,0,0};
	static int[] dc = {0,0,-1,1};

	static Queue<Integer> bq;
	static Queue<Integer> dq;

	static int ANSWER;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder answer = new StringBuilder();
		StringTokenizer st;

		// max H 15, max Width 12
		board = new int[15][12];
		cBoard = new int[15][12];

		bq = new LinkedList<>();
		dq = new LinkedList<>();

		// max N 4
		seq = new int[4];

		T = Integer.parseInt(br.readLine());
		for (int t=1; t<=T; t++) {
			st = new StringTokenizer(br.readLine());

			N = Integer.parseInt(st.nextToken());
			W = Integer.parseInt(st.nextToken());
			H = Integer.parseInt(st.nextToken());

			// Emptying board, cBoard, seq
			initialize();

			for (int h=H-1; h>=0; h--) {
				st = new StringTokenizer(br.readLine());
				for (int w=0; w<W; w++) {
					board[h][w] = Integer.parseInt(st.nextToken());
				}
			}// end of getting information
			ANSWER = Integer.MAX_VALUE;

			DFS(N,0);
			// N = 3;
			// seq[0] = 2;
			// seq[1] = 2;
			// seq[2] = 6;
			// total_process(seq);
			// System.out.println();
			// printCBoard();

			answer.append("#").append(t).append(" ").append(ANSWER).append("\n");


		}// end of Test case

		System.out.println(answer.toString());
		br.close();
	}// end of main method

	public static int break_brick(int tc){
		// finding first brick in the tc
		int tr = -1;
		for (int r=H-1; r>=0; r--) {
			if(cBoard[r][tc] != 0){
				tr = r;
				break;
			}
		}

		// There's no brick to break!
		// That means no change!
		if(tr == -1) return -1;

		// starting 
		bq.add(tr); bq.add(tc);

		while(!bq.isEmpty()){
			int cr = bq.remove(); int cc = bq.remove();
			int range = cBoard[cr][cc];
			cBoard[cr][cc] = 0;

			// direction.........
			for (int d=0; d<4; d++) {
				// current range........
				for (int l=1; l<range; l++) {
					int nr = cr + dr[d]*l;
					int nc = cc + dc[d]*l;

					if(nr < 0 || nr >= H || nc < 0 || nc >= W) continue;
					if(cBoard[nr][nc] == 0) continue;

					// came here because no out of bound and no empty
					bq.add(nr); bq.add(nc);	
				}
				
			}
		}// end of while loop

		return 1;
	}// end of break brick method

	public static void drop(int result){
		// there's no brick which can be broke just return.....
		if(result == -1) return;

		for (int c=0; c<W; c++) {
			for (int h=0; h<H; h++) {
				if(cBoard[h][c] != 0) dq.add(cBoard[h][c]);
			}

			// came here not zero element 
			// Empty That column
			for (int r=0; r<H; r++) {
				int next = 0;
				if(!dq.isEmpty()) next = dq.remove();

				cBoard[r][c] = next;
			}
		}
	}// end of drop method

	public static void total_process(int[] seq){
		// cBoard copying process
		for (int r=0; r<15; r++) {
			for (int c=0; c<12; c++) {
				cBoard[r][c] = board[r][c];
			}
		}// end of copying process

		for (int i=0; i<N; i++) {
			int targetC = seq[i];

			drop(break_brick(targetC));

		}// end of targetC for loop

		check();
	}// end of total process

	public static void check(){
		int cnt = 0;
		for (int r=0; r<H; r++) {
			for (int c=0; c<W; c++) {
				if(cBoard[r][c]!=0) cnt++;
			}
		}// end of counting

		ANSWER = ANSWER > cnt ? cnt : ANSWER;
	}// end of check method

	public static void DFS(int capa, int round){
		// ending condition
		if(capa == round){
			// for (int i=0; i<N; i++) {
			// 	System.out.print(seq[i]+" ");
			// }
			// System.out.println();
			total_process(seq);
			return;
		}// end of ending condition

		for (int i=0; i<W; i++) {
			seq[round] = i;
			DFS(capa,round+1);
		}
	}// end of DFS method

	public static void initialize(){
		for (int h=0; h<15; h++) {
			Arrays.fill(board[h],0);
			Arrays.fill(cBoard[h],0);
		}

		for (int i=0; i<4; i++) {
			Arrays.fill(seq,0);
		}

		bq.clear();
		dq.clear();
	}// end of initialize method

	public static void printCBoard(){
		for (int r=H-1; r>=0; r--) {
			for (int c=0; c<W; c++) {
				System.out.print(cBoard[r][c]+" ");
			}
			System.out.println();
		}
	}
}// end of class

/*
5
3 10 10
0 0 0 0 0 0 0 0 0 0
1 0 1 0 1 0 0 0 0 0
1 0 3 0 1 1 0 0 0 1
1 1 1 0 1 2 0 0 0 9
1 1 4 0 1 1 0 0 1 1
1 1 4 1 1 1 2 1 1 1
1 1 5 1 1 1 1 2 1 1
1 1 6 1 1 1 1 1 2 1
1 1 1 1 1 1 1 1 1 5
1 1 7 1 1 1 1 1 1 1
2 9 10
0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0
0 1 0 0 0 0 0 0 0
0 1 0 0 0 0 0 0 0
1 1 0 0 1 0 0 0 0
1 1 0 1 1 1 0 1 0
1 1 0 1 1 1 0 1 0
1 1 1 1 1 1 1 1 0
1 1 3 1 6 1 1 1 1
1 1 1 1 1 1 1 1 1
3 6 7
1 1 0 0 0 0
1 1 0 0 1 0
1 1 0 0 4 0
4 1 0 0 1 0
1 5 1 0 1 6
1 2 8 1 1 6
1 1 1 9 2 1
4 4 15
0 0 0 0 
0 0 0 0 
0 0 0 0 
1 0 0 0 
1 0 0 0 
1 0 0 0 
1 0 0 0 
1 0 5 0 
1 1 1 0 
1 1 1 9 
1 1 1 1 
1 6 1 2 
1 1 1 5 
1 1 1 1 
2 1 1 2 
4 12 15
9 9 9 9 9 9 9 9 9 9 9 9
9 9 9 9 9 9 9 9 9 9 9 9
9 9 9 9 9 9 9 9 9 9 9 9
9 9 9 9 9 9 9 9 9 9 9 9
9 9 9 9 9 9 9 9 9 9 9 9
9 9 9 9 9 9 9 9 9 9 9 9
9 9 9 9 9 9 9 9 9 9 9 9
9 9 9 9 9 9 9 9 9 9 9 9
9 9 9 9 9 9 9 9 9 9 9 9
9 9 9 9 9 9 9 9 9 9 9 9
9 9 9 9 9 9 9 9 9 9 9 9
9 9 9 9 9 9 9 9 9 9 9 9
9 9 9 9 9 9 9 9 9 9 9 9
9 9 9 9 9 9 9 9 9 9 9 9
9 9 9 9 9 9 9 9 9 9 9 9
*/