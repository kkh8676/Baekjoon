import java.util.regex.Pattern;
import java.util.regex.Matcher;


class regex_test{
	public static void main(String[] args){
		String pattern = "^[A-Z]*$"; //only alphabet lower case......
		String val = "abcedf";

		boolean regex = Pattern.matches(pattern, val);
		System.out.println(regex);
	}// end of main method
}// end of class