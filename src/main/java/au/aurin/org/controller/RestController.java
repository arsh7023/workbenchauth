package au.aurin.org.controller;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URL;
import java.net.URLDecoder;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import javax.annotation.Resource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import javax.mail.internet.MimeMultipart;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import au.aurin.org.io.classMail;
import au.aurin.org.svc.AdminUser;
import au.aurin.org.svc.GeodataFinder;
import au.aurin.org.svc.acclvlData;
import au.aurin.org.svc.agreementData;
import au.aurin.org.svc.appData;
import au.aurin.org.svc.dummyuserData;
import au.aurin.org.svc.orgData;
import au.aurin.org.svc.roleData;
import au.aurin.org.svc.userData;
import au.aurin.org.svc.userDataOne;

@Controller
public class RestController {

  @Autowired
  private classMail classmail;

  private static final Logger logger = LoggerFactory
      .getLogger(RestController.class);

  @Resource
  private GeodataFinder geodataFinder;

  /** The admin username and password from the config file. */
  @Autowired
  // @Qualifier(value = "adminUser")
  private AdminUser adminUser;

  @RequestMapping(method = RequestMethod.GET, value = "/getUser", produces = "application/json")
  @ResponseStatus(HttpStatus.OK)
  public @ResponseBody
  userData getUser(@RequestHeader("X-AURIN-USER-ID") final String roleId,
      @RequestHeader("user") final String user,
      @RequestHeader("password") final String password,
      final HttpServletRequest request) {

    if (!roleId.equals(adminUser.getAdminUsername())) {
      logger.info("Incorrect X-AURIN-USER-ID passed: {}.", roleId);
      return null;
    }

    logger.info("*******>> Rest-getUser for Project user ={} and pass={} ",
        user, password);
    userData myuser = new userData();

    try {

      final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
      myuser = geodataFinder.getUser(user);
      if (myuser != null) {
        final boolean isMatch = passwordEncoder.matches(password,
            myuser.getPassword());

        if (isMatch == true) {
          // final HttpSession session = request.getSession(true);
          // session.setAttribute("user_id", "1");
          // session.setAttribute("user_name", "Alireza Shamakhy");
          // session.setAttribute("user_org", "Maroondah");
          myuser.setPassword("");
          return myuser;
        } else {
          return null;
        }
      } else {
        return null;
      }

    } catch (final Exception e) {
      logger.info(e.toString());
      logger.info("*******>> Error in Rest-getUser for  user ={}  ", user);

    }
    return null;
  }

  /* Returns user id for a given user name */
  @RequestMapping(method = RequestMethod.GET, value = "/getUserOne", produces = "application/json")
  @ResponseStatus(HttpStatus.OK)
  public @ResponseBody
  userDataOne getUserOne(@RequestHeader("X-AURIN-USER-ID") final String roleId,
      @RequestHeader("user") final String user,
      @RequestHeader("password") final String password,
      final HttpServletRequest request) {

    if (!roleId.equals(adminUser.getAdminUsername())) {
      logger.info("Incorrect X-AURIN-USER-ID passed: {}.", roleId);
      return null;
    }

    logger.info("*******>> Rest-getUser for Project user ={} and pass={} ",
        user, password);
    final userDataOne myuser = new userDataOne();

    try {

      final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
      if (geodataFinder.getUser(user) != null) {
        myuser.setUser_id(geodataFinder.getUser(user).getUser_id());
        myuser.setPassword(geodataFinder.getUser(user).getPassword());
        myuser.setEmail(geodataFinder.getUser(user).getEmail());

        final boolean isMatch = passwordEncoder.matches(password,
            myuser.getPassword());

        if (isMatch == true) {
          // final HttpSession session = request.getSession(true);
          // session.setAttribute("user_id", "1");
          // session.setAttribute("user_name", "Alireza Shamakhy");
          // session.setAttribute("user_org", "Maroondah");
          myuser.setPassword("");
          return myuser;
        } else {
          return null;
        }
      } else {
        return null;
      }

    } catch (final Exception e) {
      logger.info(e.toString());
      logger.info(
          "*******>> Error in Rest-getUser for Project user ={} and pass={} ",
          user, password);

    }
    return null;
  }

  @RequestMapping(value = "/users", method = RequestMethod.GET)
  public String getAllusers(
      @ModelAttribute("dummyuserData") final dummyuserData dummyuserdata,
      final Model model) {
    logger.info("Welcome getAllusers");

    final List<dummyuserData> alldummyuserdata = geodataFinder.getAllUsers();

    model.addAttribute("alldummyuserdata", alldummyuserdata);

    model.addAttribute("msg", "");

    return "users";
    // return "redirect:/static/usersnew.html";

  }

