// 1hr 10min Success.....
// 19128 kb, 104ms

import java.util.StringTokenizer;
import java.io.*;
import java.util.Arrays;

class p4013{
	static int T,K;
	static int[][] polar;

	static int ANSWER;

	static int[] scoring;

	static int RIGHT1, LEFT2, RIGHT2, LEFT3, RIGHT3, LEFT4;
	static int[][] rls;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder answer = new StringBuilder();

		T = Integer.parseInt(br.readLine());

		polar = new int[4][8];

		scoring = new int[4];
		rls = new int[4][2];

		for (int t=1; t<=T; t++) {
			K = Integer.parseInt(br.readLine());
			ANSWER = 0;

			// initialize the polar array and scoring array
			initialize();

			for (int p=0; p<4; p++) {
				st = new StringTokenizer(br.readLine());

				for (int i=0; i<8; i++) {
					polar[p][i] = Integer.parseInt(st.nextToken());
				}
			}// end of getting polar information

			//System.out.println("No rotating");
			//print_gears();

			for (int k=0; k<K; k++) {
				st = new StringTokenizer(br.readLine());

				int targetGear = Integer.parseInt(st.nextToken())-1;
				int dir = Integer.parseInt(st.nextToken());

				total(targetGear, dir);

				//System.out.println();
				//System.out.println("After process "+k+" done!");
				//print_gears();

			}

			for (int i=0; i<4; i++) {
			// S polarity
			if(polar[i][ scoring[i] ] == 1) ANSWER += Math.pow(2,i);
		}

			answer.append("#").append(t).append(" ").append(ANSWER).append("\n");
		}// end of test case 

		System.out.println(answer.toString());
		br.close();
	}// end of main method

	public static void initialize(){
		for (int r=0; r<4; r++) {
			Arrays.fill(polar[r],0);
			Arrays.fill(rls[r],0);
		}
		Arrays.fill(scoring,0);
		RIGHT1=2; LEFT2=6; RIGHT2=2; LEFT3=6; RIGHT3=2; LEFT4=6;
		rls[0][0] = 6; rls[0][1] = 2;
		rls[1][0] = 6; rls[1][1] = 2;
		rls[2][0] = 6; rls[2][1] = 2;
		rls[3][0] = 6; rls[3][1] = 2;
	}// end of initialize method

	public static void total(int tg, int dir){
		int[] rotateDirs = new int[4];
		// decide which way to rotate!
		rotateDirs[tg] = dir;

		if(tg == 0){
			// check 1th gear
			if(polar[0][rls[0][1]] != polar[1][rls[1][0]]) rotateDirs[1] = dir/(-1);
			if(polar[1][rls[1][1]] != polar[2][rls[2][0]]) rotateDirs[2] = rotateDirs[1] /(-1);
			if(polar[2][rls[2][1]] != polar[3][rls[3][0]]) rotateDirs[3] = rotateDirs[2] / (-1);

		} else if(tg == 1){
			// check th 0th gear
			if(polar[0][rls[0][1]] != polar[1][rls[1][0]]) rotateDirs[0] = dir/(-1);
			// check the 2th gear
			if(polar[1][rls[1][1]] != polar[2][rls[2][0]]) rotateDirs[2] = dir/(-1);
			// check the last gear
			if(polar[2][rls[2][1]] != polar[3][rls[3][0]]) rotateDirs[3] = rotateDirs[2] / (-1);

		} else if(tg == 2){
			// check the 1th gear
			//System.out.println("Came here?");
			//System.out.println("2th gear RIGHT " + polar[2][rls[2][1]]);
			//System.out.println("3th gear LEFT " + polar[3][rls[3][0]]);
			if(polar[1][rls[1][1]] != polar[2][rls[2][0]]) rotateDirs[1] = dir/(-1);
			// check the third gear
			if(polar[2][rls[2][1]] != polar[3][rls[3][0]]) rotateDirs[3] = rotateDirs[2] / (-1);
			// check the 0th gear
			if(polar[0][rls[0][1]] != polar[1][rls[1][0]]) rotateDirs[0] = rotateDirs[1]/(-1);
		} else{
			// check the 2th gear
			if(polar[2][rls[2][1]] != polar[3][rls[3][0]]) rotateDirs[2] = dir / (-1);
			// check the 1th gear
			if(polar[1][rls[1][1]] != polar[2][rls[2][0]]) rotateDirs[1] = rotateDirs[2]/(-1);
			// check the zero the gear
			if(polar[0][rls[0][1]] != polar[1][rls[1][0]]) rotateDirs[0] = rotateDirs[1]/(-1);
		}

		for (int i=0; i<4; i++) {
			//System.out.println("Rotating "+i+"th gear in " + rotateDirs[i]+" way");
			rotate(i, rotateDirs[i]);
		}

		
	}// end of total method

	public static void rotate(int tg, int dir){
		// one gear rotating!
		if(dir == 1){
			//clockwise
			scoring[tg] = (scoring[tg]+7)%8;
			rls[tg][0] = (rls[tg][0]+7)%8;
			rls[tg][1] = (rls[tg][1]+7)%8;

		} else if(dir == -1){
			// anticlockwise
			scoring[tg] = (scoring[tg]+1)%8;
			rls[tg][0] = (rls[tg][0]+1)%8;
			rls[tg][1] = (rls[tg][1]+1)%8;
		} 
	}// end of rotate method

	public static void print_gears(){
		for (int i=0; i<4; i++) {
			System.out.print("Num"+i+": ");
			for (int s=0; s<8; s++) {
				System.out.print(polar[i][(scoring[i]+s)%8]+" ");
			}
			System.out.println();
		}
	}// end of print_gears
}// end of class


