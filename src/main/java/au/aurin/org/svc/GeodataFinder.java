package au.aurin.org.svc;

import static org.geotools.jdbc.JDBCDataStoreFactory.DATABASE;
import static org.geotools.jdbc.JDBCDataStoreFactory.HOST;
import static org.geotools.jdbc.JDBCDataStoreFactory.SCHEMA;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import au.aurin.org.io.DataSourceFactory;
import au.aurin.org.io.GeospatialDataSource;
import au.aurin.org.io.PostgisDataStoreConfig;

@Component
public class GeodataFinder {

  private static final Logger LOGGER = LoggerFactory
      .getLogger(GeodataFinder.class);

  /** The data source. */
  @Autowired
  private DataSource dataSource;

  /** The postgis data store config. */
  @Autowired
  @Qualifier(value = "authDataStoreConfig")
  private PostgisDataStoreConfig postgisDataStoreConfig;

  private GeospatialDataSource envDataSource;

  /** The data source factory. */
  @Autowired
  private DataSourceFactory dataSourceFactory;

  @PostConstruct
  public void init() {
    LOGGER.info(
        "using the following Envision server: {} for database/schema: {}",
        postgisDataStoreConfig.getDataStoreParams().get(HOST.key),
        postgisDataStoreConfig.getDataStoreParams().get(DATABASE.key) + "/"
            + postgisDataStoreConfig.getDataStoreParams().get(SCHEMA.key));
  }

  /**
   * Cleanup.
   */

  @PreDestroy
  public void cleanup() {
    if (envDataSource != null) {
      if (envDataSource.getDataStore() != null) {
        LOGGER.info(" Attempting to dispose of data store ");
        envDataSource.getDataStore().dispose();
      }
    }
    LOGGER.trace(" Service succesfully cleared! ");
  }

  public List<String> getPolygonIDS(final String uazTbl, final String polygonStr) {

    final String query = "select a.propid, ST_Asgeojson(geom)  from "
        + postgisDataStoreConfig.getDataStoreParams().get(SCHEMA.key) + "."
        + uazTbl + " as a INNER JOIN   ST_GeomFromText('" + polygonStr
        + "', 4326) as b  ON   ST_Intersects(a.geom, b.geometry)"
        + " where a.propid is not null "; // this line added later for avoiding
    // null.

    final JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
    LOGGER.info("getPolygonIDS: query is {} ", query);
    final List<String> entries = jdbcTemplate.query(query,
        new RowMapper<String>() {

      @Override
      public String mapRow(final ResultSet rs, final int arg1)
          throws SQLException {
        return rs.getObject(1).toString();
      }

    });

    LOGGER.info(" returning {} distinct entries ", entries.size());
    return entries;
  }

  public userData getUser(final String email) {

    String query = "select * from users " + " where email=?";

    // final String query =
    // "select user_id, email, enabled,firstname,lastname,password from users "
    // + " where email='" + useremail + "'";
    try {
      final JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

      LOGGER.info("getUser: query is {} ", query);

      final userData user = (userData) jdbcTemplate.queryForObject(query,
          new String[] { email }, new BeanPropertyRowMapper(userData.class));
      if (user != null) {
        final long user_id = user.getUser_id();
        LOGGER.info("user_id in  getUser query  is {} ", user_id);

        query = "select a.role_id,b.rolename from user_roles as a , roles as b where a.role_id = b.role_id "
            + " and  a.user_id = " + user.getUser_id();
        final List<roleData> roleuser = jdbcTemplate.query(query,
            new BeanPropertyRowMapper(roleData.class));
        user.setUserRoles(roleuser);

        query = "select a.org_id,b.orgname,b.orgcountry,b.orgstate,b.orglga,b.orgbounds,b.orgextent, b.orgcenter from user_orgs as a , organisations as b where a.org_id = b.org_id "
            + " and  a.user_id = " + user.getUser_id();
        final List<orgData> orguser = jdbcTemplate.query(query,
            new BeanPropertyRowMapper(orgData.class));
        user.setUserOrganisations(orguser);

        query = "select a.app_id,b.appname from user_apps as a , application as b where a.app_id = b.app_id "
            + " and  a.user_id = " + user.getUser_id();
        final List<appData> appuser = jdbcTemplate.query(query,
            new BeanPropertyRowMapper(appData.class));
        user.setUserApplications(appuser);

        query = "select a.acclvl_id,b.acclvlname from user_acclvls as a , acclvls as b where a.acclvl_id = b.acclvl_id "
            + " and  a.user_id = " + user.getUser_id();
        final List<acclvlData> accuser = jdbcTemplate.query(query,
            new BeanPropertyRowMapper(acclvlData.class));
        user.setUserAccessLevels(accuser);

        query = "select a.agr_id,a.agreed, a.aggtime, b.lictext,b.licblob,b.licver,b.lic_id,c.orgname, d.appname from agreement as a , license as b, organisations as c, application as d "
            + " where a.lic_id = b.lic_id  and b.org_id = c.org_id and a.app_id = d.app_id"
            + " and  a.user_id = " + user.getUser_id();
        final List<agreementData> agguser = jdbcTemplate.query(query,
            new BeanPropertyRowMapper(agreementData.class));
        user.setUserAgreements(agguser);
      }

      return user;

    } catch (final Exception e) {

      LOGGER.info("error in  getUser is : {}", e.toString());

    }
    return null;

  }

