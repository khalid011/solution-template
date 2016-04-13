package com.tigerit.exam;


import static com.tigerit.exam.IO.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.StringTokenizer;

/**
 * All of your application logic should be placed inside this class.
 * Remember we will load your application from our custom container.
 * You may add private method inside this class but, make sure your
 * application's execution points start from inside run method.
 */
public class Solution implements Runnable {
    private int totalTestCase;
    private int numOfTable;
    private String tableName;
    private int numOfColumn;
    private int numOfRow;
    private int numOfQuery;
    private String[] columnNames;
    private int[][] row;
    private int[][] tempRow;
    private int[][] tempRow2;
    private ArrayList<String> joinColumns;
    private ArrayList<ArrayList<Integer>> joinRows;

    private String firstTable;
    private String secondTable;
    private String firstAttribute;
    private String secondAttribute;
    private ArrayList<String> firstTableColumns;
    private ArrayList<String> secondTableColumns;
    private ArrayList<String> tempArray;
    private boolean selectAll;
    private String[] tokenizedString;

    private int tableIndex1;
    private int tableIndex2;
    private int attrIndex1;
    private int attrIndex2; 
    
    Table[] tables;
    Query query;
    
    @Override
    public void run() {
        // your application entry point
        userInput();
    }

    private void userInput() {
        
        totalTestCase = readLineAsInteger();
        
        for ( int i=0; i < totalTestCase ; i++) {
            
            System.out.println("Test: "+ (i+1));
            numOfTable = readLineAsInteger();
            
            tables=new Table[numOfTable];
            
            for( int j=0; j< numOfTable ; j++){
                singleTableInfo();
                
                tables[j]=new Table(tableName, numOfColumn, numOfRow);
                tables[j].setColumns(columnNames);
                tables[j].setRows(row); 
            }
            
            numOfQuery = readLineAsInteger();
            for ( int j=0 ; j< numOfQuery ; j++) {
                singleQueryInfo();
                query= new Query(firstTable, secondTable, firstAttribute, secondAttribute,selectAll);  
                query.setTableColumns(firstTableColumns,secondTableColumns);
                System.out.println();
                queryProcess(tables,query);
            }
        }        
    }

    private void queryProcess(Table[] tables,Query query) {
        // Finding Table and Attribute values
        for (int i=0; i < tables.length; i++) {
            
            // finding table
            if(tables[i].gettableName().equals(query.getFirstTable())) {
                firstTable=tables[i].gettableName();
                tableIndex1=i;
                // finding attribute
                columnNames= tables[i].getColumns().clone();
                for( int j=0; j< columnNames.length ;j++) {
                    if(columnNames[j].equals(query.getFirstAttribute())){
                        firstAttribute= columnNames[j];                
                        attrIndex1=j;
                    }
                }
            }
            else if(tables[i].gettableName().equals(query.getSecondTable())){
                secondTable=tables[i].gettableName();
                tableIndex2=i;
                columnNames= tables[i].getColumns().clone();
                for( int j=0; j< columnNames.length ;j++) {
                    if(columnNames[j].equals(query.getSecondAttribute())){
                        secondAttribute= columnNames[j];                
                        attrIndex2=j;
                    }
                }
            }
        }
        findColumns(tables,query);
        findRows(tables,query);
    }
    private void findColumns(Table[] tables,Query query) {
        joinColumns= new ArrayList<String>();
        if(query.isSelectAll()){
            columnNames=tables[tableIndex1].getColumns().clone();
            for(int j=0; j< columnNames.length; j++) {
                joinColumns.add(columnNames[j]);
            }
            columnNames=tables[tableIndex2].getColumns().clone();
            for(int j=0; j< columnNames.length; j++) {
                joinColumns.add(columnNames[j]);
            }
        }
        else{
            ArrayList<String> columns1 = query.getFirstTableColumns();
            for(int j=0; j< columns1.size(); j++) {
                joinColumns.add(columns1.get(j));
            }
            ArrayList<String> columns2 = query.getSecondTableColumns();
            for(int j=0; j< columns2.size(); j++) {
                joinColumns.add(columns2.get(j)); 
            }            
        }
        for(int j=0; j< joinColumns.size(); ){
            System.out.print(joinColumns.get(j));             
            j++;
            System.out.print(" ");
        } 
        System.out.println();             
    }

    private void findRows(Table[] tables,Query query) {
        tempRow=tables[tableIndex1].getRows().clone();
        tempRow2=tables[tableIndex2].getRows().clone();

        joinRows= new ArrayList<ArrayList<Integer>>();

        int flag=0;
        for(int i=0; i<tables[tableIndex1].getNumOfRow();i++) {

            for(int j=0; j<tables[tableIndex2].getNumOfRow();j++){
                if(tempRow[i][attrIndex1] == tempRow2[j][attrIndex2]){
                    flag=1;
                    addRows(i,j);
                }                
            }
        }
        if(flag==1){
            sortRows();
            displayRows();            
        }

    }
    
