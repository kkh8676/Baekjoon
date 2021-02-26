// 43min, 13624kb, 92ms

import java.util.StringTokenizer;
import java.io.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Arrays;
import java.lang.Math;

class boj_17471_210222{
	static int N;
	static int[] people;
	static int[][] adj;
	static int[] team;
	static int[] isV;

	static Queue<Integer> q;

	static int ANSWER;
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		N = Integer.parseInt(br.readLine());

		people = new int[N];
		team = new int[N];
		isV = new int[N];
		adj = new int[N][N];

		q = new LinkedList<>();

		st = new StringTokenizer(br.readLine());
		for (int n=0; n<N; n++) {
			people[n] = Integer.parseInt(st.nextToken());
		}// end of getting people information

		for (int n=0; n<N; n++) {
			st = new StringTokenizer(br.readLine());

			int num = Integer.parseInt(st.nextToken());

			for (int nn=0; nn<num; nn++) {
				int adjacent = Integer.parseInt(st.nextToken())-1;

				adj[n][adjacent] = 1;
			}
		}// end of getting adjacent arr

		ANSWER = Integer.MAX_VALUE;

		DFS(N,0,0,0);
		// team[0] = 1;
		// team[3] = 1;

		// team[1] = 2;
		// team[2] = 2;
		// team[4] = 2;
		// team[5] = 2;

		// int red_people = BFS(0,1);
		// int blue_people = BFS(1,2);

		if(ANSWER == Integer.MAX_VALUE) ANSWER = -1;
		System.out.println(ANSWER);
	}// end of main method

	public static void DFS(int capa, int round, int red, int blue){
		if(capa == round){

			// intialize isV arr
			Arrays.fill(isV,0);

			// there's no team in red or blue
			if(red == 0 || blue == 0) return;

			// 1팀 2팀 나눈대로 갈 수 있다면 BFS로 인구수 check
			// finding red first and blue first
			int red_first = -1;
			int blue_first = -1;
			for (int i=0; i<N; i++) {
				if(team[i] == 1){
					red_first = i;
					break;
				}
			}

			for (int i=0; i<N; i++) {
				if(team[i] == 2){
					blue_first = i;
					break;
				}
			}

			int red_people = BFS(red_first,1); // if condition satisfied, return people in that team. Else, return -1
			int blue_people = BFS(blue_first,2);

			if(red_people > 0 && blue_people > 0){
				int diff = Math.abs(red_people - blue_people);
				ANSWER = ANSWER > diff ? diff : ANSWER;
			}

			return;
		}// ending condition

		// 2 way
		team[round] = 1;
		DFS(capa, round+1, red+1, blue);

		team[round] = 2;
		DFS(capa, round+1, red, blue+1);

	}// end of DFS method

	public static int BFS(int start, int color){
		isV[start] = color;
		q.add(start);

		while(!q.isEmpty()){
			int ci = q.remove();

			for (int i=0; i<N; i++) {
				if(adj[ci][i] == 1 && isV[i] == 0 && team[i] == color){
					q.add(i);
					isV[i] = color;
				}
			}
		}// end of while loop

		boolean feasible = true;
		int people_sum = 0;
		for (int i=0; i<N; i++) {
			if(team[i] != color) continue;

			// came here because currnet team is color
			people_sum += people[i];

			if(!(team[i] == color && isV[i] == color)){
				feasible = false;
				break;
			}
		}// end of for loop

		if(!feasible) return -1;

		return people_sum;
	}// end of BFS method
}// end of class