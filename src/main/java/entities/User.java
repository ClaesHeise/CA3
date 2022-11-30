package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.mindrot.jbcrypt.BCrypt;

@Entity
@Table(name = "users")
public class User implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @Basic(optional = false)
  @NotNull
  @Column(name = "user_name", length = 25)
  private String username;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 255)
  @Column(name = "pass_word")
  private String password;
  @JoinTable(name = "user_roles", joinColumns = {
          @JoinColumn(name = "user_name", referencedColumnName = "user_name")}, inverseJoinColumns = {
          @JoinColumn(name = "role_name", referencedColumnName = "role_name")})
  @ManyToMany
  private List<Role> roleList = new ArrayList<>();

  public List<String> getRolesAsStrings() {
    if (roleList.isEmpty()) {
      return null;
    }
    List<String> rolesAsStrings = new ArrayList<>();
    roleList.forEach((role) -> {
      rolesAsStrings.add(role.getRoleName());
    });
    return rolesAsStrings;
  }
//  @JoinTable(name = "user_roles", joinColumns = {
//    @JoinColumn(name = "username", referencedColumnName = "username")}, inverseJoinColumns = {
//    @JoinColumn(name = "role_name", referencedColumnName = "role_name")})
//  @ManyToOne
//  private Role role = new Role();
//
//  public void addRole(Role role) {
//    if(role != null) {
//      this.role = role;
//      role.getUserList().add(this);
//    }
//  }
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

  public User() {}

   public boolean verifyPassword(String pw){
     System.out.println(pw);
     System.out.println(password);
     System.out.println(BCrypt.hashpw(pw, BCrypt.gensalt()));
     System.out.println(BCrypt.hashpw(pw, BCrypt.gensalt()) == password);
    return(BCrypt.checkpw(pw, password));
  }

  public User(String username, String password) {
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
    this.password = BCrypt.hashpw(password, BCrypt.gensalt());
  }

  public List<Role> getRoleList() {
    return roleList;
  }

//  public String getRole() {
//    return role.getRoleName();
//  }
//
  public void setRoleList(List<Role> roleList) {
    this.roleList = roleList;
  }

  public void addRole(Role userRole) {
    roleList.add(userRole);
  }

}
