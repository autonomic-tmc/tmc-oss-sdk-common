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

import static java.util.Optional.ofNullable;
import static java.util.jar.Attributes.Name.IMPLEMENTATION_TITLE;
import static java.util.jar.Attributes.Name.IMPLEMENTATION_VERSION;

import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import lombok.extern.slf4j.Slf4j;

/**
 * Provides project properties - name and version - using Manifest, if available.
 */
@Slf4j
public final class ProjectProperties {

  public static final String DEFAULT_NAME = "[ AUTONOMIC ]";
  public static final String DEFAULT_VERSION = "[ SDK ]";

  private static final Map<String, ProjectProperties> SINGLETON_INSTANCES = new HashMap<>();

  private Manifest manifest;

  private <T> ProjectProperties(Class<T> clazz) {
    setManifest(clazz);
  }

  /**
   * @param clazz The class used to find package information
   * @param <T> Type of clazz
   * @return An instance of <code>ProjectProperties</code> for given class
   */
  public static <T> ProjectProperties get(Class<T> clazz) {
    final String classKey = ofNullable(clazz.getPackage().getImplementationTitle())
        .orElseGet(clazz::getName);
    return put(classKey, new ProjectProperties(clazz));
  }

  public static ProjectProperties put(String classKey, ProjectProperties projectProperties) {
    return SINGLETON_INSTANCES.computeIfAbsent(classKey, s -> projectProperties);
  }

  public static void removeAll() {
    SINGLETON_INSTANCES.clear();
  }

  @SuppressWarnings("squid:S1181")
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
    } catch (Throwable t) {
      log.debug("Unable to find manifest", t);
    }
  }

  @SuppressWarnings("squid:S1181")
  private String getAttribute(String name, String defaultValue) {
    try {
      return ofNullable(getValueFromManifest(name, defaultValue)).orElse(defaultValue);
    } catch (Throwable t) {
      log.trace("Ignoring exception, returning " + defaultValue, t);
      return defaultValue;
    }
  }

  private String getValueFromManifest(String name, String defaultValue) {
    return ofNullable(manifest)
        .map(m -> m.getMainAttributes().getValue(name))
        .orElse(defaultValue);
  }

  /**
   * @param defaultName default value to use for the name
   * @return Value of Implementation-Title, if available, or the specified default value
   */
  public String getName(String defaultName) {
    return getAttribute(IMPLEMENTATION_TITLE.toString(), defaultName);
  }

  /**
   * @param defaultVersion default value to use for the version
   * @return Value of Implementation-Version, if available, or the specified default value
   */
  public String getVersion(String defaultVersion) {
    return getAttribute(IMPLEMENTATION_VERSION.toString(), defaultVersion);
  }
}
