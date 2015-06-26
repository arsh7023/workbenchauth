package au.aurin.org.svc;

import java.io.Serializable;

public class orgData implements Serializable {

  private long org_id;
  private String orgname;
  private String orgcountry;
  private String orgstate;
  private String orglga;
  private String orgbounds;
  private String orgextent;
  private String orgcenter;

  public String getOrgcenter() {
    return orgcenter;
  }

  public void setOrgcenter(final String orgcenter) {
    this.orgcenter = orgcenter;
  }

  public String getOrgextent() {
    return orgextent;
  }

  public void setOrgextent(final String orgextent) {
    this.orgextent = orgextent;
  }

  public String getOrgbounds() {
    return orgbounds;
  }

  public void setOrgbounds(final String orgbounds) {
    this.orgbounds = orgbounds;
  }

  public String getOrgcountry() {
    return orgcountry;
  }

  public void setOrgcountry(final String orgcountry) {
    this.orgcountry = orgcountry;
  }

  public String getOrgstate() {
    return orgstate;
  }

  public void setOrgstate(final String orgstate) {
    this.orgstate = orgstate;
  }

  public String getOrglga() {
    return orglga;
  }

  public void setOrglga(final String orglga) {
    this.orglga = orglga;
  }

  public long getOrg_id() {
    return org_id;
  }

  public void setOrg_id(final long org_id) {
    this.org_id = org_id;
  }

  public String getOrgname() {
    return orgname;
  }

  public void setOrgname(final String orgname) {
    this.orgname = orgname;
  }

}
