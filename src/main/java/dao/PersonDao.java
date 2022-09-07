package dao;


import entities.Person;
import java.sql.Connection;
import java.util.List;

public interface PersonDao {

    int createEmployee(Person employee);

    Person getByUsername(String username);

    List<Person> getEmployees();

    Person getById(int id, Connection con);

    void updateEmployee(Person employee);

    List<Person> getManagers();


}

