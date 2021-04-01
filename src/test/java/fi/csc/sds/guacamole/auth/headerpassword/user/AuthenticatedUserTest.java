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

import javax.servlet.http.HttpServletRequest;

import org.apache.guacamole.net.auth.AuthenticationProvider;
import org.apache.guacamole.net.auth.Credentials;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;

import fi.csc.sds.guacamole.auth.headerpassword.HTTPHeaderPasswordAuthenticationProvider;

public class AuthenticatedUserTest {

    @Inject
    private AuthenticatedUser user;
    private Credentials credentials;

    protected Injector injector = Guice.createInjector(new AbstractModule() {
        @Override
        protected void configure() {
            bind(AuthenticationProvider.class).to(HTTPHeaderPasswordAuthenticationProvider.class);
        }
    });

    @BeforeMethod
    public void setUp() {
        credentials = new Credentials(null, null, (HttpServletRequest) Mockito.mock(HttpServletRequest.class));
        injector.injectMembers(this);
    }

    @Test
    public void testSuccess() {
        user.init("username123456789123456789123456789123456789123456789123456789@domain", "password", credentials);
        Assert.assertEquals("username123456789123456789123456789123456789123456789123456789@domain", user.getIdentifier());
        Assert.assertEquals("username123456789123456789123456", credentials.getUsername());
        Assert.assertEquals("password", credentials.getPassword());
        Assert.assertNotNull(user.getAuthenticationProvider());
    }

}