  @RequestMapping(method = RequestMethod.GET, value = "/getLicense", produces = "application/json")
  @ResponseStatus(HttpStatus.OK)
  public @ResponseBody
  agreementData getLicense(
      @RequestHeader("X-AURIN-USER-ID") final String roleId,
      @RequestHeader("user_id") final String user_id,
      @RequestHeader("app_id") final String app_id,
      @RequestHeader("org_id") final String org_id,
      final HttpServletRequest request) {

    if (!roleId.equals(adminUser.getAdminUsername())) {
      logger.info("Incorrect X-AURIN-USER-ID passed: {}.", roleId);
      return null;
    }

    logger.info(
        "*******>> Rest-getLicense for Project user_id ={} and app_id={} ",
        user_id, app_id);
    try {
      final List<agreementData> licData = geodataFinder.getLicense(user_id,
          app_id, org_id);
      int i = -1;
      for (final agreementData aggr : licData) {
        i = i + 1;
        if (i == 0) {
          return aggr;
        }

      }
      if (i == -1) {
        return null;
      }

    } catch (final Exception e) {
      logger.info(e.toString());

    }
    return null;
  }

  @RequestMapping(method = RequestMethod.POST, value = "/putLicense", produces = "application/json")
  @ResponseStatus(HttpStatus.OK)
  public @ResponseBody
  String updateLicense(@RequestHeader("X-AURIN-USER-ID") final String roleId,
      @RequestHeader("user_id") final String user_id,
      @RequestHeader("app_id") final String app_id,
      @RequestHeader("lic_id") final String lic_id,
      final HttpServletRequest request) {

    if (!roleId.equals(adminUser.getAdminUsername())) {
      logger.info("Incorrect X-AURIN-USER-ID passed: {}.", roleId);
      return null;
    }

    logger.info("*******>> Rest-putLicense for user_id ={} and app_id={} ",
        user_id, app_id);
    try {
      return geodataFinder.updateLicense(user_id, app_id, lic_id);

    } catch (final Exception e) {
      logger.info(e.toString());

    }
    return null;
  }

  @RequestMapping(method = RequestMethod.POST, value = "/resetLicense", produces = "application/json")
  @ResponseStatus(HttpStatus.OK)
  public @ResponseBody
  String resetLicense(@RequestHeader("X-AURIN-USER-ID") final String roleId,
      @RequestHeader("user_id") final String user_id,
      @RequestHeader("app_id") final String app_id,
      @RequestHeader("lic_id") final String lic_id,
      final HttpServletRequest request) {

    if (!roleId.equals(adminUser.getAdminUsername())) {
      logger.info("Incorrect X-AURIN-USER-ID passed: {}.", roleId);
      return null;
    }

    logger.info("*******>> Rest-resetLicense for user_id ={} and app_id={} ",
        user_id, app_id);
    try {
      return geodataFinder.resetLicense(user_id, app_id, lic_id);

    } catch (final Exception e) {
      logger.info(e.toString());

    }
    return null;
  }

  @RequestMapping(method = RequestMethod.GET, value = "/getPDFLicense.pdf", produces = "application/pdf")
  @ResponseStatus(HttpStatus.OK)
  public @ResponseBody
  void getPDFLicense(final HttpServletRequest request,
      final HttpServletResponse httpServletResponse) {

    logger.info("*******>> Rest-getPDFLicense  ");
    try {
      httpServletResponse.setContentType("application/pdf");

      // final URL shpURL = getClass().getResource("AURIN-license.pdf");

      final URL peopleresource = getClass().getResource("/AURIN-license.pdf");

      logger.info(peopleresource.getPath());

      final InputStream inputStream = new FileInputStream(
          peopleresource.getPath());

      // final Resource yourfile = (Resource) new ClassPathResource(
      // "AURIN-license.pdf");
      //
      // final InputStream inputStream = new FileInputStream();
      // final ServletContext servletContext;
      // inputStream = servletContext
      // .getResourceAsStream("/WEB-INF/content/somecontent.txt");

      final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

      int data;
      while ((data = inputStream.read()) >= 0) {
        outputStream.write(data);
      }

      inputStream.close();
      httpServletResponse.getOutputStream().write(outputStream.toByteArray());

    } catch (final Exception e) {
      logger.info(e.toString());

    }

  }

