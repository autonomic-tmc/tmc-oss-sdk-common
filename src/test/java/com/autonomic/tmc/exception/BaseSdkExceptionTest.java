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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.jar.Manifest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BaseSdkExceptionTest {

    public static final String AUTONOMIC = "AUTONOMIC";
    public static final String SDK = "SDK";
    private Manifest mockManifest;

    private ProjectProperties mockProperties;

    @BeforeEach
    void setup() {
        mockManifest = mock(Manifest.class);
        mockProperties = mock(ProjectProperties.class);
        ProjectProperties.SINGLETON_INSTANCES.put(this.getClass().getName(), mockProperties);
    }

    @AfterEach
    void tearDown() {
        ProjectProperties.SINGLETON_INSTANCES.clear();
    }

    @Test
    void initializeManifest_throwsRuntimeException_doesNotCauseExceptionInitializer() {
        ProjectProperties.get(this.getClass()).manifest = mockManifest;
        when(mockManifest.getMainAttributes()).thenThrow(new RuntimeException("End of the world"));

        verifyExceptionCanBeInitialized();
        final String actualMessage2 = BaseSdkException
            .buildMessage(ErrorSourceType.SERVICE, "unit test", this.getClass());
        assertThat(actualMessage2).contains(AUTONOMIC).contains(SDK);
    }

    @Test
    void initializeManifest_returnsNull_NoNullPointerException_returnsDefault() {
        ProjectProperties.get(this.getClass()).manifest = mockManifest;
        when(mockManifest.getMainAttributes()).thenReturn(null);

        verifyExceptionCanBeInitialized();
        final String actualMessage2 = BaseSdkException
            .buildMessage(ErrorSourceType.SERVICE, "unit test", this.getClass());
        assertThat(actualMessage2).contains(AUTONOMIC).contains(SDK);
    }

    @Test
    void projectPropertiesGetName_returnsNull_doesNotCascadeNullPointerException() {
        when(mockProperties.getName()).thenReturn(null);

        verifyExceptionCanBeInitialized();
    }

    @Test
    void projectPropertiesGetVersion_returnsNull_doesNotCascadeNullPointerException() {
        when(mockProperties.getName()).thenReturn("something");
        when(mockProperties.getVersion()).thenReturn(null);

        verifyExceptionCanBeInitialized();
    }

    @Test
    void projectPropertiesGetName_returnsNull_doesNotPrintNullInMessage() {
        when(mockProperties.getName()).thenReturn(null);

        assertThat(BaseSdkException.buildMessage(SDK_CLIENT, "test", this.getClass()))
            .doesNotContain("null");
    }

    @Test
    void projectPropertiesGetVersion_returnsNull_doesNotPrintNullInMessage() {
        when(mockProperties.getName()).thenReturn("something");
        when(mockProperties.getVersion()).thenReturn(null);

        assertThat(BaseSdkException.buildMessage(SDK_CLIENT, "test", this.getClass()))
            .doesNotContain("null");
    }

    @Test
    void projectPropertiesMethods_returnStringContainingNull_doesNotCorrectForNullInMessage() {
        when(mockProperties.getName()).thenReturn("null");
        when(mockProperties.getVersion()).thenReturn("null");

        assertThat(BaseSdkException.buildMessage(SDK_CLIENT, "test", this.getClass()))
            .contains("null");
    }

    @Test
    void projectPropertiesGetName_throwsRuntimeException_doesNotCascadeException() {
        when(mockProperties.getName()).thenThrow(new RuntimeException("Bada"));
        BaseSdkException actual = new SdkClientException("test", new RuntimeException("Boom!"), this.getClass());
        assertThat(actual.getMessage()).contains("[ AUTONOMIC ]~");
    }

    @Test
    void projectPropertiesGetVersion_throwsRuntimeException_doesNotCascadeException() {
        when(mockProperties.getName()).thenReturn("something");
        when(mockProperties.getVersion()).thenThrow(new RuntimeException("Bada"));

        BaseSdkException actual = new SdkClientException("test", new RuntimeException("Boom!"), this.getClass());
        assertThat(actual.getMessage()).contains("[ SDK ]~");
    }

    private void verifyExceptionCanBeInitialized() {
        try {
            new BaseSdkException("test");
        } catch (Throwable e) {
            fail("exceptions must not be thrown");
        }
    }
}
