package entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Plaul
 */
@Entity
@Table(name = "Role")
@NamedQuery(name = "Role.deleteAllRows", query = "DELETE from Role")
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "role_name", length = 20)
    private String roleName;

    @ManyToMany(mappedBy = "roleList")
    private List<User> userList;

    public Role() {
    }

    public Role(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(User user) {
        this.userList.add(user);
    }   
}
