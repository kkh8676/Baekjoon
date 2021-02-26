// Success 35min.....
// 44076 KB, 88ms
// At first, Used isV dimension 3 ; isV[ A water ][ B water ][ C water ]
// But, water sum is always same which value is Capability of C
// So, just use isV dimension 2
// it can reduce much memory usage.

// optimzed version, 11752 KB, 76ms....

import java.util.StringTokenizer;
import java.io.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.PriorityQueue;


class boj_2251{
	static int A,B,C;
	static int[] curWater, capa;
	static int[][] isV;
	static Queue<Integer> q;
	static PriorityQueue<Integer> pq;

	public static void main(String[] args) throws IOException{	
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		StringBuilder sb = new StringBuilder();

		capa = new int[3];
		A = Integer.parseInt(st.nextToken());
		B = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());
		capa[0] = A; capa[1] = B; capa[2] = C;

		isV = new int[A+1][C+1];
		curWater = new int[3];
		BFS(A,B,C);

		int prev = -1;
		while(!pq.isEmpty()){
			int removed = pq.remove();
			if(prev != removed){
				sb.append(removed).append(" ");
				prev = removed;	
			}
		}

		System.out.println(sb.toString());
		br.close();
	}// end of main method

	public static void BFS(int a, int b, int c){
		// a, b, c are capability of each pot....
		q = new LinkedList<>();
		pq = new PriorityQueue<>();

		q.add(0); q.add(0); q.add(c);
		pq.add(c);
		isV[0][c] = 1;

		while(!q.isEmpty()){
			int ca = q.remove(); int cb = q.remove(); int cc = q.remove();
			curWater[0] = ca; curWater[1] = cb; curWater[2] = cc; 

			for (int i=0; i<3; i++) {
				for (int j=0; j<3; j++) {
					if(i==j) continue;

					int[] nWater = pour(curWater, i, j);
					int na = nWater[0]; int nb = nWater[1]; int nc = nWater[2];

					if(isV[na][nc] == 1) continue;

					q.add(na); q.add(nb); q.add(nc);
					isV[na][nc] = 1;

					// if A is empty, add possible c water nc
					if(na == 0) pq.add(nc);
					// if just adding nc can be repeated...... PriorityQueue can add repeated data....

				}
			}// end of outer for loop
		}// end of while loop
	}// end of BFS method

	public static int[] pour(int[] cur, int from, int to){
		// from same to case do not come here!!
		int[] next = new int[3];
		
		int min = cur[from] < capa[to] - cur[to]? cur[from] : capa[to] - cur[to];

		for (int i=0; i<3; i++) {
			if(i == from) next[from] = cur[from] - min;
			else if(i == to) next[to] = cur[to] + min;
			else next[i] = cur[i];
		}

		return next;

	}// end of pour index
}// end of class 