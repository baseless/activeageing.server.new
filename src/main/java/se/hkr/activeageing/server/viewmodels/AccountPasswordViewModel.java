package se.hkr.activeageing.server.viewmodels;

/**
 * account password viewModel
 * Created by Emil Eider & Daniel Ryhle on 2015-11-10.
 */
public class AccountPasswordViewModel {
    private String oldPassword;
    private String newPassword;

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
