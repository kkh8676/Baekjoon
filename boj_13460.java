// simulation + BFS
// state red, blue ball location and move count
// if the ball dropped, then moving method is done
// else ball moves until it meets the wall.
// if blue and red ball moves together, just change the last location
// if we move the ball upward, then we don't have to move downward in next situation. It just become original state at all.
// So if we move upward or downward, next feasible action is just right or left.





import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.IOException;
import java.util.StringTokenizer;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.util.Queue;
import java.util.LinkedList;

class boj_13460{

	static int N;
	static int M;
	static int[][] map;
	static int[][] isV;

	static int[] dr = {-1,1,0,0};
	static int[] dc = {0,0,-1,1};

	static final int ROAD = 0;
	static final int WALL = 1;
	static final int HOLE = -1;

	static final int DROPPED = -1;
	static final int ALIVE = 1;

	static int targetR;
	static int targetC;

	static Queue<Integer> q;

	static String[] dir = {"UP", "DOWN", "LEFT", "RIGHT"};

	static int ANSWER;
	public static void main(String[] args) throws IOException{
		BufferedReader BR = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter BW = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st = new StringTokenizer(BR.readLine());

		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		map = new int[N][M];
		// do i need isV?

		int blueR = -1;
		int blueC = -1;
		int redR = -1;
		int redC = -1;

		q = new LinkedList<>();

		for (int n=0; n<N; n++) {
			String curStr = BR.readLine();
			String[] strAry = curStr.split("");
			for (int m=0; m<M; m++) {
				String cur = strAry[m];
				if(cur.equals("#")) map[n][m] = WALL;
				else if(cur.equals(".")) map[n][m] = ROAD;
				else if(cur.equals("O")){
				 	map[n][m] = HOLE;
				 	targetR = n;
				 	targetC = m;
				}
				else if(cur.equals("R")){
					map[n][m] = ROAD;
					redR = n;
					redC = m;
				} else{
					map[n][m] = ROAD;
					blueR = n;
					blueC = m;
				}
			}// innner for loop
		}// outer for loop 
		// finish to save the map

		// get in the ball location in the Queue
		// and current count

		q.add(redR);
		q.add(redC);
		q.add(blueR);
		q.add(blueC);
		q.add(-1); // means the initial direction!!
		q.add(0);

		BFS();

		System.out.println(ANSWER);

	}// end of main method

	public static void BFS(){
		while(true){
			int cRedR = q.remove();
			int cRedC = q.remove();
			int cBlueR = q.remove();
			int cBlueC = q.remove();
			int prevDir = q.remove();
			int cCount = q.remove();
			//if(prevDir!=-1) System.out.println("\nprevious direction was " + dir[prevDir]);
			//printMap(cRedR, cRedC, cBlueR, cBlueC);
			// ending condition 1
			// after 10 just answer -1
			if(cCount > 10){
				ANSWER = -1;
				break;
			}

			// ending condition 2
			// good drop condition
			// red one fall and blue one didn't fall
			// success!!
			if((cRedR == targetR && cRedC == targetC) && !(cBlueR == targetR && cBlueC == targetC)){
				ANSWER = cCount;
				break;
			}

			// blue one falls but red one not fall
			if(!(cRedR == targetR && cRedC == targetC) && (cBlueR == targetR && cBlueC == targetC)){
				continue;
			}

			if((cRedR == targetR && cRedC == targetC) && (cBlueR == targetR && cBlueC == targetC)){
				continue;
			}


			// prev direction up 0 down 1
			// prev direction left 2 right 3
			if(prevDir == -1){
				for (int i=0; i<4; i++) {
					if(i==0) up(cRedR, cRedC, cBlueR, cBlueC, cCount);
					if(i==1) down(cRedR, cRedC, cBlueR, cBlueC, cCount);
					if(i==2) left(cRedR, cRedC, cBlueR, cBlueC, cCount);
					if(i==3) right(cRedR, cRedC, cBlueR, cBlueC, cCount);
				}
			} else if(prevDir == 0 || prevDir == 1){
				for (int i=2; i<4; i++) {
					if(i==2) left(cRedR, cRedC, cBlueR, cBlueC, cCount);
					if(i==3) right(cRedR, cRedC, cBlueR, cBlueC, cCount);	
				}
			} else if(prevDir == 2 || prevDir ==3){
				for (int i=0; i<2; i++) {
					if(i==0) up(cRedR, cRedC, cBlueR, cBlueC, cCount);
					if(i==1) down(cRedR, cRedC, cBlueR, cBlueC, cCount);
				}
			}


		} // end of while loop
	}// end of BFS method

