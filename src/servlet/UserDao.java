package servlet;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class UserDao {
    // 验证用户登录
    public boolean Login_verify(String username, String password) {
        boolean isValidUser = false;
        String sql = "SELECT COUNT(*) FROM user WHERE username = ? AND password = ?";
        Connection conn = DBUtil.getConnectDb();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);

            rs = ps.executeQuery();
            if (rs.next()) {
                isValidUser = rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.CloseDB(rs, ps, conn);
        }

        return isValidUser;
    }

    // 获取用户信息
    public User getUserInfo(String username, String password) {
        User user = null;
        String sql = "SELECT id, username, password, status FROM user WHERE username = ? AND password = ?";
        Connection conn = DBUtil.getConnectDb();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);

            rs = ps.executeQuery();
            if (rs.next()) {
                user = new User();
                user.setAid(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setStatus(rs.getInt("status"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.CloseDB(rs, ps, conn);
        }

        return user;
    }
    
   
    
    public ArrayList<User> get_ListInfo() {
        ArrayList<User> tag_Array = new ArrayList<User>();
        Connection conn = DBUtil.getConnectDb();
        String sql = "select * from user where status=2";
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            stm = conn.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
            	User adminbean = new User();
                adminbean.setAid(rs.getInt("id"));
                adminbean.setUsername(rs.getString("username"));
                adminbean.setPassword(rs.getString("password"));
                
                tag_Array.add(adminbean);
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            DBUtil.CloseDB(rs, stm, conn);
        }
        return tag_Array;
    }
    // 插入用户
    public boolean insertUser(String username, String password, int status) {
        String sql = "INSERT INTO user (username, password, status) VALUES (?, ?, ?)";
        boolean isInserted = false;
        Connection conn = DBUtil.getConnectDb();
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            ps.setInt(3, status);

            int rowsAffected = ps.executeUpdate();
            isInserted = rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.CloseDB(null, ps, conn);
        }

        return isInserted;
    }
    
    public boolean deleteUser(String username) {
    	String sql = "DELETE FROM user WHERE username = ?";
        boolean isDeleted = false;
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DBUtil.getConnectDb();
            ps = conn.prepareStatement(sql);
            ps.setString(1, username);

            int rowsAffected = ps.executeUpdate();
            isDeleted = rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.CloseDB(null, ps, conn);
        }

        return isDeleted;
    }
    public int getUserStatus(String username, String password) {
        int status = 0;
        String sql = "SELECT status FROM user WHERE username = ? AND password = ?";
        Connection conn = DBUtil.getConnectDb();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);

            rs = ps.executeQuery();
            if (rs.next()) {
                status = rs.getInt("status");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.CloseDB(rs, ps, conn);
        }

        return status;
    }
    //修改密码
    public boolean changePassword(String username, String newPassword) {
        boolean success = false;
        String sql = "UPDATE user SET password = ? WHERE username = ?";
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DBUtil.getConnectDb();
            ps = conn.prepareStatement(sql);
            ps.setString(1, newPassword);
            ps.setString(2, username);

            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated > 0) {
                success = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.CloseDB(null, ps, conn);
        }

        return success;
    }
}
