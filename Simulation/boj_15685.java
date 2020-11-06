// in java, we cannot create the array of generic 
// So instead of array, use ArrayList.......
// using ArrayList.get("index") method we can get element at "index"

// Or just use @SuppressWarnings("unchecked")...........not type_safe ............

import java.util.Deque;
import java.util.LinkedList;
import java.io.*;
import java.util.List;
import java.util.ArrayList;
@SuppressWarnings("unchecked")


class boj_15685{
	static int N;
	static int[][] map;
	static int[][] curve;

	static int ANSWER;

	static int[] dr = {0,-1,0,1};
	static int[] dc = {1,0,-1,0};

	
	public static void main(String[] args) throws IOException{
		map = new int[101][101];

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		N = Integer.parseInt(br.readLine());

		curve = new int[N][4];
		// 1 index x, column number
		// 0 index y, row number 
		// 2 index dir
		// 3 index generation

		for (int n=0; n<N; n++) {
			String[] info = br.readLine().split(" ");
			curve[n][0] = Integer.parseInt(info[1]);
			curve[n][1] = Integer.parseInt(info[0]);
			curve[n][2] = Integer.parseInt(info[2]);
			curve[n][3] = Integer.parseInt(info[3]);
		}

		for (int n=0; n<N; n++) {
			make_curve(curve[n]);
		}

		count();

		System.out.println(ANSWER);


	}// end of main method

	public static void make_curve(int[] oneCurve){
		int r = oneCurve[0];
		int c = oneCurve[1];
		int d = oneCurve[2];
		int g = oneCurve[3];

		Deque<Integer>[] dqs = new Deque[g+1];
		dqs[0] =  new LinkedList<Integer>();
		dqs[0].add(d);

		for (int cg=1; cg<=g; cg++) {
			dqs[cg] = (Deque<Integer>)new LinkedList<Integer>();
			Deque<Integer> pdq = (Deque<Integer>)dqs[cg-1];
			while(!pdq.isEmpty()){
				int pd = pdq.removeLast();
				int nd = (pd+1)%4;

				dqs[cg].addFirst(pd);
				dqs[cg].addLast(nd);

			}
		}

		// g index deque is not empty
		int nr = r;
		int nc = c;
		map[nr][nc] = 1;
		Deque<Integer> ldq = ( Deque<Integer>) dqs[g];
		while( !ldq.isEmpty() ){
			int nd = ldq.removeFirst();
			nr = nr + dr[nd];
			nc = nc + dc[nd];
			map[nr][nc] = 1;

		}


	}// end of make_curve 

	public static void count(){
		for (int row=0; row<100; row++) {
			for (int col=0; col<100; col++) {
				if(map[row][col] == 1 && map[row+1][col] == 1 && map[row][col+1] == 1 && map[row+1][col+1] == 1) ANSWER++;
			}
		}
	}// end of count

}// end of class 