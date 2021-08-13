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

import static java.lang.String.format;

/**
 * Combines information from {@link SystemProperties} and {@link ProjectProperties} to
 * give all available details about the runtime environment.
 */
public class EnvironmentDetails {
  private static final String UNKNOWN = "unknown";

  private final ProjectProperties projectProperties;
  private final SystemProperties systemProperties;

  public <T> EnvironmentDetails(Class<T> clazz) {
    this.projectProperties = ProjectProperties.get(clazz);
    this.systemProperties = new SystemProperties();
  }

  /**
   * Returns environment details as a pre-formatted String
   *
   * @param defaultAppName Default name to use for the project
   * @return a String in the following format, where {...} are placeholders: <br>
   * <code>{sdk-name}/{sdk-version} (java.version/{x}, java.runtime.version/{y}, os.name/{a}, os.version/{b})</code>
   */
  public String get(String defaultAppName) {
    return format("%s/%s (java.version/%s, java.runtime.version/%s, os.name/%s, os.version/%s)",
        projectProperties.getName(defaultAppName), projectProperties.getVersion(UNKNOWN),
        systemProperties.getJavaVersion(),
        systemProperties.getJavaRuntimeVersion(),
        systemProperties.getOsName(),
        systemProperties.getOsVersion()
    );
  }
}
