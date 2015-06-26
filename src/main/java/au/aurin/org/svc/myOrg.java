package au.aurin.org.svc;

import java.io.Serializable;

public class myOrg implements Serializable {
  // private AutoPopulatingList<orgData> vOrgs = new
  // AutoPopulatingList<orgData>(
  // orgData.class);
  //
  // public AutoPopulatingList<orgData> getVOrgs() {
  // return vOrgs;
  // }
  //
  // public void setVOrgs(final AutoPopulatingList<orgData> vOrgs) {
  // this.vOrgs = vOrgs;
  // }

  private String org_id;
  private String orgname;

  private String selected;

  public String getOrg_id() {
    return org_id;
  }

  public void setOrg_id(final String org_id) {
    this.org_id = org_id;
  }

  public String getOrgname() {
    return orgname;
  }

  public void setOrgname(final String orgname) {
    this.orgname = orgname;
  }

  public String getSelected() {
    return selected;
  }

  public void setSelected(final String selected) {
    this.selected = selected;
  }

}
