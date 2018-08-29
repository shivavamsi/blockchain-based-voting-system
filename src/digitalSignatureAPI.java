import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class digitalSignatureAPI {
	public static int n =256;
	public static BigInteger p = new BigInteger("1666690761967415944999639802677532091929239876518217233089073184469429039287327");
	public static BigInteger g = new BigInteger("3");

	private static String bytesToHex(byte[] hash)
	{
	    StringBuffer hexString = new StringBuffer();
	    for (int i = 0; i < hash.length; i++) {
		    String hex = Integer.toHexString(0xff & hash[i]);
		    if(hex.length() == 1) hexString.append('0');
		        hexString.append(hex);
	    	}
	    return hexString.toString();
	}
	
	public static BigInteger[] genkeys(){
		
		BigInteger[] keys = new BigInteger[2];
		BigInteger sk = new BigInteger(n, new SecureRandom());
		BigInteger pk = g.modPow(sk, p);
		
		keys[0] = sk;
		keys[1] = pk;
		
		return keys;
	}
	
	public static BigInteger sign(BigInteger sk,String msg) throws NoSuchAlgorithmException{
		
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		byte[] encodedhash = digest.digest(msg.getBytes(StandardCharsets.UTF_8));
		String encodedHashHexadecimal = digitalSignatureAPI.bytesToHex(encodedhash);
		BigInteger HashDecimal = new BigInteger(encodedHashHexadecimal, 16);

		BigInteger diffHash_sk = HashDecimal.subtract(sk);
		BigInteger sig = g.modPow(diffHash_sk, p);
		return sig;
	}
	
	public static boolean verify(BigInteger pk,BigInteger sig,String msg) throws NoSuchAlgorithmException{
		
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		byte[] encodedhash = digest.digest(msg.getBytes(StandardCharsets.UTF_8));
		String encodedHashHexadecimal = digitalSignatureAPI.bytesToHex(encodedhash);
		BigInteger HashMsgDecimal = new BigInteger(encodedHashHexadecimal, 16);
		
		BigInteger gPowHashMsg = g.modPow(HashMsgDecimal, p);
		//System.out.println("g^Msg: " + gPowHashMsg);
		BigInteger mulSigpk = (sig.multiply(pk)).mod(p);
		//System.out.println("Sig*pk: " + mulSigpk);
		if(mulSigpk.equals(gPowHashMsg)) {
			return true;
		}
		else return false;

	}
	
	public static void main(String[] args) throws NoSuchAlgorithmException {
		
		String msg = ("CS7082: Advanced Algorithms II - Assignment 1");
		System.out.println("The following values of n, p and g are considered for the DigitalSignatureAPI\n" + "n: " + n + "\n" + "p: " + p + "\n"  + "g: " + g + "\n");
		
		BigInteger[] keysGenerated = digitalSignatureAPI.genkeys();
		BigInteger sk = keysGenerated[0];
		BigInteger pk = keysGenerated[1];
		System.out.println("The keys generated are:\n" + "secret key, sk: \t" + sk + "\n" + "sectet key, pk: \t" + pk + "\n");
		
		BigInteger msgSigned = digitalSignatureAPI.sign(sk, msg);
		System.out.println("The original Message is: \t\t" + msg);
		System.out.println("The Signed Message in Hexadecimal: \t" + msgSigned + "\n");
		
		System.out.println("Calling the verify() method . . .");
		boolean verification = digitalSignatureAPI.verify(pk, msgSigned, msg);
		System.out.println("for true Signature, verification is: " + verification + "\n");
		
		System.out.println("Calling the verify() method . . .");
		verification = digitalSignatureAPI.verify(pk, msgSigned.add(new BigInteger("1")), msg);
		System.out.println("for false Signature, verification is: " + verification);
	}
}