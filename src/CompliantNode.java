import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/* CompliantNode refers to a node that follows the rules (not malicious)*/
public class CompliantNode implements Node{
	double p_graph;
	double p_malicious;
	double p_txDistribution;
	int numRounds;
	int followeeCount = 0;
	Set<Transaction> pendingTransactions;
	boolean[] followees;
	Set<Candidate> candidates;
	private HashMap<Transaction, HashSet<Integer>> receivedTransactions;
	HashSet <Integer> FolloweeOfNodes;
	Set<Transaction> sendTxFollowers = new HashSet<Transaction>();
	Set<Transaction> receivedTxList = new HashSet<Transaction>();
	
    public CompliantNode(double p_graph, double p_malicious, double p_txDistribution, int numRounds){
        this.p_graph = p_graph;
    	this.p_malicious = p_malicious;
    	this.p_txDistribution = p_txDistribution;
    	this.numRounds = numRounds;
    	receivedTransactions = new HashMap<Transaction, HashSet<Integer>>();
    }

    public void setFollowees(boolean[] followees){
    	this.followees = followees;
    }

    public void setPendingTransaction(Set<Transaction> pendingTransactions){
    	this.pendingTransactions = pendingTransactions;
    }

    public Set<Transaction> sendToFollowers(){
    	if(!pendingTransactions.isEmpty()) {
    		for(Transaction tx1 : pendingTransactions){
    			sendTxFollowers.add(tx1);
    		}
    	}
    	
    	//clear pendingTransactions after first round
    	pendingTransactions.clear();
    	
    	//System.out.println("sendTxFollowers Size: " + sendTxFollowers.size());
    	return sendTxFollowers;
    }

    public void receiveFromFollowees(Set<Candidate> candidates){
    	this.candidates = candidates;
   	
    	for(Candidate c: candidates) {
    		//Accept Transactions only from nodes which are not followees of current node
    		if(!(followees[c.sender] = true)) {
    			//Creating HashMap of a transaction and nodes which sent the transaction 
                if (!receivedTransactions.containsKey(c.tx)) {
                	receivedTransactions.put(c.tx, new HashSet<Integer>());
                }
    	        receivedTransactions.get(c.tx).add(c.sender);
    	        //Creating a list of Nodes from which current node received transactions
    	        FolloweeOfNodes.add(c.sender);
    	        //Creating a list of received Transaction
    	        receivedTxList.add(c.tx);
    	        
    		    //Number of nodes from which current node receives transactions
    		    followeeCount = FolloweeOfNodes.size();
    		    for(Transaction tx1 : receivedTxList) {
    		    	//Number of nodes which sent the same transaction
    		    	int noOfSenders = receivedTransactions.get(tx1).size();
    		    	//Consider a transaction as Valid Transaction only when at least 1/3rd of the nodes send the same transaction
    		    	if(noOfSenders > followeeCount%3) {
    		    		sendTxFollowers.add(tx1);
    		    	}
    		    }
    		}
	    }
    }
}