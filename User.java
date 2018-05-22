/*
 * @Author : Daniel Ornelas
 * Class User stores the user's username, password, salt, and hashedpassword
 */
public class User {
	public String username;
	public String password;
	public String hashedPassword;
	public String salt;
	
	public User(String un, String salt, String hashed){
		this.username = un;
		this.hashedPassword = hashed;
		this.salt = salt;
		this.password = "";
	}
	

}
