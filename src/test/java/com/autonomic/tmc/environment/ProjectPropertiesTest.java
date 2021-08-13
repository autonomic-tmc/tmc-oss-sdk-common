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

import com.autonomic.tmc.auth.TokenSupplier;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProjectPropertiesTest {

  @AfterEach
  void tearDown() {
    ProjectProperties.removeAll();
  }

  @Test
  void projectProperties_whenPomFileNotFound_returnsDefault() {
    ProjectProperties projectProperties = ProjectProperties.get(this.getClass());

    assertThat(projectProperties.getName("[ AUTONOMIC ]")).isEqualTo("[ AUTONOMIC ]");
    assertThat(projectProperties.getVersion("[ SDK ]")).isEqualTo("[ SDK ]");
  }

  @Test
  void projectProperties_usedWithDiffPackages_maintainsTwoDiffInstances() {
    assertThat(ProjectProperties.get(TokenSupplier.class))
        .isNotEqualTo(ProjectProperties.get(Test.class));
  }

  @Test
  void projectProperties_forSameProject_maintainsSingleInstance() {
    assertThat(ProjectProperties.get(Test.class))
        .isEqualTo(ProjectProperties.get(ExtendWith.class));
  }
}
