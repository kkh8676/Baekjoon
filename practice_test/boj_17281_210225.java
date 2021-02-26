// 40 min Success.....
// 16300 kb, 532 ms

import java.util.StringTokenizer;
import java.io.*;
import java.util.Arrays;

class boj_17281_210225{
	static int N;
	static int[][] results;

	static int[] seq;
	static int[] isV;
	static int[] base;
	static int ANSWER;
	// static int CNT;
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		N = Integer.parseInt(br.readLine());
		seq = new int[9];
		isV = new int[9];
		base = new int[3];

		results = new int[N][9];
		for (int n=0; n<N; n++) {
			st = new StringTokenizer(br.readLine());
			for (int i=0; i<9; i++) {
				results[n][i] = Integer.parseInt(st.nextToken());
			}
		}// end of getting results information

		ANSWER = Integer.MIN_VALUE;

		DFS(9,0);

		System.out.println(ANSWER);
		// System.out.println(CNT);
		// System.out.println(8*7*6*5*4*3*2);
		br.close();

	}// end of main method

	public static void simulation(){

		//int next_batter = seq[0];
		int next = 0;
		int score = 0;

		// base 1, base 2, base 3........

		for (int inning=0; inning<N; inning++) {
			int out = 0;

			Arrays.fill(base,-1);

			while(out < 3){
				// next batter gogo
				// baseball algorithm here.....
				// there's 5 results in here.......
				int next_batter = seq[next];
				int res = results[inning][next_batter];

				// out count + 1!
				if(res == 0){
					out++;
				}

				else if(res == 1){
					// single
					// third base runner gets the score and empty third base
					if(base[2] != -1){
						score++;
					}
					base[2] = -1;

					// second base and first base goes to third and second base each
					int prev_second = base[1];
					int prev_first = base[0];

					base[2] = prev_second;
					base[1] = prev_first;

					base[0] = next_batter;
				}

				else if(res == 2){
					// double
					// third base and second base runner gets the score
					for (int b=2; b>=1; b--) {
						if(base[b] != -1){
							score++;
						}
						base[b] = -1;
					}

					// if first base runner goes to third base
					base[2] = base[0];
					base[0] = -1;

					// batter goes to second base
					base[1] = next_batter;

				}

				else if(res == 3){
					// triple
					// all the runners make the score and empty the base
					for (int b=2; b>=0; b--) {
						if(base[b] != -1){
							score++;
						}
						base[b] = -1;
					}

					// third base is occupied by the batter
					base[2] = next_batter;
				}

				else{
					// home run
					// all the runner makes the score and empty the base
					for (int b=2; b>=0; b--) {
						if(base[b] != -1){
							score++;
						}
						base[b] = -1;
					}

					// batter gets the score
					score++;
				}

				// updating next index
				next = (next+1)%9;
			}
		}// for N inning.......

		ANSWER = ANSWER < score? score : ANSWER;
		return;
	}// end of simulation method

	public static void DFS(int capa, int round){
		if(capa == round){
			simulation(); // ANSWER updating process in simulation method
			// for (int i=0; i<9; i++) {
			// 	System.out.print(seq[i]+" ");
			// }
			// System.out.println();
			//CNT++;
			return;
		}

		if(round == 3){
			seq[round] = 0;
			DFS(capa,round+1);
			return;
		}
		// visiting next vertex
		for (int n=1; n<9; n++) {

			// round is not 3 
			// if not visited yet, then visit
			if(isV[n] == 0){
				isV[n] = 1;
				seq[round] = n;
				DFS(capa, round+1);

				// backtracking process
				isV[n] = 0;
			}

		}
	}// end of DFS method


}// end of class