  @RequestMapping(method = RequestMethod.GET, value = "/getUsers", produces = "application/json")
  @ResponseStatus(HttpStatus.OK)
  public @ResponseBody
  List<userData> getUsers(
      @RequestHeader("X-AURIN-USER-ID") final String roleId,
      @RequestHeader("X-AURIN-PASSWORD") final String rolePw,
      final HttpServletRequest request) {
    final List<userData> outList = new ArrayList<userData>();

    if (!(roleId.equals(adminUser.getAdminUsername()) && rolePw
        .equals(adminUser.getAdminPassword()))) {
      logger.info("incorrect admin credentials");
      return null;
    }

    try {

      final List<dummyuserData> alldummyuserdata = geodataFinder.getAllUsers();

      logger.info("*******>> Rest-getUsers  ");
      for (final dummyuserData duser : alldummyuserdata) {
        userData myuser = new userData();
        myuser = geodataFinder.getUser(duser.getEmail());
        myuser.setPassword("");
        outList.add(myuser);
      }
    } catch (final Exception e) {
      logger.info("*******>> Error in getUsers is " + e.toString());

    }
    return outList;
  }

  @RequestMapping(method = RequestMethod.POST, value = "/changePassword", produces = "application/json")
  @ResponseStatus(HttpStatus.OK)
  public @ResponseBody
  String resetPassword(@RequestHeader("X-AURIN-USER-ID") final String roleId,
      @RequestHeader("user_id") final String user_id,
      @RequestHeader("newPassword") final String password,
      final HttpServletRequest request) {

    if (!roleId.equals(adminUser.getAdminUsername())) {
      logger.info("Incorrect X-AURIN-USER-ID passed: {}.", roleId);
      return null;
    }

    final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    final String hashedPassword = passwordEncoder.encode(password);

    logger.info("*******>> changePassword for user_id ={}  ", user_id);
    try {
      return geodataFinder.changePassword(user_id, hashedPassword);

    } catch (final Exception e) {
      logger.info(e.toString());

    }
    return null;
  }

  @RequestMapping(value = "/userstest", method = RequestMethod.GET)
  public String getAlluserstest(
      @ModelAttribute("dummyuserData") final dummyuserData dummyuserdata,
      final Model model) {
    logger.info("Welcome getAllusers");

    final List<dummyuserData> alldummyuserdata = geodataFinder.getAllUsers();

    model.addAttribute("alldummyuserdata", alldummyuserdata);

    model.addAttribute("msg", "");

    // return "users";
    return "redirect:/static/users.html";

  }

  @RequestMapping(method = RequestMethod.PUT, value = "/putRoles", produces = "application/json")
  @ResponseStatus(HttpStatus.OK)
  public @ResponseBody
  String putRoles(@RequestHeader("X-AURIN-USER-ID") final String roleId,
      @RequestHeader("X-AURIN-PASSWORD") final String rolePw,
      @RequestHeader("user_id") final String user_id,
      @RequestHeader("role_id") final String[] role_id,
      final HttpServletRequest request) {

    if (!(roleId.equals(adminUser.getAdminUsername()) && rolePw
        .equals(adminUser.getAdminPassword()))) {
      logger.info("incorrect admin credentials");
      return null;
    }

    logger.info("*******>> Rest-putRoles for user_id ={} and role_id={} ",
        role_id);
    try {
      geodataFinder.updateRoles(user_id, role_id);

    } catch (final Exception e) {
      logger.info(e.toString());

    }
    return null;
  }

  @RequestMapping(method = RequestMethod.PUT, value = "/putOrgs", produces = "application/json")
  @ResponseStatus(HttpStatus.OK)
  public @ResponseBody
  String putorgs(@RequestHeader("X-AURIN-USER-ID") final String orgId,
      @RequestHeader("X-AURIN-PASSWORD") final String orgPw,
      @RequestHeader("user_id") final String user_id,
      @RequestHeader("org_id") final String[] org_id,
      final HttpServletRequest request) {

    if (!(orgId.equals(adminUser.getAdminUsername()) && orgPw.equals(adminUser
        .getAdminPassword()))) {
      logger.info("incorrect admin credentials");
      return null;
    }

    logger
    .info("*******>> Rest-putorgs for user_id ={} and org_id={} ", org_id);
    try {
      geodataFinder.updateorgs(user_id, org_id);

    } catch (final Exception e) {
      logger.info(e.toString());

    }
    return null;
  }

  @RequestMapping(method = RequestMethod.PUT, value = "/putApps", produces = "application/json")
  @ResponseStatus(HttpStatus.OK)
  public @ResponseBody
  String putapps(@RequestHeader("X-AURIN-USER-ID") final String appId,
      @RequestHeader("X-AURIN-PASSWORD") final String appPw,
      @RequestHeader("user_id") final String user_id,
      @RequestHeader("app_id") final String[] app_id,
      final HttpServletRequest request) {

    if (!(appId.equals(adminUser.getAdminUsername()) && appPw.equals(adminUser
        .getAdminPassword()))) {
      logger.info("incorrect admin credentials");
      return null;
    }

    logger
    .info("*******>> Rest-putapps for user_id ={} and app_id={} ", app_id);
    try {
      geodataFinder.updateapps(user_id, app_id);

    } catch (final Exception e) {
      logger.info(e.toString());

    }
    return null;
  }

