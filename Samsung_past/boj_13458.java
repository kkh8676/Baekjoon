import java.util.Scanner;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

class boj_13458{
	static int N; // 1<= N <= 1,0000,000
	static int[] A; // 1 <= A <= 1,000,000
	static int B; // 1 <= B <= 1,000,000
	static int C; // 1 <= C <= 1,000,000

	static long ANSWER;

	public static void main(String[] args) throws IOException{
		BufferedReader BR = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter BW = new BufferedWriter(new OutputStreamWriter(System.out));

		N = Integer.parseInt(BR.readLine());

		A = new int[N+1];
		

		String as = BR.readLine();
		String[] AsSplit = as.split(" ");

		for (int n=1; n<=N; n++) {
			A[n] = Integer.parseInt(AsSplit[n-1]);
		}

		String bc = BR.readLine();
		String[] bcSplit = bc.split(" ");

		B = Integer.parseInt(bcSplit[0]);
		C = Integer.parseInt(bcSplit[1]);

		ANSWER = 0;


		for (int n=1; n<=N; n++) {
			int remain = A[n] - B;
			int mainChecker = 1;
			int subChecker;

			if(remain > 0){
				if(remain%C == 0){
					subChecker = remain/C;
				} else{
					subChecker = remain/C + 1;
				}
			} else{
				subChecker = 0;
			}

			
			ANSWER += (subChecker+mainChecker);
		}
		// int max_val = Integer.MAX_VALUE;
		System.out.println(ANSWER);
		// System.out.println(1000000);
		// System.out.println(max_val);

	}// end of main method




}// end of class 