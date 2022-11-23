package security;

import entities.Teacher;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserPrincipal implements Principal {

  private String username;
  private String role;

  /* Create a UserPrincipal, given the Entity class User*/
  public UserPrincipal(Teacher teacher) {
    this.username = teacher.getUsername();
    this.role = teacher.getRole();
  }

  public UserPrincipal(String username, String role) {
    super();
    this.username = username;
    this.role = role;
  }

  @Override
  public String getName() {
    return username;
  }

  public boolean isUserInRole(String role) {
    return this.role.equals(role);
  }
}