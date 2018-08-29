import java.math.BigInteger;
import java.util.Random;

public class voter {
	public static String name;
	private BigInteger[] sk = new BigInteger[2];
	public BigInteger[] pk = new BigInteger[2];
	private static boolean isValidVoter;
	public static BigInteger signedpk2;
	public static BigInteger randomBigInt;
	public static BigInteger message;
	public static BigInteger messageDash;
	public static BigInteger signDash;
	public static BigInteger sign;
	public static int numBits = 256;

	//constructor
	public voter(String tempVoterName,BigInteger N,BigInteger e){
		name = tempVoterName;
		genKeyPairs();
		Random rand = new Random();
		randomBigInt = new BigInteger(numBits, rand);
		message = pk[1];
		messageDash = message.multiply(randomBigInt.modPow(e, N)); // Blinding the message
	}
	
	//generate 2 public and private key pairs.
	public void genKeyPairs() {
		BigInteger[] temp1 = digitalSignatureAPI.genkeys();
		BigInteger[] temp2 = digitalSignatureAPI.genkeys();
		sk[0] = temp1[0];
		pk[0] = temp1[1];
		sk[1] = temp2[0];
		pk[1] = temp2[1];
		
	}
	
	public void setValidity(boolean tempFlag) {
		setValidVoter(tempFlag);
	}
	
    // Calculates S from S'
	public void unblindSignature(BigInteger N) {
		sign = signDash.multiply(randomBigInt.modInverse(N)); //unblind Signature
	}

	public BigInteger getMessageDash() {
		return messageDash;
	}

	public static void setMessageDash(BigInteger messageDash) {
		voter.messageDash = messageDash;
	}

	public boolean isValidVoter() {
		return isValidVoter;
	}

	public static void setValidVoter(boolean isValidVoter) {
		voter.isValidVoter = isValidVoter;
	}

	public static BigInteger getSignDash() {
		return signDash;
	}

	public void setSignDash(BigInteger signDash) {
		voter.signDash = signDash;
	}
	
}