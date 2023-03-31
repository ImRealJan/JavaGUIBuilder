package de.bellmannjan.javaguibuilder.Tools;

import java.sql.*;
import java.util.ArrayList;


public class MySQL {

    private String host;
    private String database;
    private String user;
    private String passwort;

    public String error = "";
    private boolean connected = false;


    private Connection con;
    private ResultSet rs;
    private ArrayList<ArrayList<String>> list = new ArrayList<>();


    public MySQL(String host, String database, String user, String passwort)
    {
        this.host = host;
        this.database = database;
        this.user = user;
        this.passwort = passwort;
    }

    public MySQL(String database)
    {
        this.host = "localhost";
        this.database = database;
        this.user = "root";
        this.passwort = "";
    }

    public boolean connect()
    {
        try
        {
            con = DriverManager.getConnection("jdbc:mysql://" + host + "/" + database, user, passwort);
            connected = true;
        }
        catch (SQLException e)
        {
            error = "Es ist ein Fehler bei der Verbindung aufgetreten: " + e.getMessage();
            return false;
        }
        return true;
    }

    public boolean isConnected()
    {
        return connected;
    }

    public boolean closeConnection()
    {
        try
        {
            con.close();
            connected = false;
        }
        catch (SQLException e)
        {
            error = "Es ist ein Fehler bei den Verbindungsabbau aufgetreten: " + e.getMessage();
            return false;
        }
        return true;
    }

    public boolean createSQL(String sql)
    {
        try
        {
            Statement stat = con.createStatement();
            rs = stat.executeQuery(sql);

        }
        catch (SQLException e)
        {
            error = "Es ist ein Fehler bei der Abfrage aufgetreten: " + e.getMessage();
            return false;
        }
        return true;
    }
    public boolean update(String query) {
        try {
            Statement statement = con.createStatement();
            statement.execute(query);
            statement.close();
        }catch (SQLException e) {
            error = "Es ist ein Fehler bei der Abfrage aufgetreten: " + e.getMessage();
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public int getColumnCount()
    {
        try
        {
            return rs.getMetaData().getColumnCount();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return 0;
        }

    }

    public ResultSet getResultSet() {
        return rs;
    }

    public boolean createTable()
    {
        list.clear();
        try
        {
            while(rs.next())
            {
                ArrayList<String> zeile = new ArrayList<>();
                for(int i = 1; i <= getColumnCount(); i++)
                {
                    if(rs.getObject(i) != null)
                        zeile.add(rs.getObject(i).toString());
                }
                list.add(zeile);
            }
            rs.getStatement().close();
            return true;
        }
        catch (SQLException e)
        {
            error = "Es ist ein Fehler beim Abrufen der Tabelle aufgetreten: " + e.getMessage();
            return false;
        }
    }
    public ArrayList<ArrayList<String>> getList() {
        return list;
    }
}