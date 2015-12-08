package se.hkr.activeageing.server.viewmodels;

/**
 * account password viewModel
 * Created by Emil Eider & Daniel Ryhle on 2015-11-10.
 */
public class AccountAuthenticateViewModel {
    private String userName;
    private String password;
    private String email;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean userNameIsSet() {
        return userName!=null && userName.length()>0;
    }

    public boolean passwordIsSet() {
        return password!=null && password.length()>0;
    }

    public boolean emailIsSet() {
        return email !=null && email.length()>0;
    }
    
    public boolean credentialsAreValid() {
        return  passwordIsSet() &&
                (userNameIsSet() || emailIsSet());
    }
}