  public List<Map<String, Object>> getOrgs() {

    final String query = "select org_id,orgname from organisations";

    try {
      final JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

      final List<Map<String, Object>> outMap = jdbcTemplate.queryForList(query);
      return outMap;

    } catch (final Exception e) {

      LOGGER.info("error in  getOrgs is : {}", e.toString());

    }
    return null;

  }

  public List<Map<String, Object>> getApps() {

    final String query = "select app_id,appname from application";

    try {
      final JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

      final List<Map<String, Object>> outMap = jdbcTemplate.queryForList(query);
      return outMap;

    } catch (final Exception e) {

      LOGGER.info("error in  getApps is : {}", e.toString());

    }
    return null;

  }

  public List<Map<String, Object>> getRoles() {

    final String query = "select role_id,rolename from roles";

    try {
      final JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

      final List<Map<String, Object>> outMap = jdbcTemplate.queryForList(query);
      return outMap;

    } catch (final Exception e) {

      LOGGER.info("error in  getRoles is : {}", e.toString());

    }
    return null;

  }

  public long InsertUser(final String email, final String firstname,
      final String lastname, final String password,
      final String randomUUIDString) {

    LOGGER.info("InsertUser, email {} ", email);

    try {
      final JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

      final String query = "Insert into users (email, firstname,lastname,password, uuid) values('"
          + email
          + "','"
          + firstname
          + "','"
          + lastname
          + "','"
          + password
          + "','" + randomUUIDString + "')";

      LOGGER.info(" query insert table  is {} ", query);
      jdbcTemplate.execute(query);

      final long user_id = jdbcTemplate
          .queryForLong("SELECT max(user_id) FROM users");

      return user_id;

    } catch (final Exception e) {
      LOGGER.info("InsertUser failed  error is: {} ", e.toString());
      return 0;
    }

  }

  public long InsertOrgs(final String org_id, final long user_id) {

    LOGGER.info("InsertOrgs, org_id {}, user_id {} ", org_id, user_id);

    try {
      final JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

      final String query = "Insert into user_orgs (org_id, user_id) values("
          + org_id + "," + user_id + ")";

      LOGGER.info(" query InsertOrgs  is {} ", query);
      jdbcTemplate.execute(query);

      return jdbcTemplate
          .queryForLong("SELECT max(user_org_id) FROM user_orgs");

    } catch (final Exception e) {
      LOGGER.info("InsertOrgs failed  error is: ", e.toString());
      return 0;
    }

  }

  public long InsertApps(final String app_id, final long user_id) {

    LOGGER.info("InsertApps, app_id {}, user_id {} ", app_id, user_id);

    try {
      final JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

      final String query = "Insert into user_apps (app_id, user_id) values("
          + app_id + "," + user_id + ")";

      LOGGER.info(" query InsertApps  is {} ", query);
      jdbcTemplate.execute(query);

      return jdbcTemplate
          .queryForLong("SELECT max(user_app_id) FROM user_apps");

    } catch (final Exception e) {
      LOGGER.info("InsertApps failed  error is: ", e.toString());
      return 0;
    }

  }

  public long InsertRoles(final String role_id, final long user_id) {

    LOGGER.info("InsertRoles, role_id {}, user_id {} ", role_id, user_id);

    try {
      final JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

      final String query = "Insert into user_roles (role_id, user_id) values("
          + role_id + "," + user_id + ")";

      LOGGER.info(" query InsertRoles  is {} ", query);
      jdbcTemplate.execute(query);

      return jdbcTemplate
          .queryForLong("SELECT max(user_role_id) FROM user_roles");

    } catch (final Exception e) {
      LOGGER.info("InsertRoles failed  error is: ", e.toString());
      return 0;
    }

  }

