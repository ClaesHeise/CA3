/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import entities.RenameMe;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author tha
 */
public class TeacherDTO {
    String username;

    String password;

    String role;

    public TeacherDTO(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public static List<TeacherDTO> getDtos(List<Teacher> rms){
        List<TeacherDTO> rmdtos = new ArrayList();
        rms.forEach(rm->rmdtos.add(new TeacherDTO(rm)));
        return rmdtos;
    }


    public TeacherDTO(Teacher rm) {
        if(rm.getUsername() != null)
            this.username = rm.getUsername();
        this.password = rm.getPassword();
        this.role = rm.getRole();
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

    @Override
    public String toString() {
        return "TeacherDTO{" + "username=" + username + ", password=" + password + ", role=" + role + '}';
    }

}
