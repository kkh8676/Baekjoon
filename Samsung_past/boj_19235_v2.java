import java.util.Arrays;
import java.io.*;
import java.util.Queue;
import java.util.LinkedList;


class boj_19235_v2{
	static int N;

	static int[][] ts;

	// first index Green or BLUE
	// second row
	// second column number 
	static int[][][] map;

	static final int GREEN = 0;
	static final int BLUE = 1;

	static int SCORE:
	static int NUM;

	public static void main(String[] args){
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st;

		N = Integer.parseInt(br.readLine());

		// ts second index 0 tile type
		// second index 1 row 
		// second index 2 col
		ts = new int[N][3];

		for (int n=0; n<N; n++) {
			st = new StringTokenizer(br.readLine().trim());
			for (int i=0; i<3; i++) {
				ts[n][i] = Integer.parseInt(st.nextToken());
			}
		} // we got the ts information!!

		map = new int[2][10][4];
		SCORE = 0;
		NUM = 0;

		for (int round=0; round<N; round++) {
			// initial Tile on the red board
			// we have tile type, row, column information
			int type = ts[round][0];
			int r = ts[round][1];
			int c = ts[round][2];

			// first move!
			first_move(GREEN, type, r, c);
			// maybe this way blue should be transformed!!
			first_move(BLUE, type, r, c);



			while(true){
				// break_full_line
				// check if the line is full
				// then break it!

				boolean breakedFull = false;

				for (int i=0; i<2; i++) {
					// color check
					// check the each row
					Queue<Integer> fulledRow = new LinkedList<>();
					for(int row=6; row<10; row++){
						boolean isFull = true;
						for (int col=0; col<4; col++) {
							if(map[i][row][col] == 0){
								isFull = false;
								break;
							}// end of column check for loop
						}
						if(isFull){
							// that row is full of block!
							fulledRow.add(i,row);
							breakedFull = true;
						}
					}// end of checking each row!!
				}// end of color!!

				if(breakedFull){
					while(!fulledRow.isEmpty()){
						int color = fulledRow.remove();
						int thatRow = fulledRow.remove();
						break_full_line(color,thatRow);
					}
					update(GREEN);
					update(BLUE);
					continue;
				}

				// break_last_line
				// check each color
				boolean breakedLast = false;
				for (int i=0; i<2; i++) {
					// i means color
					// check middle board 
					boolean isThere = false;
					for (int row=4; row<6; row++) {
						boolean curRow = false;
						for (int col=0; col<4; col++) {

							if(map[i][row][col] != 0) {
								// there is any tile in the middle board!!
								curRow = true;
								break;
							}
						}// end of checking columns
						if(curRow){
							isThere = true;
							break;
						}
					}// end of checking row
					if(isThere){
						break_last_line(i);
						update(i);
						breakedLast = true;
					}
				}

				if(breakedLast) continue;
				
				// if update do nothing, break the while loop
				// if comes here, it means breakedLast and breakedFull are all false....!!
				// so just break the while loop
				break;

			}// end of while loop

		}// end of round for loop

		System.out.println(SCORE);
		System.out.println(NUM);

	}// end of main method

	public static void first_move(int color, int type, int r, int c){

	} // end of first_move method

	public static void move_t1(int r,int c){

	}

	public static void move_t2(int r,int c){

	}

	public static void move_t3(int r, int c){
		
	}

	public static void break_full_line(int color, int row){
		for (int col=0; col<4; col++) {
			map[color][row][col] = 0;
		}
	} // end of break_full_line method

	public static void update(int color){

	}// end of update method!!

	public static break_last_line(int color){
		for (int col=0; col<4; col++) {
			map[color][9][col] = 0;
		}
	}// end of break_last_line method
}// end of class 