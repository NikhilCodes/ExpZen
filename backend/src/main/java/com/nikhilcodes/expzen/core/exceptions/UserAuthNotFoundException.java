package com.nikhilcodes.expzen.core.exceptions;

public class UserAuthNotFoundException extends Exception {

    public UserAuthNotFoundException(String uid) {
        super(String.format("UserAuth record not found for given UID [%s].", uid));
    }
}