  @RequestMapping(method = RequestMethod.PUT, value = "/putAccss", produces = "application/json")
  @ResponseStatus(HttpStatus.OK)
  public @ResponseBody
  String putaccs(@RequestHeader("X-AURIN-USER-ID") final String appId,
      @RequestHeader("X-AURIN-PASSWORD") final String appPw,
      @RequestHeader("user_id") final String user_id,
      @RequestHeader("acclvl_id") final String[] acclvl_id,
      final HttpServletRequest request) {

    if (!(appId.equals(adminUser.getAdminUsername()) && appPw.equals(adminUser
        .getAdminPassword()))) {
      logger.info("incorrect admin credentials");
      return null;
    }

    logger.info("*******>> Rest-putaccs for user_id ={} and acclvl_id={} ",
        acclvl_id);
    try {
      geodataFinder.updateaccs(user_id, acclvl_id);

    } catch (final Exception e) {
      logger.info(e.toString());

    }
    return null;
  }

  @RequestMapping(method = RequestMethod.PUT, value = "/putUserDetails", produces = "application/json")
  @ResponseStatus(HttpStatus.OK)
  public @ResponseBody
  String putUserDetails(@RequestHeader("X-AURIN-USER-ID") final String appId,
      @RequestHeader("X-AURIN-PASSWORD") final String appPw,
      @RequestHeader("user_id") final String user_id,
      @RequestHeader("firstname") final String firstname,
      @RequestHeader("lastname") final String lastname,
      @RequestHeader("email") final String email,
      final HttpServletRequest request) {

    if (!(appId.equals(adminUser.getAdminUsername()) && appPw.equals(adminUser
        .getAdminPassword()))) {
      logger.info("incorrect admin credentials");
      return null;
    }

    logger.info("*******>> Rest-putUserDetails for user_id ={} and email={} ",
        user_id, email);
    try {
      geodataFinder.updateuser(user_id, firstname, lastname,email);

    } catch (final Exception e) {
      logger.info(e.toString());

    }
    return null;
  }

  @RequestMapping(method = RequestMethod.GET, value = "/getAllRoles", produces = "application/json")
  @ResponseStatus(HttpStatus.OK)
  public @ResponseBody
  List<roleData> getAllRoles(
      @RequestHeader("X-AURIN-USER-ID") final String roleId,
      @RequestHeader("X-AURIN-PASSWORD") final String rolePw,
      final HttpServletRequest request) {
    List<roleData> outList = new ArrayList<roleData>();

    if (!(roleId.equals(adminUser.getAdminUsername()) && rolePw
        .equals(adminUser.getAdminPassword()))) {
      logger.info("incorrect admin credentials");
      return null;
    }

    try {

      outList = geodataFinder.getAllRoles();

    } catch (final Exception e) {
      logger.info("*******>> Error in getAllRoles is " + e.toString());

    }
    return outList;
  }

  @RequestMapping(method = RequestMethod.GET, value = "/getAllOrganisations", produces = "application/json")
  @ResponseStatus(HttpStatus.OK)
  public @ResponseBody
  List<orgData> getAllOrganisations(
      @RequestHeader("X-AURIN-USER-ID") final String roleId,
      @RequestHeader("X-AURIN-PASSWORD") final String rolePw,
      final HttpServletRequest request) {
    List<orgData> outList = new ArrayList<orgData>();

    if (!(roleId.equals(adminUser.getAdminUsername()) && rolePw
        .equals(adminUser.getAdminPassword()))) {
      logger.info("incorrect admin credentials");
      return null;
    }

    try {

      outList = geodataFinder.getAllOrganisations();

    } catch (final Exception e) {
      logger.info("*******>> Error in getAllOrganisations is " + e.toString());

    }
    return outList;
  }

  @RequestMapping(method = RequestMethod.GET, value = "/getAllApplications", produces = "application/json")
  @ResponseStatus(HttpStatus.OK)
  public @ResponseBody
  List<appData> getAllApplications(
      @RequestHeader("X-AURIN-USER-ID") final String roleId,
      @RequestHeader("X-AURIN-PASSWORD") final String rolePw,
      final HttpServletRequest request) {
    List<appData> outList = new ArrayList<appData>();

    if (!(roleId.equals(adminUser.getAdminUsername()) && rolePw
        .equals(adminUser.getAdminPassword()))) {
      logger.info("incorrect admin credentials");
      return null;
    }

    try {

      outList = geodataFinder.getAllApplications();

    } catch (final Exception e) {
      logger.info("*******>> Error in getAllApplications is " + e.toString());

    }
    return outList;
  }

