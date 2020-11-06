// Took 1hr 30 min
// Time Out...............
// Don't need to process BFS M times......
// just once!

// Fixing it Success 
// But How can I deal it precisely and neatly?


import java.io.*;
import java.util.Queue;
import java.util.LinkedList;

class boj_19238_v2{
	static int[] dr = {-1,1,0,0};
	static int[] dc = {0,0,-1,1};

	static int N;
	static int M;
	static int fuel;

	static int[][] map;

	static int[][] passLoc;
	static int[][] desti;

	static int tR;
	static int tC;

	static int[] isMoved;



	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		String[] NMfuel = br.readLine().split(" ");
		N = Integer.parseInt(NMfuel[0]);
		M = Integer.parseInt(NMfuel[1]);
		fuel = Integer.parseInt(NMfuel[2]);

		map = new int[N+1][N+1];
		passLoc = new int[M+1][2];
		desti = new int[M+1][2];

		for (int r=1; r<=N; r++) {
			String[] curRow = br.readLine().split(" ");
			for (int c=1; c<=N; c++) {
				map[r][c] = Integer.parseInt(curRow[c-1]);
			}
		} // end of getting map

		String[] tRC = br.readLine().split(" ");
		tR = Integer.parseInt(tRC[0]);
		tC = Integer.parseInt(tRC[1]);

		for (int m=1; m<=M; m++) {
			String[] curPass = br.readLine().split(" ");

			passLoc[m][0] = Integer.parseInt(curPass[0]);
			passLoc[m][1] = Integer.parseInt(curPass[1]);

			desti[m][0] = Integer.parseInt(curPass[2]);
			desti[m][1] = Integer.parseInt(curPass[3]);
		} // end of getting passengers location and destination information!

		// we can't go through passengers, so check locations of passengers as wall
		// for (int m=1; m<=M; m++) {
		// 	map[ passLoc[m][0] ][ passLoc[m][0] ] = 1;
		// }

		isMoved = new int[M+1]; // At first, all the passengers are not moved......

		// System.out.println(BFS(tR,tC,passLoc[1][0],passLoc[1][1]));
		// System.out.println(BFS(tR,tC,passLoc[2][0],passLoc[2][1]));
		// System.out.println(BFS(tR,tC,passLoc[3][0],passLoc[3][1]));
		
		// We should move total M passengers!!
		for (int round=1; round<=M; round++) {

			// Find which passengers to ride
			int dist = Integer.MAX_VALUE;
			int targetIndex = -1;

			int[] result = BFS_once(tR,tC);

			for (int m=1; m<=M; m++) {

				if(isMoved[m]==1) continue;

				if(result[m]==-1) continue;

				if(dist > result[m]){
					dist = result[m];
					targetIndex = m;
				}

				if(dist == result[m]){
					if(passLoc[ targetIndex ][0] > passLoc[m][0]) targetIndex = m;
					else if(passLoc[targetIndex][0] == passLoc[m][0]){
						if( passLoc[targetIndex][1] > passLoc[m][1]) targetIndex = m;
					}
				}
			}
			
			// // compare all the passengers......
			// // if that passenger is already moved, just continue
			// for (int m=1; m<=M; m++) {
			// 	if(isMoved[m] == 1) continue;

			// 	int curDist = BFS(tR,tC,passLoc[m][0],passLoc[m][1]);

			// 	if(curDist == -1) continue;

			// 	if(dist > curDist) {
			// 		//System.out.println("came here?");
			// 		dist = curDist;
			// 		targetIndex = m;
			// 	}

			// 	// setting priority &&&&&&&&
			// 	if(dist == curDist){
			// 		// compare rows of that passengers
			// 		// current row : passLoc[ targetIndex ][0]
			// 		// comparing row : passLoc[m][0]

			// 		if(passLoc[ targetIndex ][0] > passLoc[m][0] ) targetIndex = m;
			// 		else if(passLoc[ targetIndex ][0] == passLoc[m][0] ){
			// 			// compare columns of that passengers
			// 			// current col : passLoc[ targetIndex ][1]
			// 			// comparing col : passLoc[m][1]

			// 			if( passLoc[ targetIndex ][1] > passLoc[m][1] ) targetIndex = m;
			// 		}
			// 	}
			// }

			//System.out.println("This round passenger index is " + targetIndex);
			//System.out.println("Distance is " + dist);

			// if targetIndex is -1, current taxi can't access all the passengers so break the for loop and return -1
			if(targetIndex == -1) {
				fuel = -1;
				break;
			}

			// go to selected passengers location
			// if fuel is not enough.......
			if(fuel < dist){
				fuel = -1;
				break;
			}

			tR = passLoc[ targetIndex ][0];
			tC = passLoc[ targetIndex ][1];
			fuel = fuel - dist;

			// take that passengers to the destination of that passenger
			int dist_with_passenger = BFS(tR,tC, desti[targetIndex][0], desti[targetIndex][1]);

			// if that destination is not reachable, end the taxi
			if(dist_with_passenger == -1){
				fuel = -1;
				break;
			}

			// reachable, but fuel is not enough
			if(fuel < dist_with_passenger){
				fuel = -1;
				break;
			}

			tR = desti[ targetIndex ][0];
			tC = desti[ targetIndex ][1];
			fuel = fuel - dist_with_passenger;
			fuel = fuel + (2*dist_with_passenger);


			// update passengers status
			isMoved[ targetIndex ] = 1;
		} // end of moving total M passengers


