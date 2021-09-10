/*-
 * ‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾
 * TMC OSS SDK Common Classes
 * ——————————————————————————————————————————————————————————————————————————————
 * Copyright (C) 2016 - 2021 Autonomic, LLC
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

import static org.assertj.core.api.Assertions.assertThat;

class SdkServiceExceptionTest {
  private static final String TEST_SDK_NAME = "test-app-name";
  private static final String TEST_VERSION = "versionNumber";
  public static final String CLIENT_MESSAGE = "client message";


  @Test
  void testSdkServiceExceptionWithOnlyMessageSdkNameAndVersion() {
    final SdkServiceException sdkServiceException = new SdkServiceException(CLIENT_MESSAGE, TEST_SDK_NAME, TEST_VERSION);

    assertThat(sdkServiceException.getMessage()).contains(ErrorSourceType.SERVICE.name()).contains(CLIENT_MESSAGE);
  }

  @Test
  void testSdkServiceExceptionWithCauseMessageSdkNameAndVersion() {
    final RuntimeException cause = new RuntimeException("service exception");
    final SdkServiceException sdkServiceException = new SdkServiceException(CLIENT_MESSAGE, cause, TEST_SDK_NAME, TEST_VERSION);

    assertThat(sdkServiceException.getMessage()).contains(ErrorSourceType.SERVICE.name()).contains(CLIENT_MESSAGE);
    assertThat(sdkServiceException.getCause()).isEqualTo(cause);
  }

  @Test
  void testSdkServiceExceptionWithOnlyCauseSdkNameAndVersion() {
    final RuntimeException cause = new RuntimeException("service exception");
    final SdkServiceException sdkServiceException = new SdkServiceException(cause, TEST_SDK_NAME, TEST_VERSION);

    assertThat(sdkServiceException.getMessage()).contains(ErrorSourceType.SERVICE.name());
    assertThat(sdkServiceException.getCause()).isEqualTo(cause);
  }
}
