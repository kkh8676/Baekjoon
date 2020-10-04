// Simulation problem
// rolling the dice is bottleneck problem to me.
// save the 3 way position of dice
// forward, bottom, right
// after 4 action, change the dice information

import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

class boj_14499{
	static int[] dice;
	

	static int bottom;
	static int right;
	static int forward;

	// right, left, up, down sequence
	static int[] dr = {0, 0, 0, -1, 1};
	static int[] dc = {0, 1,-1, 0, 0};

	static int N;
	static int M;
	static int x;
	static int y;
	static int K;

	static int[][] map;

	static int[] action;

	public static void main(String[] args) throws IOException{
		BufferedReader BR = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter BW = new BufferedWriter(new OutputStreamWriter(System.out));

		String[] nmxyk = BR.readLine().split(" ");

		dice = new int[7];
		bottom = 6;
		right = 3;
		forward = 5;

		N = Integer.parseInt(nmxyk[0]);
		M = Integer.parseInt(nmxyk[1]);
		x = Integer.parseInt(nmxyk[2]);
		y = Integer.parseInt(nmxyk[3]);
		K = Integer.parseInt(nmxyk[4]);

		map = new int[N][M];

		action = new int[K];

		for (int n=0; n<N; n++) {
			String[] curLine = BR.readLine().split(" ");
			for (int m=0; m<M; m++) {
				map[n][m] = Integer.parseInt(curLine[m]);
			}
		} // end of making map

		String[] actionStr = BR.readLine().split(" ");

		for (int k=0; k<K; k++) {
			action[k] = Integer.parseInt(actionStr[k]);
		}

		for (int command=0; command<K; command++) {
			if(x+dr[ action[command] ] < 0 || x+dr[ action[command] ] >= N || y+dc[ action[command] ] < 0 || y+dc[ action[command] ] >= M) continue;

			// print the lid of the dice!
			move(action[command]);
			System.out.println(dice[7-bottom]);
		}



	}// end of main method

	public static void move(int action){
		// parameter action 1,2,3,4
		// 1 right
		// 2 left
		// 3 up
		// 4 down

		//change the current dice location
		x = x + dr[action];
		y = y + dc[action];


		// change the bottom and the right of dice

		if(action==1){
			// move right
			// bottom become left
			// right become bottom
			int temp = bottom;
			bottom = right;
			right = 7 - temp;

		} else if(action==2){
			// move left

			// bottom becomes right
			// left one become bottom

			int temp = bottom;
			bottom = 7 - right;
			right = temp;

		} else if(action==3){
			// move up
			// right same ........
			int temp = bottom;
			bottom = 7 - forward;
			forward = temp;
		} else{
			//move down
			int temp = bottom;
			bottom = forward;
			forward = 7 - temp;
		}


		// change the number of dice and the map;
		// rule 1.	if map[x][y] == 0, map gets the number of dice bottom.
		// rule 2.  if map[x][y] != 0, dice bottom gets the number of map, and the map number become 0

		if(map[x][y] == 0){
			// rule 1.
			map[x][y] = dice[bottom];
		} else{
			dice[bottom] = map[x][y];
			map[x][y] = 0;
		}
		

	} // end of move method
}// end of class 
