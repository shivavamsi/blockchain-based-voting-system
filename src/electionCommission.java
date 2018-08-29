import java.math.BigInteger;
import java.util.ArrayList;

public class electionCommission {
	public BigInteger N, e, d;
	static ArrayList<voter> validVoterList = new ArrayList<voter>();
	ArrayList<BigInteger> validpk1List = new ArrayList<BigInteger>();
		
	public ArrayList<voter> verifyIdentity(voter[] voterList) {
		for(int i = 0; i < voterList.length; i++) {
			if(voterList[i].isValidVoter()) {
				validVoterList.add(voterList[i]);
			}
		}
		return validVoterList;
	}
	
	public void doBlindSignature(ArrayList<voter> validVoterList) {
		BlindSignature bs = new BlindSignature();
		for (voter v: validVoterList) {
			v.setSignDash(bs.blindSign(N, e, d, v.getMessageDash()));
		}
	}
	
	public ArrayList<BigInteger> prepareValidpkList(ArrayList<voter> validVoterList){
		for(voter v: validVoterList){
			validpk1List.add(v.pk[0]);
		}
		return validpk1List;
	}
}