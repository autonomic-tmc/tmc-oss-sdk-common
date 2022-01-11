/*-
 * ‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾
 * TMC OSS SDK Common Classes
 * ——————————————————————————————————————————————————————————————————————————————
 * Copyright (C) 2016 - 2022 Autonomic, LLC
 * ——————————————————————————————————————————————————————————————————————————————
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 * ______________________________________________________________________________
 */
package com.autonomic.tmc.exception;

import org.junit.jupiter.api.Test;

import static com.autonomic.tmc.exception.ErrorSourceType.SDK_CLIENT;
import static com.autonomic.tmc.exception.ErrorSourceType.SERVICE;
import static org.assertj.core.api.Assertions.assertThat;

class BaseSdkExceptionTest {

    private static final String TEST_SDK_NAME = "test-app-name";
    private static final String TEST_VERSION = "versionNumber";
    public static final String CLIENT_MESSAGE = "client_message";


    @Test
    void environmentDetails_buildMessage_usesEnvironmentDetailsNameAndVersion() {
        assertThat(BaseSdkException.buildMessage(SDK_CLIENT, CLIENT_MESSAGE, TEST_SDK_NAME, TEST_VERSION))
                .contains(TEST_SDK_NAME)
                .contains(TEST_VERSION)
                .contains(CLIENT_MESSAGE)
                .contains(SDK_CLIENT.toString());
    }

    @Test
    void buildMessageGetEmptySdkName() {
        assertThat(BaseSdkException.buildMessage(SERVICE, CLIENT_MESSAGE, "", TEST_VERSION))
                .contains(BaseSdkException.DEFAULT_NAME)
                .contains(TEST_VERSION);
    }

    @Test
    void buildMessageGetEmptySdkVersion() {
        assertThat(BaseSdkException.buildMessage(SERVICE, CLIENT_MESSAGE, TEST_SDK_NAME, ""))
                .contains(TEST_SDK_NAME)
                .contains(BaseSdkException.DEFAULT_VERSION);
    }

}
