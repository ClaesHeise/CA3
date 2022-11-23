package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.mindrot.jbcrypt.BCrypt;

@Entity
@Table(name = "Teacher")
public class Teacher implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @Basic(optional = false)
  @NotNull
  @Column(name = "username", length = 25)
  private String username;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 255)
  @Column(name = "password")
  private String password;
  @JoinTable(name = "user_roles", joinColumns = {
    @JoinColumn(name = "username", referencedColumnName = "username")}, inverseJoinColumns = {
    @JoinColumn(name = "role_name", referencedColumnName = "role_name")})
  @ManyToOne
  private Role role = new Role();

  public void addRole(Role role) {
    if(role != null) {
      this.role = role;
      role.getTeacherList().add(this);
    }
  }
//  public List<String> getRolesAsStrings() {
//    if (roleList.isEmpty()) {
//      return null;
//    }
//    List<String> rolesAsStrings = new ArrayList<>();
//    roleList.forEach((role) -> {
//        rolesAsStrings.add(role.getRoleName());
//      });
//    return rolesAsStrings;
//  }

  public Teacher() {}

   public boolean verifyPassword(String pw){return(BCrypt.checkpw(pw, password));}

  public Teacher(String username, String password) {
    this.username = username;
    this.password = BCrypt.hashpw(password, BCrypt.gensalt());
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

  public String getRole() {
    return role.getRoleName();
  }
//
//  public void setRoleList(List<Role> roleList) {
//    this.roleList = roleList;
//  }
//
//  public void addRole(Role userRole) {
//    roleList.add(userRole);
//  }

}
