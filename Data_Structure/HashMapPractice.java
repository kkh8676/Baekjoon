import java.util.HashMap;

class HashMapPractive{
	public static void main(String[] args){
		HashMap<String, String> hmap = new HashMap<>();

		// Putting Item in the dictionary, HashMap
		// put(key, value)
		hmap.put("people","answer");
		hmap.put("baseball", "sports");
		hmap.put("baseball","sports2");

		// We can get the value of key by get method
		// Hashmap.get(key) -> value
		System.out.println(hmap.get("people"));
		System.out.println(hmap.get("baseball"));

		// If we wanna know there's key in the Map
		System.out.println(hmap.containsKey("people"));


		// Size returning by size() method
		System.out.println(hmap.size());

	}// end of main method
}// end of class 