import java.util.Set;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

public class MaliciousNode implements Node {
	double p_graph;
	double p_malicious;
	double p_txDistribution;
	int numRounds;
	String script;
	String pkToBase58;
	
	Set<Transaction> pendingTransactions = new HashSet<Transaction>();
	Set<Transaction> sendTxFollowers = new HashSet<Transaction>();
	Set<Transaction> tempTx = new HashSet<Transaction>();
	HashSet<Integer> MaliciousTxIds = new HashSet<Integer>();
	
	int n =256;
	BigInteger p = new BigInteger("1666690761967415944999639802677532091929239876518217233089073184469429039287327");
	BigInteger g = new BigInteger("3");
	
    public MaliciousNode(double p_graph, double p_malicious, double p_txDistribution, int numRounds) {
    }

    public void setFollowees(boolean[] followees) {
        return;
    }

    public void setPendingTransaction(Set<Transaction> pendingTransactions) {
    	this.pendingTransactions = pendingTransactions;
        return;
    }

    public Set<Transaction> sendToFollowers() {
    	Random random = new Random();
 
    	HashMap<Integer, String> maliciousTxs = new HashMap<Integer, String>();
    	tempTx.clear();
        for (int i = 0; i < 100; i++){
            int r = random.nextInt();
            
           	int randomScript = random.nextInt(4);
           	if(randomScript == 0) script = "P2PKH";
           	else if(randomScript == 1) script = "P2PT";
           	else if(randomScript == 2) script = "P2UCS";
           	else script = "P2HS";
            
            MaliciousTxIds.add(r);
            BigInteger[] keysGenerated = digitalSignatureAPI.genkeys();
            BigInteger pk = keysGenerated[1];
            pkToBase58 = encodeAddress.encodeCatx(pk);
            maliciousTxs.put(r, pkToBase58);
            
         }
        
        for(Integer txID : MaliciousTxIds){
            	sendTxFollowers.add(new Transaction(script, txID, maliciousTxs.get(txID)));
         }
        
        for(Transaction tx : sendTxFollowers) {
        	tempTx.add(tx);
        } 
        tempTx = sendTxFollowers;
        sendTxFollowers.clear();
        int behaviour = random.nextInt(3);
        if(behaviour == 0)	return tempTx;
        else if(behaviour == 1)	return pendingTransactions;
        else return new HashSet<Transaction>();
        	
    } 

    public void receiveFromFollowees(Set<Candidate> candidates) {
        return;
    }
}