package servlets;

public class Users {
    private static int employeeId;
    private static int managerId;
    private static boolean isEmployee;
    private static boolean isManager;

    public static void setEmployee(int employeeId, boolean isManager) {
        Users.employeeId = employeeId;
        Users.isManager = isManager;
    }

    public static void setManager(int managerId, boolean isEmployee) {
        Users.managerId = managerId;
        Users.isEmployee = isEmployee;
    }

    public static int getEmployeeId() {
        return employeeId;
    }

    public static int getManagerId() {
        return managerId;
    }

    public static boolean isManager() {
        return isManager;
    }

    public static boolean isEmployee() {
        return isEmployee;
    }

    public static boolean isEmployeeNull() {
        return employeeId >= 0;
    }

    public static boolean isManagerNull() {
        return managerId >= 0;
    }

}