  public Boolean addRole(final String role) {

    LOGGER.info("Inside addRole, role {} ", role);

    try {
      final JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

      String query = "select count(*) from roles where lower(rolename) ='"
          + role.toLowerCase() + "'";

      LOGGER.info(" query addRole  is {} ", query);
      final Integer out = jdbcTemplate.queryForInt(query);
      if (out == 0) {
        query = "Insert into roles (rolename) values('" + role + "')";

        LOGGER.info(" query addRole  is {} ", query);
        jdbcTemplate.execute(query);
        return true;
      } else {
        return false;
      }

    } catch (final Exception e) {
      LOGGER.info("addRole failed  error is: ", e.toString());
      return false;
    }

  }

  public Boolean addOrg(final String org) {

    LOGGER.info("Inside addOrg, Org {} ", org);

    try {
      final JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

      String query = "select count(*) from organisations where lower(orgname) ='"
          + org.toLowerCase() + "'";

      LOGGER.info(" query addOrg  is {} ", query);
      final Integer out = jdbcTemplate.queryForInt(query);
      if (out == 0) {

        query = "Insert into organisations (orgname,orgemail,orgurl) values('"
            + org + "', '','')";

        LOGGER.info(" query addOrg  is {} ", query);
        jdbcTemplate.execute(query);
        return true;
      } else {
        return false;
      }

    } catch (final Exception e) {
      LOGGER.info("addOrg failed  error is: ", e.toString());
      return false;
    }

  }

  public Boolean addApp(final String app) {

    LOGGER.info("Inside adAapp, app {} ", app);

    try {
      final JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

      String query = "select count(*) from application where lower(appname) ='"
          + app.toLowerCase() + "'";

      LOGGER.info(" query adAapp  is {} ", query);
      final Integer out = jdbcTemplate.queryForInt(query);
      if (out == 0) {

        query = "Insert into application (appname,appcontact,appurl) values('"
            + app + "', '','')";

        LOGGER.info(" query addApp  is {} ", query);
        jdbcTemplate.execute(query);
        return true;
      } else {
        return false;
      }

    } catch (final Exception e) {
      LOGGER.info("addApp failed  error is: ", e.toString());
      return false;
    }

  }

  public Boolean addAcc(final String acc) {

    LOGGER.info("Inside adAcc, acc {} ", acc);

    try {
      final JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

      String query = "select count(*) from acclvls where lower(acclvlname) ='"
          + acc.toLowerCase() + "'";

      LOGGER.info(" query addAcc  is {} ", query);
      final Integer out = jdbcTemplate.queryForInt(query);
      if (out == 0) {

        query = "Insert into acclvls (acclvlname) values('" + acc + "')";

        LOGGER.info(" query addAcc  is {} ", query);
        jdbcTemplate.execute(query);
        return true;
      } else {
        return false;
      }

    } catch (final Exception e) {
      LOGGER.info("addAcc failed  error is: ", e.toString());
      return false;
    }

  }

  public long InsertAcclvl(final String acclvl_id, final long user_id) {

    LOGGER.info("InsertAcclvl, acclvl_id {}, user_id {} ", acclvl_id, user_id);

    try {
      final JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

      final String query = "Insert into user_acclvls (acclvl_id, user_id) values("
          + acclvl_id + "," + user_id + ")";

      LOGGER.info(" query InsertAcclvl  is {} ", query);
      jdbcTemplate.execute(query);

      return jdbcTemplate
          .queryForLong("SELECT max(user_acclvl_id) FROM user_acclvls");

    } catch (final Exception e) {
      LOGGER.info("InsertAcclvl failed  error is: ", e.toString());
      return 0;
    }

  }

  public Integer InsertAgreement(final long user_id) {

    LOGGER.info("InsertAgreement for user_id {} ", user_id);

    try {
      final JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

      final String query = "insert into  agreement(agrname,lic_id,user_id, app_id) "
          + " select 'aggrname', c.lic_id, a.user_id, b.app_id from user_orgs as a,  user_apps as b, license as c "
          + " where a.user_id = b.user_id and c.org_id = a.org_id and a.user_id = "
          + user_id;

      LOGGER.info(" query InsertAgreement  is {} ", query);
      jdbcTemplate.execute(query);
      return 1;

    } catch (final Exception e) {
      LOGGER.info("InsertAgreement failed  error is: ", e.toString());
      return 0;

    }

  }

