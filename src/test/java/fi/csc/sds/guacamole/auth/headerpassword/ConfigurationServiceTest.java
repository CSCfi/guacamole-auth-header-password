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

import org.apache.guacamole.GuacamoleException;
import org.apache.guacamole.environment.Environment;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;

public class ConfigurationServiceTest {

    private Environment environment = Mockito.mock(Environment.class);
    @Inject
    private ConfigurationService configuration;

    protected Injector injector = Guice.createInjector(new AbstractModule() {
        @Override
        protected void configure() {
            bind(Environment.class).toInstance(environment);
        }
    });

    @BeforeMethod
    public void setUp() throws GuacamoleException {
        Mockito.doReturn("OIDC_REMOTE_USER").when(environment).getProperty(Mockito.any(),
                Mockito.eq("OIDC_REMOTE_USER"));
        Mockito.doReturn("OIDC_access_token").when(environment).getProperty(Mockito.any(),
                Mockito.eq("OIDC_access_token"));
        Mockito.doReturn("OIDC_CLAIM_sdDesktopProjects").when(environment).getProperty(Mockito.any(),
                Mockito.eq("OIDC_CLAIM_sdDesktopProjects"));
        injector.injectMembers(this);
    }

    @Test
    public void testSuccess() throws GuacamoleException {
        Assert.assertEquals("OIDC_REMOTE_USER", configuration.getUsername());
        Assert.assertEquals("OIDC_access_token", configuration.getPassword());
        Assert.assertEquals("OIDC_CLAIM_sdDesktopProjects", configuration.getGroups());
    }
}
