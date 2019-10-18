package tn.esprit.multiply.entities;


public class Entry {


    private String detail ;
    private int number1;
    private int number2;


    //constructor

    public Entry() {

    }
    public String getDetail() {
        return detail;
    }

    public Entry(String detail, int number1, int number2) {
        super();
        this.detail = detail;
        this.number1 = number1;
        this.number2 = number2;
    }
    public void setDetail(String detail) {
        this.detail = detail;
    }
    public int getNumber1() {
        return number1;
    }
    public void setNumber1(int number1) {
        this.number1 = number1;
    }
    public int getNumber2() {
        return number2;
    }
    public void setNumber2(int number2) {
        this.number2 = number2;
    }


    @Override
    public String toString() {
        return "Entry [detail=" + detail + ", number1=" + number1 + ", number2=" + number2 + "]";
    }





}
