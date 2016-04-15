package client;

public class PasswordEncryption {
	public static String encrypt(String password) {
		String encrypted = "";
		String alpha = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String num = "0123456789";
		
		password = password.toUpperCase();
		for(int i = 0; i < password.length(); i++) {
			if(password.charAt(i) >= 'A' && password.charAt(i) <= 'Z') {
				if(password.charAt(i) == 'Z') {
					encrypted += alpha.charAt(0);
				}
				else {
					char c = password.charAt(i);
					encrypted += alpha.charAt((int) c - 65 + 1);
				}
			}
			if(password.charAt(i) >= '0' && password.charAt(i) <= '9') {
				if(password.charAt(i) == '9') {
					encrypted += num.charAt(0);
				}
				else {
					int  index = (int) password.charAt(i) - 48 + 1;
					encrypted += index;
				}
			}
			
		}
		
		return encrypted;
	}
}

