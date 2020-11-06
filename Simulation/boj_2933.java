// Failed.........SSiBAL
// 반례 찾아서 겨우 완성했네......
// 한 두시간 반 걸린 듯.
// 거의 뭐 네시간이라고 보면 됨.
// 전체탐색으로 나누어진 클러스터 찾고 내려갈 칸 수 찾는 거 오래 걸림.

// 떨어질 때 최소 2칸이상 떨어져있으나 2칸 다음 미네랄 또는 바닥 이라면 distance는 한칸만 움직이면 된다. -> 이게 좀 헷갈림.
// Queue를 이용한 방법 좀 깔끔하게 짜고 싶은데 어떻게 짜야할까
// Generic array로 선언하지 않고 모두 써주면 unchecked 발생하지 않음........

import java.io.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Arrays;
//@SuppressWarnings("unchecked")

class boj_2933{
	static int R,C,L;
	static int[][] cave;
	static int[] stick;

	static int[] dr = {-1,1,0,0};
	static int[] dc = {0,0,-1,1};

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		String[] rc = br.readLine().split(" ");
		R = Integer.parseInt(rc[0]);
		C = Integer.parseInt(rc[1]);
		cave = new int[R+1][C+1];

		for (int r=R; r>0; r--) {
			String[] curRow = br.readLine().split("");
			for (int c=1; c<=C; c++) {
				if(curRow[c-1].equals(".")) cave[r][c] = 0;
				else cave[r][c] = 1;

			}
		}// end of getting cave information

		L = Integer.parseInt(br.readLine());
		stick = new int[L+1];
		String[] sticks = br.readLine().split(" ");
		for (int l=1; l<=L; l++) {
			stick[l] = Integer.parseInt(sticks[l-1]);
		}// end of getting stick information

		for (int l=1; l<=L; l++) {
			int[] deleted = throw_stick(stick[l], l-1);
			//System.out.println("After stick throw");
			//printCave();

			Queue<Integer> upQ = clustering(deleted[0], deleted[1]);

			drop_cluster(upQ);

			//System.out.println();
			//System.out.println("After dropping");
			//printCave();
		}
		//System.out.println();
		printCave();
	}// end of main method

	public static int[] throw_stick(int r, int round){
		int dir;
		if(round%2==0) dir = 0; //left to right
		else dir = 1; // right to left
		int[] deleted = {-1,-1};
		if(dir==0){
			for (int c=1; c<=C; c++) {
				if(cave[r][c] == 1){
					cave[r][c] = 0;
					deleted[0] = r;
					deleted[1] = c;
					break;
				}
			}
		} else{
			for(int c=C; c>0; c--){
				if(cave[r][c] == 1){
					cave[r][c] = 0;
					deleted[0] = r;
					deleted[1] = c;
					break;
				}
			}
		}
		//System.out.println(deleted[0] + " " + deleted[1]);
		return deleted;
	}// end of throw_stick method

	public static Queue<Integer> clustering(int delR, int delC){
		int[][] isV = new int[R+1][C+1];

		Queue<Integer> q;
		Queue<Integer> cq = new LinkedList<>();

		for (int i=0; i<4; i++) {
			int nr = delR + dr[i];
			int nc = delC + dc[i];
			if(nr > R || nr < 1 || nc > C || nc < 1) continue;
			boolean grounded = false;
			boolean existQ = false;

			if(cave[nr][nc] == 1 && isV[nr][nc] == 0){
				// BFS process and making Queue
				existQ = true;
				q = new LinkedList<>();
				cq = new LinkedList<>();
				q.add(nr);
				q.add(nc);

				while(!q.isEmpty()){
					int cr = q.remove();
					int cc = q.remove();

					cq.add(cr);
					cq.add(cc);

					if(cr==1) grounded = true;

					for (int p=0; p<4; p++) {
						int nnr = cr + dr[p];
						int nnc = cc + dc[p];
						if(nnr > R || nnr < 1 || nnc > C || nnc < 1) continue;
						if(cave[nnr][nnc] == 1 && isV[nnr][nnc] == 0){
							isV[nnr][nnc] = 1;
							q.add(nnr);
							q.add(nnc);
						}
					}
				}// end of while loop

			}// end of if loop

			if(existQ && !grounded) return cq;

		}// end of for loop

		return new LinkedList<Integer>();
	}// end of clustering method



	public static void drop_cluster(Queue<Integer> drop_q){
		if(drop_q.size() == 0) return;

		int[] minRow = new int[C+1];
		Arrays.fill(minRow, Integer.MAX_VALUE);

		Queue<Integer> copiedQ = new LinkedList<>();

		while(!drop_q.isEmpty()){
			int cr = drop_q.remove();
			int cc = drop_q.remove();
			copiedQ.add(cr);
			copiedQ.add(cc);

			if(cr < minRow[cc] ) minRow[cc] = cr;
		}

		// How many can go?
		int minD = Integer.MAX_VALUE;
		//System.out.println();
		//System.out.println("minD is " + minD);
		for (int c=1; c<=C; c++) {
			if(minRow[c] != Integer.MAX_VALUE){
				// checking below cc 
				int distance = 1;
				for (int r=minRow[c]; r>0; r--) {
					if(cave[r-2][c] == 1 || r-2 == 0) break;

					distance++;
				}
				//System.out.println("distance is " + distance);
				minD = minD > distance? distance : minD;
			}
		}

		Queue<Integer> copiedQ2 = new LinkedList<>();
		while(!copiedQ.isEmpty()){
			int cr = copiedQ.remove();
			int cc = copiedQ.remove();

			copiedQ2.add(cr);
			copiedQ2.add(cc);

			cave[cr][cc] = 0;
		}

		while(!copiedQ2.isEmpty()){
			int cr = copiedQ2.remove();
			int cc = copiedQ2.remove();

			cave[cr-minD][cc] = 1;
		}


	}// end of drop_cluster method

	public static void printCave(){
		for (int r=R; r>0; r--) {
			for (int c=1; c<=C; c++) {
				String element;
				if(cave[r][c] == 0) element = ".";
				else element = "x";
				System.out.print(element);
			}
			System.out.println();
		}
	}// end of printCave method
}// end of class 

/**
5 5 
xxxxx
x....
xxxxx
x....
x....
10
1 2 3 4 5 1 2 3 4 5

10 10
xxxxxxxxxx
....x.....
...xxx....
.....x....
....xx....
.....x....
xxxxxx....
..x.......
.xxxx.....
...xxxxxxx
10
9 8 7 6 5 4 3 2 1 1


// miss
3 3
...
..x
x.x
2
1 1


4 4
...x
..xx
xx.x
x..x
2
1 1

4 4
...x
..xx
.xxx
xxxx
10
1 2 3 4 1 2 3 4 3 4

**/