import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.security.interfaces.RSAPrivateKey;

public class BlindSignature {
	
	//Variables taken from BlindSignature.java
    static RSAPublicKey publicKey; // Combination of (e,N)
    static BigInteger N, e;
    static RSAPrivateKey privateKey;
    static BigInteger d;

    public BigInteger blindSign(BigInteger N, BigInteger e, BigInteger d, BigInteger messageDash){
        //BigInteger message_dash = message.multiply(random.modPow(e, N)); // Blinding the message
        BigInteger signDash = messageDash.modPow(d, N);
        return signDash;// Calculating S from S'
    }
    
    boolean verifySign(BigInteger N, BigInteger e, BigInteger sign, BigInteger message){
        return message.equals(sign.modPow(e, N));
    }
    
    public static void main(String args[]) {

		//generating N,e,d to perform blind signature
        try {
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
            keyPairGen.initialize(2048);
            KeyPair kp = keyPairGen.generateKeyPair();
            publicKey = (RSAPublicKey) kp.getPublic();
            privateKey = (RSAPrivateKey) kp.getPrivate();
            N = publicKey.getModulus();
            e = publicKey.getPublicExponent();
            d = privateKey.getPrivateExponent();
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
        //System.out.println(N);
		BlindSignature bs = new BlindSignature();
		BigInteger messageDash = new BigInteger("5454545454545455454554545454545454545454545454545454545");
		//BigInteger rand = new BigInteger("23");
		System.out.println(bs.verifySign(N, e, bs.blindSign(N, e, d, messageDash), messageDash));
		
		//voter temp = new voter("voter1", N, e);
    }
}