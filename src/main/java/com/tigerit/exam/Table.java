/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tigerit.exam;
/**
 *
 * @author Khalid
 */
public class Table {

    private String tableName;
    private int numOfColumn;
    private int numOfRow;
    private String[] columnNames;
    private int[][] row;
    
    public Table(String tableName, int numOfColumn, int numOfRow) {
        this.tableName=tableName;
        this.numOfColumn=numOfColumn;
        this.numOfRow=numOfRow;
    }

    public int getNumOfColumn() {
        return numOfColumn;
    }

    public int getNumOfRow() {
        return numOfRow;
    }
    
    public void setColumns(String[] columnNames) {
        this.columnNames = columnNames.clone();
    }

    public void setRows(int[][] row) {
        this.row = row.clone();
    }

    public String gettableName() {
        return tableName;
    }

    public String[] getColumns() {
        return columnNames;
    }

    public int[][] getRows() {
        return row;
    }
    
}
