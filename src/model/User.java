package model;

public class User {

    private int userId;
    private String username;
    private String password;

    public User(int userId, String username, String password) {

    }


    // Set Methods

    public void setUserId(int userId){
        this.userId = userId;
    }

    public void setUsername(String username){
        this.username = username;
    }
    public void setPassword(String password){
        this.password = password;
    }

    // Get Methods

    public int getUserId(int userId){
        return userId;
    }

    public String getUsername(String username){
        return username;
    }

    public String getPassword(String password){
        return password;
    }
}
