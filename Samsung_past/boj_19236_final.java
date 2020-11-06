// Took 1hr 55 min.........
// Already solved Problem


import java.io.*;
import java.util.Arrays;

class boj_19236_final{
	static int[][] board;


	// first mean fish index
	// 0 : row
	// 1 : column
	// 2 : direction
	// 3 : dead or not
	static int[][] fish;
	static final int SHARK = 0;

	static int[] dr = {0,-1,-1,0,1,1,1,0,-1};
	static int[] dc = {0,0,-1,-1,-1,0,1,1,1};

	static int ANSWER;
	static int SCORE;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		board = new int[4][4];
		fish = new int[17][4];

		for (int r=0; r<4; r++) {
			String[] curRow = br.readLine().split(" ");
			for(int c=0; c<4; c++){
				int fish_num = Integer.parseInt(curRow[2*c]); // 0,2,4,6
				int fish_dir = Integer.parseInt(curRow[2*c+1]); // 1,3,5,7
				board[r][c] = fish_num;
				fish[fish_num][0] = r;
				fish[fish_num][1] = c;
				fish[fish_num][2] = fish_dir;
				fish[fish_num][3] = 0;
			} 
		}// end of getting board.......

		SCORE = 0;
		ANSWER = Integer.MIN_VALUE;
		DFS(0,0);

		System.out.println(ANSWER);
	}// end of main method

	public static void DFS(int r, int c){
		// 상어가 r,c로 이동
		fish[SHARK][0] = r ;
		fish[SHARK][1] = c;
		int eaten_f = board[r][c];
		fish[SHARK][2] = fish[ eaten_f ][2];

		// 점수 변화
		SCORE = SCORE + eaten_f;

		// 해당 위치의 물고기 변화, 즉 물고기는 죽는다.
		fish[ eaten_f ][3] = 1; // 3 dead
		fish[ eaten_f ][0] = -1; // 0 row
		fish[ eaten_f ][1] = -1; // 1 col
		fish[ eaten_f ][2] = -1; // 2 dir

		// board에서 해당 위치는 0이 된다!!
		// board에서 상어는 0 나머지는 자연수, 빈칸은 -1로 처리
		board[r][c] = 0;

		// 물고기 이동
		update();
		//System.out.println("After fishes moves are done!\n");
		//print_board();

		// 상어가 갈 수 있는 위치가 있는 지 확인, 갈 데가 없다면 DFS 종료
		// 최대 3번 상어가 갈 수 있는 지 아닌 지 확인. 갈 수 있다면 해당 위치로 ㄱㄱ
		int cSR = fish[SHARK][0]; // current Shark Row
		int cSC = fish[SHARK][1]; // current Shark Column
		int cSD = fish[SHARK][2]; // current Shark direction



		for (int p=0; p<3; p++) {
			// 상어 DFS ㄱㄱ
			// 해당 위치에 물고기가 있따면 ㄱㄱ
			int nSR = cSR + dr[cSD]*(p+1);
			int nSC = cSC + dc[cSD]*(p+1);

			// 범위를 넘어선다면 그냥 contunue;
			if(nSR < 0 || nSR >= 4 || nSC < 0 || nSC >= 4) continue;

			if(board[nSR][nSC] > 0) {
				// Saving State 
				
				int prevScore = SCORE;
				int[][] prevBoard = new int[4][4];
				int[][] prevFish = new int[17][4];
				copyAry(prevBoard, board);
				copyAry(prevFish, fish);
				board[cSR][cSC] = -1;
				//System.out.println("DFS at "+nSR + " "+nSC);
				DFS(nSR,nSC);

				copyAry(fish, prevFish);
				copyAry(board,prevBoard);

				SCORE = prevScore;

				// Loading STate

			}
		}// end of for loop

		ANSWER = SCORE > ANSWER? SCORE : ANSWER;

	}// end of DFS method

	// fish location updating!!
	public static void update(){
		// 작은 물고기부터 순서대로 움직인다.
		for (int num=1; num<=16; num++) {
			// 물고기가 이미 죽었다면 그냥 coninue;
			// 내가 빈 칸인 경우는 없다.
			if(fish[num][3] == 1) continue;

			// 현재 방향 먼저 살피고 총 여덟번 살펴야한다.
			int cfr = fish[num][0];
			int cfc = fish[num][1];
			int cfd = fish[num][2];
			int nfd = cfd;
			for (int i=0; i<8; i++) {
				

				
				if(i!=0) nfd = nfd%8 + 1;
				 
				//System.out.println("cfd " + cfd+ " nfd "+nfd);
				int nfr = cfr + dr[nfd];
				int nfc = cfc + dc[nfd];

				// 범위를 넘어선다면 다음으로 ㄱㄱ;
				if(nfr < 0 || nfr >= 4 || nfc <0 || nfc >= 4) continue;

				// 다음 위치에 물고기가 있거나 빈칸이라면 위치 바꾼다.!
				if(board[nfr][nfc] == -1 || board[nfr][nfc] > 0 ){
					// 해당 위치와 change!!
					fish[num][2] = nfd;
					change(cfr,cfc,nfr,nfc);
					//System.out.print("Updating proces "+ num +" fish \n");
					//print_board();
					break;
				} // end of change possible if condition
			
			}// end of for loop for next 8 direction

		}//end of fish number
	} // end of update method

	// changing the location of two fishes.........
	// fish가 없을 수도 있는데 이건 어떻게 처리를 하지?
	public static void change(int r1, int c1, int r2, int c2){
		// fish num 1 : alive fish _num
		int fish1 = board[r1][c1];
		int fish2 = board[r2][c2];

		board[r1][c1] = fish2;
		board[r2][c2] = fish1;

		fish[fish1][0] = r2;
		fish[fish1][1] = c2;

		if(fish2 != -1){
			fish[fish2][0] = r1;
			fish[fish2][1] = c1;
		}

		// fish num 2: changing, but 살았는 지 죽었는 지 모른다.
	}

	public static void copyAry(int[][] cAry, int[][] oAry){
		for (int r=0; r<cAry.length; r++) {
			for (int c=0; c<cAry[0].length; c++) {
				cAry[r][c] = oAry[r][c];
			}
		}
	}

	public static void print_board(){
		for (int i=0; i<4; i++) {
			for (int c=0; c<4; c++) {
				System.out.print(board[i][c] + " " );
			}
			System.out.println();
		}
	}
}// end of class