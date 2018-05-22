/*
 * @Author Daniel Ornelas
 * DictionaryPwFinder performs a dictionary attack to the usernames provided in a text file with the format
 * Username,salt,hashedpassword
 * 
 * 
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


import org.apache.commons.codec.digest.*;

public class DictionaryPwFinder {
	/*
	 * dictAttack() reads the user file and stores each user into an object, then it performs a dictionary attack by 
	 * concatenating the salt to each dictionary password and comparing the SHA1 hashes.
	 */
	public static void dictAttack() throws IOException{
		//Modify user and dictionary text files to match your file path
		File userDataPath = new File("C:\\Users\\Daniel Ornelas\\Downloads\\new\\decryptedpwdfile.txt");
		File dictionaryPath = new File("C:\\Users\\Daniel Ornelas\\Downloads\\new\\wordsEn.txt");
		try {
			int start = (int) System.currentTimeMillis();
			BufferedReader userReader = new BufferedReader(new FileReader(userDataPath)); //user file reader
			BufferedReader dictionaryReader = new BufferedReader(new FileReader(dictionaryPath));//dictionary file reader
			ArrayList<User> allUsers =arrayToUser(fileToArray(userReader));//get all users in file into objects
			ArrayList<String> dictionary = fileToArray(dictionaryReader);//save the dictionary into an arraylist
			getUserPasswords(dictionary,allUsers);//get the user passwords
			int end = (int) System.currentTimeMillis();
			System.out.println(end-start);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	/*@param String hashedGuess: hash of your guess
	 * @param String originalHash : hash of user password
	 *@return boolean
	 *isEqual returns true if both hashes are equal, else it returns false
	 */
	public static boolean isEqual(String hashedGuess,String originalHash){
		return hashedGuess.trim().equals(originalHash.trim());
	}
	/*
	 * @param: BufferedReader reader
	 * @return : ArrayList<String>
	 * fileToArray reads a text file and writes its content into an arraylist
	 */
	public static ArrayList<String> fileToArray(BufferedReader reader) throws IOException{
		String temp = "";
		ArrayList<String> data = new ArrayList<String>();
		while(( temp = reader.readLine()) != null)
			data.add(temp);
		return data;
	}

	/*
	 * @param fileRead
	 * @return ArrayList<User>
	 * arrayToUser gets an array in format: [username,salt,hashedPassword] and converts it into a user object
	 * then it returns an arraylist with all the created users.
	 */
	public static ArrayList<User> arrayToUser(ArrayList<String> fileRead){
		String[] currentUser = new String[3]; // Size 3 for username, salt and hashed password
		ArrayList<User> allUsers = new ArrayList<User>();
		for(int i = 0; i < fileRead.size(); i++){
			currentUser = fileRead.get(i).split(",", 3);//split strings using "," as separator
			User current = new User(currentUser[0],currentUser[1],currentUser[2]);//initialize each User
			allUsers.add(current);
		}
		return allUsers;
	}
	/*
	 * @param dict
	 * @param allUsers
	 * 
	 * getUserPasswords concatenates the salt of each user to a word in the dictionary, hashes the string and compares to user hash
	 * if hashes are equal then it continues with the next user until it cracks all passwords.
	 */
	public static void getUserPasswords(ArrayList<String> dict,ArrayList<User> allUsers){
		String saltedPw = "";
		for(int i = 0; i<allUsers.size();i++){
			for(int j=0;j<dict.size();j++){
				saltedPw = dict.get(j).trim().concat(allUsers.get(i).salt.trim());//concatenate password and salt
				String hashedPw = DigestUtils.sha1Hex(saltedPw);// hash the current password using sha1
				if(isEqual(hashedPw,allUsers.get(i).hashedPassword)){ //compare passwords and print password if it matches
					System.out.println("Username:" + allUsers.get(i).username);
					System.out.println(allUsers.get(i).password = dict.get(j)); //print and store the password in user structure
					break;
				}
			}
		}
	}
}







