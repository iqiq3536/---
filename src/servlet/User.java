package servlet;

public class User {
    private int aid;
    private String username;
    private String password;
    private int status;

    // 无参构造函数
    public User() {
    }

    // 带参数构造函数
    public User(int aid, String username, String password, int status) {
        this.aid = aid;
        this.username = username;
        this.password = password;
        this.status = status;
    }

    // Getter和Setter方法
    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    // 重写toString方法
    @Override
    public String toString() {
        return "User{" +
                "aid=" + aid +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", status=" + status +
                '}';
    }
}

