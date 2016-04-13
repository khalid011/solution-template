/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tigerit.exam;

import java.util.ArrayList;
/**
 *
 * @author Khalid
 */
public class Query {

    private String firstTable;
    private String secondTable;
    private String firstAttribute;
    private String secondAttribute;
    private ArrayList<String> firstTableColumns;
    private ArrayList<String> secondTableColumns;
    private boolean selectAll;
    
    public Query(String firstTable, String secondTable, String firstAttribute, String secondAttribute, boolean selectAll) {
        this.firstTable=firstTable;
        this.secondTable=secondTable;
        this.firstAttribute=firstAttribute;
        this.secondAttribute=secondAttribute;
        this.selectAll=selectAll;
        firstTableColumns= new ArrayList<String>();
        secondTableColumns= new ArrayList<String>();
    }

    public void setTableColumns(ArrayList<String> firstTableColumns , ArrayList<String> secondTableColumns) {
        this.firstTableColumns=firstTableColumns;
        this.secondTableColumns=secondTableColumns;
    }

    public String getFirstTable() {
        return firstTable;
    }

    public String getSecondTable() {
        return secondTable;
    }

    public String getFirstAttribute() {
        return firstAttribute;
    }

    public String getSecondAttribute() {
        return secondAttribute;
    }

    public boolean isSelectAll() {
        return selectAll;
    }
    
    public ArrayList<String> getFirstTableColumns() {
        return firstTableColumns;
    }

    public ArrayList<String> getSecondTableColumns() {
        return secondTableColumns;        
    }
}
