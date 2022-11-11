package com.company;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import com.company.Entity;
public abstract class AbstractDAO <K, T extends Entity> {
    public abstract List<T> findAll();
    public abstract T findEntityById(K id);
    public abstract boolean delete(K id);
    public abstract boolean delete(T entity);
    public abstract boolean create(T entity);
    public abstract T update(T entity);
}
    public void close(Statement st) {
        try {
            if (st != null) {
                st.close();
            }
        } catch (SQLException e) {
// лог о невозможности закрытия Statement
        }
    }
    public void close(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
// генерация исключения, т.к. нарушается работа пула
        }
    }
    package by.bsac.simpledao;
            import java.sql.Connection;
            import java.sql.PreparedStatement;
            import java.sql.ResultSet;
            import java.sql.SQLException;
            import java.sql.Statement;
            import java.util.*;
            import by.bsac.pool.ConnectionPool;
            import by.bsac.subject.Abonent;
public class AbonentDAO extends AbstractDAO <Integer, Abonent> {
    public static final String SQL_SELECT_ALL_ABONENTS = "SELECT * FROM
    phonebook";
    public static final String SQL_SELECT_ABONENT_BY_LASTNAME =
            "SELECT idphonebook,phone FROM phonebook WHERE lastname=?";
    @Override
    public List<Abonent> findAll() {
        List<Abonent> abonents = new ArrayList<>();
        Connection cn = null;
        Statement st = null;
        try {
            cn = ConnectionPool.getConnection();
            st = cn.createStatement();
            ResultSet resultSet =
                    st.executeQuery(SQL_SELECT_ALL_ABONENTS);
            while (resultSet.next()) {
                Abonent abonent = new Abonent();
                abonent.setId(resultSet.getInt("idphonebook"));
                abonent.setPhone(resultSet.getInt("phone"));
                abonent.setLastName(resultSet.getString("lastname"));
                abonents.add(abonent);
            }
        } catch (SQLException e) {
            System.err.println("SQL exception (request or table failed): " + e);
        } finally {
            close(st);
// код возвращения экземпляра Connection в пул
        }
        return abonents;
    }
    @Override
    public Abonent findEntityById(Integer id) {
        throw new UnsupportedOperationException();
    }
    @Override
    public boolean delete(Integer id) {
        throw new UnsupportedOperationException();
    }
    @Override
    public Abonent create(Abonent entity) {
        throw new UnsupportedOperationException();
    }
    @Override
    public Abonent update(Abonent entity) {
        throw new UnsupportedOperationException();
    }
    // собственный метод DAO
    public Abonent findAbonentByLastName(String name) {
        Abonent abonent = new Abonent();
        Connection cn = null;
        PreparedStatement st = null;
        try {
            cn = ConnectionPool.getConnection();
            st =
                    cn.prepareStatement(SQL_SELECT_ABONENT_BY_LASTNAME);
            st.setString(1, name);
            ResultSet resultSet =st.executeQuery();
            resultSet.next();
            abonent.setId(resultSet.getInt("idphonebook"));
            abonent.setPhone(resultSet.getInt("phone"));
            abonent.setLastName(name);
        } catch (SQLException e) {
            System.err.println("SQL exception (request or table failed): " + e);
        } finally {
            close(st);
// код возвращения экземпляра Connection в пул
        }
        return abonent;
    }
    @Override
    public boolean delete(Abonent entity) {
        throw new UnsupportedOperationException();
    }
}
package com.company.pool;
        import java.sql.Connection;
        import java.sql.SQLException;
        import javax.naming.Context;
        import javax.naming.InitialContext;
        import javax.naming.NamingException;
        import javax.sql.DataSource;
public class ConnectionPool {
    private static final String DATASOURCE_NAME = "jdbc/testphones";
    private static DataSource dataSource;
    static {
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            dataSource = (DataSource) envContext.lookup(DATASOURCE_NAME);
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }
    private ConnectionPool() { }
    public static Connection getConnection() throws SQLException {
        Connection connection = dataSource.getConnection();
        return connection;
    }
// метод возвращения Connection в пул
}

public class Student {
    private String name;
    private Integer personalNumber;
    private String groupNumber;
    private Integer course;
    // класс с информацией о студенте
    public Student(String name, Integer personalNumber, String groupNumber, int course) {
        this.name = name;
        this.personalNumber = personalNumber;
        this.groupNumber = groupNumber;
        this.course = course;
        System.out.println("Created student " + getName() + " with personalId " +
                getPersonalNumber() + ", course "
                + getCourse() + ", group " + getGroupNumber());
    }
    public Student() {
        System.out.println("New student has been created");
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getGroupNumber() {
        return groupNumber;
    }
    public void setGroupNumber(String groupNumber) {
        this.groupNumber = groupNumber;
    }
    public Integer getCourse() {
        return course;
    }
    public void setCourse(Integer course) {
        this.course = course;
    }
    public void setPersonalNumber(Integer personalNumber) {
        this.personalNumber = personalNumber;
    }
    public int getPersonalNumber() {
        return personalNumber;
    }

    @Override
    public String toString() {
        return "Student " + getName() + ", personalId = " + getPersonalNumber() + ", course = " +
                getCourse()
                + ", group = " + getGroupNumber();
    }
}
#class TestDAO
package com.company;
        import com.company.dao.DAOFactory;
        import com.company.dao.StudentDAO;
        import com.company.model.Student;
public class TestDAO {
    public static void main(String[] args) {
        DAOFactory mysqlFactory = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
        StudentDAO studentDAO = mysqlFactory.getStudentDAO();
        // CREATE
        Student student = new Student();
        student.setPersonalNumber(1);
        student.setName("Arsenij Alferov");
        student.setCourse(1);
        student.setGroupNumber("SP441");
        int systemId = studentDAO.create(student);
        System.out.println("Student's system id = " + systemId);
        // READ
        Student studentFromDB1 = studentDAO.readBySystemlId(systemId);
        System.out.println("after create: " + studentFromDB1);
        // UPDATE
        studentFromDB1.setGroupNumber("SP442");
        studentDAO.update(studentFromDB1);
        // READ
        Student studentFromDB2 = studentDAO.readBySystemlId(systemId);
        System.out.println("after update: " + studentFromDB2);
        // DELETE
        studentDAO.delete(studentFromDB2.getPersonalNumber());
    }
}