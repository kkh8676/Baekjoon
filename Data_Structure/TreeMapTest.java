import java.util.TreeMap;
import java.util.Map.Entry;

class TreeMapTest{
	public static void main(String[] args){
		TreeMap<Integer, String> map = new TreeMap<>();

		map.put(1,"one");
		map.put(2,"two");

		for (Entry<Integer,String> entry : map.entrySet() ) {
			System.out.println("[KEY]:"+entry.getKey()+" [VALUE]:"+entry.getValue());
		}

		map.put(1,"ONE");
		for (Entry<Integer,String> entry : map.entrySet() ) {
			System.out.println("[KEY]:"+entry.getKey()+" [VALUE]:"+entry.getValue());
		}

		for (Integer i : map.keySet() ) {
			System.out.println("[KEY]:"+i);
		}
	}// end of main method
}// end of TreeMap class 