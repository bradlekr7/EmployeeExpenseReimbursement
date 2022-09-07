package dao;

import entities.Person;
import entities.Request;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RequestDaoImpl implements RequestDao {

    @Override
    public Request getRequestById(int id) {
        Request req = null;
        String sql = "SELECT * FROM REQUEST WHERE id = ?";
        ResultSet rs = null;
        try (Connection con = ConnectionUtil.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                req = populateRequest(rs, con);
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return req;
    }

    @Override
    public BigDecimal getSumByType(String type) {
        BigDecimal amount = new BigDecimal('0');
        String sql = "SELECT SUM(AMOUNT) FROM REQUEST WHERE DESCRIPTION = ?";
        ResultSet rs = null;
        try (Connection con = ConnectionUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, type);
            rs = ps.executeQuery();
            while (rs.next()) {
                amount = rs.getBigDecimal(1);
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return amount;
    }

    @Override
    public Request getRequestById(int id, Connection con) {
        Request req = null;
        String sql = "SELECT * FROM REQUEST WHERE id = ?";
        ResultSet rs = null;
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                String description = rs.getString("DESCRIPTION");
                Boolean isApproved = rs.getBoolean("ISAPPROVED");
               // String decision = rs.getString("DECISION");
                BigDecimal amount = rs.getBigDecimal("AMOUNT");
                int employeeId = rs.getInt("EMPLOYEE");
                int managerId = rs.getInt("MANAGER");
                PersonDao personDao = new PersonDaoImpl();
                Person employee = personDao.getById(employeeId, con);
                //PersonDao md = new PersonDaoImpl();
                Person manager = personDao.getById(managerId, con);

                req = new Request();
                req.setId(id);
                req.setDescription(description);
                req.setIsApproved(isApproved);
                req.setAmount(amount);
                req.setEmployee(employee);
                req.setManager(manager);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return req;
    }

    private List<Request> getRequestsByManagerId(int managerId, String sql) {
        List<Request> requests = new ArrayList<>();
        ResultSet rs = null;
        try (Connection con = ConnectionUtil.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            rs = ps.executeQuery();
            while (rs.next()) {
                Request req = populateRequest(rs, con);
                requests.add(req);
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return requests;
    }

    @Override
    public List<Request> getRequest(int managerId) {
        return getRequestsByManagerId(managerId, "SELECT * FROM REQUEST");
    }

    @Override
    public List<Request> getPendingRequest(int managerId) {
        return getRequestsByManagerId(managerId, "SELECT * FROM REQUEST WHERE isApproved =0 ");
    }

    @Override
    public List<Request> getResolvedRequests(int managerId) {
        return getRequestsByManagerId(managerId, "SELECT * FROM REQUEST WHERE isApproved  =1 ");
    }

    private List<Request> getRequestsByEmployeeId(int employeeId, String sql) {
        List<Request> requests = new ArrayList<>();
        ResultSet rs = null;
        try (Connection con = ConnectionUtil.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, employeeId);
            rs = ps.executeQuery();
            while (rs.next()) {
                Request req = populateRequest(rs, con);
                requests.add(req);
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return requests;
    }

    @Override
    public List<Request> getRequestsByEmployeeId(int employeeId) {
        return getRequestsByEmployeeId(employeeId, "SELECT * FROM REQUEST WHERE EMPLOYEE= ?");
    }

    @Override
    public List<Request> getPendingRequestsByEmployeeId(int employeeId) {
        String sql = "SELECT * FROM REQUEST WHERE EMPLOYEE = ? AND isAPPROVED = 0 ";
        return getRequestsByEmployeeId(employeeId, sql);
    }

    @Override
    public List<Request> getResolvedRequestsByEmployeeId(int employeeId) {
        String sql = "SELECT * FROM REQUEST WHERE EMPLOYEE = ? AND isAPPROVED = 1";
        return getRequestsByEmployeeId(employeeId, sql);
    }

    @Override
    public void addRequest(Request request) {
        request.setId(getNextId());
        String sql = "INSERT INTO REQUEST (ID, DESCRIPTION, isAPPROVED, AMOUNT, EMPLOYEE, MANAGER) VALUES (DEFAULT, ?, ?, ?, ?,?)";
        try (Connection con = ConnectionUtil.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
          //  ps.setInt(1, request.getId());
            ps.setString(1, request.getDescription());
            ps.setBoolean(2, false);
            ps.setBigDecimal(3, request.getAmount());
            ps.setInt(4, request.getEmployee().getId());
            ps.setInt(5, 1);
            ps.executeUpdate();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Request> getAllRequestsByEmployeeId(int employeeId) {
        String sql = "SELECT * FROM REQUEST WHERE EMPLOYEE = ?";
        return getRequestsByEmployeeId(employeeId, sql);
    }

    @Override
    public void updateRequest(int id, String decision, int managerId) {
        String sql = "UPDATE REQUEST SET DECISION =? , MANAGER = ?, ISAPPROVED = ?  WHERE ID = ?";
        try (Connection con = ConnectionUtil.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, decision);
            ps.setBoolean(3, "approve".equals(decision) || "deny".equals(decision));
            ps.setInt(2, managerId);
            ps.setInt(4, id);
            ps.executeUpdate();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    private int getNextId() {
        int nextId = -1;
        ResultSet rs = null;
        try (Connection con = ConnectionUtil.getConnection(); Statement s = con.createStatement()) {
            rs = s.executeQuery("SELECT MAX(ID) FROM REQUEST");
            if (rs.next()) {
                nextId = rs.getInt(1) + 1;
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return nextId;
    }

    @Override
    public void updateRequest(Request request) {
        String sql = "UPDATE REQUEST SET ID = ?, DESCRIPTION = ?, isAPPROVED = ?, AMOUNT = ?, EMPLOYEE = ?, MANAGER = ? WHERE ID = ?";

        try (Connection con = ConnectionUtil.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, request.getId());
            ps.setString(2, request.getDescription());
            ps.setBoolean(3, request.getIsApproved());
            ps.setBigDecimal(4, request.getAmount());
            ps.setInt(5, request.getEmployee().getId());
            ps.setInt(6, request.getManager().getId());
            ps.setInt(7, request.getId());
            ps.executeQuery();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    private Request populateRequest(ResultSet rs, Connection con) throws SQLException {
        int requestId = rs.getInt("ID");
        String description = rs.getString("DESCRIPTION");
        String decision = rs.getString("decision");
        Boolean isApproved = rs.getBoolean("isAPPROVED");
        BigDecimal amount = rs.getBigDecimal("AMOUNT");
        int employeeId = rs.getInt("EMPLOYEE");
        int managerId = rs.getInt("MANAGER");
        PersonDao personDao = new PersonDaoImpl();
        Person employee = personDao.getById(employeeId, con);
        Person manager = personDao.getById(managerId, con);

        Request request = new Request();
        request.setId(requestId);
        request.setDescription(description);
        request.setDecision(decision);
        request.setIsApproved(isApproved);
        request.setAmount(amount);
        request.setEmployee(employee);
        request.setManager(manager);
        return request;
    }
}