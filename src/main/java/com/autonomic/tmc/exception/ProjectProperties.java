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

import static com.autonomic.tmc.exception.BaseSdkException.DEFAULT_NAME;
import static com.autonomic.tmc.exception.BaseSdkException.DEFAULT_VERSION;
import static java.lang.String.format;
import static java.util.Optional.ofNullable;
import static java.util.jar.Attributes.Name.IMPLEMENTATION_TITLE;
import static java.util.jar.Attributes.Name.IMPLEMENTATION_VERSION;

import java.util.HashMap;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class ProjectProperties {

  static final HashMap<String, ProjectProperties> SINGLETON_INSTANCES = new HashMap<>();

  Manifest manifest;

  private <T> ProjectProperties(Class<T> clazz) {
    setManifest(clazz);
  }

  public static <T> ProjectProperties get(Class<T> clazz) {
    final String classKey = ofNullable(clazz.getPackage().getImplementationTitle())
        .orElseGet(clazz::getName);
    return SINGLETON_INSTANCES.computeIfAbsent(classKey, key -> new ProjectProperties(clazz));
  }

  private <T> void setManifest(Class<T> clazz) {
    try {
      String jarPath = clazz.getProtectionDomain()
          .getCodeSource().getLocation().toURI().getPath();
      try (JarFile jarFile = new JarFile(jarPath)) {
        // This branch is intentionally not unit tested. This cannot be tested
        // because the tests run before the library gets packaged. These lines have
        // been tested in the examples which are distributed separately from this
        // library.
        manifest = jarFile.getManifest();
      }
    } catch (Throwable e) {
      log.debug("Unable to find manifest", e);
    }
  }

  private String getAttribute(String name, String defaultValue) {
    try {
      return ofNullable(manifest)
          .map(m -> m.getMainAttributes().getValue(name))
          .orElse(defaultValue);
    } catch (Throwable e) {
      log.trace("Ignoring exception, returning " + defaultValue, e);
      return defaultValue;
    }
  }

  String getName() {
    return getName(DEFAULT_NAME);
  }

  String getName(String defaultName) {
    return getAttribute(IMPLEMENTATION_TITLE.toString(), defaultName);
  }

  String getVersion() {
    return getVersion(DEFAULT_VERSION);
  }

  String getVersion(String defaultVersion) {
    return getAttribute(IMPLEMENTATION_VERSION.toString(), defaultVersion);
  }

  public String getFormattedUserAgent(String defaultAppName) {
    String name = getName(defaultAppName);
    String version = getVersion("unknown");
    return format("%s/%s", name, version);
  }
}
