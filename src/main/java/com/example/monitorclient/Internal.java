package com.example.monitorclient;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Internal {

    static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    static String startupTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));


    public static String getCurrentTime(){
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }

    public static String[] getDatafile(){
        try {
            Scanner scanner = new Scanner(new File("data.csv"));
            while (scanner.hasNext()) {
                return scanner.next().split(",");
            }
            scanner.close();
        } catch (
                FileNotFoundException e) {
            throw new RuntimeException("File not found", e);
        }
        return null;
    }

    public static boolean getForcedState(){
        String[] fdata = getFfile();
        if(fdata != null){
            if(fdata[0].equals("0")){
                return false;
            } else if (fdata[0].equals("1")) {
                return true;
            }
            else {
                return false;
            }
        }
        return false;
    }

    public static String getForcedURL(){
        String[] fdata = getFfile();
        if(fdata != null){
            if(fdata[0].equals("0")){
                String[] data = new String[]{"0",""};
                try {
                    setForcedFile(data);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            } else if (fdata[0].equals("1")) {
                return fdata[1];
            }
        }
        return null;
    }

    public static void setForcedFile(String[] data) throws IOException {
        //overwrites the data into the forced.csv
        Files.write(Paths.get("forced.csv"), Arrays.asList(String.join(",", data)), StandardCharsets.UTF_8, StandardOpenOption.TRUNCATE_EXISTING);
    }

    public static String[] getFfile(){
        try {
            Scanner scanner = new Scanner(new File("forced.csv"));
            while (scanner.hasNext()) {
                return scanner.next().split(",");
            }
            scanner.close();
        } catch (
                FileNotFoundException e) {
            throw new RuntimeException("File not found", e);
        }
        return null;
    }

    public static String[] getLastLogFromLogCSVFile(){
        try {
            BufferedReader br = new BufferedReader(new FileReader("logs.csv"));
            String line;
            String lastLine = null;
            while ((line = br.readLine()) != null) {
                lastLine = line;
            }
            br.close();
            return lastLine.split(",");
        } catch (IOException e) {
            throw new RuntimeException("Error reading log file", e);
        }
    }

    public static void cleanLogfile(){
        //if the logs.csv file has more than 500 lines remove all but the first
        try {
            List<String> lines = Files.readAllLines(Paths.get("logs.csv"));
            if (lines.size() > 500) {
                lines.subList(1, lines.size()).clear();
                String[] firstRow = lines.get(0).split(",");
                int value = Integer.parseInt(firstRow[7]);
                value += 500;
                firstRow[7] = String.valueOf(value);
                lines.set(0, String.join(",", firstRow));
                Files.write(Paths.get("logs.csv"), lines, StandardCharsets.UTF_8, StandardOpenOption.TRUNCATE_EXISTING);
            }
        } catch (IOException ex) {
            System.out.println("An error occurred while trying to clean the logfile");
            ex.printStackTrace();
        }
    }

    public static String getHostIP(){
        try {
            InetAddress ip = InetAddress.getLocalHost();
            return ip.getHostAddress();
        } catch (UnknownHostException e) {
            throw new RuntimeException("Error getting host IP", e);
        }
    }

    public static void writeLog(String url, String vTime) throws IOException {
        //read the last line of the logs.csv file cope it into a String array
        cleanLogfile();
        String[] lastLog = getLastLogFromLogCSVFile();
        lastLog[0] = String.valueOf(Integer.parseInt(lastLog[0]) + 1);
        lastLog[1] = startupTime;
        lastLog[2] = getHostIP();
        lastLog[3] = getCurrentTime();
        lastLog[4] = String.valueOf(Integer.parseInt(lastLog[0]) + -1);
        lastLog[5] = url;
        lastLog[6] = vTime;
        lastLog[7] = String.valueOf(Integer.parseInt(lastLog[7]) + 1);
        String logEntry = String.join(",", lastLog);
        logEntry = System.lineSeparator() + logEntry;
        Files.write(Paths.get("logs.csv"), Arrays.asList(logEntry), StandardCharsets.UTF_8, StandardOpenOption.APPEND);
    }

    public static void writeMonitorData(String id, String m_row, String m_column) throws IOException {
        String[] data = getDatafile();
        data[0] = id;
        data[6] = m_row;
        data[7] = m_column;
        String dataEntry = String.join(",", data);
        Files.write(Paths.get("data.csv"), dataEntry.getBytes(), StandardOpenOption.TRUNCATE_EXISTING);
    }

    public static void writedbData(String db_name, String db_ip, String port, String username, String password) throws IOException {
        String[] data = getDatafile();
        data[1] = db_name;
        data[2] = db_ip;
        data[3] = port;
        data[4] = username;
        data[5] = password;
        String dataEntry = String.join(",", data);
        Files.write(Paths.get("data.csv"), dataEntry.getBytes(), StandardOpenOption.TRUNCATE_EXISTING);
    }

    public static void forceReboot() throws IOException {
        Runtime.getRuntime().exec("sudo reboot");
    }

}
