// 45min failed......

// 46 failed.....
// 20/50 success......
// 30 example???

// 53 min Success....
// 88028 kb, 503ms


import java.io.*;
import java.util.StringTokenizer;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Arrays;

class p5653_v2{
	static int T,N,M,K;
	static Cell[][] board;

	static int[] dr = {-1,1,0,0};
	static int[] dc = {0,0,-1,1};

	static int OFFSET = 500;

	static Queue<Cell> cq;

	static int ANSWER;
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder answer = new StringBuilder();
		StringTokenizer st;
		T = Integer.parseInt(br.readLine());


		board = new Cell[1000][1000];
		cq = new LinkedList<>();

		for (int t=1; t<=T; t++) {
			st = new StringTokenizer(br.readLine());

			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());
			K = Integer.parseInt(st.nextToken());

			// Initializing process, Empty Board array......
			initialize();

			for (int r=0; r<N; r++) {
				st = new StringTokenizer(br.readLine());
				for (int c=0; c<M; c++) {
					int curLife = Integer.parseInt(st.nextToken());
					Cell adding = null;
					if(curLife != 0) adding = new Cell(OFFSET+r, OFFSET+c, curLife, 0);
					board[ OFFSET + r ][ OFFSET + c ] = adding;
					if(adding != null) cq.add(adding);
				}
			}// end of getting information

			int ROUND = K;
			while(ROUND-- > 0){ // For K time for reproducing
				// Some process here.......
				int curSize = cq.size();

				while(curSize-- > 0){
					// reproducing algorithm here......
					Cell removed = cq.remove();

					// 3 way .....
					// 1. inactivated status, 3. activated status but not reproducing
					if(removed.hp > removed.LIFE || removed.hp < removed.LIFE){
						removed.time++;
						removed.hp--;
						if(removed.hp != 0) cq.add(removed);
						continue;
					}

					// 2. activated status : reproducing time
					if(removed.hp == removed.LIFE){
						int prevT = removed.time;
						removed.time++;
						removed.hp--;
						if(removed.hp != 0) cq.add(removed);

						// Looking 4 way 
						int cr = removed.r; int cc = removed.c; int cl = removed.LIFE;
						for (int d=0; d<4; d++) {
							int nr = cr + dr[d];
							int nc = cc + dc[d];

							if(board[nr][nc] == null){
								Cell adding = new Cell(nr,nc,cl,prevT+1);
								board[nr][nc] = adding;
								cq.add(adding);
								continue;
							} else{
								// came here because nr,nc location has cell already.........
								Cell exist = board[nr][nc]; // not null
								if(exist.hp == exist.LIFE*2 && exist.time == prevT+1 && exist.LIFE < removed.LIFE){
									exist.LIFE = removed.LIFE;
									exist.hp = exist.LIFE*2;
									continue;
								}
							}

						}// end of 4 way for loop

					}// end of case 2 if condition

				}// end of current cq removing time!

			}// end of ROUND while loop

			ANSWER = cq.size();

			answer.append("#").append(t).append(" ").append(ANSWER).append("\n");

		}// end of Test case

		System.out.println(answer.toString());
		br.close();
	}// end of main method

	public static void initialize(){
		for (int r=0; r<1000; r++) {
			Arrays.fill(board[r],null);
		}

		cq.clear();
	}// end of initialize method
}// end of class

class Cell{
	int r,c,LIFE,BIRTH,time,hp;

	public Cell(int r, int c, int LIFE, int time){
		this.r = r;
		this.c = c;
		this.LIFE = LIFE;
		this.time = time;
		this.BIRTH = BIRTH;
		this.hp = LIFE*2;
	}// end of constructor
}

