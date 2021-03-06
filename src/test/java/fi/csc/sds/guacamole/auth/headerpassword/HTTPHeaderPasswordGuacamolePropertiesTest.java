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
import org.testng.Assert;
import org.testng.annotations.Test;

public class HTTPHeaderPasswordGuacamolePropertiesTest {

    @Test
    public void testSuccess() throws GuacamoleException {
        Assert.assertEquals("http-username-header", HTTPHeaderPasswordGuacamoleProperties.HTTP_USERNAME_HEADER.getName());
        Assert.assertEquals("http-password-header", HTTPHeaderPasswordGuacamoleProperties.HTTP_PASSWORD_HEADER.getName());
        Assert.assertEquals("http-groups-header", HTTPHeaderPasswordGuacamoleProperties.HTTP_GROUPS_HEADER.getName());
    }
}
