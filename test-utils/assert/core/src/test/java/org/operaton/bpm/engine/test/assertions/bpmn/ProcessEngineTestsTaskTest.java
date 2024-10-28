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
package org.operaton.bpm.engine.test.assertions.bpmn;

import static org.operaton.bpm.engine.test.assertions.bpmn.BpmnAwareTests.assertThat;
import static org.operaton.bpm.engine.test.assertions.bpmn.BpmnAwareTests.complete;
import static org.operaton.bpm.engine.test.assertions.bpmn.BpmnAwareTests.runtimeService;
import static org.operaton.bpm.engine.test.assertions.bpmn.BpmnAwareTests.task;
import static org.operaton.bpm.engine.test.assertions.bpmn.BpmnAwareTests.taskQuery;

import org.operaton.bpm.engine.ProcessEngineException;
import org.operaton.bpm.engine.runtime.ProcessInstance;
import org.operaton.bpm.engine.test.Deployment;
import org.operaton.bpm.engine.test.ProcessEngineRule;
import org.operaton.bpm.engine.test.assertions.helpers.Failure;
import org.operaton.bpm.engine.test.assertions.helpers.ProcessAssertTestCase;
import org.junit.Rule;
import org.junit.Test;

public class ProcessEngineTestsTaskTest extends ProcessAssertTestCase {

  @Rule
  public ProcessEngineRule processEngineRule = new ProcessEngineRule();

