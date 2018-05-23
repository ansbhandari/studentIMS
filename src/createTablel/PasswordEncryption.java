package createTablel;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordEncryption {
	public static String getHashPass(String pText){
		String hText = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.reset();
			md.update(pText.getBytes());
			byte[] digest = md.digest();
			BigInteger bigInt = new BigInteger(1, digest);
			hText = bigInt.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return hText;
	}
}
