/*
 * Copyright (c) 2021 CSC- IT Center for Science, www.csc.fi
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package fi.csc.sds.guacamole.auth.headerpassword;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.apache.guacamole.GuacamoleException;
import org.apache.guacamole.net.auth.AbstractAuthenticationProvider;
import org.apache.guacamole.net.auth.AuthenticatedUser;
import org.apache.guacamole.net.auth.Credentials;

/**
 * Allows users to be authenticated by headers. User will be authenticated if
 * configured headers contain username and password information.
 */
public class HTTPHeaderPasswordAuthenticationProvider extends AbstractAuthenticationProvider {

    /**
     * Injector to manage object for this authentication provider.
     */
    private final Injector injector;

    /**
     * Constructor.
     * 
     * @throws GuacamoleException If creation of Injector fails.
     */
    public HTTPHeaderPasswordAuthenticationProvider() throws GuacamoleException {
        injector = Guice.createInjector(new HTTPHeaderPasswordAuthenticationProviderModule(this));
    }

    /** {@inheritDoc} */
    @Override
    public String getIdentifier() {
        return "fi.csc.sds.headerpassword";
    }

    /** {@inheritDoc} */
    @Override
    public AuthenticatedUser authenticateUser(Credentials credentials) throws GuacamoleException {
        AuthenticationProviderService authProviderService = injector.getInstance(AuthenticationProviderService.class);
        return authProviderService.authenticateUser(credentials);

    }
}
