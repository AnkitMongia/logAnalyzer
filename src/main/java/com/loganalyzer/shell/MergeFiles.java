package com.loganalyzer.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

@ShellComponent
public class MergeFiles {

    @ShellMethod(value = "merge")
    public String merge() {
        String filePath="C:\\Users\\ankit.mongia\\OneDrive - Algosec Systems Ltd\\Desktop\\JIRA\\SUP-21234\\appviz-support-zip_PROD_2023-02-09\\";
        Integer i=10;
        StringBuilder sb= new StringBuilder();
        try {
            while (i > 0) {
                sb = sb.append(Files.readString(Paths.get(filePath + "bflow.log." + i)));
                sb.append("\n");
                i--;
            }
            sb = sb.append(Files.readString(Paths.get(filePath + "bflow.log")));
        }
        catch(Exception e){
            int num= 10-i;
            System.out.println("Found " + num + " log files in the directory");
        }
        try {
            Files.writeString(Paths.get(filePath + "mergedLogs"), sb.toString(), StandardOpenOption.CREATE);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return "Logs Merged to \n" + filePath + "mergedLogs";
    }
}
