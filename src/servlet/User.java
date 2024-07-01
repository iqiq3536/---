package servlet;

public class User {
    private int aid;
    private String username;
    private String password;
    private int status;

    // �޲ι��캯��
    public User() {
    }

    // ���������캯��
    public User(int aid, String username, String password, int status) {
        this.aid = aid;
        this.username = username;
        this.password = password;
        this.status = status;
    }

    // Getter��Setter����
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

    // ��дtoString����
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

