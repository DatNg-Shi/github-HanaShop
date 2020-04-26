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
public class LoginError implements Serializable{
    private String noSpecialChar;
    private String accountNotFound;

    public LoginError() {
    }

    /**
     * @return the noSpecialChar
     */
    public String getNoSpecialChar() {
        return noSpecialChar;
    }

    /**
     * @param noSpecialChar the noSpecialChar to set
     */
    public void setNoSpecialChar(String noSpecialChar) {
        this.noSpecialChar = noSpecialChar;
    }

    /**
     * @return the accountNotFound
     */
    public String getAccountNotFound() {
        return accountNotFound;
    }

    /**
     * @param accountNotFound the accountNotFound to set
     */
    public void setAccountNotFound(String accountNotFound) {
        this.accountNotFound = accountNotFound;
    }
    
    
    
}