	// do we need the flag this time??
	public static void up(int rR, int rC, int bR, int bC, int count){
		// current location is given by parameters.......
		// move the balls until we cannot move the ball
		// ends when encounter wall or the hole!

		int mRedR = rR;
		 // Column factor doesn't impact up method
		int mBlueR = bR;
		

		int redFlag = ALIVE;
		int blueFlag = ALIVE;

		// move the red ball
		while(true){
			if(mRedR + dr[0] < 0 || map[mRedR + dr[0]][rC] == WALL){
				// if there is no room to go, break the while loop
				break;
			} else if(mRedR + dr[0] == targetR && rC == targetC){
				// if there is Hole right up current ball
				// drop the ball and break the while loop

				// changing the ball location
				mRedR = mRedR + dr[0];
				redFlag = DROPPED;
				break;
			}
			mRedR = mRedR + dr[0];
		}

		// move the blue ball
		while(true){
			if(mBlueR + dr[0] < 0 || map[mBlueR + dr[0]][bC] == WALL){
				break;
			}
			else if( mBlueR + dr[0] == targetR && bC == targetC){
				mBlueR = mBlueR + dr[0];
				blueFlag = DROPPED;
				break;
			} 
			mBlueR = mBlueR + dr[0];
		}

		// check if the red and the blue ball in same location
		// if two balls are not in the hole and location is the same........
		if(rC == bC && mRedR == mBlueR){
			if(redFlag != DROPPED){
				 // if redball is dropped and the location is same, it means two balls are in the hole
				// lower starting point gets the lower location
				// compare rR and bR
				if(rR > bR){
					// lower starting point is the red one
					mRedR = mRedR - dr[0];
				} else{
					mBlueR = mBlueR -dr[0];
				}
			}
		}

		// insert this information on the queue
		q.add(mRedR);
		q.add(rC);
		
		q.add(mBlueR);
		q.add(bC);
		q.add(0);
		q.add(count+1);


	}// end of up method

	public static void down(int rR, int rC, int bR, int bC, int count){
		// current location is given by parameters.......
		// move the balls until we cannot move the ball
		// ends when encounter wall or the hole!

		int mRedR = rR;
		 // Column factor doesn't impact up method
		int mBlueR = bR;
		

		int redFlag = ALIVE;
		int blueFlag = ALIVE;

		// move the red ball
		while(true){
			if(mRedR + dr[1] >= N || map[mRedR + dr[1]][rC] == WALL){
				// if there is no room to go, break the while loop
				break;
			} else if(mRedR + dr[1] == targetR && rC == targetC){
				// if there is Hole right up current ball
				// drop the ball and break the while loop

				// changing the ball location
				mRedR = mRedR + dr[1];
				redFlag = DROPPED;
				break;
			}
			mRedR = mRedR + dr[1]; 
		}

		// move the blue ball
		while(true){
			if(mBlueR + dr[1] >= N || map[mBlueR + dr[1]][bC] == WALL){
				break;
			}
			else if( mBlueR + dr[1] == targetR && bC == targetC){
				mBlueR = mBlueR + dr[1];
				blueFlag = DROPPED;
				break;
			}
			mBlueR = mBlueR + dr[1];
		}

		// check if the red and the blue ball in same location
		// if two balls are not in the hole and location is the same........
		if(rC == bC && mRedR == mBlueR){
			if(redFlag != DROPPED){
				 // if redball is dropped and the location is same, it means two balls are in the hole
				// upper starting point gets the upper location
				// compare rR and bR
				if(rR < bR){
					// lower starting point is the red one
					mRedR = mRedR - dr[1];
				} else{
					mBlueR = mBlueR -dr[1];
				}
			}
		}

		// insert this information on the queue
		q.add(mRedR);
		q.add(rC);
		
		q.add(mBlueR);
		q.add(bC);
		q.add(1);
		q.add(count+1);

	}// end of down method

