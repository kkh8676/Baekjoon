// Took 2hr......Shit!!
// using index complicately....
// 14248 KB, 236 ms

import java.io.*;

class boj_1248{
	static int N;
	static int[] seq;
	static int[] sign;
	static StringBuilder sb;
	static String str;

	static boolean found;

	public static void main(String[] args)	throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		sb = new StringBuilder();

		N = Integer.parseInt(br.readLine());

		str = br.readLine();

		sign = new int[N];
		seq = new int[N];

		int line = 0;
		// getting sign 
		for (int i=0; i<N*(N+1)/2; i++) { // line
			if(str.charAt(i) == '-') sign[line] = -1;
			else if(str.charAt(i) == '+') sign[line] = 1;
			else sign[line] = 0;

			for (int n=0; n < N-line; n++) {
				i++;	
			}
			i--;
			line++;
		}

		found = false;

		DFS(N,0);

		System.out.println(sb.toString());
		br.close();

	}// end of main method

	public static void DFS(int capa, int round){
		if(capa == round){
			// came here is the ANSWER!!
			found = true;
			for (int i=0; i<N; i++) {
				sb.append(seq[i]).append(" ");
			}
			sb.append("\n");
			return;
		}// end of ending condition

		for (int n=0; n<=10; n++) {
			// sign multiplied!!

			// if it satisfy current condition, go on.........
			// condition should be coded
			//
			if(check(seq, round, n) && !found){
				//System.out.println("Next round?");
				seq[round] = sign[round] * n;
				DFS(capa, round+1);	
			}
			
		}
	}// end of DFS method

	public static boolean check(int[] seq, int round, int num){
		boolean result = true;
		int line = 0;
		int sum = 0;
		for (int i=0; i<round; i++) {
			sum += seq[i];
		}

		sum += (num*sign[round]);
		char cur;

		int line_sum = 0;
		int remain = sum;

		for (int i=0; i<N*(N+1)/2; i++) { // line

			// check i+round is same with current seq
			// current seq sum 
			//printArr(seq,round);
			//System.out.println("Current Round"+round+ " checking "+ (i+round-line)+" with "+(num*sign[round]));
			
			// System.out.println(cur);
			// System.out.println();
			
			//System.out.println("TOTAL SUM"+sum+ " LINE SUM"+line_sum+" REMAIN"+remain);

			if(remain > 0) cur = '+';
			else if(remain < 0) cur = '-';
			else cur = '0';			
			//System.out.println("Target Char "+str.charAt(i+round-line) + " now "+cur);

			if(str.charAt(i+round-line) != cur){
				result = false;
				break;
			}

			for (int n=0; n < N-line; n++) {
				i++;	
			}
			i--;
			remain -= seq[line];
			line++;
			

			if(line > round) break;
		}


		return result;
	}// end of check method

	public static void printArr(int[] arr, int round){
		for (int i=0; i<round; i++) {
			System.out.print(arr[i]+" ");
		}
		System.out.println();
	}
}// end of class 