  public List<dummyuserData> getAllUsers() {

    String query = "select * from users";

    try {
      final JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

      LOGGER.info("getUser: query is {} ", query);

      query = "select a.user_id,a.email,a.firstname, a.lastname from users as a ";
      final List<dummyuserData> listusers = jdbcTemplate.query(query,
          new BeanPropertyRowMapper(dummyuserData.class));

      return listusers;

    } catch (final Exception e) {

      LOGGER.info("error in  getAllUsers is : {}", e.toString());

    }
    return null;

  }

  public List<dummyuserData> searchAllUsers(final String name,
      final String family, final String email) {

    String query = "select * from users";

    try {
      final JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

      query = "select a.user_id,a.email,a.firstname, a.lastname from users as a where a.firstname ~* '"
          + name
          + "' and  a.lastname ~* '"
          + family
          + "' and a.email ~* '"
          + email + "'";

      LOGGER.info("serachAllUsers: query is {} ", query);
      final List<dummyuserData> listusers = jdbcTemplate.query(query,
          new BeanPropertyRowMapper(dummyuserData.class));

      return listusers;

    } catch (final Exception e) {

      LOGGER.info("error in  serachAllUsers is : {}", e.toString());

    }
    return null;

  }

  public dummyuserData getOneUser(final String user_id) {

    String query = "select user_id, email, firstname, lastname from users where user_id=?";

    try {
      final JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

      LOGGER.info("getUser: query is {} ", query);

      query = "select a.user_id,a.email,a.firstname, a.lastname from users as a where a.user_id="
          + user_id;

      // final dummyuserData user = (dummyuserData) jdbcTemplate.queryForObject(
      // query, new String[] { user_id }, new BeanPropertyRowMapper(
      // dummyuserData.class));

      query = "select user_id, email, firstname, lastname from users where user_id="
          + user_id;
      final List<Map<String, Object>> rows = jdbcTemplate.queryForList(query);
      final dummyuserData user = new dummyuserData();
      for (final Map row : rows) {

        user.setUser_id((Integer) row.get("user_id"));
        user.setEmail((String) row.get("email"));
        user.setFirstname((String) row.get("firstname"));
        user.setLastname((String) row.get("lastname"));

      }

      LOGGER.info(user.getEmail());
      return user;

    } catch (final Exception e) {

      LOGGER.info("error in  getOneUser is : {}", e.toString());

    }
    return null;

  }

  public Integer FindOrgUser(final String org_id, final String user_id) {

    LOGGER.info("FindOrgUser, org_id {}, user_id {} ", org_id, user_id);

    Integer out = 0;
    try {
      final JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

      final String query = "select count(*) from user_orgs where org_id ="
          + org_id + " and user_id =" + user_id;

      LOGGER.info(" query FindOrgUser  is {} ", query);
      out = jdbcTemplate.queryForInt(query);

      return out;

    } catch (final Exception e) {
      LOGGER.info("FindOrgUser failed  error is: ", e.toString());
      return 0;
    }

  }

  public Integer FindAppUser(final String app_id, final String user_id) {

    LOGGER.info("FindAppUser, app_id {}, user_id {} ", app_id, user_id);

    Integer out = 0;
    try {
      final JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

      final String query = "select count(*) from user_apps where app_id ="
          + app_id + " and user_id =" + user_id;

      LOGGER.info(" query FindAppUser  is {} ", query);
      out = jdbcTemplate.queryForInt(query);

      return out;

    } catch (final Exception e) {
      LOGGER.info("FindAppUser failed  error is: ", e.toString());
      return 0;
    }

  }

  public Integer FindRoleUser(final String role_id, final String user_id) {

    LOGGER.info("FindRoleUser, role_id {}, user_id {} ", role_id, user_id);

    Integer out = 0;
    try {
      final JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

      final String query = "select count(*) from user_roles where role_id ="
          + role_id + " and user_id =" + user_id;

      LOGGER.info(" query FindRoleUser  is {} ", query);
      out = jdbcTemplate.queryForInt(query);

      return out;

    } catch (final Exception e) {
      LOGGER.info("FindRoleUser failed  error is: ", e.toString());
      return 0;
    }

  }