  @RequestMapping(method = RequestMethod.GET, value = "/getAllAccessLevels", produces = "application/json")
  @ResponseStatus(HttpStatus.OK)
  public @ResponseBody
  List<acclvlData> getAllAccessLevels(
      @RequestHeader("X-AURIN-USER-ID") final String roleId,
      @RequestHeader("X-AURIN-PASSWORD") final String rolePw,
      final HttpServletRequest request) {
    List<acclvlData> outList = new ArrayList<acclvlData>();

    if (!(roleId.equals(adminUser.getAdminUsername()) && rolePw
        .equals(adminUser.getAdminPassword()))) {
      logger.info("incorrect admin credentials");
      return null;
    }

    try {

      outList = geodataFinder.getAllAccessLevels();

    } catch (final Exception e) {
      logger.info("*******>> Error in getAllAccessLevels is " + e.toString());

    }
    return outList;
  }

  @RequestMapping(method = RequestMethod.POST, value = "/searchUser", produces = "application/json")
  @ResponseStatus(HttpStatus.OK)
  public @ResponseBody
  List<userData> serachUser(
      @RequestHeader("X-AURIN-USER-ID") final String roleId,
      @RequestHeader("X-AURIN-PASSWORD") final String rolePw,
      // @RequestHeader("name1") final String name1,
      // @RequestHeader("family1") final String family1,
      // @RequestHeader("email1") final String email1,
      @RequestBody final String data, final HttpServletRequest request)
          throws UnsupportedEncodingException {
    final List<userData> outList = new ArrayList<userData>();

    logger.info("*******>> X-AURIN-USER-ID: " + roleId);
    logger.info("*******>> name: " + URLDecoder.decode(data, "UTF-8"));

    String dataclient = URLDecoder.decode(data, "UTF-8");
    dataclient = dataclient.replace("=", "");

    logger.info("*******>> dataclient: " + dataclient);
    final String[] ar = dataclient.split(",");

    String name = "";
    String family = "";
    String email = "";

    for (int i = 0; i < ar.length; i++) {
      if (i == 0) {
        name = ar[0];
      } else if (i == 1) {
        family = ar[1];
      } else if (i == 2) {
        email = ar[2];
      }
    }

    if (name.length() == 0) {
      name = "";
    }
    if (family.length() == 0) {
      family = "";
    }
    if (email.length() == 0) {
      email = "";
    }

    if (!(roleId.equals(adminUser.getAdminUsername()) && rolePw
        .equals(adminUser.getAdminPassword()))) {
      logger.info("incorrect admin credentials");
      return null;
    }

    try {

      final List<dummyuserData> alldummyuserdata = geodataFinder
          .searchAllUsers(name, family, email);

      logger.info("*******>> Rest-serachUser  ");
      for (final dummyuserData duser : alldummyuserdata) {
        userData myuser = new userData();
        myuser = geodataFinder.getUser(duser.getEmail());
        myuser.setPassword("");
        outList.add(myuser);
      }
    } catch (final Exception e) {
      logger.info("*******>> Error in serachUser is " + e.toString());

    }
    return outList;
  }

