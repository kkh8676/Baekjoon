// NullPointer Error
// 1hr 39min.......

import java.util.StringTokenizer;
import java.io.*;
import java.util.LinkedList;
import java.util.Queue;

class boj_20061_210218{
	static int N;
	static Block[][] blue, green;
	static int[][] blue_v, green_v;

	static int SCORE;
	static int CNT;
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		N = Integer.parseInt(br.readLine());

		blue = new Block[4][4+2+4];
		green = new Block[4][4+2+4];
		blue_v = new int[4][4+2+4];
		green_v = new int[4][4+2+4];

		SCORE = 0;
		for (int n=0; n<N; n++) {
			st = new StringTokenizer(br.readLine());

			int t = Integer.parseInt(st.nextToken());
			int r = Integer.parseInt(st.nextToken());
			int c = Integer.parseInt(st.nextToken());

			Block on_blue = new Block(t,r,c);

			int gt;
			if(t==1) gt = 1;
			else if(t==2) gt = 3;
			else gt = 2;
			Block on_green = new Block(gt,c,r);

			put(blue, blue_v, on_blue);
			put(green, green_v, on_green);
			
		}// end of putting block on the map

		System.out.println(SCORE);

		CNT = 0;
		for (int r=0; r<4; r++) {
			for (int c=0; c<4+2+4; c++) {
				if(green_v[r][c]!=0) CNT++;
				if(blue_v[r][c]!=0) CNT++;
			}
		}
		System.out.println(CNT);
		br.close();
	}// end of main method

	public static void print_isV(int[][] isV){
		for (int r=0; r<4; r++) {
			for (int c=0; c<10; c++) {
				System.out.print(isV[r][c]+" ");
			}
			System.out.println();
		}
	}

	public static void print_isV_prime(int[][] isV){
		for (int c=0; c<10; c++) {
			for (int r=0; r<4; r++) {
				System.out.print(isV[r][c]+" ");
			}
			System.out.println();
		}
	}

	public static void put(Block[][] map, int[][] isV, Block block){
		// put in the red zone
		int sr = block.r; int sc = block.c; int type = block.type;
		
		// decide which location the block is placed... == moving to each color board 
		if(type == 1){
			// just one block
			int nc = sc;
			while(true){
				nc = nc + 1;

				if(nc >= (4+2+4) || isV[sr][nc] != 0){
					nc--;
					break;
				}
			}
			// place that block on the board

			map[sr][nc] = block;
			block.c = nc;
			isV[sr][nc] = 1;

		} else if(type == 2){
			// 가로로 된 블록
			int nc = sc;
			while(true){
				nc = nc + 1;

				if(nc+1 >= (4+2+4) || isV[sr][nc+1] != 0){
					nc--;
					break;
				}
			}

			// place that block on the board process
			map[sr][nc] = block;
			block.c = nc;
			isV[sr][nc] = 2;
			isV[sr][nc+1] = 2;

		} else{
			// 세로로 된 블록
			int nc = sc;
			while(true){
				nc = nc + 1;

				if(nc >= (4+2+4) || isV[sr][nc] != 0 || isV[sr+1][nc] != 0){
					nc--;
					break;
				}
			}

			// place that block on the board process
			map[sr][nc] = block;
			block.c = nc;
			isV[sr][nc] = 3;
			isV[sr+1][nc] = 3;
		}

		// breaking process
		// first, there's breakable column in the 6 7 8 9
		// moving rightward....
		for (int bc=9; bc>5; bc--) {
			// all row check
			boolean break_or_not = true;
			
			for (int br=0; br<4; br++) {
				if(isV[br][bc] == 0){
					break_or_not = false;
					break;
				}
			}

			if(!break_or_not) continue;

			// came here because that line is broken!
			// check type two blocks and change it to type 1
			SCORE++;
			Queue<Integer> type_2_q = new LinkedList<>();
			for (int br =0; br<4; br++) {
				if(isV[br][bc] == 2) type_2_q.add(br);
				else if(map[br][bc] != null) map[br][bc] = null;
				isV[br][bc] = 0;

			}

			while(!type_2_q.isEmpty()){
				int bbr = type_2_q.remove();
				if(map[bbr][bc] != null){
					// current location is not null, next location is type 2 block
					Block prev_type_2 = map[bbr][bc];
					map[bbr][bc] = null;
					prev_type_2.c = bc+1;
					prev_type_2.type = 1;
					map[bbr][bc+1] = prev_type_2;
					isV[bbr][bc+1] = 1;

				} else{
					// current location is null, so c-1 location is type 2 block
					Block prev_type_2 = map[bbr][bc-1];
					isV[bbr][bc-1] = 1;
					prev_type_2.type = 1;
				}
			}

			// one way rightward gogo
			for(int cbc = bc-1; cbc > 3; cbc--){
				for (int r=0; r<4; r++) {
					isV[r][cbc+1] = isV[r][cbc];
					if(map[r][cbc] != null){
						map[r][cbc+1] = map[r][cbc];
						map[r][cbc] = null;
					}
				}
			}
			// 4 column initialize...
			for (int r=0; r<4; r++) {
				isV[r][4] = 0;
				map[r][4] = null;
			}

			bc++;
		}
		// second, there's block on the 4,5 column
		// if there is, move rightward......(after break last column)
		for (int bc = 4; bc <6 ; bc++) {
			// check there's any element in current column
			boolean breakLast_or_not = false;
			for (int r=0; r<4; r++) {
				if(isV[r][bc] != 0){
					breakLast_or_not = true;
					break;
				}
			}

			if(!breakLast_or_not) continue;

			// came here because we have to break the last column
			Queue<Integer> type_2_q = new LinkedList<>();
			for (int r=0; r<4; r++) {
				if(isV[r][9] == 2) type_2_q.add(r);
				else if(map[r][9] != null) map[r][9] = null;
				isV[r][9] = 0;
			}

			while(!type_2_q.isEmpty()){
				int bbr = type_2_q.remove();

				Block prev_type_2 = map[bbr][8];
				isV[bbr][8] = 1;
				prev_type_2.type = 1;
				
			}// end of while loop

			// moving right ward.....
			for(int cbc = 8; cbc > 3; cbc--){
				for (int r=0; r<4; r++) {
					isV[r][cbc+1] = isV[r][cbc];
					if(map[r][cbc] != null){
						map[r][cbc+1] = map[r][cbc];
						map[r][cbc] = null;
					}
				}
			}
			// 4 column initialize...
			for (int r=0; r<4; r++) {
				isV[r][4] = 0;
				map[r][4] = null;
			}
		}

		
	}// end of put method
}// end of class

class Block{
	int r,c, type;

	Block(int type, int r, int c){
		this.type = type;
		this.r = r;
		this.c = c;
	}// end of constructor
}// end of class

/*
9
2 1 0
2 1 0
2 1 0
2 1 0
2 1 0
3 0 2
3 0 2
3 0 3
3 0 3
*/