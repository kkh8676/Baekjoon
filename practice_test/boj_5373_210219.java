import java.util.StringTokenizer;
import java.io.*;
import java.util.Arrays;

class boj_5373_210219{
	static int T,N;
	static String[][] board;
	// cube 

	static int[][][] planes;
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder answer = new StringBuilder();

		board = new String[9][12];
		planes = new int[6][2][2];

		// upper plane R C infomation
		planes[0][0][0] = 3; planes[0][0][1] = 5; planes[0][1][0] = 3; planes[0][1][1] = 5;
		// lower plane R C information
		planes[1][0][0] = 3; planes[1][0][1] = 5; planes[1][1][0] = 9; planes[1][1][1] = 11;
		// forward plane R C information
		planes[2][0][0] = 6; planes[2][0][1] = 8; planes[2][1][0] = 3; planes[2][1][1] = 5;
		// backward plane R C information
		planes[3][0][0] = 0; planes[3][0][1] = 2; planes[3][1][0] = 3; planes[3][1][1] = 5;
		// left plane R C information
		planes[4][0][0] = 3; planes[4][0][1] = 5; planes[4][1][0] = 0; planes[4][1][1] = 2;
		// right plane R C information
		planes[5][0][0] = 3; planes[5][0][1] = 5; planes[5][1][0] = 6; planes[5][1][1] = 8;

		T = Integer.parseInt(br.readLine());
		for (int t=1; t<=T; t++) {

			// initialize the cube
			initialize();
			// fill upper plane
			

			N = Integer.parseInt(br.readLine());

			st = new StringTokenizer(br.readLine());
			for (int n=0; n<N; n++) {
				String[] cur = st.nextToken().split("");

				String plane = cur[0];
				String dir = cur[1];

				// rotating process here
				int plane_idx;
				if(plane.equals("U")) plane_idx = 0;
				else if(plane.equals("D")) plane_idx = 1;
				else if(plane.equals("F")) plane_idx = 2;
				else if(plane.equals("B")) plane_idx = 3;
				else if(plane.equals("L")) plane_idx = 4;
				else plane_idx = 5;

				int dir_int;
				if(dir.equals("+")) dir_int = 0;
				else dir_int = 1;


			}// end of getting information

			rotate(0,1);


			// printing upper plane process here
			System.out.println();
			System.out.println("Round "+t);
			print_board();
			
			for (int r=3; r<6; r++) {
				for (int c=3; c<6; c++) {
					answer.append(board[r][c]);
				}
				answer.append("\n");
			}


		}// end of test case

		System.out.println(answer.toString());
		br.close();
	}// end of main method

	public static void rotate(int plane_idx, int dir){
		// dir == "+" clockwise.....
		// dir == "-" anti clockwise...

		// plane == "U" ; upper, == "D" ; Down
		//       == "F" ; Front, == "B" ; Back
		//       == "L" ; Left , == "R" ; right

		// rotating that plane into clockwise or anticlockwise.......
		String dirStr;
		if(dir==0) dirStr = "clockwise";
		else dirStr = "anticlockwise";
		System.out.println("Rotating "+plane_idx+ " "+dirStr);

		String[][] copied = new String[9][12];
		for (int r=0; r<9; r++) {
			for (int c=0; c<12; c++) {
				copied[r][c] = board[r][c];
			}
		}

		String[][] rotating_plane = new String[3][3];
		for (int r=planes[plane_idx][0][0]; r<=planes[plane_idx][0][1]; r++) {
			for (int c=planes[plane_idx][1][0]; c<=planes[plane_idx][1][1]; c++) {
				rotating_plane[ r - planes[plane_idx][0][0] ][ c - planes[plane_idx][1][0] ] = board[r][c];
			}
		}

		// rotating
		if(dir == 0){
			// clockwise 
			for (int r=0; r<3; r++) {
				for (int c=0; c<3; c++) {
					System.out.print(rotating_plane[r][c]+" ");
					board[ c+planes[plane_idx][0][0] ][ planes[plane_idx][1][1] - r ] = rotating_plane[r][c];
				}
				System.out.println();
			}
		} else{
			// anticlockwise
			for (int r=0; r<3; r++) {
				for (int c=0; c<3; c++) {
					board[ planes[plane_idx][0][1] - c ][ planes[plane_idx][1][0] + r ] = rotating_plane[r][c];
				}
			}
		}

		// around 4 planes rotating..........	

	}// end of rotate method

	public static void print_board(){
		for (int r=0; r<9; r++) {
			for (int c=0; c<12; c++) {
				if(board[r][c] == null) System.out.print("X ");
				else System.out.print(board[r][c]+" ");
			}
			System.out.println();
		}
	}

	public static void initialize(){
		// upper plane white
		for (int r=3; r<6; r++) {
			for (int c=3; c<6; c++) {
				board[r][c] = "w";
			}
		}
		// lower plane yellow
		for (int r=3; r<6; r++) {
			for (int c=9; c<12; c++) {
				board[r][c] = "y";
			}
		}
		// forward plane red
		for (int r=6; r<9; r++) {
			for (int c=3; c<6; c++) {
				board[r][c] = "r";
			}
		}
		// backward plane orange
		for (int r=0; r<3; r++) {
			for (int c=3; c<6; c++) {
				board[r][c] = "o";
			}
		}
		// left plane green
		for (int r=3; r<6; r++) {
			for (int c=0; c<3; c++) {
				board[r][c] = "g";
			}
		}
		// right plane blue
		for (int r=3; r<6; r++) {
			for (int c=6; c<9; c++) {
				board[r][c] = "b";
			}
		}
	}
}// end of class