  @RequestMapping(value = "/insertuser", method = RequestMethod.POST)
  public @ResponseBody
  Boolean insertuser(@RequestHeader("X-AURIN-USER-ID") final String roleId,
      @RequestHeader("X-AURIN-PASSWORD") final String rolePw,
      @RequestHeader("name") final String name,
      @RequestHeader("family") final String family,
      @RequestHeader("email") final String email,
      @RequestHeader("roles") final String stroles,
      @RequestHeader("orgs") final String storgs,
      @RequestHeader("apps") final String stapps,
      @RequestHeader("accs") final String staccs)
          throws MessagingException, IOException {

    if (!(roleId.equals(adminUser.getAdminUsername()) && rolePw
        .equals(adminUser.getAdminPassword()))) {
      logger.info("incorrect admin credentials");
      return false;
    }

    String password = ""; // aurin

    final SecureRandom random = new SecureRandom();
    for (int i = 0; i < 1; i++) {
      password = new BigInteger(130, random).toString(32);

      logger.info("random password is :" + password);
    }

    final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    final String hashedPassword = passwordEncoder.encode(password);

    logger.info("hashedPassword is :" + hashedPassword);

    // ALTER TABLE users ADD COLUMN uuid character varying(100);
    // for (int i = 1; i < 150; i++) {
    // final UUID uuid = UUID.randomUUID();
    // final String randomUUIDString = uuid.toString();
    // System.out.println("update users set uuid ='" + randomUUIDString
    // + "' where user_id=" + i + ";");
    // }

    // Alter TABLE organisations Drop Constraint fk_contact_id
    // Alter TABLE organisations alter column contact_id DROP NOT NULL;
    // Alter TABLE roles Add Constraint users_role UNIQUE (rolename);
    // Alter TABLE organisations Add Constraint users_orgs UNIQUE (orgname);
    // Alter TABLE application Add Constraint users_apps UNIQUE (appname);
    // Alter TABLE acclvls Add Constraint users_acclvl UNIQUE (acclvlname);

    final UUID uuid = UUID.randomUUID();
    final String randomUUIDString = uuid.toString();

    logger.info("randomUUIDString is :" + randomUUIDString);

    final long user_id = geodataFinder.InsertUser(email, name, family,
        hashedPassword, randomUUIDString);

    Boolean lsw = true;
    if (user_id == 0) {
      lsw = false;
    } else {
      final String[] orgs = storgs.split(",");
      for (final String st : orgs) {
        if (st.length() > 0) {
          if (geodataFinder.InsertOrgs(st, user_id) == 0) {
            lsw = false;
          }
        }
      }
      final String[] apps = stapps.split(",");
      for (final String st : apps) {
        if (st.length() > 0) {
          if (geodataFinder.InsertApps(st, user_id) == 0) {
            lsw = false;
          }
        }
      }
      final String[] roles = stroles.split(",");
      for (final String st : roles) {
        if (st.length() > 0) {
          if (geodataFinder.InsertRoles(st, user_id) == 0) {
            lsw = false;
          }
        }
      }
      final String[] accs = staccs.split(",");

      for (final String st : accs) {
        if (st.length() > 0) {
          if (geodataFinder.InsertAcclvl(st, user_id) == 0) {
            lsw = false;
          }
        }
      }
      if (geodataFinder.InsertAgreement(user_id) == 0) {
        lsw = false;
      }
    }

    if (lsw == false) {

      return false;

    } else {

      // //////////////// works
      // final Properties props = new Properties();
      // props.put("mail.smtp.starttls.enable", "true");
      // props.put("mail.smtp.host", "smtp.unimelb.edu.au");
      // props.put("mail.smtp.port", "587");
      // props.put("mail.smtp.auth", "true");
      // final Session session = Session.getDefaultInstance(props,
      // new javax.mail.Authenticator() {
      // @Override
      // protected PasswordAuthentication getPasswordAuthentication() {
      // return new PasswordAuthentication("ashamakhy", "*******");
      // }
      // });
      // try {
      //
      // final Message message = new MimeMessage(session);
      // // message.setFrom(new InternetAddress("alireza.shamakhy@gmail.com"));
      // message.setFrom(new
      // InternetAddress("alireza.shamakhy@unimelb.edu.au"));
      // message.setRecipients(Message.RecipientType.TO,
      // InternetAddress.parse("alireza.shamakhy@gmail.com"));
      // message.setSubject("Testing Subject");
      // message.setText("Dear Mail Crawler,"
      // + "\n\n No spam to my email, please!");
      //
      // Transport.send(message);
      //
      // System.out.println("Done");
      //
      // } catch (final MessagingException e) {
      // throw new RuntimeException(e);
      // }

      // final String clink = classmail.getUrl() + "/authchangepassword/"
      // + randomUUIDString;
      //
      // final String msg = "<br>Your current password is : " + password
      // + " <br> please change it using link: <br> <a href='" + clink
      // + "'> change password </a>";
      //
      // final String subject = "Workbench Access";
      //
      // final String from = classmail.getFrom();
      // final String to = email;
      //
      // try {
      // final Message message = new MimeMessage(getSession());
      //
      // message.addRecipient(RecipientType.TO, new InternetAddress(to));
      // message.addFrom(new InternetAddress[] { new InternetAddress(from) });
      //
      // message.setSubject(subject);
      // message.setContent(msg, "text/html");
      //
      // Transport.send(message);
      // logger.info("Email sent to:" + email);
      // } catch (final MessagingException mex) {
      // logger.info(mex.toString());
      // return false;
      // }
      final String[] apps = stapps.split(",");
      final List<String> lst= new ArrayList<String>();
      for (final String st : apps) {
        if (st.length() > 0) {
          lst.add(geodataFinder.FindApp(st));//
        }
      }
      return sendEmail(randomUUIDString, password, email, lst);

      // return true;

    }

  }

  private class Authenticator extends javax.mail.Authenticator {
    private final PasswordAuthentication authentication;

