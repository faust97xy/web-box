package pcshaman.web.core.front.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Service;
import pcshaman.web.core.util.object.ObjectTool;

/**
 *
 * @author jjurczak
 */
@Service
public class SecurityManager {

    public UserDetails getUserDetails() {
        UserDetails userDetails = null;
        SecurityContext context = SecurityContextHolder.getContext();
        if (ObjectTool.exist(context)) {
            Authentication authentication = context.getAuthentication();
            if (ObjectTool.exist(authentication)) {
                Object principal = authentication.getPrincipal();
                if (principal instanceof UserDetails) {
                    userDetails = (UserDetails) principal;
                }
            }
        }
        return userDetails;
    }

    public String getUserName() {
        String username = null;

        UserDetails userDetails = getUserDetails();
        if (userDetails != null) {
            username = userDetails.getUsername();
        }

        return username;
    }

    public String getIpAddress() {
        Object details = SecurityContextHolder.getContext().getAuthentication().getDetails();
        String ip = "";
        if (details instanceof WebAuthenticationDetails) {
            WebAuthenticationDetails webDetails = (WebAuthenticationDetails) details;
            ip = webDetails.getRemoteAddress();

        } else {
            ip = null;
        }

        return ip;
    }
}
