package au.aurin.org.svc;

public class agreementData {

  private long agr_id;
  private int agreed;
  private int lic_id;
  private String aggtime;
  private String lictext;
  private byte[] liclob;
  private double licver;
  private String orgname;
  private String appname;

  public int getLic_id() {
    return lic_id;
  }

  public void setLic_id(final int lic_id) {
    this.lic_id = lic_id;
  }

  public long getAgr_id() {
    return agr_id;
  }

  public void setAgr_id(final long agr_id) {
    this.agr_id = agr_id;
  }

  public String getLictext() {
    return lictext;
  }

  public void setLictext(final String lictext) {
    this.lictext = lictext;
  }

  public byte[] getLiclob() {
    return liclob;
  }

  public void setLiclob(final byte[] liclob) {
    this.liclob = liclob;
  }

  public double getLicver() {
    return licver;
  }

  public void setLicver(final double licver) {
    this.licver = licver;
  }

  public String getOrgname() {
    return orgname;
  }

  public void setOrgname(final String orgname) {
    this.orgname = orgname;
  }

  public int getAgreed() {
    return agreed;
  }

  public void setAgreed(final int agreed) {
    this.agreed = agreed;
  }

  public String getAggtime() {
    return aggtime;
  }

  public void setAggtime(final String aggtime) {
    this.aggtime = aggtime;
  }

  public String getAppname() {
    return appname;
  }

  public void setAppname(final String appname) {
    this.appname = appname;
  }

}
