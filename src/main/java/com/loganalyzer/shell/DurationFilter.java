package com.loganalyzer.shell;


import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.io.BufferedReader;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;

@ShellComponent
public class DurationFilter {

    @ShellMethod(value = "Get logs", key = "gl")
    public String getLogs(@ShellOption("ns") Integer duration, @ShellOption(value="f", defaultValue="yyyy-MM-dd k:m:s,S") String dateTimeFormat) {
        SimpleDateFormat df = new SimpleDateFormat(dateTimeFormat);
        ArrayList<Line> returnList= new ArrayList<>();
        String filePath="C:\\Users\\ankit.mongia\\OneDrive - Algosec Systems Ltd\\Desktop\\JIRA\\SUP-21234\\appviz-support-zip_PROD_2023-02-09\\mergedLogs";

        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));

            String prevLine= null;
            Integer lineNum=1;
            while((prevLine= br.readLine()) != null){
                if(prevLine.startsWith("[webapp:2-thread-31]")){
                    break;
                }
                lineNum++;
            }
            String currLine= br.readLine();
            lineNum++;
            while (currLine!=null ) {

                if(currLine.startsWith("[webapp:2-thread-31]") && !currLine.isEmpty()) {
                    Date oDate1 = parseDateTime(prevLine, df);
                    Date oDate2 = parseDateTime(currLine, df);
                    if ((oDate2.getTime() - oDate1.getTime()) >= duration * 1000) {
                        returnList.add(new Line(lineNum, currLine+"\n"));
                    }
                    prevLine= currLine;
                }
                lineNum++;
                currLine= br.readLine();
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return "Total Lines=" + returnList.size() + "\n" +returnList.toString();
    }
    public Date parseDateTime(String line, SimpleDateFormat df){
        StringTokenizer stringTokenizer = new StringTokenizer(line, " ");
        stringTokenizer.nextElement();
        String date = stringTokenizer.nextElement().toString().replace("[", "");
        String time = stringTokenizer.nextElement().toString().replace("]", "");;
        String dateTime= date + " " + time;
        try {
            Date oDate = df.parse(dateTime);
            return oDate;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    class Line{
        public Integer lineNum;
        public String line;
        public Line(Integer lineNum, String line){
            this.line= line;
            this.lineNum= lineNum;
        }
        public String toString(){
            return this.lineNum + ": " + line;
        }
    }
}
