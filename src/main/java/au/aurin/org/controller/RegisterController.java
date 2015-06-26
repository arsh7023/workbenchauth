package au.aurin.org.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import au.aurin.org.svc.GeodataFinder;
import au.aurin.org.svc.dummyuserData;
import au.aurin.org.svc.dummyuserData2;
import au.aurin.org.svc.myApp;
import au.aurin.org.svc.myOrg;
import au.aurin.org.svc.myRole;

@Controller
public class RegisterController {

  // @Autowired
  // private userValidator userValidator;

  @Resource
  private GeodataFinder geodataFinder;

  private static final Logger logger = LoggerFactory
      .getLogger(RegisterController.class);

  @RequestMapping(value = "/register", method = RequestMethod.GET)
  public String register(
      @ModelAttribute("dummyuserData") final dummyuserData dummyuserdata,
      final Model model) {
    logger.info("Welcome register");

    final Map<String, String> orgList = orgList();
    final Map<String, String> appList = appList();
    final Map<String, String> roleList = roleList();

    model.addAttribute("userOrgs", orgList);
    model.addAttribute("userApps", appList);
    model.addAttribute("userRoles", roleList);

    model.addAttribute("dummyuserdata", dummyuserdata);

    model.addAttribute("msg", "");

    return "register";

  }

  @InitBinder
  protected void initBinder(final WebDataBinder binder) {

    // binder.setValidator(userValidator);
  }

  @RequestMapping(value = "/register", method = RequestMethod.POST)
  // public String createCustomer(
  public ModelAndView createCustomer(
      @Valid @ModelAttribute("dummyuserData") final dummyuserData dummyuserdata,
      final BindingResult bindingResult, final Model model,
      final HttpServletRequest request) {

    if (bindingResult.hasErrors()) {

      final Map<String, String> orgList = orgList();
      final Map<String, String> appList = appList();
      final Map<String, String> roleList = roleList();

      model.addAttribute("userOrgs", orgList);
      model.addAttribute("userApps", appList);
      model.addAttribute("userRoles", roleList);
      // return "register";
      return new ModelAndView("register");

    }

    logger.info("email is {}.", dummyuserdata.getEmail());
    logger.info("firstname is {}.", dummyuserdata.getFirstname());
    logger.info("lastname is {}.", dummyuserdata.getLastname());
    logger.info("orglist is {}", dummyuserdata.getUserOrgs());
    logger.info("applist is {}", dummyuserdata.getUserApps());
    logger.info("rolelist is {}", dummyuserdata.getUserRoles());

    final String password = "aurin";
    final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    final String hashedPassword = passwordEncoder.encode(password);

    final long user_id = geodataFinder.InsertUser(dummyuserdata.getEmail(),
        dummyuserdata.getFirstname(), dummyuserdata.getLastname(),
        hashedPassword, "");

    Boolean lsw = true;
    if (user_id == 0) {
      lsw = false;
    } else {
      final String[] orgs = dummyuserdata.getUserOrgs().split(",");
      for (final String st : orgs) {
        if (geodataFinder.InsertOrgs(st, user_id) == 0) {
          lsw = false;
        }
      }
      final String[] apps = dummyuserdata.getUserApps().split(",");
      for (final String st : apps) {
        if (geodataFinder.InsertApps(st, user_id) == 0) {
          lsw = false;
        }
      }
      final String[] roles = dummyuserdata.getUserRoles().split(",");
      for (final String st : roles) {
        if (geodataFinder.InsertRoles(st, user_id) == 0) {
          lsw = false;
        }
      }
      if (geodataFinder.InsertAgreement(user_id) == 0) {
        lsw = false;
      }
    }

    if (lsw == false) {
      model.addAttribute("msg", "Registeration not successful for email :"
          + dummyuserdata.getEmail());
      final Map<String, String> orgList = orgList();
      final Map<String, String> appList = appList();
      final Map<String, String> roleList = roleList();

      model.addAttribute("userOrgs", orgList);
      model.addAttribute("userApps", appList);
      model.addAttribute("userRoles", roleList);
      return new ModelAndView("register");

    } else {
      model.addAttribute("msg",
          "successfully registered: " + dummyuserdata.getEmail());
      return new ModelAndView("success");

    }

  }

  Map<String, String> orgList() {
    final Map<String, String> orgList = new LinkedHashMap<String, String>();
    List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
    try {
      mapList = geodataFinder.getOrgs();
      for (final Map<String, Object> vmap : mapList) {
        final Iterator iterator = vmap.entrySet().iterator();
        String id = "";
        String value = "";
        int i = 0;
        while (iterator.hasNext()) {
          final Map.Entry mapEntry = (Map.Entry) iterator.next();
          if (i == 0) {
            id = mapEntry.getValue().toString();
          } else {
            value = mapEntry.getValue().toString();
          }
          i = i + 1;
        }
        orgList.put(id, value);
      }
    } catch (final Exception e) {
      logger.info("orgList failed  error is: ", e.toString());

    }

    return orgList;
  }

  Map<String, String> appList() {
    final Map<String, String> appList = new LinkedHashMap<String, String>();
    List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
    try {
      mapList = geodataFinder.getApps();
      for (final Map<String, Object> vmap : mapList) {
        final Iterator iterator = vmap.entrySet().iterator();
        String id = "";
        String value = "";
        int i = 0;
        while (iterator.hasNext()) {
          final Map.Entry mapEntry = (Map.Entry) iterator.next();
          if (i == 0) {
            id = mapEntry.getValue().toString();
          } else {
            value = mapEntry.getValue().toString();
          }
          i = i + 1;
        }
        appList.put(id, value);
      }
    } catch (final Exception e) {
      logger.info("appList failed  error is: ", e.toString());

    }

    return appList;
  }

