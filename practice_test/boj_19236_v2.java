// 1hr 50min failed......
// 4 %?
// 2hr Failed.......
import java.util.StringTokenizer;
import java.io.*;

class boj_19236_v2{
	// static Fish[][] board;
	// static Fish[] fishes;

	static int[][] board; // last index 
	static int[][] fishes; // first index size number, second index 0 dir , 1 dead or not


	static int[] dr = {-1,-1,0,1,1,1,0,-1};
	static int[] dc = {0,-1,-1,-1,0,1,1,1};

	static int ANSWER;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st ;

		//board = new Fish[4][4];
		//fishes = new Fish[17];

		board = new int[4][4];
		fishes = new int[17][4]; // 0 row, 1 col, 2 dir, 3 dead or not

		for (int r=0; r<4; r++) {
			st = new StringTokenizer(br.readLine());
			for (int c=0; c<4; c++) {
				int num = Integer.parseInt(st.nextToken());
				int dir = Integer.parseInt(st.nextToken())-1;

				//Fish newFish = new Fish(r,c,dir,num);
				board[r][c] = num; 
				fishes[num][0] = r; fishes[num][1] = c; fishes[num][2] = dir; fishes[num][3] = 0;

			}
		}// end of getting information
		//print_fish();
		// initialize the shark!!
		//Fish first = board[0][0];
		 // 1 direction
		int fSize = board[0][0]; // 0 size
		int fDir = fishes[fSize][2];
		ANSWER += fSize; // plus
		//first.dead = 1;
		
		// shark state update 
		fishes[0][0] = 0;
		fishes[0][1] = 0;
		fishes[0][2] = fDir;

		// fish state update
		fishes[fSize][3] = 1;
		fishes[fSize][0] = -1;
		fishes[fSize][1] = -1;

		// board state update
		board[0][0]= -1;
		


		// fish_move();
		// System.out.println();
		// print_fish();
		DFS(fSize);


		System.out.println(ANSWER);

	}// end of main method

	public static void DFS(int cnt){
		// System.out.println("after shark moved");
		// print_fish();
		
		fish_move();

		// System.out.println();
		// System.out.println("Fish moving over");
		// print_fish();
		// System.out.println();

		int[][] prev_board = new int[4][4];
		int[][] prev_fish = new int[17][4];

		for (int r=0; r<4; r++) {
			for (int c=0; c<4; c++) {
				prev_board[r][c] = board[r][c];
				}
		}// end of copying board process

		for (int i=0; i<17; i++) {
			prev_fish[i][0] = fishes[i][0];
			prev_fish[i][1] = fishes[i][1];
			prev_fish[i][2] = fishes[i][2];
			prev_fish[i][3] = fishes[i][3];
		}// end of copying fish state process

		// System.out.println("BackTracking Array saved in" + prev_board);

		int cr = fishes[0][0]; // row
		int cc = fishes[0][1]; // column
		int cd = fishes[0][2]; // direction

		for (int i=1; i<4; i++) {
			// check if shark can move to that location
			int nr = cr + dr[cd]*i;
			int nc = cc + dc[cd]*i;

			if(nr < 0 || nr >= 4 || nc < 0 || nc >= 4) continue;
			if(board[nr][nc] <= 0) continue;

			// System.out.println("Shark move to R"+nr+" C"+nc+ " from R"+cr+" C"+cc);

			// if can, shark move and eat
			int targetFish = board[nr][nc];
			int targetFishDir = fishes[targetFish][2];
			
			// shark state update
			fishes[0][0] = nr; fishes[0][1] = nc; fishes[0][2] = fishes[targetFish][2];

			// fish state update
			fishes[targetFish][3] = 1;

			// map state update
			board[nr][nc] = -1;
			
			board[cr][cc] = 0;

			// DFS 
			DFS(cnt+targetFish);
			// System.out.println();
			// System.out.println("BackTracking process has been done!");
			
			// // shark state backtrack
			// fishes[0][0] = cr; fishes[0][1] = cc; fishes[0][2] = cd;

			// // fish state backtrack
			// fishes[targetFish][0] = nr; fishes[targetFish][1] = nc; fishes[targetFish][3] = 0;

			// shark and fish state backtrack
			//fishes = prev_fish;
			for (int r=0; r<4; r++) {
				for (int c=0; c<4; c++) {
					board[r][c] = prev_board[r][c];
				}
			}

			for (int s=0; s<17; s++) {
				fishes[s][0] = prev_fish[s][0];
				fishes[s][1] = prev_fish[s][1];
				fishes[s][2] = prev_fish[s][2];
				fishes[s][3] = prev_fish[s][3];
			}

			// // map state backtrack
			// board[cr][cc] = -1;
			// board[nr][nc] = targetFish;

			// map state backtrack using array
			//board = prev_board;
			// System.out.println("Backtracked to "+board);
			// System.out.println("Shark location backtracked to R"+fishes[0][0]+" C"+fishes[0][1]);
		}

		ANSWER = ANSWER < cnt? cnt : ANSWER;
	}// end of DFS method

	public static void fish_move(){
		for (int i=1; i<17; i++) {  // 1 ~ 16 fish move

			int cfr = fishes[i][0]; int cfc = fishes[i][1]; int cfd = fishes[i][2]; int dead = fishes[i][3];
			// System.out.println(i+"th fish moving");
			if(dead == 1) continue;

			// came here because fish is not dead yet.....
			// looking for next direction
			int nd = -1;
			

			for (int d=0; d<8; d++) {
				nd = (cfd + d)%8;
				int nr = cfr + dr[nd];
				int nc = cfc + dc[nd];

				if(nr < 0 || nr >=4 || nc < 0 || nc >= 4) continue;

				if(board[nr][nc] < 0 ) continue;

				// came here because can swap place
				int nextSize = board[nr][nc];

				// map information change
				board[nr][nc] = board[cfr][cfc]; // size change
				
				board[cfr][cfc] = nextSize; // size change
				

				// fish information change...
				if(nextSize != 0){
					fishes[nextSize][0] = cfr; fishes[nextSize][1] = cfc;	
				}
				
				fishes[i][0] = nr; fishes[i][1] = nc; fishes[i][2] = nd;

				break;

			}
			
			// System.out.println();
			// System.out.println(i+"th fish moved");
			// print_fish();

		}
	}// end of fish_move method

	public static void print_fish(){
		for (int r=0; r<4; r++) {
			for (int c=0; c<4; c++) {
				System.out.print(board[r][c]+" ");
			}
			System.out.println();
		}
	}


}// end of class 


/*
7 1 2 6 15 7 9 3
3 5 1 4 14 1 10 6
6 4 13 3 4 6 11 1
16 5 8 7 5 2 12 2

7 6 2 6 15 7 9 3
3 5 1 4 14 1 10 6
6 4 13 3 4 6 11 1
16 5 8 7 5 2 12 2

7 6 2 1 15 1 9 1
3 1 1 1 14 7 10 3
6 1 13 6 4 3 11 4
16 3 8 7 5 2 12 2

12 6 14 5 4 5 6 7
15 1 11 3 3 7 7 5
10 3 8 2 16 6 1 1
5 8 2 7 13 6 9 4
*/
