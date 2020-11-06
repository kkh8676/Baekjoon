// basic Dynamic Programming
// 
// i th day optimal score = max ((i th consulting is included), (i th consulting is not included))
// 
// 
// 

import java.util.Scanner;
import java.lang.Math;


class boj_14501{

	static int N;

	static int[] T;
	static int[] P;

	static int[] from;

	public static void main(String[] args){

		Scanner scan = new Scanner(System.in);

		N = scan.nextInt();

		T = new int[N+1];
		P = new int[N+1];
		from = new int[N+1];

		T[0] = 0;
		P[0] = 0;

		for (int r=1; r<=N; r++) {
			T[r] = scan.nextInt();
			P[r] = scan.nextInt();
		}

		// DP process........

		dp();

		
		System.out.println(from[1]);
	}// end of main method

	public static void dp(){

		//System.out.println("dp process is being done!");
		// we go backward decreasing the array index
		for (int r=N; r>0; r--) {
			
			int notIn;
			int in;
			int cand;

			if(r+1 > N){
				notIn = 0;
			} else{
				notIn = from[r+1];
			}

			
			if(r+T[r] > N){
				in = r+T[r] == N+1? P[r] : 0;
			}else{
				in = P[r] + from[r+T[r]];
			}

			from[r] = in > notIn ? in : notIn;

			//System.out.print(r + "th from array value is " + from[r]);
		} // end of for loop
	}
}// end of class