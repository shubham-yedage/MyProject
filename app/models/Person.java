package models;


import util.DBUtil;

import java.io.FileNotFoundException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Person {
    public String name;
    public int id;
    public static int count = 0;

    public Person(String name) throws SQLException, ClassNotFoundException {
        this.name = name;
        count=getIDFromDB()+1;
        this.id = getIDFromDB() + 1;
    }

    public Person() throws SQLException, ClassNotFoundException {

    }

    public static Person getPersonObject(Map<String, String[]> form) throws SQLException, ClassNotFoundException {
        Map<String, String> form1 = new HashMap<>();

        for (String key : form.keySet()) {
            if (key.trim().length() > 0 && form.get(key).length > 0) {
                form1.put(key, form.get(key)[0]);
            }
        }
        if (form1.size() < 1) {
            throw new IllegalArgumentException("Insufficient Parameters");
        }
        String name = form1.get("name");

        return new Person(name);
    }

    private static int getIDFromDB() throws SQLException, ClassNotFoundException {
        Statement stmt = DBUtil.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet listPerson = stmt.executeQuery("select * from person");
        listPerson.last();
        return listPerson.getInt("id");
    }

    public String getPersonName() throws SQLException, FileNotFoundException, ClassNotFoundException {
        Statement statement = DBUtil.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery("select * from person");
        String list = "";
        while (resultSet.next()) {
            String name1 = resultSet.getString("name");
            int id = resultSet.getInt("id");
            list = list.concat("<p>" + name1 + " <button onclick=\"remove("+id+")\">k</button></p>");
        }
        return list;
    }
    public boolean save() throws SQLException, ClassNotFoundException {
        PreparedStatement stmt = DBUtil.getConnection().prepareStatement("insert into person(id,name) values (?,?)");
            stmt.setInt(1,id);
            stmt.setString(2, name);
            return stmt.execute();
    }

    public static Boolean remove(int id) throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement = DBUtil.getConnection().prepareStatement("delete from person where id=?");
        preparedStatement.setInt(1, id);
        return preparedStatement.execute();
    }

    public static Boolean update(int id, String name) throws SQLException, FileNotFoundException, ClassNotFoundException {
        PreparedStatement preparedStatement = DBUtil.getConnection().prepareStatement("update person set name=? where id=?");
        preparedStatement.setString(1,name);
        preparedStatement.setInt(2, id);

        if(preparedStatement.execute()) {
            throw new FileNotFoundException("ID Not found!");
        }

        return true;
    }
}