package com.wecamchat.aviddev.api.io;

import com.wecamchat.aviddev.api.ApiInput;

public class ClaimCodeRegisterInput extends ApiInput {

//    private String claimCode;
    private String phone;
    private String email;

//    /**
//     * @return the claimCode
//     */
//    public String getClaimCode() {
//        return claimCode;
//    }
//
//    /**
//     * @param claimCode
//     *            the claimCode to set
//     */
//    public void setClaimCode(String claimCode) {
//        this.claimCode = claimCode;
//        setValueInMap("claimCode", claimCode);
//    }

    /**
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone
     *            the phone to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
        setValueInMap("phone", phone);
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email
     *            the email to set
     */
    public void setEmail(String email) {
        this.email = email;
        setValueInMap("email", email);
    }

}
