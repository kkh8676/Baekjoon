// 21min Failed.....
// 25min Success......
// 20428 kb, 113 ms

import java.util.StringTokenizer;
import java.io.*;
import java.util.Arrays;

class p4008_210213{
	static int T,N;
	static int[] seq;
	static int[] operators;

	static int MAX,MIN;
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder answer = new StringBuilder();

		T = Integer.parseInt(br.readLine());
		seq = new int[12];
		operators = new int[4];

		for (int t=1; t<=T; t++) {
			// initialize seq and operators MAX MIN
			Arrays.fill(seq,0);
			Arrays.fill(operators,0);
			MAX = Integer.MIN_VALUE;
			MIN = Integer.MAX_VALUE;

			N = Integer.parseInt(br.readLine());

			st = new StringTokenizer(br.readLine());
			for (int i=0; i<4; i++) {
				operators[i] = Integer.parseInt(st.nextToken());
			}// end of getting operator number

			st = new StringTokenizer(br.readLine());
			for (int i=0; i<N; i++) {
				seq[i] = Integer.parseInt(st.nextToken());
			}// end of getting sequence information	

			DFS(N-1,0,seq[0]);

			int DIFFERENCE = MAX - MIN;
			// System.out.println("Curent Round is "+ t);
			// System.out.println(MAX);
			// System.out.println(MIN);

			answer.append("#").append(t).append(" ").append(DIFFERENCE).append("\n");

			
		}// end of test case

		System.out.println(answer.toString());
		br.close();
	}// end of main method

	public static void DFS(int capa, int round, int score){
		if(capa == round){
			// ending condition
			//System.out.println("No came here?");
			MAX = MAX < score? score : MAX;
			MIN = MIN > score? score : MIN;
			return;
		}

		// next vertex visit process
		for (int o=0; o<4; o++) {

			// remain operator is none.....then continue
			if(operators[o] <= 0 ) continue;

			// came here because there is remained operator
			// use it
			operators[o]--;
			int nextScore = operate(score, seq[round+1], o);
			
			DFS(capa, round+1, nextScore);

			// backtracking process
			operators[o]++;

		}
	}// end of DFS method

	public static int operate(int curScore, int nextNum, int operator){
		if(operator == 0){
			// plus
			return curScore + nextNum;
		} else if(operator== 1){
			return curScore - nextNum;
		} else if(operator == 2){
			return curScore * nextNum;
		} else{
			return curScore / nextNum;
		}
	}// end of operate method
}// end of class

/*
10
5
2 1 0 1
3 5 3 7 9
6
4 1 0 0
1 2 3 4 5 6 
5
1 1 1 1
9 9 9 9 9 
6
1 4 0 0
1 2 3 4 5 6 
4
0 2 1 0
1 9 8 6
6
2 1 1 1
7 4 4 1 9 3 
7
1 4 1 0
2 1 6 7 6 5 8 
8
1 1 3 2
9 2 5 3 4 9 5 6 
10
1 1 5 2
8 5 6 8 9 2 6 4 3 2 
12
2 1 6 2
2 3 7 9 4 5 1 9 2 5 6 4 
*/
