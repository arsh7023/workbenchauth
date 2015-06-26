package au.aurin.org.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import au.aurin.org.io.classMail;
import au.aurin.org.svc.AdminUser;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

  private static final Logger logger = LoggerFactory
      .getLogger(HomeController.class);

  @Autowired
  private classMail classmail;

  @Autowired
  private AdminUser adminUser;

  /**
   * Simply selects the home view to render by returning its name.
   */
  @RequestMapping(value = { "/", "/welcome**" }, method = RequestMethod.GET)
  public String home(final Locale locale, final Model model) {
    logger.info("Welcome home! The client locale is {}.", locale);

    final Date date = new Date();
    final DateFormat dateFormat = DateFormat.getDateTimeInstance(
        DateFormat.LONG, DateFormat.LONG, locale);

    // final String formattedDate = dateFormat.format(date);

    // model.addAttribute("msg", formattedDate );

    return "home";
  }

  /**
   * @param model
   * @return
   */
  @RequestMapping(value = "/success", method = RequestMethod.GET)
  public String sucess_opt(final Model model) {
    logger.info("Welcome success");

    // final String formattedDate = dateFormat.format(date);

    model.addAttribute("msg", "");

    return "success";
  }

  /**
   * @param error
   * @param logout
   * @return
   */
  @RequestMapping(value = "/login", method = RequestMethod.GET)
  public ModelAndView login(
      @RequestParam(value = "error", required = false) final String error,
      @RequestParam(value = "logout", required = false) final String logout) {

    final ModelAndView model = new ModelAndView();
    if (error != null) {
      model.addObject("error", "Invalid username and password!");
    }

    if (logout != null) {
      model.addObject("msg", "You've been logged out successfully.");
    }
    model.setViewName("login");

    return model;

  }

  @RequestMapping(value = "/action", method = RequestMethod.POST)
  public String action(final Model model) {
    return "action";

  }

  @RequestMapping(value = "/admin**", method = RequestMethod.GET)
  public ModelAndView adminPage() {

    final ModelAndView model = new ModelAndView();
    model.addObject("title",
        "Spring Security Login Form - Database Authentication");
    model.addObject("message", "This page is for ROLE_ADMIN only!");
    model.setViewName("admin");
    return model;

  }

  @RequestMapping(value = "/403", method = RequestMethod.GET)
  public ModelAndView accesssDenied() {

    final ModelAndView model = new ModelAndView();

    // check if user is login
    final Authentication auth = SecurityContextHolder.getContext()
        .getAuthentication();
    if (!(auth instanceof AnonymousAuthenticationToken)) {
      final UserDetails userDetail = (UserDetails) auth.getPrincipal();
      model.addObject("username", userDetail.getUsername());
    }

    model.setViewName("403");
    return model;

  }

  @RequestMapping(value = { "/userlist" }, method = RequestMethod.GET)
  public String usertest(final Locale locale, final Model model) {
    logger.info("userlist controller");

    model.addAttribute("url", classmail.getUrl());
    model.addAttribute("username", adminUser.getAdminUsername());
    model.addAttribute("password", adminUser.getAdminPassword());

    return "userlist";
  }

  @RequestMapping(value = { "/search" }, method = RequestMethod.GET)
  public String search(final Locale locale, final Model model) {
    logger.info("search controller");

    model.addAttribute("url", classmail.getUrl());
    model.addAttribute("username", adminUser.getAdminUsername());
    model.addAttribute("password", adminUser.getAdminPassword());

    return "search";
  }

  @RequestMapping(value = { "/usernew" }, method = RequestMethod.GET)
  public String usernew(final Locale locale, final Model model) {
    logger.info("usernew controller");

    model.addAttribute("url", classmail.getUrl());
    model.addAttribute("username", adminUser.getAdminUsername());
    model.addAttribute("password", adminUser.getAdminPassword());

    return "usernew";
  }

  @RequestMapping(value = { "/additem" }, method = RequestMethod.GET)
  public String additem(final Locale locale, final Model model) {
    logger.info("additem controller");

    model.addAttribute("url", classmail.getUrl());
    model.addAttribute("username", adminUser.getAdminUsername());
    model.addAttribute("password", adminUser.getAdminPassword());

    return "additem";
  }

}
