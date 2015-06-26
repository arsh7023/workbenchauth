package au.aurin.org.svc;

public class AdminUser{
  /* default username and password, overridden in configuration file */
  private String adminUsername = "aurin";
  private String adminPassword = "aurin";

  public String getAdminUsername() {
    return adminUsername;
  }
  public void setAdminUsername(final String adminUsername) {
    this.adminUsername = adminUsername;
  }

  public String getAdminPassword() {
    return adminPassword;
  }

  public void setAdminPassword(final String adminPassword) {
    this.adminPassword = adminPassword;
  }
}
