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
package com.autonomic.tmc.environment;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import org.junit.jupiter.api.Test;

class SystemPropertiesTest {

  private static final String OS_NAME_PROPERTY = "os.name";
  private static final String UNKNOWN = "unknown";
  private static final String rawValue = "~!@#$%^&*()";

  @Test
  void shouldReturnValuesForSystemProperties() {
    final SystemProperties systemProperties = new SystemProperties();
    assertThat(systemProperties.getJavaVersion()).isNotBlank();
    assertThat(systemProperties.getJavaRuntimeVersion()).isNotBlank();
    assertThat(systemProperties.getOsName()).isNotBlank();
    assertThat(systemProperties.getOsVersion()).isNotBlank();
  }

  @Test
  void shouldDefaultToUnknown_whenThereIsException() {
    final SystemProperties systemProperties = new SystemProperties() {
      @Override
      protected String getEncodedValue(String systemPropertyValue)
          throws UnsupportedEncodingException {
        throw new UnsupportedEncodingException("For testing");
      }
    };
    assertThat(systemProperties.getJavaVersion()).isEqualTo(UNKNOWN);
    assertThat(systemProperties.getJavaRuntimeVersion()).isEqualTo(UNKNOWN);
    assertThat(systemProperties.getOsName()).isEqualTo(UNKNOWN);
    assertThat(systemProperties.getOsVersion()).isEqualTo(UNKNOWN);
  }

  @Test
  void shouldDefaultToUnknownIfPropertyIsNotSet() {
    final String savedOsNameValue = System.getProperty(OS_NAME_PROPERTY);
    System.out.println(savedOsNameValue);
    System.clearProperty(OS_NAME_PROPERTY);
    final SystemProperties systemProperties = new SystemProperties();
    assertThat(systemProperties.getOsName()).isEqualTo(UNKNOWN);
    System.setProperty(OS_NAME_PROPERTY, savedOsNameValue);
  }

  @Test
  void shouldEncodePropertyValues() throws UnsupportedEncodingException {
    final String savedOsNameValue = System.getProperty(OS_NAME_PROPERTY);
    System.out.println(savedOsNameValue);
    System.setProperty(OS_NAME_PROPERTY, rawValue);
    final SystemProperties systemProperties = new SystemProperties();
    assertThat(systemProperties.getOsName()).isEqualTo(
        URLEncoder.encode(rawValue, "UTF-8"));
    System.setProperty(OS_NAME_PROPERTY, savedOsNameValue);
  }

}
