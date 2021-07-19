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

import static org.assertj.core.api.Assertions.assertThat;

import com.autonomic.tmc.auth.TokenSupplier;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProjectPropertiesTest {

  public static final String TEST_APP_NAME = "test-app-name";

  @AfterEach
  void tearDown() {
    ProjectProperties.SINGLETON_INSTANCES.clear();
  }

  @Test
  void projectProperties_returnsUnknown_when_POMFileNotFound() {
    ProjectProperties projectProperties = ProjectProperties.get(this.getClass());

    assertThat(projectProperties.getName()).isEqualTo("[ AUTONOMIC ]");
    assertThat(projectProperties.getVersion()).isEqualTo("[ SDK ]");
  }

  @Test
  void projectPropertiesGetFormattedUserAgent_returnFormattedUserAgent() {
    ProjectProperties projectProperties = ProjectProperties.get(this.getClass());
    assertThat(projectProperties.getFormattedUserAgent(TEST_APP_NAME))
        .isEqualTo(TEST_APP_NAME + "/unknown");
  }

  @Test
  void projectProperties_usedWithDiffPackages_maintainsTwoDiffInstances() {
    ProjectProperties authProjectProperties = ProjectProperties.get(TokenSupplier.class);
    assertThat(authProjectProperties).isNotEqualTo(ProjectProperties.get(Test.class));
    assertThat(ProjectProperties.SINGLETON_INSTANCES.size()).isEqualTo(2);
  }

  @Test
  void projectProperties_forSameProject_maintainsSingleInstance() {
    ProjectProperties junitTestProjectProperties = ProjectProperties.get(Test.class);
    assertThat(junitTestProjectProperties).isEqualTo(ProjectProperties.get(ExtendWith.class));
    assertThat(ProjectProperties.SINGLETON_INSTANCES.size()).isEqualTo(1);
  }

  @Test
  void projectProperties_implementationTitleIsNull_usesClassName() {
    ProjectProperties.get(MockitoExtension.class);
    assertThat(ProjectProperties.SINGLETON_INSTANCES.containsKey(null)).isFalse();
  }
}
