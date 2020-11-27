// Took 1hr 3min
// Got damn!!!!

// How can I solve it neatly?

import java.io.*;
import java.util.StringTokenizer;
import java.util.Arrays;

class boj_14890_v2{
	static int N,L;
	static int[][] board;
	static int[][] slide;

	static int ANSWER;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		L = Integer.parseInt(st.nextToken());

		board = new int[N][N];
		slide = new int[N][N];

		for (int r=0; r<N; r++) {
			st = new StringTokenizer(br.readLine());
			for (int c=0; c<N; c++) {
				board[r][c] = Integer.parseInt(st.nextToken());
			}
		}// end of getting information

		ANSWER = 0;

		for (int r=0; r<N; r++) {
			r_check(r);
		}

		for (int c=0; c<N; c++) {
			c_check(c);
		}

		System.out.println(ANSWER);

	}// end of main method

	public static void r_check(int r){
		// checking rth row
		// if possible ANSWER++

		boolean result = true;
		
		int c=0;
		int prev = board[r][c];
		int cnt = 1;
		int diff = Integer.MAX_VALUE;

		c++;

		while(c<N) {

			int current = board[r][c];
			prev = board[r][c-1];
			diff = current - prev;

			if(!result) break;

			if(diff == 0){
				cnt++;
				c++;
				continue;

			} else if(diff == 1 && cnt >= L){

				// check the road 
				for (int l=1; l<=L; l++) {
					slide[r][c-l] = 1;
				}

				cnt = 1;
				prev = board[r][c];
				c++;
				continue;

			} else if(diff == -1 && forward_check(r,c)){

				for (int l=0; l<L; l++) {
					slide[r][c+l] = 1;
				}

				cnt = 0;
				
				c = c+L;
				continue;

			} else{
				result = false;
				break;
			}

		}// end of while

		if(result) ANSWER++;
		else Arrays.fill(slide[r], 0);

	}// end of r check method

	public static void c_check(int c){
		// checking cth column
		// if possible ANSWER++
		int[] original_slide = new int[N];
		for (int r=0; r<N; r++) {
			original_slide[r] = slide[r][c];
		}

		boolean result = true;
		
		int r=0;
		int prev = board[r][c];
		int cnt = 1;
		int diff = Integer.MAX_VALUE;

		r++;

		while(r<N) {

			int current = board[r][c];
			prev = board[r-1][c];
			diff = current - prev;

			if(!result) break;

			if(diff == 0){
				cnt++;
				r++;
				continue;

			} else if(diff == 1 && cnt >= L){

				for (int l=1; l<=L; l++) {
					slide[r-l][c] = 1;
				}

				cnt = 1;
				prev = board[r][c];
				r++;
				continue;

			} else if(diff == -1 && c_forward_check(r,c)){

				for (int l=0; l<L; l++) {
					slide[r+l][c] = 1;
				}

				cnt = 0;
				
				r = r+L;
				continue;

			} else{
				result = false;
				break;
			}

		}// end of while

		if(result) ANSWER++;
		else{
			for (int ri=0; ri<N; ri++) {
				slide[ri][c] = original_slide[ri];
			}
		}

	}// end of c check method

	public static boolean forward_check(int r, int c){
		// Starts at row r and row c
		boolean result = true;
		int cnt = 1;
		int same = board[r][c];
		int cIndex = c;

		for (int l=1; l<=L; l++) {
			int nc = cIndex + l;
			if(nc >= N){
				
				break;
			}

			if(same != board[r][nc]) break;

			cnt++;
		}

		if(cnt < L) result = false;

		return result;

	}

	public static boolean c_forward_check(int r, int c){
		// Starts at column c and row r
		boolean result = true;
		int cnt = 1;
		int same = board[r][c];
		int rIndex = r;

		for (int l=1; l<=L; l++) {
			int nr = rIndex + l;
			if(nr >= N) break;

			if(same != board[nr][c]) break;

			cnt++;
		}

		if(cnt < L) result = false;

		return result;
	}// end of c_forward check


}// end of class 