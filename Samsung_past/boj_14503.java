import java.util.Scanner;
import java.lang.Math;


class boj_14503{

	static int R;
	static int C;

	// for the board map
	// if location is cleaned then isC[cR][cC] gets the value of true;

	static int[][] map;
	static int[][] isC;

	static int cR;
	static int cC;
	static int cD;

	static int N = 0;
	static int W = 3;
	static int S = 2;
	static int E = 1;

	static int[] backwardD = {2,3,0,1};

	static int[] dr = {-1,0,1,0};
	static int[] dc = {0,1,0,-1};

	static int totalC = 0;

	public static void main(String[] args){

		Scanner scan = new Scanner(System.in);

		// First N and M is given.....
		R = scan.nextInt();
		C = scan.nextInt();

		// we can make the map using R&C
		map = new int[R][C];
		isC = new int[R][C];

		//Second line, current location of cleaner and diredction of cleaner is given.....
		cR = scan.nextInt();
		cC = scan.nextInt();
		cD = scan.nextInt();


		// loading map with wall or aisle
		for (int r=0; r<R; r++) {
			for (int c=0; c<C; c++) {
				map[r][c] = scan.nextInt();
				if(map[r][c] == 1) isC[r][c] = 1;
			}
		}

		// Go clean, cleaner!!
		while(true){
			//number one routine
			// if current location is not cleaned, clean it........
			/*
			for (int r=0; r<R; r++) {
				for(int c=0; c<C; c++){
					System.out.print(isC[r][c] + " ");
				}
				System.out.println();
				
			}*/
			
			if(isC[cR][cC] != 1){
				//System.out.println("clean!");
				//totalC++;
				isC[cR][cC] = 1;
				totalC++;
				continue;
			}

			//number two routine...
			//four direction lookling.......
			// if there is no cleaned place go forward that direction and clean it 

			boolean movedForward = false;

			for (int j=0; j<4; j++) {
				cD = (cD+3)%4;

				// limit of the map, continue the for loop
				if(cR + dr[cD] < 0 || cR + dr[cD]>= R || cC + dc[cD] <0 || cC + dc[cD]>=C) continue;

				// check the location if that location is cleaned or not
				// if location is not  wall and is not cleaned, go forward

				if(isC[ cR + dr[cD] ][ cC + dc[cD] ] != 1 && map[ cR + dr[cD] ][ cC + dc[cD] ] != 1){
					cR = cR + dr[cD];
					cC = cC + dc[cD];
					movedForward = true;
					break;
				}
			}

			// if movedForward and didn't clean up
			if(movedForward) continue;

			// there's no clean to four way.....

			if(cR + dr[ backwardD[cD] ] < 0 || cR + dr[ backwardD[cD] ]>= R || cC + dc[ backwardD[cD] ] <0 || cC + dc[ backwardD[cD] ]>=C || map[cR + dr[ backwardD[cD] ]][cC + dc[ backwardD[cD] ]] == 1) break;


			// if we can go backward then, go backward.....
			cR = cR + dr[ backwardD[cD] ];
			cC = cC + dc[ backwardD[cD] ];

		} // clean process has been done!!


		System.out.println(totalC);


	} // end of main method


}