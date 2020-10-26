// Took 26 min
// Can be optimized in check method
// if current gear direction is zero, next of previous gear direction are all zero
// 이전 톱니, 이 다음 톱니의 방향을 설정할 때, 
// 회전하지 않는 톱니부터는 방향을 설정할 필요없이 그냥 0로 설정하면 시간을 아낄 수 있다. for문을 더 돌지않고..........
// 그냥 구현 문제, 톱니바퀴 1이랑 똑같아서 시간 훨씬 절약됨.

import java.util.Arrays;
import java.io.*;

class boj_15662{ 
	static int T;
	static int K;

	static int[][] gears;
	static int[][] actions;

	static int ANSWER;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		T = Integer.parseInt(br.readLine());
		gears = new int[T+1][8];

		for (int t=1; t<=T; t++) {
			String[] teeth = br.readLine().split("");
			for (int tooth=0; tooth<8; tooth++) {
				gears[t][tooth] = Integer.parseInt(teeth[tooth]);
			}// end of getting one gear
		}// end of getting total gear

		K = Integer.parseInt(br.readLine());
		actions = new int[K][2]; // second index 0 gear number, index 1 direction

		for (int k=0; k<K; k++) {
			String[] numDir = br.readLine().split(" ");
			actions[k][0] = Integer.parseInt(numDir[0]);
			actions[k][1] = Integer.parseInt(numDir[1]);
		}// end of getting actions
		br.close();
		
		for (int k=0; k<K; k++) {
			int[] dirs;
			dirs = check(actions[k][0], actions[k][1]);

			for (int t=1; t<=T; t++) {
				rotate(t, dirs[t]);
			}// end of totla gear rotating action
		}// end of total actions........

		// Calculating ANSWER process 
		ANSWER = 0;
		for (int t=1; t<=T; t++) {
			if(gears[t][0] == 1) ANSWER++;
		}

		System.out.println(ANSWER);

	}// end of main method

	public static int[] check(int gearNum, int dir){
		int[] dirs = new int[T+1];
		dirs[gearNum] = dir;

		// previous gear direction checking
		for (int gear=gearNum-1; gear>=1; gear--) {
			if(gears[gear][2] != gears[gear+1][6] && dirs[gear+1] != 0){
				dirs[gear] = dirs[gear+1] * (-1);
			}
		}

		// next gear direction checking
		for (int gear=gearNum+1; gear<=T; gear++) {
			if(gears[gear][6] != gears[gear-1][2] && dirs[gear-1] != 0){
				dirs[gear] = dirs[gear-1] * (-1);
			}
		}

		return dirs;

	}// end of check method

	public static void rotate(int gearNum, int dir){
		if(dir == 0){
			// do nothing
			return;
		}

		if(dir == 1){
			// rotate clockwise
			int[] newGear = new int[8];
			for (int i=0; i<8; i++) {
				newGear[i] = gears[gearNum][(i+7)%8];
			}

			gears[gearNum] = newGear;
			return;
		}

		if(dir == -1){
			// rotate anti-clockwise
			int[] newGear = new int[8];
			for (int i=0; i<8; i++) {
				newGear[(i+7)%8] = gears[gearNum][i];
			}

			gears[gearNum] = newGear;
			return;
		}
	}// end of rorate method

}// end of class 
