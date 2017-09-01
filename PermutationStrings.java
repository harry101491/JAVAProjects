import java.util.HashMap;

// program to find whether two string are permutation of each other
public class Main {
    public static void main(String args[]) {
        String str1 = "Harshit";
        String str2 = "arshitP";
        if(isPermutation(str1, str2)) {
            System.out.println("two given strings are permutation of each other");
        }
        else {
            System.out.println("two given strings are not permutation of each other");
        }
    }

    public static boolean isPermutation(String str1, String str2) {
        if(str1.length() != str2.length()) {
            return false;
        }
        else if (str1.length() == 0){
            return false;
        }
        else {
            String toLower1 = str1.toLowerCase();
            String toLower2 = str2.toLowerCase();
            char[] arr1 = toLower1.toCharArray();
            char[] arr2 = toLower2.toCharArray();
            HashMap<Character, Integer> map = new HashMap<>();

            // populating the frequency of the first character array into the hash map
            for(int i=0;i<arr1.length;i++) {
                if(map.containsKey(arr1[i])) {
                    map.replace(arr1[i], map.get(arr1[i])+1);
                }
                else {
                    map.put(arr1[i], 1);
                }
            }


            // finding whether all the elements are present or not inside the hash map
            for(int i=0;i<arr2.length;i++) {
                if(map.containsKey(arr2[i])) {
                    if(map.get(arr2[i]) == 1) {
                        map.remove(arr2[i]);
                    }
                    else {
                        map.replace(arr2[i], map.get(arr2[i])-1);
                    }
                }
                else {
                    return false;
                }
            }

            if(map.size() == 0) {
                return true;
            }
            else {
                return false;
            }

        }
    }
}
