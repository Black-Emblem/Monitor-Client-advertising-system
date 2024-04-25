package com.example.monitorclient.database;

import java.lang.reflect.Array;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static com.example.monitorclient.database.dbconn.db_conn;

public class dbf {

    public static String getMonitorView(int monitor_ID) throws SQLException
    {
        String sqlStr = "select Current_listingID from monitors where monitor_ID = ?";
        PreparedStatement ps = db_conn().prepareStatement(sqlStr);
        ps.setInt(1, monitor_ID);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            return String.valueOf(rs.getInt("Current_listingID"));
        }
        return "-1";
    }

    public static String[] getMonitorPosition(int monitor_ID) throws SQLException
    {
        String sqlStr = "select monitor_row, monitor_column from monitors where monitor_ID = ?";
        PreparedStatement ps = db_conn().prepareStatement(sqlStr);
        ps.setInt(1, monitor_ID);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            return new String[]{String.valueOf(rs.getInt("monitor_row")), String.valueOf(rs.getInt("monitor_column"))};
        }
        return new String[0];
    }

    public static void setMonitorPosition(String monitor_ID, String row, String column) throws SQLException {
        String sqlStr = "update monitors set monitor_row = ?, monitor_column = ? where monitor_ID = ?";
        PreparedStatement ps = db_conn().prepareStatement(sqlStr);
        ps.setInt(1, Integer.parseInt(row));
        ps.setInt(2, Integer.parseInt(column));
        ps.setInt(3, Integer.parseInt(monitor_ID));
        ps.executeUpdate();
    }

    public static Boolean getMonitorState(int monitor_ID) throws SQLException
    {
        String sqlStr = "select monitor_state from monitors where monitor_ID = ?";
        PreparedStatement ps = db_conn().prepareStatement(sqlStr);
        ps.setInt(1, monitor_ID);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            if (rs.getInt("monitor_state") == 1){
                return true;
            } else if (rs.getInt("monitor_state") == 0){
                return false;
            }
            {
                return false;
            }
        }
        return false;
    }

    public static String getMonitorheartbeat(int monitor_ID) throws SQLException
    {
        String sqlStr = "select heartbeat from monitors where monitor_ID = ?";
        PreparedStatement ps = db_conn().prepareStatement(sqlStr);
        ps.setInt(1, monitor_ID);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            return String.valueOf(rs.getTimestamp("heartbeat"));
        }
        return "Null";
    }

    public static void updateMonitorView(int monitor_ID, int listing_ID) throws SQLException {
        String sqlStr = "update monitors set Current_listingID = ? where monitor_ID = ?";
        PreparedStatement ps = db_conn().prepareStatement(sqlStr);
        ps.setInt(1, listing_ID);
        ps.setInt(2, monitor_ID);
        ps.executeUpdate();
    }

    public static void setMonitorState(int monitor_ID, int state) throws SQLException
    {
        String sqlStr = "update monitors set monitor_state = ? where monitor_ID = ?";
        PreparedStatement ps = db_conn().prepareStatement(sqlStr);
        ps.setInt(1, state);
        ps.setInt(2, monitor_ID);
        ps.executeUpdate();
    }

    public static int getNumberOfentrys(String tablename) throws SQLException {
        String sqlStr = "select count(*) from "+tablename;
        PreparedStatement ps = db_conn().prepareStatement(sqlStr);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            return rs.getInt(1);
        }
        return -1;
    }

    public static String getStringfromtable(int ID, String tableName, String columnName, String identifier) throws SQLException {
        String sqlStr = "select " + columnName + " from " + tableName + " where " + identifier + " = " + ID;
        PreparedStatement ps = db_conn().prepareStatement(sqlStr);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            return rs.getString(columnName);
        }
        return "error";
    }

    public static String getVstateOfAd(int ID) throws SQLException {
        String sqlStr = "select view_state from listing_relation where add_id = ?";
        PreparedStatement ps = db_conn().prepareStatement(sqlStr);
        ps.setInt(1, ID);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            if (rs.getBoolean(1)) {
                return "Active";
            }
            else {
                return "Inactive";
            }
        }
        return "error";
    }
    public static String getVarcharfromtable(int ID, String tableName, String columnName, String identifier) throws SQLException {
        String sqlStr = "select " + columnName + " from " + tableName + " where " + identifier + " = " + ID;
        PreparedStatement ps = db_conn().prepareStatement(sqlStr);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            return String.valueOf(rs.getInt(columnName));
        }
        return "error";
    }

    public static int getIntfromtable(int ID, String tableName, String columnName, String identifier) throws SQLException {
        String sqlStr = "select " + columnName + " from " + tableName + " where " + identifier + " = " + ID;
        PreparedStatement ps = db_conn().prepareStatement(sqlStr);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            return rs.getInt(columnName);
        }
        return -1;
    }

    public static String getDatefromtable(int ID, String tableName, String columnName, String identifier) throws SQLException {
        String sqlStr = "select " + columnName + " from " + tableName + " where " + identifier + " = " + ID;
        PreparedStatement ps = db_conn().prepareStatement(sqlStr);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            return String.valueOf(rs.getDate(1));
        }
        return "Error";
    }
}