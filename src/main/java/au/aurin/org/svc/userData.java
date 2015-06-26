package au.aurin.org.svc;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Pattern;

public class userData {

  private long user_id;

  @Pattern(regexp = "[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}", message = "Invalid email address.")
  private String email;

  private int enabled;
  private String firstname;
  private String lastname;
  private String password;

  private List<roleData> userRoles;
  private List<appData> userApplications;
  private List<orgData> userOrganisations;
  private List<agreementData> userAgreements;
  private List<acclvlData> userAccessLevels;

  public userData() {
    this.userRoles = new ArrayList<roleData>();
    this.userApplications = new ArrayList<appData>();
    this.userOrganisations = new ArrayList<orgData>();
    this.userAgreements = new ArrayList<agreementData>();
    this.userAccessLevels = new ArrayList<acclvlData>();

  }

  public long getUser_id() {
    return user_id;
  }

  public void setUser_id(final long user_id) {
    this.user_id = user_id;
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

  public int getEnabled() {
    return enabled;
  }

  public void setEnabled(final int enabled) {
    this.enabled = enabled;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(final String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(final String password) {
    this.password = password;
  }

  public List<roleData> getUserRoles() {
    return userRoles;
  }

  public void setUserRoles(final List<roleData> userRoles) {
    this.userRoles = userRoles;
  }

  public List<appData> getUserApplications() {
    return userApplications;
  }

  public void setUserApplications(final List<appData> userApplications) {
    this.userApplications = userApplications;
  }

  public List<orgData> getUserOrganisations() {
    return userOrganisations;
  }

  public void setUserOrganisations(final List<orgData> userOrganisations) {
    this.userOrganisations = userOrganisations;
  }

  public List<agreementData> getUserAgreements() {
    return userAgreements;
  }

  public void setUserAgreements(final List<agreementData> userAgreements) {
    this.userAgreements = userAgreements;
  }

  public List<acclvlData> getUserAccessLevels() {
    return userAccessLevels;
  }

  public void setUserAccessLevels(final List<acclvlData> userAccessLevels) {
    this.userAccessLevels = userAccessLevels;
  }

}
