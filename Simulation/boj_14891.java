// Took 1hr 30min - 43min = 47min
// Simple simulation problem
// indexing skill in rotate method

// 0,1,2,3,4,5,6,7 : new array index
// 7,0,1,2,3,4,5,6 : previous array index
// (i+7) % 8, i; 0->7 increasing.....
// new_array[i] = prev_array[ (i+7)%8 ]

// 7,0,1,2,3,4,5,6 : new array index
// 0,1,2,3,4,5,6,7 : previous array index
// just reverse of upper case
// new_array[ (i+7)%8 ] = prev_array[i]

import java.util.Arrays;
import java.io.*;


class boj_14891{
	// first index gear number 1,2,3,4
	// second index 
	static int[][] gears;

	// first index action number
	// second index 0 gear number
	// second index 1 direction
	static int[][] actions;
	static int K;

	static int ANSWER;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		gears = new int[5][8];

		for (int gear=1; gear<5; gear++) {
			String[] poles = br.readLine().split("");
			for (int i=0; i<8; i++) {
				gears[gear][i] = Integer.parseInt(poles[i]);
			}// end of getting one gear poles
		}// end of getting all the gear poles..........

		K = Integer.parseInt(br.readLine());

		actions = new int[K][2]; // k 0,1,2,3,4........

		for (int k=0; k<K; k++) {
			String[] numDir = br.readLine().split(" ");
			actions[k][0] = Integer.parseInt(numDir[0]);
			actions[k][1] = Integer.parseInt(numDir[1]);
		} // end of getting actions informations


		// What i should do??

		for (int k=0; k<K; k++) {
			int[] nextDirs;
			nextDirs = check(actions[k][0], actions[k][1]);

			for (int gear=1; gear<5; gear++) {
				rotate(gear, nextDirs[gear]);
			}
		}// end of total action round

		ANSWER = score();

		System.out.println(ANSWER);

	}// end of main method

	public static int[] check(int gearNum, int dir){
		int[] dirs = new int[5];

		dirs[gearNum] = dir;

		// previous number of gear checking
		for (int gear = gearNum-1; gear >= 1; gear--) {
			// next gear same pole?
			if(gears[gear+1][6] == gears[gear][2]){
				dirs[gear] = 0;
			} else if(gears[gear+1][6] != gears[gear][2] && dirs[gear+1] != 0 ){
				dirs[gear] = dirs[gear+1] * (-1);
			}
		}

		// next number of gear checking
		for (int gear = gearNum+1; gear<=4; gear++) {
			if(gears[gear-1][2] == gears[gear][6]){
				dirs[gear] = 0;
			} else if(gears[gear-1][2] != gears[gear][6] && dirs[gear-1] != 0 ){
				dirs[gear] = dirs[gear-1] * (-1);
			}
		}

		return dirs;
	}// end of check method

	public static void rotate(int gearNum, int dir){
		
		if(dir == 0){
			// do nothing on that gear......
			return;
		}

		if(dir==1){
			// rotate clockwise!!
			int[] newGear = new int[8];
			for (int i=0; i<8; i++) {
				newGear[i] = gears[gearNum][(i+7)%8];
			}
			gears[gearNum] = newGear;
			return;
		}

		if(dir==-1){
			// rotate anti-clockwise!!
			int[] newGear = new int[8];
			for (int i=0; i<8; i++) {
				newGear[(i+7)%8] = gears[gearNum][i];
			}
			gears[gearNum] = newGear;
			return;
		}


	}// end of rotate method 

	public static int score(){
		int score = 0;

		if(gears[1][0] == 1){
			score += 1;
		}
		if(gears[2][0] == 1){
			score += 2;
		}
		if(gears[3][0] == 1){
			score += 4;
		}

		if(gears[4][0] == 1){
			score += 8;
		}

		return score;
	}// end of score method
}// end of class 