    private void addRows(int first, int second){
        ArrayList<Integer> singleRow= new ArrayList<Integer>();
        
        if(query.isSelectAll()){
            for(int j=0; j< tables[tableIndex1].getNumOfColumn(); j++) {
                singleRow.add(tempRow[first][j]);
            }
            for(int j=0; j< tables[tableIndex2].getNumOfColumn(); j++) {
                singleRow.add(tempRow2[second][j]);
            }
        }
        else{
            ArrayList<String> columns1 = query.getFirstTableColumns();
            columnNames=tables[tableIndex1].getColumns().clone();
            for(int j=0; j< columns1.size(); j++) {
                
                for(int k=0; k<tables[tableIndex1].getNumOfColumn();k++){
                    if(columns1.get(j).equals(columnNames[k])){
                        singleRow.add(tempRow[first][k]);
                        break;
                    }
                }
            }
            ArrayList<String> columns2 = query.getSecondTableColumns();
            columnNames=tables[tableIndex2].getColumns().clone();
            for(int j=0; j< columns2.size(); j++) {
                
                for(int k=0; k<tables[tableIndex2].getNumOfColumn();k++){
                    if(columns2.get(j).equals(columnNames[k])){
                        singleRow.add(tempRow2[second][k]);
                        break;
                    }
                }
            }
        }
        joinRows.add(singleRow);
    }

    // Sorting using bubble sort
    private void sortRows(){
        ArrayList<Integer> temp= new ArrayList<Integer>();
 
        for(int p=0; p<joinRows.get(0).size(); p++){
            for(int i = 0; i < joinRows.size()-1 ; i++)
            {
                for(int j = 1; j < (joinRows.size() -i); j++)
                {
                    if(joinRows.get(j-1).get(p) > joinRows.get(j).get(p))
                    {
                        temp.clear();
                        for(int k=0; k<joinRows.get(j-1).size(); k++){
                            temp.add(joinRows.get(j-1).get(k)); 
                        }

                        joinRows.get(j-1).clear();
                        for(int k=0; k<joinRows.get(j).size(); k++){
                            joinRows.get(j-1).add(joinRows.get(j).get(k));
                        }
                        
                        joinRows.get(j).clear();                        
                        for(int k=0; k<temp.size(); k++){
                            joinRows.get(j).add(temp.get(k));
                        }
                    }
                }
            }   
        }
    }
    private void displayRows(){
        
        for(int i=0; i<joinRows.size() ;i++){
            for(int k=0; k<joinRows.get(i).size();k++){
               System.out.print(joinRows.get(i).get(k)+ " ");                             
            }
            System.out.println();                             
        }
        System.out.println();                     
    }
    
    private void singleQueryInfo() {
        
        selectAll=false;
        tempArray = new ArrayList<String>();
        firstTableColumns = new ArrayList<String>();
        secondTableColumns = new ArrayList<String>();
        
        for(int i=0; i<4 ; i++) {
            
            tokenizedString = tokenizeString(readLine()," .=,").clone();  
                       
            if(tokenizedString[0].equals("SELECT")) {
                if(tokenizedString[1].equals("*")) {
                    selectAll=true;
                }
                else{
                    int k=1;
                    while(tokenizedString[k] != null){
                        tempArray.add(tokenizedString[k]); 
                        k++;
                    }
                }
            }
            else if(tokenizedString[0].equals("FROM")) {
                firstTable= tokenizedString[1];
                if(!selectAll){
                    for(int k=0; k<tempArray.size(); k+=2) {
                       if(tempArray.get(k).equalsIgnoreCase(tokenizedString[2])){
                            firstTableColumns.add(tempArray.get(k+1));
                       } 
                       else{
                            secondTableColumns.add(tempArray.get(k+1));                           
                       }
                    }
                }
            }
            else if(tokenizedString[0].equals("JOIN")) {
                secondTable= tokenizedString[1];                
            }
            else {
                firstAttribute= tokenizedString[2];                
                secondAttribute= tokenizedString[4];                
            }
        }
    }
    
    private void singleTableInfo() {
        
        tableName=readLine();        
        tokenizedString = tokenizeString(readLine()," ").clone();
        numOfColumn=Integer.parseInt(tokenizedString[0]);
        numOfRow=Integer.parseInt(tokenizedString[1]);

        tokenizedString = tokenizeString(readLine()," ").clone();    
        
        // taking column names
        columnNames=new String[numOfColumn];        
        for( int k=0; k < numOfColumn ; k++) {
            columnNames[k]=tokenizedString[k];
        }
        
        // taking row values
        row = new int[numOfRow][numOfColumn];
        for( int k=0 ; k< numOfRow ; k++) {
            tokenizedString = tokenizeString(readLine()," ").clone();  
            for( int l=0; l < numOfColumn ; l++) {
                row[k][l]= Integer.parseInt(tokenizedString[l]);
            }
        }
    }

    private String[] tokenizeString(String str, String del) {

        int i=0;
        String[] tokens=new String[110];
        StringTokenizer multiTokenizer = new StringTokenizer(str, del);
        
        while (multiTokenizer.hasMoreTokens()){
            tokens[i++]=multiTokenizer.nextToken();
        }    
        return tokens;
    }
}