		// Printing remaining fuel
		System.out.println(fuel);


	}// end of main method



	// Getting shorsted path distance from [cR][cC] to [fR][fC] 
	// return distance
	public static int BFS(int cR, int cC, int fR, int fC){
		// this method for getting shortest path of two locations!!
		// from [cR][cC] to [fR][fC]

		int[][] isV = new int[N+1][N+1];
		for (int r=1; r<=N; r++) {
			for (int c=1; c<=N; c++) {
				if(map[r][c] == 1) isV[r][c] = 1;
				else isV[r][c] = 0;
			}
		} // check the wall in isV.........

		isV[cR][cC] = 1;

		// check the not moved passengers......
		// for (int m=1; m<=M; m++) {
		// 	if(isMoved[m] == 1) continue;

		// 	isV[ passLoc[m][0] ][ passLoc[m][1] ] = 1;
		// }


		// for (int r=1; r<=N; r++) {
		// 	for (int c=1; c<=N; c++) {
		// 		System.out.print(isV[r][c] + " ");

		// 	}
		// 	System.out.println();
		// }

		Queue<Integer> q = new LinkedList<>();
		q.add(cR);
		q.add(cC);
		q.add(0);

		// while queue is not empty.......
		while(!q.isEmpty()){
			
			int mR = q.remove();
			int mC = q.remove();
			int d = q.remove();
			//System.out.println("mR"+mR + " mC"+mC+" d"+d);

			// ending condition
			if(mR == fR && mC == fC) {
				return d;
			}

			//print_(isV);
			// 4 way, up down left right
			for (int i=0; i<4; i++) {
				int mmR = mR + dr[i];
				int mmC = mC + dc[i];
				
				// check that location is out of bound
				if( mmR <= 0 || mmR > N || mmC <= 0 || mmC > N) continue;

				// that location is not out of bound and not visited, visit that location
				if(isV[mmR][mmC] != 1){
					
					q.add(mmR);
					q.add(mmC);
					q.add(d+1);
					isV[mmR][mmC] = 1;
				}
			} // end of 4 way for loop

		}// end of while loop

		// when comes here.......
		// it can't be access to target location
		// so return -1
		return -1;
	}// end of BFS method


	// Getting shorsted path distance from [cR][cC] to [fR][fC] 
	// return distance
	public static int[] BFS_once(int cR, int cC){
		// this method for getting shortest path of two locations!!
		// from [cR][cC] to [fR][fC]

		int[] result = new int[M+1];
		for (int m=1; m<=M; m++) {
			result[m] = -1;
		}


		int[] checked = new int[M+1];
		for (int m=1; m<=M; m++) {
			if(isMoved[m] == 1) checked[m] = 1;
			else checked[m] = 0;
		}

		int[][] isV = new int[N+1][N+1];
		for (int r=1; r<=N; r++) {
			for (int c=1; c<=N; c++) {
				if(map[r][c] == 1) isV[r][c] = 1;
				else isV[r][c] = 0;
			}
		} // check the wall in isV.........

		isV[cR][cC] = 1;

		// check the not moved passengers......
		// for (int m=1; m<=M; m++) {
		// 	if(isMoved[m] == 1) continue;

		// 	isV[ passLoc[m][0] ][ passLoc[m][1] ] = 1;
		// }


		// for (int r=1; r<=N; r++) {
		// 	for (int c=1; c<=N; c++) {
		// 		System.out.print(isV[r][c] + " ");

		// 	}
		// 	System.out.println();
		// }

		Queue<Integer> q = new LinkedList<>();
		q.add(cR);
		q.add(cC);
		q.add(0);

		// while queue is not empty.......
		while(!q.isEmpty()){
			
			int mR = q.remove();
			int mC = q.remove();
			int d = q.remove();
			//System.out.println("mR"+mR + " mC"+mC+" d"+d);

			for (int m=1; m<=M; m++) {
				if(mR == passLoc[m][0] && mC == passLoc[m][1]){
					result[m] = d;
					checked[m] = 1;
				}
			}

			// ending condition
			boolean allChecked = true;
			for (int m=1; m<=M; m++) {
				if(checked[m] == 0){
					allChecked = false;
					break;
				}
			}

			if(allChecked){
				return result;
			}

			//print_(isV);
			// 4 way, up down left right
			for (int i=0; i<4; i++) {
				int mmR = mR + dr[i];
				int mmC = mC + dc[i];
				
				// check that location is out of bound
				if( mmR <= 0 || mmR > N || mmC <= 0 || mmC > N) continue;

				// that location is not out of bound and not visited, visit that location
				if(isV[mmR][mmC] != 1){
					
					q.add(mmR);
					q.add(mmC);
					q.add(d+1);
					isV[mmR][mmC] = 1;
				}
			} // end of 4 way for loop

		}// end of while loop

		// when comes here.......
		// it can't be access to target location
		// so return -1
		return result;
	}// end of BFS method

	public static void print_(int[][] map){

		for (int r=1; r<=N; r++) {
			for (int c=1; c<=N; c++) {
				System.out.print(map[r][c] + " ");
			}
			System.out.println();
		}

		System.out.println();
	}



}// end of class 