  @Test
  @Deployment(resources = {"bpmn/ProcessEngineTests-task.bpmn"
  })
  public void testTask_OnlyActivity_Success() {
    // Given
    ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-task"
    );
    // And
    runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-task"
    );
    // When
    assertThat(processInstance).isNotNull();
    // Then
    assertThat(task()).isNotNull().hasDefinitionKey("UserTask_1");
  }

  @Test
  @Deployment(resources = {"bpmn/ProcessEngineTests-task.bpmn"
  })
  public void testTask_OnlyActivity_Failure() {
    // Given
    runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-task"
    );
    // And
    runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-task"
    );
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        task();
      }
    }, IllegalStateException.class);
  }

  @Test
  @Deployment(resources = {"bpmn/ProcessEngineTests-task.bpmn"
  })
  public void testTask_TwoActivities_Failure() {
    // Given
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-task"
    );
    // And
    runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-task"
    );
    // And
    assertThat(processInstance).isNotNull();
    // And
    complete(task());
    // When
    expect(new Failure() {
      @Override
      public void when() {
        task();
      }
    }, ProcessEngineException.class);
  }

  @Test
  @Deployment(resources = {"bpmn/ProcessEngineTests-task.bpmn"
  })
  public void testTask_taskDefinitionKey_OnlyActivity_Success() {
    // Given
    ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-task"
    );
    // And
    runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-task"
    );
    // When
    assertThat(processInstance).isNotNull();
    // Then
    assertThat(task("UserTask_1")).isNotNull().hasDefinitionKey("UserTask_1");
  }

  @Test
  @Deployment(resources = {"bpmn/ProcessEngineTests-task.bpmn"
  })
  public void testTask_taskDefinitionKey_TwoActivities_Success() {
    // Given
    ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-task"
    );
    // And
    runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-task"
    );
    // When
    assertThat(processInstance).isNotNull();
    complete(task());
    // Then
    assertThat(task("UserTask_2")).isNotNull().hasDefinitionKey("UserTask_2");
    // And
    assertThat(task("UserTask_3")).isNotNull().hasDefinitionKey("UserTask_3");
  }

  @Test
  @Deployment(resources = {"bpmn/ProcessEngineTests-task.bpmn"
  })
  public void testTask_taskDefinitionKey_OnlyActivity_Failure() {
    // Given
    runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-task"
    );
    // And
    runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-task"
    );
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        task("UserTask_1");
      }
    }, IllegalStateException.class);
  }

  @Test
  @Deployment(resources = {"bpmn/ProcessEngineTests-task.bpmn"
  })
  public void testTask_taskQuery_OnlyActivity_Success() {
    // Given
    ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-task"
    );
    // And
    runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-task"
    );
    // When
    assertThat(processInstance).isNotNull();
    // Then
    assertThat(task(taskQuery().taskDefinitionKey("UserTask_1"))).isNotNull().hasDefinitionKey("UserTask_1");
  }

  @Test
  @Deployment(resources = {"bpmn/ProcessEngineTests-task.bpmn"
  })
  public void testTask_taskQuery_OnlyActivity_Failure() {
    // Given
    runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-task"
    );
    // And
    runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-task"
    );
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        task(taskQuery().taskDefinitionKey("UserTask_1"));
      }
    }, IllegalStateException.class);
  }

  @Test
  @Deployment(resources = {"bpmn/ProcessEngineTests-task.bpmn"
  })
  public void testTask_taskQuery_TwoActivities_Success() {
    // Given
    ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-task"
    );
    // And
    runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-task"
    );
    // When
    assertThat(processInstance).isNotNull();
    complete(task());
    // Then
    assertThat(task(taskQuery().taskDefinitionKey("UserTask_2"))).isNotNull().hasDefinitionKey("UserTask_2");
    // And
    assertThat(task(taskQuery().taskDefinitionKey("UserTask_3"))).isNotNull().hasDefinitionKey("UserTask_3");
  }

  @Test
  @Deployment(resources = {"bpmn/ProcessEngineTests-task.bpmn"
  })
  public void testTask_taskQuery_TwoActivities_Failure() {
    // Given
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-task"
    );
    // And
    runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-task"
    );
    // And
    assertThat(processInstance).isNotNull();
    // And
    complete(task());
    // When
    expect(new Failure() {
      @Override
      public void when() {
        task(taskQuery());
      }
    }, ProcessEngineException.class);
  }

  @Test
  @Deployment(resources = {"bpmn/ProcessEngineTests-task.bpmn"
  })
  public void testTask_processInstance_OnlyActivity_Success() {
    // Given
    ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-task"
    );
    // And
    runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-task"
    );
    // Then
    assertThat(task(processInstance)).isNotNull().hasDefinitionKey("UserTask_1");
  }

  @Test
  @Deployment(resources = {"bpmn/ProcessEngineTests-task.bpmn"
  })
  public void testTask_TwoActivities_processInstance_Failure() {
    // Given
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-task"
    );
    // And
    runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-task"
    );
    // And
    complete(task(processInstance));
    // When
    expect(new Failure() {
      @Override
      public void when() {
        task(processInstance);
      }
    }, ProcessEngineException.class);
  }

  @Test
  @Deployment(resources = {"bpmn/ProcessEngineTests-task.bpmn"
  })
  public void testTask_taskDefinitionKey_processInstance_OnlyActivity_Success() {
    // Given
    ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-task"
    );
    // And
    runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-task"
    );
    // Then
    assertThat(task("UserTask_1", processInstance)).isNotNull().hasDefinitionKey("UserTask_1");
  }

  @Test
  @Deployment(resources = {"bpmn/ProcessEngineTests-task.bpmn"
  })
  public void testTask_taskDefinitionKey_processInstance_TwoActivities_Success() {
    // Given
    ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-task"
    );
    // And
    runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-task"
    );
    // When
    complete(task(processInstance));
    // Then
    assertThat(task("UserTask_2", processInstance)).isNotNull().hasDefinitionKey("UserTask_2");
    // And
    assertThat(task("UserTask_3", processInstance)).isNotNull().hasDefinitionKey("UserTask_3");
  }

  @Test
  @Deployment(resources = {"bpmn/ProcessEngineTests-task.bpmn"
  })
  public void testTask_taskQuery_processInstance_OnlyActivity_Success() {
    // Given
    ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-task"
    );
    // And
    runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-task"
    );
    // Then
    assertThat(task(taskQuery().taskDefinitionKey("UserTask_1"), processInstance)).isNotNull().hasDefinitionKey("UserTask_1");
  }

  @Test
  @Deployment(resources = {"bpmn/ProcessEngineTests-task.bpmn"
  })
  public void testTask_taskQuery_processInstance_TwoActivities_Success() {
    // Given
    ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-task"
    );
    // And
    runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-task"
    );
    // When
    complete(task(processInstance));
    // Then
    assertThat(task(taskQuery().taskDefinitionKey("UserTask_2"), processInstance)).isNotNull().hasDefinitionKey("UserTask_2");
    // And
    assertThat(task(taskQuery().taskDefinitionKey("UserTask_3"), processInstance)).isNotNull().hasDefinitionKey("UserTask_3");
  }

  @Test
  @Deployment(resources = {"bpmn/ProcessEngineTests-task.bpmn"
  })
  public void testTask_taskQuery_processInstance_TwoActivities_Failure() {
    // Given
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-task"
    );
    // And
    runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-task"
    );
    // And
    complete(task(processInstance));
    // When
    expect(new Failure() {
      @Override
      public void when() {
        task(taskQuery(), processInstance);
      }
    }, ProcessEngineException.class);
  }

}