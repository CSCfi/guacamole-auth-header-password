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

import com.google.inject.Inject;
import org.apache.guacamole.GuacamoleException;
import org.apache.guacamole.environment.Environment;

/**
 * Locate HTTP Header Password properties.
 */
public class ConfigurationService {

    /**
     * Guacamole server environment.
     */
    @Inject
    private Environment environment;

    /**
     * Get name of the header carrying username.
     * 
     * @return name of the header carrying username.
     * @throws GuacamoleException if something unexpected happens.
     */
    public String getUsername() throws GuacamoleException {
        return environment.getProperty(HTTPHeaderPasswordGuacamoleProperties.HTTP_USERNAME_HEADER, "OIDC_REMOTE_USER");
    }

    /**
     * Get name of the header carrying password.
     * 
     * @return name of the header carrying password.
     * @throws GuacamoleException if something unexpected happens.
     */
    public String getPassword() throws GuacamoleException {
        return environment.getProperty(HTTPHeaderPasswordGuacamoleProperties.HTTP_PASSWORD_HEADER, "OIDC_access_token");
    }

    /**
     * Get name of the header carrying groups.
     *
     * @return name of the header carrying groups.
     * @throws GuacamoleException if something unexpected happens.
     */
    public String getGroups() throws GuacamoleException {
        return environment.getProperty(HTTPHeaderPasswordGuacamoleProperties.HTTP_GROUPS_HEADER, "OIDC_CLAIM_sdDesktopProjects");
    }
}
