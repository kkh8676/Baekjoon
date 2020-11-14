// Took 58 min
// problem description caution!!
// how to tornado??? using Recursion????? Wow.......
// neatly coding process??

import java.io.*;

class boj_20057{
	static int N;
	static int[][] map;

	static int[] dr = {0,1,0,-1};
	static int[] dc = {-1,0,1,0};
	static int[] odd_dir = {1,3};
	static int[] even_dir = {0,2};
	
	static int[][] test;

	static int ANSWER;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		N = Integer.parseInt(br.readLine());

		map = new int[N+1][N+1];
		test = new int[N+1][N+1];

		for (int r=1; r<=N; r++) {
			String[] row = br.readLine().split(" ");
			for (int c=1; c<=N; c++) {
				map[r][c] = Integer.parseInt(row[c-1]);
			}
		}// end of getting information
		//System.out.println();

		ANSWER = 0;
		tornado();

		System.out.println(ANSWER);
		
	}// end of main method

	public static void tornado(){
		int cr = N/2+1;
		int cc = N/2+1;
		test[cr][cc] = 1;
		int cd = 0;
		int cs = 0;

		while(cs <= N-1){
			if(cd % 2 == 0 && cs != N-1)  cs++;

			// move cs speed in cd
			for (int s=1; s<=cs; s++) {
				int nr = cr + dr[cd]*s;
				int nc = cc + dc[cd]*s;
				
				// testing process
				test[nr][nc] = 1;
				sandStorm(nr,nc,cd);
			}
			cr = cr + dr[cd]*cs;
			cc = cc + dc[cd]*cs;

			// change the direction
			cd = (cd+1)%4;
			//System.out.println();
			//printMap();
			//System.out.println();

			if(cr==1 && cc == 1) break;
		}

	}// end of tornado method

	public static void sandStorm(int r, int c, int d){
		// in location row r, col c
		int nr;
		int nc;
		int sand = map[r][c];
		//System.out.println("Sand is "+sand);
		int sum = 0;
		

		// 5 % location
		// current direction 2 time
		nr = r + dr[d]*2;
		nc = c + dc[d]*2;
		if(nr < 1 || nr > N || nc < 1 || nc > N){
			ANSWER += (int)(sand * 0.05);

		} 
		else{
			map[nr][nc] = map[nr][nc] + (int)(sand * 0.05);
		} 
		sum += (int)(sand * 0.05);

		// 10% location
		// current direction 1 time and 90 rotated 2 way
		int[] rotated_dir;
		if(d%2 == 0) rotated_dir = odd_dir;
		else rotated_dir = even_dir;
		for (int i=0; i<2; i++) {
			nr = r + dr[d] + dr[rotated_dir[i]];
			nc = c + dc[d] + dc[rotated_dir[i]];

			if(nr < 1 || nr > N || nc < 1 || nc > N){
				ANSWER += (int)(sand * 0.10);	
			} 
			else{
				map[nr][nc] = map[nr][nc] + (int)(sand * 0.10); 	
			} 
		}
		sum += (int)(sand * 0.10)*2;


		// 7% location
		// current direction 90 rotated direction 1 time
		for (int i=0; i<2; i++) {
			nr = r + dr[rotated_dir[i]];
			nc = c + dc[rotated_dir[i]];

			if(nr < 1 || nr > N || nc < 1 || nc > N){
				ANSWER += (int)(sand * 0.07);	
			} 
			else{
				map[nr][nc] = map[nr][nc] + (int)(sand * 0.07);	
			} 
		}
		sum += (int)(sand * 0.07)*2;

		// 2% location
		// current direction 90 rotated direction 2 time
		for (int i=0; i<2; i++) {
			nr = r + dr[rotated_dir[i]]*2;
			nc = c + dc[rotated_dir[i]]*2;

			if(nr < 1 || nr > N || nc < 1 || nc > N){
				ANSWER += (int)(sand * 0.02);	
			} 
			else{
				map[nr][nc] = map[nr][nc] + (int)(sand * 0.02);	
			} 
		}
		sum += (int)(sand * 0.02)*2;

		// 1% 
		// current direction backward 1 time and 90 rotated direction 1time
		for (int i=0; i<2; i++) {
			nr = r - dr[d] + dr[rotated_dir[i]];
			nc = c - dc[d] + dc[rotated_dir[i]];

			if(nr < 1 || nr > N || nc < 1 || nc > N){
				ANSWER += (int)(sand * 0.01);	
			} 
			else{
				map[nr][nc] = map[nr][nc] + (int)(sand * 0.01); 	
			} 
		}
		sum += (int)(sand * 0.01)*2;

		// 55 % location 
		// current direction 1 time
		nr = r + dr[d];
		nc = c + dc[d];

		if(nr < 1 || nr > N || nc < 1 || nc > N){
			ANSWER += (sand-sum);	
		} 
		else{
			map[nr][nc] = map[nr][nc] + (sand-sum); 	
		} 

		map[r][c] = 0;
		//System.out.println(ANSWER);
	}// end of sandStorm method

	public static void printMap(){
		for (int r=1; r<=N; r++) {
			for (int c=1; c<=N; c++) {
				System.out.print(map[r][c] + " ");
			}
			System.out.println();
		}
	}
}// end of class 