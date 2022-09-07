package dao;

import entities.Person;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonDaoImpl implements PersonDao {
    private List<Person> getLow(boolean wantManagers) {
        List<Person> employeeList = new ArrayList<>();
        String sql = "SELECT * FROM PERSON WHERE " + (wantManagers ? "" : "NOT") + " isMANAGER";
        ResultSet rs = null;
        try (Connection con = ConnectionUtil.getConnection();
             Statement s = con.createStatement()) {
            rs = s.executeQuery(sql);
            while (rs.next())
                employeeList.add(commonFetch(rs));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return employeeList;
    }


    @Override public List<Person> getEmployees() {
        return getLow(false);
    }

    @Override public List<Person> getManagers() {
        return getLow(true);
    }

    @Override
    public int createEmployee(Person employee) {
        String sql = "INSERT INTO person(firstName, lastName, username, password, address, email, ismanager) VALUES(?, ?, ?, ?, ?, ?, ?)";
        ResultSet rs = null;
        try (Connection con = ConnectionUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             Statement s = con.createStatement()) {
            ps.setString(1, employee.getFirstname());
            ps.setString(2, employee.getLastname());
            ps.setString(3, employee.getUsername());
            ps.setString(4, employee.getPassword());
            ps.setString(5, employee.getEmail());
            ps.setString(6, employee.getAddress());
            ps.setBoolean(7, employee.getIsManager());
            ps.executeQuery();

            sql = "SELECT LAST_INSERT_ID()";
            rs = s.executeQuery(sql);
            while (rs.next())
                employee.setId(rs.getInt("ID"));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return employee.getId();
    }
    private Person commonFetch(ResultSet rs) throws SQLException {
        Person employee = new Person();
        employee.setId(rs.getInt("ID"));
        employee.setFirstname(rs.getString("FIRSTNAME"));
        employee.setLastname(rs.getString("LASTNAME"));
        employee.setUsername(rs.getString("USERNAME"));
        employee.setPassword(rs.getString("PASSWORD"));
        employee.setEmail(rs.getString("EMAIL"));
        employee.setAddress(rs.getString("ADDRESS"));
        employee.setIsManager(rs.getBoolean("isMANAGER"));
        return employee;
    }

    private Person commonRead(ResultSet rs) throws SQLException {
        Person employee = null;
        while (rs.next())
            employee = commonFetch(rs);
        return employee;
    }

    @Override public Person getByUsername(String username) {
        Person employee = null;
        String sql = "SELECT * FROM PERSON WHERE USERNAME = ?";
        ResultSet rs;
        try (Connection con = ConnectionUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, username);
            rs = ps.executeQuery();
            employee = commonRead(rs);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return employee;
    }

    @Override public Person getById(int id, Connection con) {
        Person manager = null;
        String sql = "SELECT * FROM PERSON WHERE ID = ?";
        ResultSet rs;
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            rs = ps.executeQuery();
            manager = commonRead(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return manager;
    }

    @Override
    public void updateEmployee(Person employee) {
        String sql = "UPDATE PERSON SET FIRSTNAME = ?, LASTNAME = ?, USERNAME = ?, PASSWORD = ?, ADDRESS = ? WHERE ID = ?";

        try (Connection con = ConnectionUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, employee.getFirstname());
            ps.setString(2, employee.getLastname());
            ps.setString(3, employee.getUsername());
            ps.setString(4, employee.getPassword());
            ps.setString(5, employee.getAddress());
            ps.setInt(6, employee.getId());
            ps.executeUpdate();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
}
