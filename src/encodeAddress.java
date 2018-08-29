import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class encodeAddress {
	
	public static void main(String[] args){
		BigInteger publicKey = new BigInteger("123451564516");
		System.out.println("encoded PK: "+ encodeCatx(publicKey));
	}

	public static String encodeCatx(BigInteger Pk){
		String msg = Pk.toString();
		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(msg.getBytes(StandardCharsets.UTF_8));
			RIPEMD_160 ripemd = new RIPEMD_160();
			String ripemd160Msg = ripemd.getHash(hash.toString());
			BigInteger ripemd160Decimal = hex2decimal(ripemd160Msg);
			base58Encoder bs58 = new base58Encoder();
			String encodedMsg = bs58.encode58(ripemd160Decimal);
			return encodedMsg;
		}
		catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
	}

	 public static BigInteger hex2decimal(String s) {
         String digits = "0123456789ABCDEF";
         s = s.toUpperCase();
				 BigInteger sixteen = new BigInteger("16");
         BigInteger val = new BigInteger("0");
         for (int i = 0; i < s.length(); i++) {
             char c = s.charAt(i);
             int d = digits.indexOf(c);
						 BigInteger dBig = BigInteger.valueOf(d);
						 val = val.multiply(sixteen).add(dBig);
         }
         return val;
     }
}