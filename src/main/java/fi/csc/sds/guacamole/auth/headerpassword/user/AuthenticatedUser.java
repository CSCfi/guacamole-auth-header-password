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
package fi.csc.sds.guacamole.auth.headerpassword.user;

import com.google.inject.Inject;

import org.apache.guacamole.net.auth.AbstractAuthenticatedUser;
import org.apache.guacamole.net.auth.AuthenticationProvider;
import org.apache.guacamole.net.auth.Credentials;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * HTTP Header Password specific implementation of Authenticated user. Username
 * and password are stored to credentials.
 */
public class AuthenticatedUser extends AbstractAuthenticatedUser {

    /**
     * Logger for this class.
     */
    private final Logger log = LoggerFactory.getLogger(AuthenticatedUser.class);

    /**
     * Authentication provider.
     */
    @Inject
    private AuthenticationProvider authProvider;

    /**
     * Credentials provided.
     */
    private Credentials creds;

    /**
     * Initialises the authenticated user with provided username, password and
     * credentials. Username is sanitised for login credential by stripping away 
     * '@' - delimited domain and then cutting the length to 32 characters.
     * 
     * @param username    provided username
     * @param password    provided password
     * @param credentials provided credentials
     */
    public void init(String username, String password, Credentials credentials) {
        log.debug("Initialising user with username {} and password {}", username, password);
        creds = credentials;
        setIdentifier(username);
        // For GUAC_USERNAME we remove the possible scope
        if (username != null) {
            String loginUsername=username.split("@")[0];
            if (loginUsername.length() > 32) {
                loginUsername = loginUsername.substring(0, 32);
            }
            creds.setUsername(loginUsername);
        }
        if (password != null) {
            creds.setPassword(password);
        }
    }

    /** {@inheritDoc} */
    @Override
    public AuthenticationProvider getAuthenticationProvider() {
        return authProvider;
    }

    /** {@inheritDoc} */
    @Override
    public Credentials getCredentials() {
        return creds;
    }
}