/*
10
2
0 0 1 0 0 1 0 0
1 0 0 1 1 1 0 1
0 0 1 0 1 1 0 0
0 0 1 0 1 1 0 1
1 1
3 -1
2
1 0 0 1 0 0 0 0
0 1 1 1 1 1 1 1
0 1 0 1 0 0 1 0
0 1 0 0 1 1 0 1
3 1
1 1
5
0 0 1 1 1 1 1 1
1 1 1 1 1 0 1 0
0 0 0 0 1 0 0 1
0 1 0 1 0 1 0 1
4 -1
3 1
4 -1
3 -1
1 -1
2
1 0 1 0 0 1 0 1
0 0 1 0 1 1 1 1
0 0 1 1 0 0 0 1
0 1 0 1 1 0 0 0
2 -1
1 1
7
0 0 1 1 0 1 1 1
0 1 0 1 1 0 0 0
1 1 1 0 0 0 0 1
1 1 1 0 0 1 0 0
4 1
2 1
2 -1
3 1
2 1
4 1
2 -1
10
1 0 0 0 0 0 0 1
1 0 1 0 1 1 0 1
1 0 0 1 0 0 0 1
1 1 0 1 0 1 1 1
2 1
1 1
2 -1
3 1
3 -1
2 -1
2 -1
1 1
4 1
4 1
10
0 1 0 0 1 1 0 0
0 1 1 0 1 0 1 1
0 0 0 0 0 1 1 0
0 0 1 0 1 0 1 1
3 1
1 -1
2 1
4 -1
3 1
3 -1
4 -1
2 -1
1 -1
3 -1
10
0 1 0 1 0 1 0 0
0 1 1 1 1 1 0 1
1 0 0 0 0 1 1 0
1 0 0 0 0 0 0 1
1 1
4 -1
4 -1
2 -1
2 -1
2 -1
3 -1
2 1
3 1
3 -1
20
1 0 0 0 1 1 0 0
1 0 0 1 1 1 0 0
0 1 1 1 0 1 1 1
1 1 1 1 0 1 1 1
1 1
4 -1
4 -1
2 -1
3 -1
1 1
4 1
4 -1
4 -1
4 -1
3 -1
3 -1
4 -1
4 -1
2 -1
1 1
3 -1
3 -1
2 1
1 1
20
0 0 1 1 1 0 1 0
0 1 0 0 1 0 1 0
1 1 1 0 1 0 1 0
0 0 1 0 0 1 1 1
1 -1
4 -1
3 -1
1 1
4 1
2 1
1 -1
4 1
2 -1
4 -1
1 1
4 -1
1 1
2 -1
1 -1
3 -1
1 1
2 1
3 1
3 -1

*/