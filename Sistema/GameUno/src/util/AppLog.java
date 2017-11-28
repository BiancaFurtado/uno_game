/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;

/**
 * Classe para logs da aplicação, salvas em um .txt (uno_log.txt)
 * @author sergi
 */
public class AppLog {
    private static final File myLogFile = new File("uno_log.txt");
    private static boolean isFirstInsertDay = true;
    
    /**
     * Loggar um erro na aplicação
     * @param message 
     */
    public static void error(String message){
        String line = "[Erro]\t["+Calendar.getInstance().getTime()+"]-> "+message;
        insertInFile(line);
    }
    /*
    public static void info(String message){
        String line = "[Info]\t["+Calendar.getInstance().getTime()+"]-> "+message;
        insertInFile(line);
    }
    */
    
    /**
     * Inserir linha no arquivo .txt
     * @param line 
     */
    private static void insertInFile(String line){
        if(isFirstInsertDay){
            String line_1 = "###\t UNO Logger \t###\t";
           
            String line_2 = "###["+Calendar.getInstance().getTime().toString()+"]###\t";
        
            try {
                System.out.println(line);
                FileWriter record = new FileWriter(myLogFile, true);
                PrintWriter writer = new PrintWriter(record);
                writer.println();
                writer.println(line_1);
                writer.println(line_2);
                writer.println();
                writer.close();
                record.close();
            } catch (IOException ex) {
                System.err.println("Erro ao inserir registro no "+myLogFile.getName());
            }
            isFirstInsertDay = false;
        }
            try {
                System.out.println(line);
                FileWriter record = new FileWriter(myLogFile, true);
                PrintWriter writer = new PrintWriter(record);
                writer.println(line);
                writer.close();
                record.close();
            } catch (IOException ex) {
                System.err.println("Erro ao inserir registro no "+myLogFile.getName());
            }
    
    }
}
