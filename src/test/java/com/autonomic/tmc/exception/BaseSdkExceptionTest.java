/*-
 * ‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾
 * TMC OSS SDK Common Classes
 * ——————————————————————————————————————————————————————————————————————————————
 * Copyright (C) 2016 - 2021 Autonomic, LLC
 * ——————————————————————————————————————————————————————————————————————————————
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License
 * ______________________________________________________________________________
 */
package com.autonomic.tmc.exception;

import static com.autonomic.tmc.exception.ErrorSourceType.SDK_CLIENT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.autonomic.tmc.environment.ProjectProperties;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BaseSdkExceptionTest {

    private ProjectProperties mockProperties;

    @BeforeEach
    void setup() {
        mockProperties = mock(ProjectProperties.class);
        ProjectProperties.put(this.getClass().getName(), mockProperties);
    }

    @AfterEach
    void tearDown() {
        ProjectProperties.removeAll();
    }

    @Test
    void projectPropertiesMethods_returnStringContainingNull_doesNotCorrectForNullInMessage() {
        when(mockProperties.getName(anyString())).thenReturn("null");
        when(mockProperties.getVersion(anyString())).thenReturn("null");

        assertThat(BaseSdkException.buildMessage(SDK_CLIENT, "test", this.getClass()))
            .contains("null").contains("test").contains(SDK_CLIENT.toString());
    }

    @Test
    void projectPropertiesGetName_throwsRuntimeException_doesNotCascadeException() {
        when(mockProperties.getName(anyString())).thenThrow(new RuntimeException("Bada"));
        when(mockProperties.getVersion(anyString())).thenReturn("someVersion");

        assertThat(BaseSdkException.buildMessage(SDK_CLIENT, "test", this.getClass()))
            .contains("[ AUTONOMIC ]~").contains("[ SDK ]~").doesNotContain("someVersion")
            .contains("test").contains(SDK_CLIENT.toString());
    }

    @Test
    void projectPropertiesGetVersion_throwsRuntimeException_doesNotCascadeException() {
        when(mockProperties.getName(anyString())).thenReturn("something");
        when(mockProperties.getVersion(anyString())).thenThrow(new RuntimeException("Bada"));

        assertThat(BaseSdkException.buildMessage(SDK_CLIENT, "test", this.getClass()))
            .contains("[ AUTONOMIC ]~").contains("[ SDK ]~").doesNotContain("something")
            .contains("test").contains(SDK_CLIENT.toString());
    }

    private void verifyExceptionCanBeInitialized() {
        try {
            new BaseSdkException("test");
        } catch (Throwable e) {
            fail("exceptions must not be thrown");
        }
    }
}
