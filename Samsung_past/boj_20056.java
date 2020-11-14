// Took 1hr 50 min

import java.io.*;
import java.util.Queue;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Arrays;
//@SuppressWarnings ("unchecked")

class boj_20056{
	static int[] dr = {-1,-1,0,1,1,1,0,-1};
	static int[] dc = {0,1,1,1,0,-1,-1,-1};

	static int[] odd_d = {1,3,5,7};
	static int[] even_d = {0,2,4,6};

	static int N,M,K;
	static int[][] planet;
	static LinkedList<Integer>[][] map;

	static Queue<Integer> nq;

	


	static int ANSWER;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		String[] nmk = br.readLine().split(" ");
		N = Integer.parseInt(nmk[0]);
		M = Integer.parseInt(nmk[1]);
		K = Integer.parseInt(nmk[2]);

		planet = new int[M+1][5];

		for (int m=1; m<=M; m++) {
			String[] info = br.readLine().split(" ");
			planet[m][0] = Integer.parseInt(info[0]); // row
			planet[m][1] = Integer.parseInt(info[1]); // column
			planet[m][2] = Integer.parseInt(info[2]); // mass 
			planet[m][3] = Integer.parseInt(info[4]); // direction
			planet[m][4] = Integer.parseInt(info[3]); // speed
		}

		map = new LinkedList[N+1][N+1];
		
		nq = new LinkedList<Integer>();
		

		for (int k=0; k<K; k++) {
			initialize_map();
			move();
			collision();

			
			int index = 1;
			planet = new int[M+1][5];
			while(!nq.isEmpty()){
				int r = nq.remove();
				int c = nq.remove();
				int mass = nq.remove();
				int dir = nq.remove();
				int speed = nq.remove();

				planet[index][0] = r;
				planet[index][1] = c;
				planet[index][2] = mass;
				planet[index][3] = dir;
				planet[index][4] = speed;
				//System.out.println("R"+r+" C"+c+" M"+mass+" D"+dir+" S"+speed);
				index++;
			}// end of while

		}// end of action process 

		ANSWER = 0;
		for (int m=1; m<=M; m++) {
			ANSWER += planet[m][2];
		}

		System.out.println(ANSWER);
	}// end of main method

	public static void move(){
		// total planet move
		for (int m=1; m<=M; m++) {
			int cr = planet[m][0];
			int cc = planet[m][1];
			int cm = planet[m][2];
			int cd = planet[m][3];
			int cs = planet[m][4];

			// moving each planet and add that location queue!!
			int temp_dr = dr[cd];
			int temp_dc = dc[cd];

			if(temp_dr < 0) temp_dr = temp_dr + N;
			if(temp_dc < 0) temp_dc = temp_dc + N;

			int nr = temp_dr == 0 ? cr : (cr + (temp_dr*cs))%N ;
			int nc = temp_dc == 0 ? cc : (cc + (temp_dc*cs))%N ;
			nr = nr == 0? N : nr;
			nc = nc == 0? N : nc;
			//System.out.println("R"+cr+" C"+cc+" M"+cm+" D"+cd+" S"+cs);
			//System.out.println("Next R"+nr + " Next C"+nc);



			map[nr][nc].add(cm);
			map[nr][nc].add(cd);
			map[nr][nc].add(cs);
			//System.out.println("Added in map R"+nr+" C"+nc);

		}


	}// end of move method

	public static void collision(){
		int temp_M = 0;
		for (int row=1; row<=N; row++) {
			for (int col=1; col<=N; col++) {
				
				//System.out.println("Row is "+row+" Col is "+col);
				if(map[row][col].isEmpty()) continue;
				if(map[row][col].size()/3 == 1){
					int cm = map[row][col].remove();
					int cd = map[row][col].remove();
					int cs = map[row][col].remove();

					nq.add(row);
					nq.add(col);
					nq.add(cm);
					nq.add(cd);
					nq.add(cs);

					temp_M += 1;
					continue;
				}

				int sum_mass = 0;
				int sum_speed = 0;
				int num = 0;
				boolean even = false;
				boolean odd = false;

				while(!map[row][col].isEmpty()){
					num++;
					int cm = map[row][col].remove();
					int cd = map[row][col].remove();
					int cs = map[row][col].remove();

					if(cd%2 == 0) even = true;
					if(cd%2 == 1) odd = true;

					sum_mass += cm;
					sum_speed += cs;
				}// end of while loop

				int new_mass = sum_mass / 5;
				int new_speed = sum_speed / num;
				// System.out.println("New Speed is "+new_speed);
				// System.out.println();
				int[] new_dir;

				if(new_mass == 0) continue;

				if((!even && odd) || (even && !odd)) new_dir = even_d;
				else new_dir = odd_d;

				for (int i=0; i<4; i++) {
					nq.add(row);
					nq.add(col);
					nq.add(new_mass);
					nq.add(new_dir[i]);
					nq.add(new_speed);
					//System.out.println("adding row is "+row+" col is "+col);
				}

				temp_M = temp_M + 4;

			}// end of column for loop
		}// end of row for loop

		M = temp_M;
	} // end of collision method

	public static void initialize_map(){
		for (int n=1; n<=N; n++) {
			for (int m=1; m<=N; m++) {
				map[n][m] = new LinkedList<Integer>();
			}
		}
	}// end of initialize_map method
}// end of class 