/*
 * Copyright Camunda Services GmbH and/or licensed to Camunda Services GmbH
 * under one or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information regarding copyright
 * ownership. Camunda licenses this file to you under the Apache License,
 * Version 2.0; you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.operaton.bpm.engine.test.api.mgmt.license;

import org.operaton.bpm.engine.ManagementService;
import org.operaton.bpm.engine.ProcessEngine;
import org.operaton.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.operaton.bpm.engine.test.util.ProcessEngineTestRule;
import org.operaton.bpm.engine.test.util.ProvidedProcessEngineRule;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;

import static org.assertj.core.api.Assertions.assertThat;

public class LicenseKeyDirtyDbTest {

  public ProvidedProcessEngineRule engineRule = new ProvidedProcessEngineRule();
  public ProcessEngineTestRule testRule = new ProcessEngineTestRule(engineRule);

  @Rule
  public RuleChain ruleChain = RuleChain.outerRule(testRule).around(engineRule);

  ProcessEngine processEngine;
  ProcessEngineConfigurationImpl processEngineConfiguration;
  ManagementService managementService;

  @Before
  public void init() {
    processEngine = engineRule.getProcessEngine();
    processEngineConfiguration = (ProcessEngineConfigurationImpl) processEngine.getProcessEngineConfiguration();
    managementService = processEngine.getManagementService();
    managementService.setLicenseKey("license");
  }

  @AfterClass
  public static void cleanup() {
    new ProvidedProcessEngineRule().getProcessEngine().getManagementService().deleteLicenseKey();
  }

  @Test
  public void testDirtyDatabaseOnlyLicenseKey() {
    // given
    // license key in ACT_GE_BYTEARRAY

    // when
    String licenseKey = managementService.getLicenseKey();
    Long byteArrayCount = queryByteArrayTableSize();

    // then
    assertThat(licenseKey).isNotEmpty();
    assertThat(byteArrayCount).isEqualTo(1L);
    // the after-test database check should succeed with the license key present
    // in the byte array table
  }

  private Long queryByteArrayTableSize() {
    return processEngineConfiguration.getCommandExecutorTxRequired().execute(commandContext -> {
      String tablePrefix = processEngineConfiguration.getDatabaseTablePrefix();
      return commandContext.getProcessEngineConfiguration().getManagementService().getTableCount().get(tablePrefix + "ACT_GE_BYTEARRAY");
    });
  }
}
