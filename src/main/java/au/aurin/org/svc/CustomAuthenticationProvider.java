package au.aurin.org.svc;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

  /* default username and password, overridden in spring security context */
  private String adminUsername = "aurin";
  private String adminPassword = "aurin";

  public void setAdminUsername(final String adminUsername) {
    this.adminUsername = adminUsername;
  }

  public void setAdminPassword(final String adminPassword) {
    this.adminPassword = adminPassword;
  }

  @Override
  public boolean supports(final Class<?> authentication) {
    return authentication.equals(UsernamePasswordAuthenticationToken.class);
  }

  @Override
  public Authentication authenticate(final Authentication authentication) {
    final String name = authentication.getName();
    final String password = authentication.getCredentials().toString();

    if( name.equals(adminUsername) && password.equals(adminPassword) ) {
      final List<GrantedAuthority> grantedAuths = new ArrayList<GrantedAuthority>();
      grantedAuths.add(new SimpleGrantedAuthority("ROLE_USER"));

      final Authentication auth = new UsernamePasswordAuthenticationToken(name,
          password, grantedAuths);
      return auth;
    } else {
      return null;
    }
  }
}
