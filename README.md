# guacamole-auth-header-password
Guacamole authentication extension as described in [https://guacamole.apache.org/doc/gug/custom-auth.html](https://guacamole.apache.org/doc/gug/custom-auth.html). The extension is for guacamole version 1.3.0.

# Overview
Authentication Extension that expects user's username, password and group information to be located in http headers. If header for both username and password is located user is logged in. The username and password are populated to {$GUAC_USERNAME} and {$GUAC_PASSWORD} for creating connection to VM. Username populated to {$GUAC_USERNAME} is sanitized by removing any possible domain delimited by '@' and shortening the result to maximum length of 32.

# Access login information in Services
Services may locate the login information from the credentials of AuthenticatedUser.

        AuthenticatedUser authenticatedUser...
        String headerUsername = authenticatedUser.getCredentials().getUsername();
        String headerPassword = authenticatedUser.getCredentials().getPassword();
        String headerGoups = authenticatedUser.getCredentials().getRequest().getAttribute("fi.csc.sds.guacamole.auth.headerpassword.groups");

# Build
    cd guacamole-auth-header-password
    mvn package
    
The output is target/guacamole-auth-header-password-1.3.0.jar

# Installing HTTP header password authentication

The extension guacamole-auth-header-password-1.3.0.jar must be copied to GUACAMOLE_HOME/extensions directory. 

1. Create the GUACAMOLE_HOME/extensions directory, if it does not already exist.
2. Copy guacamole-auth-header-password-1.3.0.jar to GUACAMOLE_HOME/extensions.


3. Edit guacamole.properties to include your header names. Following contains the default values.

    http-username-header=OIDC_REMOTE_USER

    http-password-header=OIDC_access_token

    http-groups-header=OIDC_CLAIM_sdDesktopProjects