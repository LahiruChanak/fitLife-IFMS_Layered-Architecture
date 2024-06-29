package lk.ijse.fitnesscentre.dao;

import java.sql.*;
import java.util.List;

public interface CrudDAO<T> extends SuperDAO {

    boolean add(T entity) throws SQLException;

    boolean add(List<T> entity) throws SQLException;

    boolean update(T entity) throws SQLException;

    boolean delete(String id) throws SQLException;

    List<T> getAll() throws SQLException;

    String currentId() throws SQLException;

    T searchById(String id) throws SQLException;

    List<String> getIds() throws SQLException;

    int getCount() throws SQLException;

    double getTotal() throws SQLException;

}
