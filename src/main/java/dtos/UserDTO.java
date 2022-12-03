/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import entities.User;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author tha
 */
public class UserDTO {
    String username;

    String password;

    List<String> role;

    public UserDTO(String username, String password, List<String> role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public static List<UserDTO> getDtos(List<User> users){
        List<UserDTO> userDTOS = new ArrayList();
        users.forEach(user-> userDTOS.add(new UserDTO(user)));
        return userDTOS;
    }


    public UserDTO(User user) {
        if(user.getUsername() != null)
            this.username = user.getUsername();
        this.password = user.getPassword();
        this.role = user.getRolesAsStrings();
    }
    public UserDTO(String name, String password) {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getRole() {
        return role;
    }

    public void setRole(List<String> role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "UserDTO{" + "username=" + username + ", password=" + password + ", role=" + role + '}';
    }
}
