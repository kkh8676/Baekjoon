// 1 Hr
// Easy and precise to process Buffered way!!

import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;

class boj_19237_v2{

	static int[] dr = {0,-1,1,0,0};
	static int[] dc = {0,0,0,-1,1};

	static int N;
	static int M;
	static int k;


	// row and column 
	// last index 0 scent
	// last index 1 remain 
	static int[][][] map;

	// first index shark number
	// second index direction 
	// 1 up
	// 2 down
	// 3 left
	// 4 right
	// third index priority number
	static int[][][] prior;

	// first index shark number
	// second index 0 row
	// second index 1 col
	static int[][] sharkLoc;

	// index mean sharknum
	static int[] sharkDir;

	static int[] sharkDead;

	static int COUNT;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		String[] NMk = br.readLine().split(" ");
		N = Integer.parseInt(NMk[0]);
		M = Integer.parseInt(NMk[1]);
		k = Integer.parseInt(NMk[2]);

		map = new int[N][N][2];
		sharkLoc = new int[M+1][2];
		sharkDir = new int[M+1];

		for (int r=0; r<N; r++) {
			String[] curRow = br.readLine().split(" ");
			for (int c=0; c<N; c++) {
				map[r][c][0] = Integer.parseInt(curRow[c]);
				map[r][c][1] = 0;
				if(map[r][c][0] != 0){
					// saving current shark location
					sharkLoc[map[r][c][0]][0] = r;
					sharkLoc[map[r][c][0]][1] = c;
					map[r][c][1] = k;
				}
			}
		}// end of getting map

		String[] firstDirs = br.readLine().split(" ");
		for (int m=1; m<=M; m++) {
			sharkDir[m] = Integer.parseInt(firstDirs[m-1]);
		}
		// for (int shark=1; shark<=M; shark++) {
		// 	System.out.println(sharkDir[shark]);
		// }

		prior = new int[M+1][5][4];
		for (int m=1; m<=M; m++) {
			for (int dir=1; dir<=4; dir++) {
				String[] priorDirs = br.readLine().split(" ");
				for (int p=0; p<4; p++) {
					prior[m][dir][p] = Integer.parseInt(priorDirs[p]);
				}
			}
		}// we got the priority
		
		sharkDead = new int[M+1];
		COUNT = 0;

		
		while(true){

			// if COUNT > 1000? then end
			if(COUNT > 1000){
				COUNT = -1;
				break;
			}

			// if all the shark except number one shark is all dead?
			// then end
			boolean allDead = true;
			for (int m=2; m<=M; m++) {
				if(sharkDead[m] == 0){
					allDead = false;
				}
			} // end of dead checking

			if(allDead){
				break;
			}

			// not satisfying end condition?
			// then move the shark

			move();
			// System.out.println("\nROUND "+ (COUNT+1));
			// printMap();
			COUNT++;
		
		}

		System.out.println(COUNT);


	}// end of main method

	public static void printMap(){
		for (int row=0; row<N; row++) {
			for (int col=0; col<N; col++) {
				System.out.print(map[row][col][0] + ""+map[row][col][1]+" " );
			}
			System.out.println();
		}
	}// end of printMAp method


	public static void move(){
		// just move
		for (int shark=1; shark<=M; shark++) {
			// shark is not dead, then move the shark
			if(sharkDead[shark] == 0 ){
				// moving shark algorithm here
				// current shark number : shark
				// curretn shark direction
				int curSharkDir = sharkDir[shark];
				int curSharkR = sharkLoc[shark][0];
				int curSharkC = sharkLoc[shark][1];
				boolean moved = false;
				// check there is empty cell in adjacent cell in priority first!
				for (int p=0; p<4; p++) {
					int nextDir = prior[shark][curSharkDir][p];

					int nextR = curSharkR + dr[ nextDir ];
					int nextC = curSharkC + dc[ nextDir ];

					// check next location is wall and no map
					if(nextR <0 || nextR >=N || nextC <0 || nextC >= N) continue;

					// if there is map, check is it empty
					// if empty, move!
					if(map[nextR][nextC][0] == 0){
						// System.out.println(shark);
						// System.out.println(nextDir);
						moved = true;
						sharkDir[shark] = nextDir;
						sharkLoc[shark][0] = nextR;
						sharkLoc[shark][1] = nextC;
						break;
					}
				}// end of for 

				// if there is not empty adjacent cell, then move the shark to the same scnet cell
				if(!moved){
					// check there is same scent cell in adjacent, in priority!!
					for (int p=0; p<4; p++) {
						int nextDir = prior[shark][curSharkDir][p];
						int nextR = curSharkR + dr[ nextDir ];
						int nextC = curSharkC + dc[ nextDir ];

						// check next location is out of bound
						if(nextR <0 || nextR >=N || nextC<0 || nextC>=N) continue;

						// if there is same scent, move to that location
						if(map[nextR][nextC][0] == shark){
							moved = true;
							sharkDir[shark] = nextDir;
							sharkLoc[shark][0] = nextR;
							sharkLoc[shark][1] = nextC;
							break;
						}
					}
				} // end of checking same scent

			} // end of alive shark 
		} // end of moving shark


		// check same location
		for (int shark=2; shark<=M; shark++) {
			for (int preShark=1; preShark<shark; preShark++) {
				// moved location is same!!
				if(sharkDead[shark] == 0 &&sharkLoc[preShark][0] == sharkLoc[shark][0] && sharkLoc[preShark][1] == sharkLoc[shark][1]){
					// then kill that shark
					sharkDead[shark] = 1;
					sharkLoc[shark][0] = -1;
					sharkLoc[shark][1] = -1;
					break;
				}
			}
		} // end of checking same location!!


		// update map scent
		for (int r=0; r<N; r++) {
			for (int c=0; c<N; c++) {
				if(map[r][c][0] != 0){
					map[r][c][1]--;
					if(map[r][c][1]==0) map[r][c][0] = 0;
				}
			}
		} // previous set update

		// update current shark location

		for (int shark=1; shark<=M; shark++) {
			if(sharkDead[shark] == 0){
				map[ sharkLoc[shark][0] ][ sharkLoc[shark][1] ][0] = shark;
				map[ sharkLoc[shark][0] ][ sharkLoc[shark][1] ][1] = k;
			}
		}// newly updated shark

	}// end of move method

	public static void printPrior(){
		for (int shark=1; shark<=M; shark++) {
			for (int dir=1; dir<5; dir++) {
				System.out.print("\n"+dir+"  ");
				for (int p=0; p<4; p++) {
					System.out.print(prior[shark][dir][p]+ " ");
				}
			}
			System.out.println();
		}
	}

}// end of class 