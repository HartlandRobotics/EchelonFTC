package org.hartlandrobotics.echelon2.database.entities;

public class PSData {
    private String someString;
    private int someNumber;
    private boolean someFlag;

    public PSData(String someString, int someNumber, boolean someFlag) {
        this.someString = someString;
        this.someNumber = someNumber;
        this.someFlag = someFlag;
    }

    public boolean isSomeFlag() {
        return someFlag;
    }

    public void setSomeFlag(boolean someFlag) {
        this.someFlag = someFlag;
    }

    public int getSomeNumber() {
        return someNumber;
    }

    public void setSomeNumber(int someNumber) {
        this.someNumber = someNumber;
    }

    public String getSomeString() {
        return someString;
    }

    public void setSomeString(String someString) {
        this.someString = someString;
    }
}
