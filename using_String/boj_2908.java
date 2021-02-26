import java.util.Scanner;
import java.util.StringTokenizer;

class boj_2908{
	
	public static void main(String[] args){
		Scanner scan = new Scanner(System.in);
		StringTokenizer st = new StringTokenizer(scan.nextLine());

		String A = st.nextToken();
		String B = st.nextToken();

		StringBuilder sb = new StringBuilder();
		for (int i=A.length()-1; i>=0; i--) {
			sb.append(A.charAt(i));
		}
		String A2 = sb.toString();
		int a2 = Integer.parseInt(A2);

		sb = new StringBuilder();
		for (int i=B.length()-1; i>=0; i--) {
			sb.append(B.charAt(i));
		}
		String B2 = sb.toString();
		int b2 = Integer.parseInt(B2);

		int ANSWER = a2 > b2 ? a2 : b2;

		System.out.println(ANSWER);

	}// end of main method
}// end of class 