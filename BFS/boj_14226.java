// 24min Failed...........
// 40min memory overflow......
// Maybe BFS adding Queue problem........

import java.util.Scanner;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Arrays;

class boj_14226{
	static int S;
	static int ANSWER;
	
	static int[][] isV;

	static int[] nextLoc;

	public static void main(String[] args){
		Scanner scan = new Scanner(System.in);
		S = scan.nextInt();

		isV = new int[1025][1025];
		
		isV[1][0] = 1;

		nextLoc = new int[2];

		BFS();

		System.out.println(ANSWER);

	}// end of main method

	public static void BFS(){
		int screen = 1;
		int clip = 0;
		int time  = 0;

		Queue<Integer> q = new LinkedList<>();

		q.add(screen);
		q.add(clip);
		q.add(time);

		while(!q.isEmpty()){
			int curScreen = q.remove();
			int curClip = q.remove();
			int curTime = q.remove();

			if(curScreen == S){
				//System.out.println(S+" is founded!!!");
				ANSWER = curTime;
				break;
			}

			for (int i=0; i<3; i++) {
				if(curClip == 0 && i == 1) continue;
				move(i, curScreen, curClip);

				int nsc = nextLoc[0];
				int ncl = nextLoc[1];

				if(nsc < 0 || nsc > 1024 || ncl < 0 || ncl > 1024) continue;
				if(isV[nsc][ncl] != 0 ) continue;

				q.add(nsc); q.add(ncl); q.add(curTime+1);
				isV[nsc][ncl] = 1;
			}

		}// end of while loop
	}// end of BFS method

	public static void move(int type, int screen, int clip){
		int nextSc;
		int nextCl;

		if(type == 0){
			// COPY method
			nextSc = screen;
			nextCl = screen;
		} else if(type == 1){
			// PASTE method
			nextSc = screen + clip;
			nextCl = clip;
		} else{
			// DELETE method
			nextSc = screen - 1;
			nextCl = clip;
		}

		nextLoc[0] = nextSc;
		nextLoc[1] = nextCl;
	}// end if move method
}// end of class