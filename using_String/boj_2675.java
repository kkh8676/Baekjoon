import java.util.Scanner;
import java.io.*;
import java.util.StringTokenizer;

class boj_2675{
	static int T;

	public static void main(String[] args) throws IOException {
		Scanner scan = new Scanner(System.in);
		StringBuilder sb = new StringBuilder();

		T = Integer.parseInt(scan.nextLine());

		for (int t=0; t<T; t++) {
			StringBuilder sb2 = new StringBuilder()	;

			String[] cur = scan.nextLine().split(" ");
			int count = Integer.parseInt(cur[0]);
			String str = cur[1];

			for (int l=0; l<str.length(); l++) {
				for (int c=0; c<count; c++) {
					sb2.append(str.charAt(l));
				}
			}

			sb.append(sb2.toString() + "\n");
		}// end of test case for loop

		System.out.println(sb.toString());
	} // end of main method
}// end of class 