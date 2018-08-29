final public class Transaction {
    final int id;
    String address;
    String script;

    public Transaction(String script, int id, String address) {
        this.id = id;
        this.address = address;
        this.script = script;
    }

    @Override
    /**  @return true if this Transaction has the same id as {@code obj} */
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Transaction other = (Transaction) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return id;
    }
}