	public static void left(int rR, int rC, int bR, int bC, int count){
		// current location is given by parameters.......
		// move the balls until we cannot move the ball
		// ends when encounter wall or the hole!

		int mRedC = rC;
		 // Row factor doesn't impact up method
		int mBlueC = bC;
		

		int redFlag = ALIVE;
		int blueFlag = ALIVE;

		// move the red ball
		while(true){
			if(mRedC + dc[2] < 0 || map[rR][mRedC + dc[2]] == WALL){
				// if there is no room to go, break the while loop
				break;
			} else if(mRedC + dc[2] == targetC && rR == targetR){
				// if there is Hole right up current ball
				// drop the ball and break the while loop

				// changing the ball location
				mRedC = mRedC + dc[2];
				redFlag = DROPPED;
				break;
			}
			mRedC = mRedC + dc[2];
		}

		// move the blue ball
		while(true){
			if(mBlueC + dc[2] < 0 || map[bR][mBlueC + dc[2]] == WALL){
				break;
			}
			else if( mBlueC + dc[2] == targetC && bR == targetR){
				mBlueC = mBlueC + dc[2];
				blueFlag = DROPPED;
				break;
			}
			mBlueC = mBlueC + dc[2];
		}

		// check if the red and the blue ball in same location
		// if two balls are not in the hole and location is the same........
		if(rR == bR && mRedC == mBlueC){
			if(redFlag != DROPPED){
				 // if redball is dropped and the location is same, it means two balls are in the hole
				// righter starting point gets the righter location
				// compare rR and bR
				if(rC > bC){
					// lower starting point is the red one
					mRedC = mRedC - dc[2];
				} else{
					mBlueC = mBlueC -dc[2];
				}
			}
		}

		// insert this information on the queue
		q.add(rR);
		q.add(mRedC);
		
		q.add(bR);
		q.add(mBlueC);
		q.add(2);
		q.add(count+1);

	} // end of left method

	public static void right(int rR, int rC, int bR, int bC, int count){
		// current location is given by parameters.......
		// move the balls until we cannot move the ball
		// ends when encounter wall or the hole!

		int mRedC = rC;
		 // Row factor doesn't impact up method
		int mBlueC = bC;
		

		int redFlag = ALIVE;
		int blueFlag = ALIVE;

		// move the red ball
		while(true){
			if(mRedC + dc[3] >= M || map[rR][mRedC + dc[3]] == WALL){
				// if there is no room to go, break the while loop
				break;
			} else if(mRedC + dc[3] == targetC && rR == targetR){
				// if there is Hole right up current ball
				// drop the ball and break the while loop

				// changing the ball location
				mRedC = mRedC + dc[3];
				redFlag = DROPPED;
				break;
			}
			mRedC = mRedC + dc[3];
		}

		// move the blue ball
		while(true){
			if(mBlueC + dc[3] >= M || map[bR][mBlueC + dc[3]] == WALL){
				break;
			}
			else if( mBlueC + dc[3] == targetC && bR == targetR){
				mBlueC = mBlueC + dc[3];
				blueFlag = DROPPED;
				break;
			}
			mBlueC = mBlueC + dc[3];
		}

		// check if the red and the blue ball in same location
		// if two balls are not in the hole and location is the same........
		if(rR == bR && mRedC == mBlueC){
			if(redFlag != DROPPED){
				 // if redball is dropped and the location is same, it means two balls are in the hole
				// lefter starting point gets the lefter location
				// compare rR and bR
				if(rC < bC){
					// lower starting point is the red one
					mRedC = mRedC - dc[3];
				} else{
					mBlueC = mBlueC -dc[3];
				}
			}
		}

		// insert this information on the queue
		q.add(rR);
		q.add(mRedC);
		
		q.add(bR);
		q.add(mBlueC);
		q.add(3);
		q.add(count+1);

	} // end of right method

	// for debuging process!!
	public static void printMap(int rR, int rC, int bR, int bC){
		System.out.println("red Row is "+rR);
		System.out.println("red Col is "+rC);

		System.out.println("blue Row is "+bR);
		System.out.println("blue Col is "+bC);

		for (int n=0; n<N; n++) {
			System.out.println();
			for (int m=0; m<M; m++) {
				if(map[n][m] == WALL){
					System.out.print("#");
				} else if(map[n][m] == ROAD){
					if(rR == n && rC == m) System.out.print("R");
					else if(bR == n && bC == m) System.out.print("B");
					else System.out.print(".");
				} else{
					System.out.print("O");
				}
			}

		}

		System.out.println("\n\n\n\n");
	}

}// end of class 
