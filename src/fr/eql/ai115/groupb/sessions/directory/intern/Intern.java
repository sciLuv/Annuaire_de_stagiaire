package fr.eql.ai115.groupb.sessions.directory.intern;

public class Intern {

    private String lastName;
    private String firstName;
    private String promotion;
    private int department;
    private int year;
    private long childNodeLeftPointer;
    private long childNodeRightPointer;
    private long parentNodePointer;
    private long currentNodePos;

    @Override
    public String toString() {
        return "Intern{" +
                "lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", promotion='" + promotion + '\'' +
                ", department=" + department +
                ", year=" + year +
                ", childNodeLeftPointer=" + childNodeLeftPointer +
                ", childNodeRightPointer=" + childNodeRightPointer +
                ", parentNodePointer=" + parentNodePointer +
                ", currentNodePos=" + currentNodePos +
                '}';
    }

    public Intern() {
    }

    public Intern(String lastName, String firstName, String promotion, int department, int year) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.promotion = promotion;
        this.department = department;
        this.year = year;
        this.currentNodePos = -1;
        this.childNodeLeftPointer = -1;
        this.childNodeRightPointer = -1;
        this.parentNodePointer = -1;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPromotion() {
        return promotion;
    }

    public void setPromotion(String promotion) {
        this.promotion = promotion;
    }

    public int getDepartment() {
        return department;
    }

    public void setDepartment(int department) {
        this.department = department;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public long getChildNodeLeftPointer() {
        return childNodeLeftPointer;
    }

    public void setChildNodeLeftPointer(long childNodeLeftPointer) {
        this.childNodeLeftPointer = childNodeLeftPointer;
    }

    public long getChildNodeRightPointer() {
        return childNodeRightPointer;
    }

    public void setChildNodeRightPointer(long childNodeRightPointer) {
        this.childNodeRightPointer = childNodeRightPointer;
    }

    public long getCurrentNodePos() {
        return currentNodePos;
    }

    public void setCurrentNodePos(long currentNodePos) {
        this.currentNodePos = currentNodePos;
    }

    public long getParentNodePointer() {
        return parentNodePointer;
    }

    public void setParentNodePointer(long parentNodePointer) {
        this.parentNodePointer = parentNodePointer;
    }


    public String getStringComparator(){
        return this.getPromotion() + this.getLastName();
    }
}