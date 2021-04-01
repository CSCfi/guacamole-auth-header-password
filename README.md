# guacamole-auth-header-password
Guacamole authentication extension as described in [https://guacamole.apache.org/doc/gug/custom-auth.html](https://guacamole.apache.org/doc/gug/custom-auth.html). The extension is for guacamole version 1.3.0.

# Overview
Authentication Extension that expects user's username and password to be located in http headers. If header for both username and password is located user is logged in. The username and password are populated to {$GUAC_USERNAME} and {$GUAC_PASSWORD} for creating connection to VM. Username populated to {$GUAC_USERNAME} is sanitized by removing any possible domain delimited by '@' and shortening  the result to maximum length of 32.    

# Build
    cd guacamole-auth-header-password
    mvn package
    
The output is target/guacamole-auth-header-password-1.2.0.jar
# Installing HTTP header password authentication

The extension guacamole-auth-header-password-1.2.0.jar must be copied to GUACAMOLE_HOME/extensions directory. 

1. Create the GUACAMOLE_HOME/extensions directory, if it does not already exist.
2. Copy guacamole-auth-header-password-1.2.0.jar to GUACAMOLE_HOME/extensions.

By default the extension assumes users username is carried in header named OIDC_REMOTE_USER and password in header named OIDC_access_token. If you use different headers you need to configure that in guacamole.properties. 

3. Edit guacamole.properties  

    http-username-header=[MY_USERNAME_HEADER]

    http-password-header=[MY_PASSWORD_HEADER]

