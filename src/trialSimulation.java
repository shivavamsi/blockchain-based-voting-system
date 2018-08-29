import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.util.ArrayList;
import java.security.interfaces.RSAPrivateKey;


public class trialSimulation {
	// Variables taken from BlindSignature.java
	public static RSAPublicKey publicKey; // Combination of (e,N)
    public static BigInteger N;
    public static BigInteger e;
    public static BigInteger d;
    public static RSAPrivateKey privateKey;
	

	public static void main(String[] args){

		int numVoters = 1000;
		double p_fakeVoters = 0.01;
		voter[] voterList = new voter[numVoters];
		ArrayList<voter> validVoterList = new ArrayList<voter>();
		ArrayList<BigInteger> validpk1List = new ArrayList<BigInteger>();
		
		// generating N,e,d to perform blind signature
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
               
		// Electoral body
		electionCommission ec = new electionCommission();
		ec.N = N;
		ec.e = e;
		ec.d = d;
		
		// Create Voters List
		for (int i = 0; i < numVoters; i++){
			String tempVoterName = "Voter"+ Integer.toString(i+1);
			voterList[i] = new voter(tempVoterName, N, e);
	        if(Math.random() < p_fakeVoters)
	           // Set fake voters
	        	voterList[i].setValidity(false);
	        else
	        	voterList[i].setValidity(true);
		}
		
		// create a list of valid voters
		validVoterList = ec.verifyIdentity(voterList);
		
		// Blind Signatures Phase
        ec.doBlindSignature(validVoterList);
        
        // voters unblind signature
        for(voter v: validVoterList) {
        	v.unblindSignature(N);
        }
        
        // Pre Election Phase
        // preparing list of valid voter publickeys
        validpk1List = ec.prepareValidpkList(validVoterList);
	}
}