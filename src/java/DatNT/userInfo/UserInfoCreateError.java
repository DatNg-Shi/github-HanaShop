/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DatNT.userInfo;

import java.io.Serializable;

/**
 *
 * @author nguye
 */
public class UserInfoCreateError implements Serializable{
    private String noSpecialCharacter;
    private String passwordLengthErr;
    private String fullNameLengthErr;
    private String confirmNotMatchPassword;
    private String userIDIsExisted;

    public UserInfoCreateError() {
    }

    /**
     * @return the noSpecialCharacter
     */
    public String getNoSpecialCharacter() {
        return noSpecialCharacter;
    }

    /**
     * @param noSpecialCharacter the noSpecialCharacter to set
     */
    public void setNoSpecialCharacter(String noSpecialCharacter) {
        this.noSpecialCharacter = noSpecialCharacter;
    }

    /**
     * @return the passwordLengthErr
     */
    public String getPasswordLengthErr() {
        return passwordLengthErr;
    }

    /**
     * @param passwordLengthErr the passwordLengthErr to set
     */
    public void setPasswordLengthErr(String passwordLengthErr) {
        this.passwordLengthErr = passwordLengthErr;
    }

    /**
     * @return the fullNameLengthErr
     */
    public String getFullNameLengthErr() {
        return fullNameLengthErr;
    }

    /**
     * @param fullNameLengthErr the fullNameLengthErr to set
     */
    public void setFullNameLengthErr(String fullNameLengthErr) {
        this.fullNameLengthErr = fullNameLengthErr;
    }

    /**
     * @return the confirmNotMatchPassword
     */
    public String getConfirmNotMatchPassword() {
        return confirmNotMatchPassword;
    }

    /**
     * @param confirmNotMatchPassword the confirmNotMatchPassword to set
     */
    public void setConfirmNotMatchPassword(String confirmNotMatchPassword) {
        this.confirmNotMatchPassword = confirmNotMatchPassword;
    }

    /**
     * @return the userIDIsExisted
     */
    public String getUserIDIsExisted() {
        return userIDIsExisted;
    }

    /**
     * @param userIDIsExisted the userIDIsExisted to set
     */
    public void setUserIDIsExisted(String userIDIsExisted) {
        this.userIDIsExisted = userIDIsExisted;
    }

    
    
}
