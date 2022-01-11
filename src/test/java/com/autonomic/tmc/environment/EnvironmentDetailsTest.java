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
package com.autonomic.tmc.environment;

import java.io.UnsupportedEncodingException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EnvironmentDetailsTest {

  private static final String TEST_SDK_NAME = "test-sdk-name";
  private static final String TEST_VERSION = "test-version-number";

  @Test
  void get_returnsFormattedString() {
    //Arrange
    EnvironmentDetails environmentDetails = new EnvironmentDetails(TEST_SDK_NAME,TEST_VERSION);

    String expectedFormattedRegex = TEST_SDK_NAME +"/"+TEST_VERSION +
        "\\s\\(java.version/.*," +
        "\\sjava.runtime.version/.*," +
        "\\sos.name/.*," +
        "\\sos.version/.*\\)";

    String encodedOutput = environmentDetails.get();
    assertTrue(encodedOutput.matches(expectedFormattedRegex));
  }

  @Test
  void get_returnsUnknown_whenUnsupportedEncodingException() {
    EnvironmentDetails environmentDetails = new EnvironmentDetails(TEST_SDK_NAME,TEST_VERSION) {
      @Override
      protected String getEncodedValue(String propertyValue)
          throws UnsupportedEncodingException {
        throw new UnsupportedEncodingException("For testing");
      }
    };

    assertThat(environmentDetails.get()).isEqualTo("unknown/unknown (java.version/unknown, java.runtime.version/unknown, os.name/unknown, os.version/unknown)");

  }

  @Test
  void get_unEncodedName_urlEncoded() {
    String unEncodedAppName = "app name with spaces";

    String expectedFormattedRegex = "app\\+name\\+with\\+spaces/"+ TEST_VERSION +
        "\\s\\(java.version/.*," +
        "\\sjava.runtime.version/.*," +
        "\\sos.name/.*," +
        "\\sos.version/.*\\)";

    EnvironmentDetails environmentDetails = new EnvironmentDetails(unEncodedAppName,TEST_VERSION);
    assertTrue(environmentDetails.get()
        .matches(expectedFormattedRegex));
  }

  @Test
  void constructor_acceptsSdkNameAndVersion(){
    //Arrange
    EnvironmentDetails environmentDetails = new EnvironmentDetails(TEST_SDK_NAME,TEST_VERSION);

    assertTrue(environmentDetails.sdkName.equals(TEST_SDK_NAME));
    assertTrue(environmentDetails.version.equals(TEST_VERSION));
  }
}
