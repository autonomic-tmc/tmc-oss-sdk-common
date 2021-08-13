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

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * Provides values for various System level properties
 */
@Slf4j
@Getter
public class SystemProperties {
  private static final String UNKNOWN = "unknown";

  private final String javaVersion;
  private final String javaRuntimeVersion;
  private final String osName;
  private final String osVersion;

  public SystemProperties() {
    this.javaVersion = getEncodedValueForProperty("java.version");
    this.javaRuntimeVersion = getEncodedValueForProperty("java.runtime.version");
    this.osName = getEncodedValueForProperty("os.name");
    this.osVersion = getEncodedValueForProperty("os.version");
  }

  private String getEncodedValueForProperty(String propertyName) {
    try {
      String systemPropertyValue = System.getProperty(propertyName, UNKNOWN);
      return getEncodedValue(systemPropertyValue);
    } catch (Exception e) {
      log.warn("Unable to find a value for system property [{}], defaulting to {}",
          propertyName, UNKNOWN, e);
      return UNKNOWN;
    }
  }

  protected String getEncodedValue(String systemPropertyValue) throws UnsupportedEncodingException {
    return URLEncoder.encode(systemPropertyValue, StandardCharsets.UTF_8.name());
  }
}
