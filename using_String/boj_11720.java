import java.util.StringTokenizer;
import java.io.*;
import java.util.Scanner;


class boj_11720{
	static int N;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		Scanner scan = new Scanner(System.in);

		N = Integer.parseInt(scan.nextLine());
		String str = scan.nextLine();
		String[] strArr = str.split("");

		int sum = 0;
		for (int n=0; n<N; n++) {
			sum += Integer.parseInt(strArr[n]);
		}

		System.out.println(sum);
	}// end of main method
}// end of class 