/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package fi.csc.sds.guacamole.auth.headerpassword;

import javax.servlet.http.HttpServletRequest;
import org.apache.guacamole.GuacamoleException;
import org.apache.guacamole.net.auth.Credentials;
import org.apache.guacamole.net.auth.credentials.CredentialsInfo;
import org.apache.guacamole.net.auth.credentials.GuacamoleInvalidCredentialsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.google.inject.Provider;

import fi.csc.sds.guacamole.auth.headerpassword.user.AuthenticatedUser;

/**
 * Convenience methods for HTTP Header Password provider implementation.
 */
public class AuthenticationProviderService {

    /**
     * Logger for this class.
     */
    private final Logger log = LoggerFactory.getLogger(AuthenticationProviderService.class);

    /**
     * Configuration information.
     */
    @Inject
    private ConfigurationService configuration;

    /**
     * HTTP Header Password authenticated user Provider.
     */
    @Inject
    private Provider<AuthenticatedUser> authenticatedUserProvider;

    /**
     * Get authenticated user. User will be authenticated if configured headers
     * contain username and password information.
     * 
     * @param credentials passed for populating username and password.
     * @return Authenticated user.
     * @throws GuacamoleException If user cannot be authenticated.
     */
    public AuthenticatedUser authenticateUser(Credentials credentials) throws GuacamoleException {
        HttpServletRequest request = credentials.getRequest();
        if (request == null) {
            throw new GuacamoleInvalidCredentialsException("Invalid login. No http servlet",
                    CredentialsInfo.USERNAME_PASSWORD);
        }
        AuthenticatedUser authenticatedUser = authenticatedUserProvider.get();
        authenticatedUser.init(request.getHeader(configuration.getUsername()),
                request.getHeader(configuration.getPassword()), credentials);
        if (authenticatedUser.getIdentifier() == null || authenticatedUser.getIdentifier().isEmpty()
                || authenticatedUser.getCredentials() == null
                || authenticatedUser.getCredentials().getUsername() == null
                || authenticatedUser.getCredentials().getUsername().isEmpty()
                || authenticatedUser.getCredentials().getPassword() == null
                || authenticatedUser.getCredentials().getPassword().isEmpty()) {
            log.error("User not authenticated, headers not containing necessary information");
            throw new GuacamoleInvalidCredentialsException(
                    "Invalid login. Headers not containing necessary information", CredentialsInfo.USERNAME_PASSWORD);
        }
        request.setAttribute("fi.csc.sds.guacamole.auth.headerpassword.groups",
                request.getHeader(configuration.getGroups()));
        log.info("User {} with groups {} authenticated", authenticatedUser.getCredentials().getUsername(),
                request.getAttribute("fi.csc.sds.guacamole.auth.headerpassword.groups"));
        return authenticatedUser;
    }
}
