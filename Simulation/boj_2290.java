// Took 57 min
// just using Strings........
// printing String problem
// 그냥 문자열 출력하는 문제, 별 다른 알고리즘은 필요하지 않다.

import java.io.*;
import java.util.Arrays;

class boj_2290{
	static int S;
	static String str;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		String[] S_str = br.readLine().split(" ");

		S = Integer.parseInt(S_str[0]);
		str = S_str[1];
		String[] num = str.split("");

		// making that array 
		int num_L = num.length;

		String[][][] total = new String[num_L][2*S+3][S+2];

		// making arrays
		for (int l=0; l<num_L; l++) {
			total[l] = new String[2*S+3][S+2];

			int cur_Num = Integer.parseInt(num[l]);

			make_ary(cur_Num , total[l]);
		}

		// Printing arrays 

		for (int r=0; r<2*S+3; r++) {
			for (int l=0; l<num_L; l++) {
				if(l!= num_L){
					for (int c=0; c<S+2; c++) {
						System.out.print(total[l][r][c]);
					}
					System.out.print(" ");
				}
				else{
					for (int c=0; c<S+2; c++) {
						System.out.print(total[l][r][c]);
					}
				}
			}
			if(r!=2*S+2) System.out.println();
		}

	}// end of main method

	public static void upper(String[][] ary){
		// 0 row, column 1,2,3,....,s
		//using - String
		for (int c=1; c<S+1; c++) {
			ary[0][c] = "-";
		}
	}// end of upper method

	public static void middle(String[][] ary){
		// s+1 row, column 1,2,3,....,s
		//using - String
		for (int c=1; c<S+1; c++) {
			ary[S+1][c] = "-";
		}
	}// end of middle method

	public static void lower(String[][] ary){
		// 2s+2 row, column 1,2,3.......,s
		//using - String
		for (int c=1; c<S+1; c++) {
			ary[2*S+2][c] = "-";
		}
	}// end of lower method

	public static void left_upper(String[][] ary){
		// 0 column, 1~s row
		// using | string
		for (int r=1; r<S+1; r++) {
			ary[r][0] = "|";
		}
	}// end of left upper method

	public static void right_upper(String[][] ary){
		// s+1 column, 1~s row 
		// using | string
		for (int r=1; r<S+1; r++) {
			ary[r][S+1] = "|";
		}
	}// end of right upper method

	public static void left_lower(String[][] ary){
		// 0 column, s+2 ~ 2s+1 row
		// using | string
		for (int r=S+2; r<2*S+2; r++) {
			ary[r][0] = "|";
		}
	}// end of left lower method

	public static void right_lower(String[][] ary){
		// s+1 column, s+2 ~ 2s+1 row
		// using | string
		for (int r=S+2; r<2*S+2; r++) {
			ary[r][S+1] = "|";
		}
	}// end of right_lower

	public static void make_ary(int num, String[][] ary){
		for (int r=0; r<ary.length; r++) {
			Arrays.fill(ary[r]," ");
		}

		//System.out.println("Making "+num);
		if(num == 0){

			upper(ary);
			lower(ary);
			left_upper(ary);
			left_lower(ary);
			right_upper(ary);
			right_lower(ary);

		} else if(num == 1){

			//upper(ary);
			//middle(ary);
			//lower(ary);
			//left_upper(ary);
			//left_lower(ary);
			right_upper(ary);
			right_lower(ary);

		} else if(num == 2){

			upper(ary);
			middle(ary);
			lower(ary);
			//left_upper(ary);
			left_lower(ary);
			right_upper(ary);
			//right_lower(ary);

		} else if(num == 3){

			upper(ary);
			middle(ary);
			lower(ary);
			//left_upper(ary);
			//left_lower(ary);
			right_upper(ary);
			right_lower(ary);

		} else if(num == 4){

			//upper(ary);
			middle(ary);
			//lower(ary);
			left_upper(ary);
			//left_lower(ary);
			right_upper(ary);
			right_lower(ary);

		} else if(num == 5){

			upper(ary);
			middle(ary);
			lower(ary);
			left_upper(ary);
			//left_lower(ary);
			//right_upper(ary);
			right_lower(ary);

		} else if(num == 6){

			upper(ary);
			middle(ary);
			lower(ary);
			left_upper(ary);
			left_lower(ary);
			//right_upper(ary);
			right_lower(ary);

		} else if(num == 7){

			upper(ary);
			//middle(ary);
			//lower(ary);
			//left_upper(ary);
			//left_lower(ary);
			right_upper(ary);
			right_lower(ary);

		} else if(num == 8){

			upper(ary);
			middle(ary);
			lower(ary);
			left_upper(ary);
			left_lower(ary);
			right_upper(ary);
			right_lower(ary);

		} else{

			upper(ary);
			middle(ary);
			lower(ary);
			left_upper(ary);
			
			right_upper(ary);
			right_lower(ary);
			
		}
	} // end of making ary
}// end of class 
