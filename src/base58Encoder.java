import java.math.BigInteger;
import java.util.ArrayList;
public class base58Encoder {
	

	char[] alphabet58 = new String("123456789abcdefghijkmnopqrstuvwxyzABCDEFGHJKLMNPQRSTUVWXYZ").toCharArray();
	Integer base_count = alphabet58.length;
	BigInteger input;
	public static void main(String[] args){
		//String base58Encoded;
		BigInteger input = new BigInteger(args[0]);
		base58Encoder bs58 = new base58Encoder();
		String base58Encoded = bs58.encode58(input);
		System.out.println(base58Encoded);
		BigInteger decodedFrom58 = bs58.decode58(base58Encoded);
		System.out.println(decodedFrom58);
	}
	
	public  String encode58(BigInteger input) {
		StringBuilder temp = new StringBuilder();
		String output;
		BigInteger base = BigInteger.valueOf(base_count.intValue());
		BigInteger zero = new BigInteger("0");
		//char[] outputReverse = new char[0];
		ArrayList<Character> outputReverse = new ArrayList<Character>();
		while(!input.equals(zero)) {
			int mod = input.mod(base).intValue();
			input = input.divide(base);
			outputReverse.add(alphabet58[mod]);
		}
	    for(Character ch: outputReverse) {
	        temp.append(ch);
	    }
		temp = temp.reverse();
		output = temp.toString();
		
		return output;
	}
	
	public BigInteger decode58(String inputBase58) {
		StringBuilder temp1 = new StringBuilder(inputBase58);
		temp1 = temp1.reverse();
		String inputReverse = temp1.toString();
		String alphabetString = new String(alphabet58);
		BigInteger base58 = BigInteger.valueOf(alphabet58.length);
		BigInteger multi = new BigInteger("1");
		BigInteger decodedOutput = new BigInteger("0");
		for(char c : inputReverse.toCharArray()) {
			int index1 = alphabetString.indexOf(c);
			BigInteger indexBig = BigInteger.valueOf(index1);
			decodedOutput = decodedOutput.add(multi.multiply(indexBig));
			multi = multi.multiply(base58);
		}
		return decodedOutput;
	}
}