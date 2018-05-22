/*
 * @Author Daniel Ornelas
 * Main method to run the different types of attacks provided
 */
import java.io.IOException;

public class PasswordFinder {
	public static void main(String[] args) throws IOException, InterruptedException{
		System.out.println("Hugo es culo");
		DictionaryPwFinder.dictAttack();
		//RandomPwFinder.randomAttack();
		//OnlinePwFinder.onlineAttack();
	}
}
