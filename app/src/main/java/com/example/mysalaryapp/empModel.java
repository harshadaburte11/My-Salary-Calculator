package com.example.mysalaryapp;

public class empModel
{
    private double ha,ta,da,pf;

    private String name,month;
    private int leaves;
private double sal;
private String email;
    public empModel()
    {
    }

    public empModel(double ha, double ta, double da, double pf, String name, String month, int leaves,double sal,String email) {
        this.ha = ha;
        this.ta = ta;
        this.da = da;
        this.pf = pf;
        this.name = name;
        this.month = month;
        this.leaves = leaves;
        this.sal=sal;
        this.email=email;
    }

    public String getEmail() {
        return email;
    }

    public double getHa() {
        return ha;
    }

    public double getTa() {
        return ta;
    }

    public double getSal() {
        return sal;
    }

    public double getDa() {
        return da;
    }

    public double getPf() {
        return pf;
    }

    public String getName() {
        return name;
    }

    public String getMonth() {
        return month;
    }

    public int getLeaves() {
        return leaves;
    }
}
