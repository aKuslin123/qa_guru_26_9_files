package models;

public class User {

    private String title;
    private Integer id;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UserInfoInner getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoInner userInfo) {
        this.userInfo = userInfo;
    }

    private UserInfoInner userInfo;
}