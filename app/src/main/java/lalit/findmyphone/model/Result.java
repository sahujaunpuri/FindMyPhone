package lalit.findmyphone.model;

/**
 * Created by Lalit on 1/20/2018.
 */

public class Result {
    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    private String UserName;
    private String PhoneNumber;
}
