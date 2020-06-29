package demo;

import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.context.HttpRequestResponseHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class UserDetailsServiceSecurityContextRepository implements SecurityContextRepository {
    private static final String ATTR = UserDetailsServiceSecurityContextRepository.class.getCanonicalName().concat(".ATTR");

    private AuthenticationTrustResolverImpl authenticationTrustResolver = new AuthenticationTrustResolverImpl();

    private final UserDetailsService userDetailsService;

    public UserDetailsServiceSecurityContextRepository(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public SecurityContext loadContext(HttpRequestResponseHolder httpRequestResponseHolder) {
        HttpServletRequest request = httpRequestResponseHolder.getRequest();
        String username = getUsername(request);
        if (username == null) {
            return SecurityContextHolder.createEmptyContext();
        }
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
        SecurityContext result = SecurityContextHolder.createEmptyContext();
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
        authentication.eraseCredentials();
        result.setAuthentication(authentication);
        return result;
    }

    @Override
    public void saveContext(SecurityContext securityContext, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        Authentication authentication = securityContext.getAuthentication();
        if (authentication == null || authenticationTrustResolver.isAnonymous(authentication)) {
            return;
        }
        httpServletRequest.getSession().setAttribute(ATTR, authentication.getName());
    }

    @Override
    public boolean containsContext(HttpServletRequest httpServletRequest) {
        return getUsername(httpServletRequest) != null;
    }

    private String getUsername(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return null;
        }
        return (String) session.getAttribute(ATTR);
    }
}
