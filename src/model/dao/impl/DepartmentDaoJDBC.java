package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.DepartmentDao;
import model.entities.Department;

public class DepartmentDaoJDBC implements DepartmentDao {

    private Connection conn = DB.getConnection();

    @Override
    public void deleteById(Integer id) {
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement(
                    "DELETE FROM department " +
                            "WHERE Id = ?");

            st.setInt(1, id);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }

    }

    @Override
    public List<Department> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement(
                    "SELECT * FROM department " +
                            "ORDER BY Id");

            rs = st.executeQuery();
            List<Department> list = new ArrayList<>();

            while (rs.next()) {
                Department dep = instanciateDepartment(rs);
                list.add(dep);
            }
            return list;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public Department findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement(
                    "SELECT * FROM department " +
                            "WHERE Id = ?");

            st.setInt(1, id);
            rs = st.executeQuery();
            if (rs.next()) {
                return instanciateDepartment(rs);
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public void insert(Department obj) {
        while (true) {
            if (obj.getId() > 0) {
                PreparedStatement st = null;
                try {
                    st = conn.prepareStatement(
                            "INSERT INTO department " +
                                    "(Id, Name) " +
                                    "VALUES " +
                                    "(?, ?)");

                    st.setInt(1, obj.getId());
                    st.setString(2, obj.getName());
                    st.executeUpdate();

                } catch (SQLException e) {
                    throw new DbException(e.getMessage());
                } finally {
                    DB.closeStatement(st);
                }
            } else {
                System.out.println("O department não pode ter id igual ou menor que zero.");
                return;
            }
        }

    }

    @Override
    public void update(Department obj) {
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement(
                    "UPDATE department " +
                            "SET Id = ?, Name = ? " +
                            "WHERE id = ?");

            st.setInt(1, obj.getId());
            st.setString(2, obj.getName());
            st.setInt(3, obj.getId());

            st.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    private Department instanciateDepartment(ResultSet rs) throws SQLException {
        Department dep = new Department();
        dep.setId(rs.getInt("Id"));
        dep.setName(rs.getString("Name"));
        return dep;
    }

}
