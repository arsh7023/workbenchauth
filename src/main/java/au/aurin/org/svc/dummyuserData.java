package au.aurin.org.svc;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

public class dummyuserData {

  private long user_id;
  @Pattern(regexp = "[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}", message = "Invalid email address.")
  private String email;

  private int enabled;

  @NotBlank
  private String firstname;
  @NotBlank
  private String lastname;

  private String password;

  @NotBlank
  private String userOrgs;

  @NotBlank
  private String userApps;

  @NotBlank
  private String userRoles;

  private List<myOrg> userOrgsNew;

  public List<myOrg> getUserOrgsNew() {
    return userOrgsNew;
  }

  public void setUserOrgsNew(final List<myOrg> userOrgsNew) {
    this.userOrgsNew = userOrgsNew;
  }

  public String getUserRoles() {
    return userRoles;
  }

  public void setUserRoles(final String userRoles) {
    this.userRoles = userRoles;
  }

  public String getUserApps() {
    return userApps;
  }

  public void setUserApps(final String userApps) {
    this.userApps = userApps;
  }

  public dummyuserData() {
    this.userOrgsNew = new ArrayList<myOrg>();
  }

  public long getUser_id() {
    return user_id;
  }

  public void setUser_id(final long user_id) {
    this.user_id = user_id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(final String email) {
    this.email = email;
  }

  public int getEnabled() {
    return enabled;
  }

  public void setEnabled(final int enabled) {
    this.enabled = enabled;
  }

  public String getFirstname() {
    return firstname;
  }

  public void setFirstname(final String firstname) {
    this.firstname = firstname;
  }

  public String getLastname() {
    return lastname;
  }

  public void setLastname(final String lastname) {
    this.lastname = lastname;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(final String password) {
    this.password = password;
  }

  public String getUserOrgs() {
    return userOrgs;
  }

  public void setUserOrgs(final String userOrgs) {
    this.userOrgs = userOrgs;
  }

}
