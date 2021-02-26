// Success in 1hr 4min 
// 11664kb, 76ms....

import java.util.StringTokenizer;
import java.io.*;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Stack;
import java.lang.Math;

class boj_16637_210223{
	static int N, max_num, max_oper;
	static int ANSWER;

	static int[] num;
	static String[] operator;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		N = Integer.parseInt(br.readLine());
		String[] str = br.readLine().split("");
		max_num = N/2 + 1;
		max_oper = N/2;

		num = new int[10];
		operator = new String[9];

		for (int n=0; n<N; n++) {
			num[n/2] = Integer.parseInt(str[n]);
			n++;
			if(n >= N) break;
			operator[n/2] = str[n];
		}// end of getting information

		
		ANSWER = Integer.MIN_VALUE;

		// solving algorithm
		DFS(N/2,0,num[0]);

		System.out.println(ANSWER);
		br.close();				

	}// end of main method

	public static void DFS(int capa, int round, int prev){
		//System.out.println("in capa"+capa+" DFS in round"+round + " with score"+prev);
		if(capa <= round){
			ANSWER = ANSWER < prev? prev : ANSWER;
			//System.out.println(prev);
			return;
		}// end of ending condition

		// visiting next vertex process!!
		// 1. 괄호 추가하지 않는 경우
		//System.out.println("Without parenthesis");
		DFS(capa, round+1, operate(prev, num[round+1], operator[round]));

		// 2. 괄호 추가하는 경우

		if(round+2 <= capa ){
			//System.out.println("With parenthesis");
			DFS(capa, round+2, operate(prev, operate(num[round+1], num[round+2], operator[round+1]), operator[round]));
		} 



	}// end of DFS method

	public static int operate(int num1, int num2, String operator){
		if(operator.equals("+")) return num1 + num2;
		else if(operator.equals("-")) return num1 - num2;
		else return num1 * num2;
	}// end of operatoe

}// end of class