  Map<String, String> roleList() {
    final Map<String, String> roleList = new LinkedHashMap<String, String>();
    List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
    try {
      mapList = geodataFinder.getRoles();
      for (final Map<String, Object> vmap : mapList) {
        final Iterator iterator = vmap.entrySet().iterator();
        String id = "";
        String value = "";
        int i = 0;
        while (iterator.hasNext()) {
          final Map.Entry mapEntry = (Map.Entry) iterator.next();
          if (i == 0) {
            id = mapEntry.getValue().toString();
          } else {
            value = mapEntry.getValue().toString();
          }
          i = i + 1;
        }
        roleList.put(id, value);
      }
    } catch (final Exception e) {
      logger.info("roleList failed  error is: ", e.toString());

    }

    return roleList;
  }

  @RequestMapping(value = "/{id}/user", method = RequestMethod.GET)
  public String getOneusers(
      @ModelAttribute("dummyuserData2") final dummyuserData2 dummyuserdata2,
      @PathVariable("id") final String user_id, final Model model) {
    logger.info("Welcome getOneusers");

    final dummyuserData onedummyuserdata = geodataFinder.getOneUser(user_id);

    final dummyuserData2 onedummyuserdata2 = new dummyuserData2();
    onedummyuserdata2.setEmail(onedummyuserdata.getEmail());
    onedummyuserdata2.setFirstname(onedummyuserdata.getFirstname());
    onedummyuserdata2.setLastname(onedummyuserdata.getLastname());

    final List<myOrg> userOrgsNew = new ArrayList<myOrg>();
    final Map<String, String> orgList = orgList();
    for (final String org_id : orgList.keySet()) {
      final myOrg o = new myOrg();
      o.setOrg_id(org_id);
      o.setOrgname(orgList.get(org_id));
      if (geodataFinder.FindOrgUser(org_id, user_id) != 0) {
        o.setSelected("selected=selected");
      }
      userOrgsNew.add(o);
    }
    onedummyuserdata2.setUserOrgsNew(userOrgsNew);

    final List<myApp> userAppsNew = new ArrayList<myApp>();
    final Map<String, String> appList = appList();
    for (final String app_id : appList.keySet()) {
      final myApp o = new myApp();
      o.setApp_id(app_id);
      o.setAppname(appList.get(app_id));
      if (geodataFinder.FindAppUser(app_id, user_id) != 0) {
        o.setSelected("selected=selected");
      }
      userAppsNew.add(o);
    }
    onedummyuserdata2.setUserAppsNew(userAppsNew);

    final List<myRole> userRolesNew = new ArrayList<myRole>();
    final Map<String, String> roleList = roleList();
    for (final String role_id : roleList.keySet()) {
      final myRole o = new myRole();
      o.setRole_id(role_id);
      o.setRolename(roleList.get(role_id));
      if (geodataFinder.FindRoleUser(role_id, user_id) != 0) {
        o.setSelected("selected=selected");
      }
      userRolesNew.add(o);
    }
    onedummyuserdata2.setUserRolesNew(userRolesNew);

    model.addAttribute("userOrgsNew", userOrgsNew);
    model.addAttribute("userAppsNew", userAppsNew);
    model.addAttribute("userRolesNew", userRolesNew);

    model.addAttribute("onedummyuserdata2", onedummyuserdata2);

    model.addAttribute("msg", "");

    // final Map<String, String> orgList = orgList();
    // final Map<String, String> appList = appList();
    // final Map<String, String> roleList = roleList();
    //
    // model.addAttribute("userOrgs", orgList);
    // model.addAttribute("userApps", appList);
    // model.addAttribute("userRoles", roleList);

    return "user";

  }

  @RequestMapping(value = "/{id}/user", method = RequestMethod.POST)
  // public String createCustomer(
  public ModelAndView updateuser(
      @Valid @ModelAttribute("dummyuserData2") final dummyuserData2 dummyuserdata2,
      final BindingResult bindingResult, final Model model,
      @PathVariable("id") final String user_id, final HttpServletRequest request) {

    String[] userOrgsNew;
    String[] userAppsNew;
    String[] userRolesNew;

    if (bindingResult.hasErrors()) {

      logger.info("email is {}.", dummyuserdata2.getEmail());
      logger.info("firstname is {}.", dummyuserdata2.getFirstname());
      logger.info("lastname is {}.", dummyuserdata2.getLastname());
      model.addAttribute("msg", "no empty.");
      return new ModelAndView("redirect:/" + user_id + "/user");

    }

    userOrgsNew = request.getParameterValues("flselectorg");
    userAppsNew = request.getParameterValues("flselectapp");
    userRolesNew = request.getParameterValues("flselectrole");

    if (userOrgsNew == null) {
      model.addAttribute("msg", "organizsation is empty.");
      return new ModelAndView("redirect:/" + user_id + "/user");

      // return null;
    } else {
      logger.info("email is {}.", dummyuserdata2.getEmail());
      logger.info("firstname is {}.", dummyuserdata2.getFirstname());
      logger.info("lastname is {}.", dummyuserdata2.getLastname());

      userOrgsNew = request.getParameterValues("flselectorg");
      userAppsNew = request.getParameterValues("flselectapp");
      userRolesNew = request.getParameterValues("flselectrole");

      for (final String m : userOrgsNew) {
        logger.info("m is {}", m);
      }

      return new ModelAndView("success");

    }

  }

}
