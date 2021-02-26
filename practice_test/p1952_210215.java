// 25min Success....
// 18604kb 108ms....

import java.util.StringTokenizer;
import java.io.*;
import java.util.Arrays;

class p1952_210215{
	static int T, D1, M1, M3, Y1;
	static int[] schedule;

	static int ANSWER;
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder answer = new StringBuilder();

		schedule = new int[12];

		T = Integer.parseInt(br.readLine());
		for (int t=1; t<=T; t++) {
			// initializing schedule arr
			Arrays.fill(schedule,0);

			st = new StringTokenizer(br.readLine());
			D1 = Integer.parseInt(st.nextToken());
			M1 = Integer.parseInt(st.nextToken());
			M3 = Integer.parseInt(st.nextToken());
			Y1 = Integer.parseInt(st.nextToken());

			st = new StringTokenizer(br.readLine());
			for (int i=0; i<12; i++) {
				schedule[i] = Integer.parseInt(st.nextToken());
			}// end of getting information

			ANSWER = Integer.MAX_VALUE;

			// solving algorithm here.........
			DFS(12,0,0);

			answer.append("#").append(t).append(" ").append(ANSWER).append("\n");
		}// end of test case

		System.out.println(answer.toString());
		br.close();
	}// end of main method

	public static void DFS(int capa, int round, int score){
		if(capa == round || capa < round){
			ANSWER = ANSWER > score? score : ANSWER;
			//System.out.println("DFS method ends");
			return;
		}

		// next round visiting process
		// 4 way.......

		// 1. 1day using
		//System.out.println(round);
		DFS(capa, round+1, score + schedule[round]*D1);

		// 2. M1 using
		DFS(capa, round+1, score + M1);

		// 3. M3 using
		DFS(capa, round+3, score + M3);

		// 4. Y1 using
		DFS(capa, round+12, score + Y1);
	}// end of DFS method
}// end of class

/*
10
10 40 100 300
0 0 2 9 1 5 0 0 0 0 0 0
10 100 50 300
0 0 0 0 0 0 0 0 6 2 7 8
10 70 180 400
6 9 7 7 7 5 5 0 0 0 0 0
10 70 200 550
0 0 0 0 8 9 6 9 6 9 8 6
10 80 200 550
0 8 9 15 1 13 2 4 9 0 0 0
10 130 360 1200
0 0 0 15 14 11 15 13 12 15 10 15
10 180 520 1900
0 18 16 16 19 19 18 18 15 16 17 16
10 100 200 1060
12 9 11 13 11 8 6 12 8 7 15 6
10 170 500 1980
19 18 18 17 15 19 19 16 19 15 17 18
10 200 580 2320
12 28 24 24 29 25 23 26 26 28 27 22
*/