import java.util.Scanner;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Arrays;

class boj_12851{
	static int N,K;
	static int[] isV;
	static int[] d;

	static Queue<Integer> q;

	static int TIME;

	public static void main(String[] args){
		Scanner scan = new Scanner(System.in);
		N = scan.nextInt(); K = scan.nextInt();
		q = new LinkedList<>();

		isV = new int[100001];
		d   = new int[100001];

		Arrays.fill(d, Integer.MAX_VALUE);
		TIME = Integer.MAX_VALUE;

		BFS(N);

		System.out.println(TIME);
		System.out.println(isV[K]);
	}// end of main method

	public static void BFS(int start){
		q.add(start); // location
		q.add(0); // time 
		isV[start] = 1;
		d[start] = 0;

		while(!q.isEmpty()){
			int curLoc = q.remove();
			int curTime = q.remove();

			// Need ending condition
			// if(curTime > TIME) break;

			if(curLoc == K){
				//isV[K]++;
				TIME = curTime;
				//break;
				//d[K] = TIME;
				
			} 
			
			for (int i=0; i<3; i++) {
				//if(curLoc == 1 && i == 2) continue;

				int next = move(curLoc, i);
				int nextTime = curTime+1;

				// Out of bounds condition
				if(next < 0 || next > 100000) continue;

				// if nextTime is larger than TIME don't need to let in the Queue.......
				if(nextTime <= d[next]){
					q.add(next);
					q.add(nextTime);
					isV[next]++;
					d[next] = nextTime;
				}
				// if(isV[next]==0){
				// 	q.add(next);
				// 	q.add(nextTime);
				// 	isV[next]=1;
				// }
			}
		}// end of while loop
	}// end of start method

	public static int move(int loc, int i){
		if(i == 0) return loc-1;
		else if(i == 1) return loc+1;
		else return loc * 2;
	}// end of move method
	
}// end of class 

// example
/*
5 100000
19
4

5 1000
11
2

5 17
4 
2

1 3
2 
2

1 4
2 
2

72 394
27
2

1000 0
1000
1

*/

// ANSWER
/*
*/