  public List<agreementData> getLicense(final String user_id,
      final String app_id, final String org_id) {

    String query = "";
    try {
      final JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

      query = "select a.agr_id,a.agreed, a.aggtime, b.lictext,b.licblob,b.licver,b.lic_id,c.orgname, d.appname from agreement as a , license as b, organisations as c, application as d "
          + " where a.lic_id = b.lic_id  and b.org_id = c.org_id and a.app_id = d.app_id "
          + " and  a.user_id = "
          + user_id
          + " and  a.app_id = "
          + app_id
          + " and  c.org_id = " + org_id + " order by b.licver desc";

      LOGGER.info("getLicense: query is {} ", query);

      final List<agreementData> agguser = jdbcTemplate.query(query,
          new BeanPropertyRowMapper(agreementData.class));

      return agguser;

    } catch (final Exception e) {

      LOGGER.info("error in  getLicense is : {}", e.toString());

    }
    return null;

  }

  public String updateLicense(final String user_id, final String app_id,
      final String lic_id) {

    String query = "";
    try {
      final JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

      query = "update agreement set agreed= 1, aggtime=now()  " + " where "
          + " user_id = " + user_id + " and  app_id = " + app_id
          + " and  lic_id = " + lic_id;

      LOGGER.info("updateLicense: query is {} ", query);

      jdbcTemplate.execute(query);

      return "Success";

    } catch (final Exception e) {

      LOGGER.info("error in  updateLicense is : {}", e.toString());

    }
    return null;

  }

  public String resetLicense(final String user_id, final String app_id,
      final String lic_id) {

    String query = "";
    try {
      final JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

      query = "update agreement set agreed= 0, aggtime=now()  " + " where "
          + " user_id = " + user_id + " and  app_id = " + app_id
          + " and  lic_id = " + lic_id;

      LOGGER.info("resetLicense: query is {} ", query);

      jdbcTemplate.execute(query);

      return "Success";

    } catch (final Exception e) {

      LOGGER.info("error in  resetLicense is : {}", e.toString());

    }
    return null;

  }

  public String changePassword(final String user_id, final String password) {

    String query = "";
    try {
      final JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

      query = "update users set password='" + password + "' where "
          + " user_id = " + user_id;

      LOGGER.info("changePassword: query is {} ", query);

      jdbcTemplate.execute(query);

      return "Success";

    } catch (final Exception e) {

      LOGGER.info("error in  changePassword is : {}", e.toString());

    }
    return null;

  }

  public String updateRoles(final String user_id, final String[] role_id) {

    String query = "";
    try {
      final JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

      query = "delete from user_roles  " + " where " + " user_id = " + user_id;
      LOGGER.info("delete role query is {} ", query);
      jdbcTemplate.execute(query);
      for (final String roleid : role_id) {
        query = "Insert into user_roles (user_id,role_id) values(" + user_id
            + "," + roleid + ")";

        LOGGER.info("insert role query is {} ", query);

        jdbcTemplate.execute(query);
      }
      return "Success";

    } catch (final Exception e) {

      LOGGER.info("error in  updateRoles is : {}", e.toString());

    }
    return null;

  }

  public String updateorgs(final String user_id, final String[] org_id) {

    String query = "";
    try {
      final JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

      query = "delete from user_orgs  " + " where " + " user_id = " + user_id;
      LOGGER.info("delete org query is {} ", query);
      jdbcTemplate.execute(query);
      for (final String orgid : org_id) {
        query = "Insert into user_orgs (user_id,org_id) values(" + user_id
            + "," + orgid + ")";

        LOGGER.info("insert org query is {} ", query);

        jdbcTemplate.execute(query);
      }
      return "Success";

    } catch (final Exception e) {

      LOGGER.info("error in  updateorgs is : {}", e.toString());

    }
    return null;

  }

  public String updateapps(final String user_id, final String[] app_id) {

    String query = "";
    try {
      final JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

      query = "delete from user_apps  " + " where " + " user_id = " + user_id;
      LOGGER.info("delete app query is {} ", query);
      jdbcTemplate.execute(query);
      for (final String appid : app_id) {
        query = "Insert into user_apps (user_id,app_id) values(" + user_id
            + "," + appid + ")";

        LOGGER.info("insert app query is {} ", query);

        jdbcTemplate.execute(query);
      }
      return "Success";

    } catch (final Exception e) {

      LOGGER.info("error in  updateapps is : {}", e.toString());

    }
    return null;

  }

