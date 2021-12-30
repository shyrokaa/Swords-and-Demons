package com.shyroka.sql_save;
import com.shyroka.game.GameManager;
import java.sql.*;

//implementat folosind singleton
public class Game_Server
{
    private static Game_Server instance = new Game_Server();

    public static Game_Server getInstance()
    {
        return instance;
    }

    public void connect()
    {
        String url = "jdbc:sqlite:save.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
            System.out.println("Connection to SQLite has been established.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
    public void exportData(String id_save, int level, int health, int score, int posX, int posY) {
        String url = "jdbc:sqlite:save.db";
        String query = "INSERT INTO player (`save_id`,`level`,`health`,`score`,`playerx`,`playery`) VALUES('" + id_save + "'," + level + "," + health + "," + score + "," + posX + "," + posY + ");";
        //System.out.println(query);
        try {
            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(query);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void importData(String id_save, GameManager gm)
    {
        String url = "jdbc:sqlite:save.db";
        try {
            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            ResultSet set = stmt.executeQuery("select * from player");

            while (set.next())
            {
                String id = set.getString("save_id");

                if (id.equals(id_save))
                {
                    gm.saveLevel = set.getInt("level");
                    gm.saveX = set.getInt("playerx");
                    gm.saveY = set.getInt("playery");
                    gm.saveHealth = set.getInt("health");
                    gm.saveScore = set.getInt("score");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteData(String id)
    {
        String url = "jdbc:sqlite:save.db";
        String query = "DELETE FROM player\nWHERE save_id = '" + id + "'";
        try {
            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(query);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void wipeData()
    {
        String url = "jdbc:sqlite:save.db";
        String query = "DELETE FROM player";
        try
        {
            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(query);

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("the SQL database is now empty");
    }
    public void showData()
    {
        String url = "jdbc:sqlite:save.db";
        try {
            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            ResultSet set = stmt.executeQuery("select * from player");

            while (set.next())
            {
                System.out.println(set.getString("save_id"));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public int importSaves(String[] Saves)
    {
        String url = "jdbc:sqlite:save.db";
        int index = 0;
        try
        {
            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            ResultSet set = stmt.executeQuery("select * from player");
            while (set.next())
            {
                Saves[index] = set.getString("save_id");
                index++;

            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return index;
    }
}
