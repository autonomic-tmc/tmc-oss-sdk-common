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
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import lombok.extern.slf4j.Slf4j;

import static java.lang.String.format;

/**
 * Combines information from {@link SystemProperties} and received SDK information to
 * give all available details about the runtime environment.
 */
@Slf4j
public class EnvironmentDetails {
  private static final String UNKNOWN = "unknown";
  private static final String JAVA_VERSION_LABEL = "java.version";
  private static final String JAVA_RUNTIME_VERSION_LABEL = "java.runtime.version";
  private static final String OS_NAME_LABEL = "os.name";
  private static final String OS_VERSION_LABEL = "os.version";

  private final SystemProperties systemProperties;

  String sdkName;
  String version;

  public EnvironmentDetails(String sdkName, String version) {
    this.systemProperties = new SystemProperties();
    this.sdkName = sdkName;
    this.version = version;
  }

  /**
   * Returns environment details as a pre-formatted String
   *
   * @return a String in the following format, where {...} are placeholders: <br>
   * <code>{sdk-name}/{sdk-version} (java.version/{x}, java.runtime.version/{y}, os.name/{a}, os.version/{b})</code>
   */
  public String get() {
    return format("%s/%s (%s/%s, %s/%s, %s/%s, %s/%s)",
        encode(sdkName), encode(version),
        JAVA_VERSION_LABEL,
        encode(systemProperties.getJavaVersion()),
        JAVA_RUNTIME_VERSION_LABEL,
        encode(systemProperties.getJavaRuntimeVersion()),
        OS_NAME_LABEL,
        encode(systemProperties.getOsName()),
        OS_VERSION_LABEL,
        encode(systemProperties.getOsVersion())
    );
  }

  private String encode(String propertyValue) {
    try {
      return getEncodedValue(propertyValue);
    } catch (Exception e) {
      log.warn("Unable to encode value [{}], defaulting to {}",
          propertyValue, UNKNOWN, e);
      return UNKNOWN;
    }
  }

  protected String getEncodedValue(String propertyValue) throws UnsupportedEncodingException {
    return URLEncoder.encode(propertyValue, StandardCharsets.UTF_8.name());
  }

}
