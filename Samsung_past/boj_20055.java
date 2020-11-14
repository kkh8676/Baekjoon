// Took 1hr 10 min
// Shit!!
// WHy?
// 다음 위치에 로봇이 없어야만 그 위치로 올라설 수 있다.

import java.io.*;

class boj_20055{
	static int N,K;
	static int[] power;
	static int[] robot;
	static int STAGE;
	static int OUT;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		String[] nk = br.readLine().split(" ");
		N = Integer.parseInt(nk[0]);
		K = Integer.parseInt(nk[1]);

		power = new int[2*N+1];
		robot = new int[2*N+1];

		String[] belts = br.readLine().split(" ");
		for (int n=1; n<=2*N; n++) {
			power[n] = Integer.parseInt(belts[n-1]);
		}// end of getting belt information

		OUT = 0;
		STAGE = 0;

		while(true){
			STAGE++;
			// System.out.println("ROUND "+STAGE);
			rotate();
			// System.out.println("Rotate");
			// printPower();
			
			robot_move();
			// System.out.println("ROBOT MOVE");
			// printPower();

			board();
			// System.out.println("BOARD");
			// printPower();

			
			
			// System.out.println();
			if(OUT >= K) break;
			
		}

		System.out.println(STAGE);

	}// end of main method

	public static void rotate(){
		int[] copied = new int[2*N+1];
		int[] copied_r = new int[2*N+1];
		

		for (int n=1; n<2*N; n++) {
			copied[n+1] = power[n];
			copied_r[n+1] = robot[n];
		}
		copied[1] = power[2*N];
		copied_r[1] = robot[2*N];

		power = copied;
		robot = copied_r;

		robot[N] = 0;
	}// end of rotate method

	public static void robot_move(){
		for (int n=N; n>=1; n--) {
			if(robot[n] != 1) continue;

			if(n==N){
				robot[n] = 0;
				continue;
			}

			else if(power[n+1] >= 1 && robot[n+1] == 0){
				robot[n] = 0;
				robot[n+1] = 1;

				power[n+1]--;
				if(power[n+1] == 0) OUT++;
			}

		}// end of for loop
		robot[N] = 0;
	}// end of move_robot method

	public static void board(){
		if(power[1] >= 1 && robot[1] == 0){
			robot[1] = 1;
			power[1]--;
			if(power[1] == 0) OUT++;
		}
	}// end of board method

	public static void printPower(){
		for (int n=1; n<=N; n++) {
			String str;
			if(robot[n] == 1) str = "O";
			else str = "X";
			System.out.print(power[n]+str+" ");
		}
		System.out.println();

		for (int n=2*N; n>=N+1; n--) {
			String str;
			if(robot[n] == 1) str = "O";
			else str = "X";
			System.out.print(power[n] +str+ " ");
		}
		System.out.println();
	}

}// end of class