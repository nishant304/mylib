
package com.wecamchat.aviddev.api.io;

import java.util.ArrayList;
import java.util.List;

public class UserList {

    private List<User> users = new ArrayList<User>();
    /**
     * 
     * @return
     *     The users
     */
    public List<User> getUsers() {
        return users;
    }

    /**
     * 
     * @param users
     *     The users
     */
    public void setUsers(List<User> users) {
        this.users = users;
    }

}
