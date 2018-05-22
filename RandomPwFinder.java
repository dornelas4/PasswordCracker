/*
 * @Author Daniel Ornelas
 * RandomPwFinder cracks user passwords using brute force attacks
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.codec.digest.DigestUtils;
import org.paukov.combinatorics3.Generator;

public class RandomPwFinder {
	/*
	 * Random attack tries to break passwords of different lengths by using brute force attacks
	 */
	public static void randomAttack() throws IOException{
		//read user files--modify for your own user files
		File userDataPath = new File("C:\\Users\\Daniel Ornelas\\Downloads\\individual_password_files\\randPwdFile69.txt");
		File testPath = new File("C:\\Users\\Daniel Ornelas\\Downloads\\4351_5352_hm1\\randPwdFile0.txt");
		String hashedPw ="";
		String pw = "";
		try {
			BufferedReader userReader = new BufferedReader(new FileReader(userDataPath));
			ArrayList<User> allUsers = arrayToUser(DictionaryPwFinder.fileToArray(userReader)); //convert user file to user objects
			//generate list with all possible combinations using the provided allowed characters
			List<List<String>> permutations = Generator
					.permutation("a", "b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t",
							"u","v","w","x","y","z","A", "B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T",
							"U","V","W","X","Y","Z","1","2","3","4","5","6","7","8","9","0","-","_")
					.withRepetitions(2) //modify length for passwords of different lengths
					.stream()
					.collect(Collectors.<List<String>>toList()); 
			for(int i =0 ; i<allUsers.size();i++){ //for each user in the list
				for(int j =0; j<permutations.size();j++){//for all possible combinations
					for(int k=1;k<permutations.get(j).size();k++){//number of characters in password, modify to passwordlength-1
						//concatenate the strings in the array provided by permutations
						//for bigger passwords, add permutations.get(j).get(k-n), for shorter delete 
						pw = permutations.get(j).get(k-1)+permutations.get(j).get(k);
						hashedPw = DigestUtils.sha1Hex(pw); // hash the current password
						if(isEqual(hashedPw,allUsers.get(i).hashedPassword)){//compare hashes, print if it matches
							System.out.println(pw + " " + allUsers.get(i).username);
							break;
						}
					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	/*
	 * Array to user takes strings in format "user,hashed password" and stores the user and hash into a User object
	 */
	public static ArrayList<User> arrayToUser(ArrayList<String> fileRead){
		String[] currentUser = new String[2]; // Size 2: user, hashed password
		ArrayList<User> allUsers = new ArrayList<User>();
		for(int i = 0; i < fileRead.size(); i++){
			currentUser = fileRead.get(i).split(",", 2);// separate strings using the "," as separator
			User current = new User(currentUser[0],"",currentUser[1]);//initialize user objects
			allUsers.add(current);
		}
		return allUsers;
	}
	/*
	 * Compare hashed guess and hashed password to see if they match
	 */
	public static boolean isEqual(String hashedGuess,String originalHash){
		return hashedGuess.trim().equals(originalHash.trim());
	}
}
