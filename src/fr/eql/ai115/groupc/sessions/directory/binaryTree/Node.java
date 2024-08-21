package fr.eql.ai115.groupc.sessions.directory.binaryTree;

public class Node {
    long ownAddress;
    long smallerChildAddress;
    long biggerChildAddress;
    long parentAdress;

    String data;

    public Node() {
    }

    public Node(String data, long ownAddress, long smallerChildAddress, long biggerChildAddress, long parentAdress) {
        this.data = data;
        this.ownAddress = ownAddress;
        this.smallerChildAddress = smallerChildAddress;
        this.biggerChildAddress = biggerChildAddress;
        this.parentAdress = parentAdress;
    }

    public long getOwnAddress() {
        return ownAddress;
    }

    public void setOwnAddress(long ownAddress) {
        this.ownAddress = ownAddress;
    }

    public long getSmallerChildAddress() {
        return smallerChildAddress;
    }

    public void setSmallerChildAddress(long smallerChildAddress) {
        this.smallerChildAddress = smallerChildAddress;
    }

    public long getBiggerChildAddress() {
        return biggerChildAddress;
    }

    public void setBiggerChildAddress(long biggerChildAddress) {
        this.biggerChildAddress = biggerChildAddress;
    }

    public long getParentAdress() {
        return parentAdress;
    }

    public void setParentAdress(long parentAdress) {
        this.parentAdress = parentAdress;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Node :" +
                "ownAddress =" + ownAddress +
                ", smallerChildAddress = " + smallerChildAddress +
                ", biggerChildAddress = " + biggerChildAddress +
                ", parentAdress = " + parentAdress +
                ", data = '" + data + '\'';
    }
}
