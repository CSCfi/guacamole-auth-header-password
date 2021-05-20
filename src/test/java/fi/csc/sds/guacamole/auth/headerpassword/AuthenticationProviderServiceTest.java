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

import javax.servlet.http.HttpServletRequest;

import org.apache.guacamole.GuacamoleException;
import org.apache.guacamole.environment.Environment;
import org.apache.guacamole.net.auth.AuthenticationProvider;
import org.apache.guacamole.net.auth.Credentials;
import org.apache.guacamole.net.auth.credentials.GuacamoleInvalidCredentialsException;
import org.junit.Assert;
import org.mockito.Mockito;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;

import fi.csc.sds.guacamole.auth.headerpassword.user.AuthenticatedUser;


public class AuthenticationProviderServiceTest {

    private Environment environment = Mockito.mock(Environment.class);
    private Credentials credentials;
    private HttpServletRequest servletRequest;
    @Inject
    private AuthenticationProviderService provider;
    

    protected Injector injector = Guice.createInjector(new AbstractModule() {
        @Override
        protected void configure() {
            bind(Environment.class).toInstance(environment);
            bind(AuthenticationProvider.class).to(HTTPHeaderPasswordAuthenticationProvider.class);
        }
    });

    @BeforeMethod
    public void setUp() throws GuacamoleException {
        servletRequest = Mockito.mock(HttpServletRequest.class);
        credentials = new Credentials(null, null, servletRequest);
        Mockito.doReturn("OIDC_REMOTE_USER").when(environment).getProperty(Mockito.any(),Mockito.eq("OIDC_REMOTE_USER"));
        Mockito.doReturn("OIDC_access_token").when(environment).getProperty(Mockito.any(),Mockito.eq("OIDC_access_token"));
        Mockito.doReturn("username").when(servletRequest).getHeader("OIDC_REMOTE_USER");
        Mockito.doReturn("password").when(servletRequest).getHeader("OIDC_access_token");
        injector.injectMembers(this);
    }

    @Test
    public void testSuccess() throws GuacamoleException {
        AuthenticatedUser user = provider.authenticateUser(credentials);
        Assert.assertEquals("username", user.getIdentifier());
        Assert.assertEquals("username", user.getCredentials().getUsername());
        Assert.assertEquals("password", user.getCredentials().getPassword());
    }
    
    @Test (expectedExceptions = GuacamoleInvalidCredentialsException.class)
    public void testMissingUsername() throws GuacamoleException {
        Mockito.doReturn(null).when(servletRequest).getHeader("OIDC_REMOTE_USER");
        provider.authenticateUser(credentials);
    }
    
    @Test (expectedExceptions = GuacamoleInvalidCredentialsException.class)
    public void testMissingPassword() throws GuacamoleException {
        Mockito.doReturn(null).when(servletRequest).getHeader("OIDC_access_token");
        provider.authenticateUser(credentials);
    }
}