  public String updateaccs(final String user_id, final String[] acclvl_id) {

    String query = "";
    try {
      final JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

      query = "delete from user_acclvls  " + " where " + " user_id = "
          + user_id;
      LOGGER.info("delete acc query is {} ", query);
      jdbcTemplate.execute(query);
      for (final String accid : acclvl_id) {
        query = "Insert into user_acclvls (user_id,acclvl_id) values("
            + user_id + "," + accid + ")";

        LOGGER.info("insert acc query is {} ", query);

        jdbcTemplate.execute(query);
      }
      return "Success";

    } catch (final Exception e) {

      LOGGER.info("error in  updateaccs is : {}", e.toString());

    }
    return null;

  }

  public List<roleData> getAllRoles() {
    String query = "";
    try {
      final JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

      query = "select * from  roles";
      LOGGER.info("getAllRoles query is {} ", query);
      final List<roleData> roles = jdbcTemplate.query(query,
          new BeanPropertyRowMapper(roleData.class));

      return roles;

    } catch (final Exception e) {

      LOGGER.info("error in  getAllRoles is : {}", e.toString());

    }
    return null;

  }

  public List<orgData> getAllOrganisations() {
    String query = "";
    try {
      final JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

      query = "select * from  organisations";
      LOGGER.info("getAllOrganisations query is {} ", query);
      final List<orgData> orgs = jdbcTemplate.query(query,
          new BeanPropertyRowMapper(orgData.class));

      return orgs;

    } catch (final Exception e) {

      LOGGER.info("error in  getAllOrganisations is : {}", e.toString());

    }
    return null;

  }

  public List<appData> getAllApplications() {
    String query = "";
    try {
      final JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

      query = "select * from  application";
      LOGGER.info("getAllApplications query is {} ", query);
      final List<appData> apps = jdbcTemplate.query(query,
          new BeanPropertyRowMapper(appData.class));

      return apps;

    } catch (final Exception e) {

      LOGGER.info("error in  getAllApplications is : {}", e.toString());

    }
    return null;

  }

  public List<acclvlData> getAllAccessLevels() {
    String query = "";
    try {
      final JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

      query = "select * from  acclvls";
      LOGGER.info("getAllAccessLevels query is {} ", query);
      final List<acclvlData> accs = jdbcTemplate.query(query,
          new BeanPropertyRowMapper(acclvlData.class));

      return accs;

    } catch (final Exception e) {

      LOGGER.info("error in  getAllAccessLevels is : {}", e.toString());

    }
    return null;

  }

  public String getUserID(final String uuid) {

    final String query = "select * from users where uuid='" + uuid + "'"; // uuid

    String out = "";

    final JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
    LOGGER.info("getUserID: query is {} ", query);

    final List<Map<String, Object>> rows = jdbcTemplate.queryForList(query);
    for (final Map row : rows) {

      out = (String) row.get("uuid");

    }
    return out;
  }

  public String getPassID(final String uuid) {

    final String query = "select * from users where uuid='" + uuid + "'"; // uuid

    String out = "";

    final JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
    LOGGER.info("getUserID: query is {} ", query);

    final List<Map<String, Object>> rows = jdbcTemplate.queryForList(query);
    for (final Map row : rows) {

      out = (String) row.get("password");

    }
    return out;
  }

  public Boolean changeuuidPassword(final String uuid, final String password) {

    String query = "";
    try {
      final JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

      query = "update users set password='" + password + "' where "
          + " uuid = '" + uuid + "'";

      LOGGER.info("changePassword: query is {} ", query);

      jdbcTemplate.execute(query);

      return true;

    } catch (final Exception e) {

      LOGGER.info("error in  changeuuidPassword is : {}", e.toString());

    }
    return false;

  }

  public String getUuidFromEmail(final String email) {

    final String query = "select * from users where email='" + email + "'";

    String out = "";

    final JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
    LOGGER.info("getUuidFromEmail: query is {} ", query);

    final List<Map<String, Object>> rows = jdbcTemplate.queryForList(query);
    for (final Map row : rows) {

      out = (String) row.get("uuid");

    }
    return out;
  }

  public String FindApp(final String app_id) {

    LOGGER.info("FindApp, app_id {} ", app_id);

    try {
      final JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

      final String query = "Select appname || ': ' || appurl from application where app_id="
          + app_id;

      LOGGER.info(" query FindApp  is {} ", query);
      jdbcTemplate.execute(query);

      return jdbcTemplate
          .queryForObject(query, String.class);
    } catch (final Exception e) {
      LOGGER.info("FindApp failed  error is: ", e.toString());
      return "";
    }

  }

}