    public Authenticator() {
      final String username = classmail.getUsername();
      final String password = classmail.getPassword();
      authentication = new PasswordAuthentication(username, password);
    }

    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
      return authentication;
    }
  }

  private Session getSession() {
    final Authenticator authenticator = new Authenticator();

    final Properties properties = new Properties();
    properties.setProperty("mail.smtp.submitter", authenticator
        .getPasswordAuthentication().getUserName());
    properties.setProperty("mail.smtp.auth", classmail.getAuth().toString());

    properties.setProperty("mail.smtp.host", classmail.getHost());
    properties.setProperty("mail.smtp.port", classmail.getPort());

    return Session.getInstance(properties, authenticator);
  }

  public Boolean sendEmail(final String randomUUIDString,
      final String password, final String email, final List<String> lstApps) throws IOException {
    final String clink = classmail.getUrl() + "authchangepassword/"
        + randomUUIDString;

    logger.info("Starting sending Email to:" + email);
    String msg="";

    if (lstApps != null)
    {
      msg = msg + "You have been given access to the following applications: <br>";
      for (final String st: lstApps)
      {
        msg = msg + st + "<br>";
      }

    }

    msg = msg + "<br>Your current password is : " + password
        + " <br> please change it using link below: <br> <a href='" + clink
        + "'> change password </a><br>After changing the password you can log onto the applications using your email and password. ";

    final String subject = "Aurin Workbench Access";

    final String from = classmail.getFrom();
    final String to = email;

    try {
      final Message message = new MimeMessage(getSession());

      message.addRecipient(RecipientType.TO, new InternetAddress(to));
      message.addFrom(new InternetAddress[] { new InternetAddress(from) });

      message.setSubject(subject);
      message.setContent(msg, "text/html");

      //////////////////////////////////
      final MimeMultipart multipart = new MimeMultipart("related");
      final BodyPart messageBodyPart = new MimeBodyPart();
      //final String htmlText = "<H1>Hello</H1><img src=\"cid:image\">";

      msg = msg +  "<br><br><img src=\"cid:AbcXyz123\" />";
      //msg = msg + "<img src=\"cid:image\">";
      messageBodyPart.setContent(msg, "text/html");
      // add it
      multipart.addBodyPart(messageBodyPart);

      /////// second part (the image)
      //      messageBodyPart = new MimeBodyPart();
      final URL peopleresource = getClass().getResource("/logo.jpg");
      logger.info(peopleresource.getPath());
      //            final DataSource fds = new FileDataSource(
      //                peopleresource.getPath());
      //
      //      messageBodyPart.setDataHandler(new DataHandler(fds));
      //      messageBodyPart.setHeader("Content-ID", "<image>");
      //      // add image to the multipart
      //      //multipart.addBodyPart(messageBodyPart);

      ///////////
      final MimeBodyPart imagePart = new MimeBodyPart();
      imagePart.attachFile(peopleresource.getPath());
      //      final String cid = "1";
      //      imagePart.setContentID("<" + cid + ">");
      imagePart.setHeader("Content-ID", "AbcXyz123");
      imagePart.setDisposition(MimeBodyPart.INLINE);
      multipart.addBodyPart(imagePart);

      // put everything together
      message.setContent(multipart);
      ////////////////////////////////

      Transport.send(message);
      logger.info("Email sent to:" + email);
    } catch (final Exception mex) {
      logger.info(mex.toString());
      return false;
    }

    return true;
  }

  @RequestMapping(value = "/sendEmailLinkPasswordChange", method = RequestMethod.POST)
  public @ResponseBody
  Boolean sendEmailLinkPasswordChange(
      @RequestHeader("X-AURIN-USER-ID") final String roleId,
      @RequestHeader("X-AURIN-PASSWORD") final String rolePw,
      @RequestHeader("email") final String email) {
    try {
      final String uuid = geodataFinder.getUuidFromEmail(email);
      if (uuid.length() > 0) {
        String password = ""; // aurin

        final SecureRandom random = new SecureRandom();
        for (int i = 0; i < 1; i++) {
          password = new BigInteger(130, random).toString(32);

          logger.info("random password is :" + password);
        }

        final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        final String hashedPassword = passwordEncoder.encode(password);

        logger.info("hashedPassword is :" + hashedPassword);
        geodataFinder.changeuuidPassword(uuid, hashedPassword);

        return sendEmail(uuid, password, email, null);

      }

    } catch (final Exception e) {
      logger.info(e.toString());
      return false;

    }
    return false;

  }

  @RequestMapping(value = "/deleteUser", method = RequestMethod.POST)
  public @ResponseBody
  String deleteUser(
      @RequestHeader("X-AURIN-USER-ID") final String roleId,
      @RequestHeader("X-AURIN-PASSWORD") final String rolePw,
      @RequestHeader("user_id") final String user_id) {
    try {

      return geodataFinder.deleteuser(user_id);

    } catch (final Exception e) {
      logger.info(e.toString());


    }
    return null;

  }

  @RequestMapping(method = RequestMethod.GET, value = "/authchangepassword/{id}")
  public String webchangePassword(@PathVariable("id") final String id,
      final Model model) {

    final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    // final String hashedPassword = passwordEncoder.encode(password);

    logger.info("*******>> changePassword for uuid ={}  ", id);

    final String out = geodataFinder.getUserID(id);
    if (out.length() > 0) {

      model.addAttribute("msg", classmail.getUrl());
      model.addAttribute("id", out);
      return "changepass";
    }
    return "403";

  }

  @RequestMapping(method = RequestMethod.POST, value = "/changeoldpassword")
  public @ResponseBody
  Boolean changeoldPassword(@RequestHeader("uuid") final String uuid,
      @RequestHeader("oldpassword") final String oldpassword,
      @RequestHeader("newpassword") final String newpassword,
      final HttpServletRequest request) {
    try {
      logger.info("*******>> changeoldpassword for uuid={}  ", uuid);
      final String password = geodataFinder.getPassID(uuid);
      if (password.length() > 0) {

        final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        final boolean isMatch = passwordEncoder.matches(oldpassword, password);

        if (isMatch == true) {
          logger
          .info("*******>> changeoldpassword passwords are match. now assigning new password. ");
          final String hashednewPassword = passwordEncoder.encode(newpassword);
          return geodataFinder.changeuuidPassword(uuid, hashednewPassword);
        }
      }

    } catch (final Exception e) {
      logger.info("Error in changeoldPassword is : " + e.toString());

    }
    return false;
  }

  @RequestMapping(value = "/addRole", method = RequestMethod.POST)
  public @ResponseBody
  Boolean addRole(@RequestHeader("X-AURIN-USER-ID") final String roleId,
      @RequestHeader("X-AURIN-PASSWORD") final String rolePw,
      @RequestHeader("role") final String role) {

    if (!(roleId.equals(adminUser.getAdminUsername()) && rolePw
        .equals(adminUser.getAdminPassword()))) {
      logger.info("incorrect admin credentials");
      return false;
    }
    try {
      return geodataFinder.addRole(role);
    } catch (final Exception e) {
      logger.info("Error in addRole is : " + e.toString());
    }
    return false;
  }

  @RequestMapping(value = "/addOrg", method = RequestMethod.POST)
  public @ResponseBody
  Boolean addOrg(@RequestHeader("X-AURIN-USER-ID") final String roleId,
      @RequestHeader("X-AURIN-PASSWORD") final String rolePw,
      @RequestHeader("org") final String org) {

    if (!(roleId.equals(adminUser.getAdminUsername()) && rolePw
        .equals(adminUser.getAdminPassword()))) {
      logger.info("incorrect admin credentials");
      return false;
    }
    try {
      return geodataFinder.addOrg(org);
    } catch (final Exception e) {
      logger.info("Error in addOrg is : " + e.toString());
    }
    return false;
  }

  @RequestMapping(value = "/addApp", method = RequestMethod.POST)
  public @ResponseBody
  Boolean addApp(@RequestHeader("X-AURIN-USER-ID") final String roleId,
      @RequestHeader("X-AURIN-PASSWORD") final String rolePw,
      @RequestHeader("app") final String app) {

    if (!(roleId.equals(adminUser.getAdminUsername()) && rolePw
        .equals(adminUser.getAdminPassword()))) {
      logger.info("incorrect admin credentials");
      return false;
    }
    try {
      return geodataFinder.addApp(app);
    } catch (final Exception e) {
      logger.info("Error in addApp is : " + e.toString());
    }
    return false;
  }

  @RequestMapping(value = "/addAcc", method = RequestMethod.POST)
  public @ResponseBody
  Boolean addAcc(@RequestHeader("X-AURIN-USER-ID") final String roleId,
      @RequestHeader("X-AURIN-PASSWORD") final String rolePw,
      @RequestHeader("Acc") final String acc) {

    if (!(roleId.equals(adminUser.getAdminUsername()) && rolePw
        .equals(adminUser.getAdminPassword()))) {
      logger.info("incorrect admin credentials");
      return false;
    }
    try {
      return geodataFinder.addAcc(acc);
    } catch (final Exception e) {
      logger.info("Error in addAcc is : " + e.toString());
    }
    return false;
  }

}
