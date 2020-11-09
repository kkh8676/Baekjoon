// 깔끔하게 할 수 있는 방법이 없을까?
// dr dc를 고민해서 고려해보자 그럼 간단히 될거 같다.
// 하드 코딩......
// 이거 안쓰고 하는 방법 없을까?

import java.io.*;


class boj_14500{
	static int N,M;
	static int[][] map;

	static int ANSWER;
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		String[] NM = br.readLine().split(" ");

		N = Integer.parseInt(NM[0]);
		M = Integer.parseInt(NM[1]);

		map = new int[N][M];

		ANSWER = Integer.MIN_VALUE;

		for (int n=0; n<N; n++) {
			String[] curRow = br.readLine().split(" ");
			for (int m=0; m<M; m++) {
				map[n][m] = Integer.parseInt(curRow[m]);
			}
		}// end of getting map information

		for (int n=0; n<N; n++) {
			for (int m=0; m<M; m++) {
				for (int t=1; t<=5; t++) {
					check(n,m,t);
				}
			}
		}

		System.out.println(ANSWER);
	}// end of main method

	public static void check(int r, int c, int type){

		if(type == 1) check_1(r,c);
		else if(type == 2) check_2(r,c);
		else if(type == 3) check_3(r,c);
		else if(type == 4) check_4(r,c);
		else if(type == 5) check_5(r,c);
	}

	// ANSWER modifying, starting point r,c 
	public static void check_1(int r, int c){
		// 2 way
		int[][] check_pts = new int[4][2];
		

		check_pts[0][0] = r;
		check_pts[0][1] = c;

		check_pts[1][0] = r;
		check_pts[1][1] = c+1;

		check_pts[2][0] = r;
		check_pts[2][1] = c+2;

		check_pts[3][0] = r;
		check_pts[3][1] = c+3;

		int temp_answer = 0;
		for (int i=0; i<4; i++) {

			if(check_pts[i][0] < 0 || check_pts[i][0] >= N || check_pts[i][1] < 0 || check_pts[i][1] >= M) break;
			temp_answer = temp_answer + map[check_pts[i][0]][check_pts[i][1]];

			if(i==3){
				ANSWER = ANSWER < temp_answer ? temp_answer : ANSWER;
			}

		}

		check_pts = new int[4][2];
		

		check_pts[0][0] = r;
		check_pts[0][1] = c;

		check_pts[1][0] = r+1;
		check_pts[1][1] = c;

		check_pts[2][0] = r+2;
		check_pts[2][1] = c;

		check_pts[3][0] = r+3;
		check_pts[3][1] = c;

		temp_answer = 0;
		for (int i=0; i<4; i++) {

			if(check_pts[i][0] < 0 || check_pts[i][0] >= N || check_pts[i][1] < 0 || check_pts[i][1] >= M) break;
			temp_answer = temp_answer + map[check_pts[i][0]][check_pts[i][1]];

			if(i==3){
				ANSWER = ANSWER < temp_answer ? temp_answer : ANSWER;
			}

		}// end of second way

	}// end of check_1 method

	public static void check_2(int r, int c){
		// 1 way
		int[][] check_pts = new int[4][2];
		

		check_pts[0][0] = r;
		check_pts[0][1] = c;

		check_pts[1][0] = r;
		check_pts[1][1] = c+1;

		check_pts[2][0] = r+1;
		check_pts[2][1] = c;

		check_pts[3][0] = r+1;
		check_pts[3][1] = c+1;

		int temp_answer = 0;
		for (int i=0; i<4; i++) {

			if(check_pts[i][0] < 0 || check_pts[i][0] >= N || check_pts[i][1] < 0 || check_pts[i][1] >= M) break;
			temp_answer = temp_answer + map[check_pts[i][0]][check_pts[i][1]];

			if(i==3){
				ANSWER = ANSWER < temp_answer ? temp_answer : ANSWER;
			}

		}	
	}// end of check_2 method

	public static void check_3(int r, int c){
		// 8 way
		int[][] check_pts = new int[4][2];
		

		check_pts[0][0] = r;
		check_pts[0][1] = c;

		check_pts[1][0] = r-1;
		check_pts[1][1] = c;

		check_pts[2][0] = r-2;
		check_pts[2][1] = c;

		check_pts[3][0] = r;
		check_pts[3][1] = c+1;

		int temp_answer = 0;
		for (int i=0; i<4; i++) {

			if(check_pts[i][0] < 0 || check_pts[i][0] >= N || check_pts[i][1] < 0 || check_pts[i][1] >= M) break;
			temp_answer = temp_answer + map[check_pts[i][0]][check_pts[i][1]];

			if(i==3){
				ANSWER = ANSWER < temp_answer ? temp_answer : ANSWER;
			}

		}// end of first way

		check_pts = new int[4][2];
		

		check_pts[0][0] = r;
		check_pts[0][1] = c;

		check_pts[1][0] = r;
		check_pts[1][1] = c-1;

		check_pts[2][0] = r;
		check_pts[2][1] = c-2;

		check_pts[3][0] = r-1;
		check_pts[3][1] = c;

		temp_answer = 0;
		for (int i=0; i<4; i++) {

			if(check_pts[i][0] < 0 || check_pts[i][0] >= N || check_pts[i][1] < 0 || check_pts[i][1] >= M) break;
			temp_answer = temp_answer + map[check_pts[i][0]][check_pts[i][1]];

			if(i==3){
				ANSWER = ANSWER < temp_answer ? temp_answer : ANSWER;
			}

		}// end of second way

		check_pts = new int[4][2];
		

		check_pts[0][0] = r;
		check_pts[0][1] = c;

		check_pts[1][0] = r;
		check_pts[1][1] = c-1;

		check_pts[2][0] = r+1;
		check_pts[2][1] = c;

		check_pts[3][0] = r+2;
		check_pts[3][1] = c;

		temp_answer = 0;
		for (int i=0; i<4; i++) {

			if(check_pts[i][0] < 0 || check_pts[i][0] >= N || check_pts[i][1] < 0 || check_pts[i][1] >= M) break;
			temp_answer = temp_answer + map[check_pts[i][0]][check_pts[i][1]];

			if(i==3){
				ANSWER = ANSWER < temp_answer ? temp_answer : ANSWER;
			}

		}// end of third way

		check_pts = new int[4][2];
		

		check_pts[0][0] = r;
		check_pts[0][1] = c;

		check_pts[1][0] = r+1;
		check_pts[1][1] = c;

		check_pts[2][0] = r;
		check_pts[2][1] = c+1;

		check_pts[3][0] = r;
		check_pts[3][1] = c+2;

		temp_answer = 0;
		for (int i=0; i<4; i++) {

			if(check_pts[i][0] < 0 || check_pts[i][0] >= N || check_pts[i][1] < 0 || check_pts[i][1] >= M) break;
			temp_answer = temp_answer + map[check_pts[i][0]][check_pts[i][1]];

			if(i==3){
				ANSWER = ANSWER < temp_answer ? temp_answer : ANSWER;
			}

		} // end of fourth way

		check_pts = new int[4][2];
		

		check_pts[0][0] = r;
		check_pts[0][1] = c;

		check_pts[1][0] = r;
		check_pts[1][1] = c-1;

		check_pts[2][0] = r-1;
		check_pts[2][1] = c;

		check_pts[3][0] = r-2;
		check_pts[3][1] = c;

		temp_answer = 0;
		for (int i=0; i<4; i++) {

			if(check_pts[i][0] < 0 || check_pts[i][0] >= N || check_pts[i][1] < 0 || check_pts[i][1] >= M) break;
			temp_answer = temp_answer + map[check_pts[i][0]][check_pts[i][1]];

			if(i==3){
				ANSWER = ANSWER < temp_answer ? temp_answer : ANSWER;
			}

		}// end of fifth way

		check_pts = new int[4][2];
		

		check_pts[0][0] = r;
		check_pts[0][1] = c;

		check_pts[1][0] = r-1;
		check_pts[1][1] = c;

		check_pts[2][0] = r;
		check_pts[2][1] = c+1;

		check_pts[3][0] = r;
		check_pts[3][1] = c+2;

		temp_answer = 0;
		for (int i=0; i<4; i++) {

			if(check_pts[i][0] < 0 || check_pts[i][0] >= N || check_pts[i][1] < 0 || check_pts[i][1] >= M) break;
			temp_answer = temp_answer + map[check_pts[i][0]][check_pts[i][1]];

			if(i==3){
				ANSWER = ANSWER < temp_answer ? temp_answer : ANSWER;
			}

		} // end of sixth way

		check_pts = new int[4][2];
		

		check_pts[0][0] = r;
		check_pts[0][1] = c;

		check_pts[1][0] = r;
		check_pts[1][1] = c+1;

		check_pts[2][0] = r+1;
		check_pts[2][1] = c;

		check_pts[3][0] = r+2;
		check_pts[3][1] = c;

		temp_answer = 0;
		for (int i=0; i<4; i++) {

			if(check_pts[i][0] < 0 || check_pts[i][0] >= N || check_pts[i][1] < 0 || check_pts[i][1] >= M) break;
			temp_answer = temp_answer + map[check_pts[i][0]][check_pts[i][1]];

			if(i==3){
				ANSWER = ANSWER < temp_answer ? temp_answer : ANSWER;
			}

		}// end of seventh way

		check_pts = new int[4][2];
		

		check_pts[0][0] = r;
		check_pts[0][1] = c;

		check_pts[1][0] = r;
		check_pts[1][1] = c-1;

		check_pts[2][0] = r;
		check_pts[2][1] = c-2;

		check_pts[3][0] = r+1;
		check_pts[3][1] = c;

		temp_answer = 0;
		for (int i=0; i<4; i++) {

			if(check_pts[i][0] < 0 || check_pts[i][0] >= N || check_pts[i][1] < 0 || check_pts[i][1] >= M) break;
			temp_answer = temp_answer + map[check_pts[i][0]][check_pts[i][1]];

			if(i==3){
				ANSWER = ANSWER < temp_answer ? temp_answer : ANSWER;
			}

		}// end of eighth way
	} // end of check_3

	public static void check_4(int r, int c){
		// 4 way
		int[][] check_pts = new int[4][2];
		

		check_pts[0][0] = r;
		check_pts[0][1] = c;

		check_pts[1][0] = r-1;
		check_pts[1][1] = c;

		check_pts[2][0] = r;
		check_pts[2][1] = c+1;

		check_pts[3][0] = r+1;
		check_pts[3][1] = c+1;

		int temp_answer = 0;
		for (int i=0; i<4; i++) {

			if(check_pts[i][0] < 0 || check_pts[i][0] >= N || check_pts[i][1] < 0 || check_pts[i][1] >= M) break;
			temp_answer = temp_answer + map[check_pts[i][0]][check_pts[i][1]];

			if(i==3){
				ANSWER = ANSWER < temp_answer ? temp_answer : ANSWER;
			}

		}// end of first way

		check_pts = new int[4][2];
		

		check_pts[0][0] = r;
		check_pts[0][1] = c;

		check_pts[1][0] = r;
		check_pts[1][1] = c-1;

		check_pts[2][0] = r-1;
		check_pts[2][1] = c;

		check_pts[3][0] = r-1;
		check_pts[3][1] = c+1;

		temp_answer = 0;
		for (int i=0; i<4; i++) {

			if(check_pts[i][0] < 0 || check_pts[i][0] >= N || check_pts[i][1] < 0 || check_pts[i][1] >= M) break;
			temp_answer = temp_answer + map[check_pts[i][0]][check_pts[i][1]];

			if(i==3){
				ANSWER = ANSWER < temp_answer ? temp_answer : ANSWER;
			}

		}// end of second way

		check_pts = new int[4][2];
		

		check_pts[0][0] = r;
		check_pts[0][1] = c;

		check_pts[1][0] = r+1;
		check_pts[1][1] = c;

		check_pts[2][0] = r;
		check_pts[2][1] = c+1;

		check_pts[3][0] = r-1;
		check_pts[3][1] = c+1;

		temp_answer = 0;
		for (int i=0; i<4; i++) {

			if(check_pts[i][0] < 0 || check_pts[i][0] >= N || check_pts[i][1] < 0 || check_pts[i][1] >= M) break;
			temp_answer = temp_answer + map[check_pts[i][0]][check_pts[i][1]];

			if(i==3){
				ANSWER = ANSWER < temp_answer ? temp_answer : ANSWER;
			}

		}// end of third way

		check_pts = new int[4][2];
		

		check_pts[0][0] = r;
		check_pts[0][1] = c;

		check_pts[1][0] = r-1;
		check_pts[1][1] = c;

		check_pts[2][0] = r-1;
		check_pts[2][1] = c-1;

		check_pts[3][0] = r;
		check_pts[3][1] = c+1;

		temp_answer = 0;
		for (int i=0; i<4; i++) {

			if(check_pts[i][0] < 0 || check_pts[i][0] >= N || check_pts[i][1] < 0 || check_pts[i][1] >= M) break;
			temp_answer = temp_answer + map[check_pts[i][0]][check_pts[i][1]];

			if(i==3){
				ANSWER = ANSWER < temp_answer ? temp_answer : ANSWER;
			}

		} // end of fourth way	
	}// end of check_4 method

	public static void check_5(int r, int c){
		// 4 way

		int[][] check_pts = new int[4][2];
		

		check_pts[0][0] = r;
		check_pts[0][1] = c;

		check_pts[1][0] = r;
		check_pts[1][1] = c-1;

		check_pts[2][0] = r;
		check_pts[2][1] = c+1;

		check_pts[3][0] = r-1;
		check_pts[3][1] = c;

		int temp_answer = 0;
		for (int i=0; i<4; i++) {

			if(check_pts[i][0] < 0 || check_pts[i][0] >= N || check_pts[i][1] < 0 || check_pts[i][1] >= M) break;
			temp_answer = temp_answer + map[check_pts[i][0]][check_pts[i][1]];

			if(i==3){
				ANSWER = ANSWER < temp_answer ? temp_answer : ANSWER;
			}

		}// end of first way

		check_pts = new int[4][2];
		

		check_pts[0][0] = r;
		check_pts[0][1] = c;

		check_pts[1][0] = r-1;
		check_pts[1][1] = c;

		check_pts[2][0] = r+1;
		check_pts[2][1] = c;

		check_pts[3][0] = r;
		check_pts[3][1] = c+1;

		temp_answer = 0;
		for (int i=0; i<4; i++) {

			if(check_pts[i][0] < 0 || check_pts[i][0] >= N || check_pts[i][1] < 0 || check_pts[i][1] >= M) break;
			temp_answer = temp_answer + map[check_pts[i][0]][check_pts[i][1]];

			if(i==3){
				ANSWER = ANSWER < temp_answer ? temp_answer : ANSWER;
			}

		}// end of second way

		check_pts = new int[4][2];
		

		check_pts[0][0] = r;
		check_pts[0][1] = c;

		check_pts[1][0] = r;
		check_pts[1][1] = c-1;

		check_pts[2][0] = r;
		check_pts[2][1] = c+1;

		check_pts[3][0] = r+1;
		check_pts[3][1] = c;

		temp_answer = 0;
		for (int i=0; i<4; i++) {

			if(check_pts[i][0] < 0 || check_pts[i][0] >= N || check_pts[i][1] < 0 || check_pts[i][1] >= M) break;
			temp_answer = temp_answer + map[check_pts[i][0]][check_pts[i][1]];

			if(i==3){
				ANSWER = ANSWER < temp_answer ? temp_answer : ANSWER;
			}

		}// end of third way

		check_pts = new int[4][2];
		

		check_pts[0][0] = r;
		check_pts[0][1] = c;

		check_pts[1][0] = r-1;
		check_pts[1][1] = c;

		check_pts[2][0] = r+1;
		check_pts[2][1] = c;

		check_pts[3][0] = r;
		check_pts[3][1] = c-1;

		temp_answer = 0;
		for (int i=0; i<4; i++) {

			if(check_pts[i][0] < 0 || check_pts[i][0] >= N || check_pts[i][1] < 0 || check_pts[i][1] >= M) break;
			temp_answer = temp_answer + map[check_pts[i][0]][check_pts[i][1]];

			if(i==3){
				ANSWER = ANSWER < temp_answer ? temp_answer : ANSWER;
			}

		} // end of fourth way
	}// end of check_4 method
}// end of class 