/*
5
2 2 10
1 1
0 2
5 5 19
3 2 0 3 0 
0 3 0 0 0 
0 0 0 0 0 
0 0 1 0 0 
0 0 0 0 2
9 10 37
0 0 0 0 0 0 0 0 3 0 
0 0 0 0 0 0 0 0 5 3 
0 0 2 0 0 0 0 4 0 0 
3 0 0 0 0 0 4 0 0 0 
0 0 0 0 0 3 5 0 0 2 
0 0 0 0 0 0 0 0 0 5 
0 0 0 0 0 0 0 0 2 3 
0 0 0 0 0 0 0 0 0 0 
0 0 2 2 0 0 0 0 0 0 
20 18 83
0 0 0 0 0 0 0 0 0 0 0 2 0 0 6 0 0 0 
0 0 0 0 0 0 0 3 0 0 0 0 0 0 0 0 0 0 
0 0 0 6 0 0 0 0 0 0 0 0 0 0 2 0 3 0 
4 0 2 0 0 0 0 0 0 0 0 0 5 0 0 0 0 3 
0 0 0 0 0 5 4 4 6 0 0 0 0 0 0 0 0 0 
5 0 0 0 0 0 2 0 2 6 0 0 0 0 0 4 0 0 
4 0 0 3 0 0 0 0 0 0 0 3 0 0 0 5 0 0 
0 0 0 0 0 0 0 2 2 0 0 0 0 3 0 0 0 0 
0 0 0 0 5 0 0 0 3 0 3 0 0 4 0 0 0 0 
0 0 0 0 6 0 0 0 0 0 0 0 0 0 0 0 0 0 
0 0 0 0 6 0 2 0 0 0 0 0 3 0 0 0 3 0 
0 5 2 0 0 0 0 4 0 0 0 0 0 0 0 0 0 0 
3 0 0 0 0 0 0 0 6 0 2 0 5 0 0 0 0 0 
5 0 0 0 4 0 0 0 0 0 0 0 0 0 0 0 0 0 
0 6 0 5 0 0 0 0 0 0 0 0 0 0 0 0 0 0 
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 
0 0 0 4 0 0 0 0 0 0 0 0 0 0 2 0 0 0 
0 0 3 4 5 0 0 0 0 0 0 0 0 0 0 6 0 0 
2 0 0 0 0 3 0 0 0 0 0 0 0 0 0 5 0 0 
0 0 0 0 0 0 0 0 0 3 6 2 0 0 0 0 0 0 
49 43 283
0 6 0 0 0 10 0 0 0 0 0 0 9 0 0 0 0 0 0 0 0 0 0 4 0 8 0 0 0 0 0 0 0 0 0 0 0 0 0 0 4 0 0 
0 5 0 0 0 2 0 0 0 0 0 0 8 0 0 8 0 0 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 
0 0 3 7 0 0 0 0 0 0 9 0 1 0 5 0 0 1 0 0 0 0 0 0 0 0 0 0 0 9 0 7 0 0 0 0 0 0 0 0 1 0 0 
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 7 0 8 0 0 0 0 0 0 0 0 0 3 0 0 0 6 0 0 0 0 6 0 0 0 0 0 0 
7 0 0 0 5 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 7 0 0 0 0 8 0 0 0 0 0 0 0 0 1 0 0 
0 9 0 0 0 0 0 0 0 0 9 6 0 2 0 0 0 0 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 9 
0 0 0 0 1 0 0 0 0 0 0 3 0 0 0 0 0 0 5 0 10 0 0 0 0 0 0 0 0 0 9 4 0 0 0 0 0 0 9 0 9 0 8 
0 0 0 0 0 0 0 0 0 7 0 0 0 0 9 0 0 0 0 0 0 0 0 0 0 8 0 0 0 0 0 0 0 0 0 0 0 0 0 3 2 0 1 
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 3 3 0 0 4 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 2 0 0 0 0 0 
0 0 0 0 0 0 0 0 0 7 0 0 0 2 0 0 0 0 0 0 8 0 0 0 0 10 0 0 1 7 0 8 0 0 0 0 0 0 0 0 0 0 0 
0 0 0 0 0 0 0 2 0 0 9 0 0 0 0 0 8 0 0 0 0 0 4 0 6 0 0 0 0 0 0 6 0 0 0 0 0 0 0 0 0 0 0 
1 0 0 0 0 0 0 6 0 2 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 6 0 5 0 0 0 0 0 0 7 0 0 0 
8 0 0 0 0 0 0 0 0 0 0 0 0 0 0 10 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 9 0 0 0 0 0 0 5 8 
0 0 0 10 0 9 0 8 0 0 0 0 0 0 2 9 0 0 0 7 2 7 0 7 0 0 0 0 2 0 4 3 0 0 0 0 0 0 0 0 0 2 0 
1 0 0 0 0 0 0 4 9 1 0 0 0 0 0 0 0 0 0 5 0 0 0 0 6 0 0 5 0 0 0 0 0 0 0 0 0 0 0 3 3 0 0 
0 0 0 0 0 0 0 0 0 0 0 0 1 0 3 1 10 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 5 0 1 0 0 0 9 0 0 
0 0 0 0 0 0 0 10 0 0 0 0 0 0 9 6 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 7 0 0 1 3 0 0 
0 0 0 0 0 0 6 0 0 0 1 0 0 2 0 0 0 0 9 0 0 0 0 0 0 0 3 0 0 0 0 0 0 0 0 0 0 0 0 7 7 0 0 
0 0 0 0 0 2 0 0 0 0 0 0 0 0 0 0 0 5 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 10 
0 0 0 0 9 0 8 0 0 0 0 0 0 4 0 0 0 10 8 0 0 0 0 0 0 10 0 0 0 5 0 0 0 0 0 0 0 1 0 0 10 4 7 
0 0 0 0 0 0 0 0 0 0 0 6 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 8 0 0 0 4 0 7 0 0 0 0 0 3 0 
0 0 0 0 5 0 3 0 0 0 0 0 0 0 8 1 0 0 7 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 
0 0 9 0 1 0 0 0 0 10 7 0 0 0 0 0 2 0 0 7 0 0 0 0 0 0 0 7 0 0 4 0 0 0 0 0 0 0 0 0 0 0 0 
0 0 0 0 0 0 0 8 2 0 4 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 3 0 0 8 0 0 7 0 2 0 0 0 0 
0 8 0 0 0 0 0 0 0 0 3 0 0 1 0 5 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 8 0 0 5 0 9 0 0 0 0 0 
0 0 0 0 0 0 0 0 3 5 0 0 1 0 4 0 0 0 0 9 0 0 0 0 0 0 0 0 0 0 5 0 0 4 0 0 0 0 10 8 0 0 0 
0 0 0 0 0 0 0 0 4 0 0 7 10 0 10 0 0 0 0 0 0 0 0 0 0 9 0 0 0 0 0 0 0 8 3 9 6 7 0 0 0 0 2 
0 0 0 0 0 0 0 0 0 0 0 0 0 5 0 0 0 8 7 10 0 0 0 0 0 0 6 0 0 0 5 0 0 0 0 0 0 0 0 0 0 10 0 
7 0 0 0 8 0 0 0 8 9 0 0 0 0 0 0 9 0 0 0 0 0 0 0 0 6 0 0 5 0 0 0 0 0 0 0 0 0 0 3 0 0 0 
0 0 0 0 0 0 0 0 0 0 0 6 0 6 0 0 0 0 3 0 0 5 3 0 0 0 0 1 9 0 6 0 0 0 0 0 0 0 0 0 0 0 0 
0 0 0 7 2 0 0 0 0 0 0 0 0 0 0 5 0 0 0 0 8 0 0 0 2 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 7 6 
0 9 0 0 0 0 0 0 0 0 0 3 0 9 2 0 0 0 4 0 2 9 2 0 6 0 0 0 0 0 0 0 0 0 0 0 0 0 8 0 0 0 0 
0 0 0 3 0 1 0 0 8 0 0 0 0 0 0 0 0 0 0 0 0 3 0 0 0 0 0 7 0 6 0 0 0 0 0 7 0 0 0 0 4 7 10 
1 0 0 0 5 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 5 0 8 0 0 0 0 0 0 0 0 3 9 2 
5 0 0 0 0 0 0 0 0 0 1 0 0 1 0 0 0 0 6 0 0 0 0 0 0 0 0 9 0 8 0 0 0 0 0 0 0 3 0 0 0 0 0 
0 0 0 0 7 0 10 0 0 0 6 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 3 0 0 0 0 0 0 0 0 0 0 0 8 2 3 0 0 
0 0 0 0 0 5 0 0 6 0 0 3 0 0 0 0 0 8 0 0 6 0 0 0 8 0 0 5 0 0 0 0 8 0 0 0 0 0 0 0 5 0 1 
7 0 9 0 7 0 0 9 0 0 0 0 4 0 0 0 0 0 0 8 1 0 4 0 0 0 0 0 0 0 0 0 4 7 0 0 8 0 0 0 0 0 0 
0 0 0 1 0 0 0 0 0 0 0 0 6 0 0 0 0 0 0 0 4 0 0 0 0 0 0 0 0 0 2 3 1 0 0 4 0 3 10 0 0 0 5 
0 0 4 0 0 0 0 0 0 4 4 0 0 0 8 0 4 0 2 0 8 0 0 0 0 0 0 0 9 0 0 0 0 5 0 0 0 0 0 0 0 0 0 
0 0 8 0 7 0 0 0 0 0 0 0 0 0 0 4 0 0 0 0 0 0 0 0 0 0 0 0 0 0 2 6 0 0 0 0 1 0 0 0 0 4 3 
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0 10 3 0 0 0 0 0 3 0 
0 0 2 0 0 0 0 0 8 5 0 0 0 0 0 0 0 0 0 0 0 0 4 8 0 0 0 0 0 1 0 5 0 0 0 0 2 3 9 0 0 0 0 
0 5 8 9 0 0 0 0 0 4 0 0 0 10 0 0 0 1 0 0 0 0 0 10 0 7 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 
6 0 0 0 0 0 10 0 5 0 0 0 0 0 0 0 0 0 0 0 6 0 0 0 0 0 0 0 0 10 0 0 0 0 0 0 0 0 0 0 0 0 0 
0 0 0 0 0 0 9 0 0 0 0 0 0 2 0 0 0 4 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 3 0 0 0 0 0 2 4 0 
0 3 0 0 0 0 0 0 0 0 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0 1 0 0 0 0 0 0 0 0 1 0 0 0 
0 0 0 9 0 0 0 0 4 0 0 0 0 0 2 0 0 0 0 0 0 0 0 0 9 0 0 0 0 0 0 0 0 0 0 0 5 0 0 0 9 2 0 
0 0 0 0 0 2 0 0 0 0 0 0 10 0 0 0 0 0 2 0 0 0 8 0 0 0 0 0 0 10 0 0 0 0 0 0 7 0 0 0 0 0 0 
*/