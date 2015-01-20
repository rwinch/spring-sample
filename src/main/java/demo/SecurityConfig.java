package demo;

/**
 * <p>Created: Jan 15, 2015</p>
 * @author Alexander S. Pogrebnyak
 */

///@name Imports
//@{

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

//@}

/**
 * Security config to reproduce SEC-2815
 *
 * @author Alexander S. Pogrebnyak
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class SecurityConfig {

}