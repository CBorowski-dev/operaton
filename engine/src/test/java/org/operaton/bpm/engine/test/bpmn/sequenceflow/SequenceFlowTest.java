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
package org.operaton.bpm.engine.test.bpmn.sequenceflow;

import static org.junit.Assert.assertEquals;

import org.operaton.bpm.engine.runtime.ProcessInstance;
import org.operaton.bpm.engine.task.Task;
import org.operaton.bpm.engine.test.Deployment;
import org.operaton.bpm.engine.test.util.PluggableProcessEngineTest;
import org.junit.Test;

/**
 * @author Thorben Lindhauer
 *
 */
public class SequenceFlowTest extends PluggableProcessEngineTest {

  @Deployment
  @Test
  public void testTakeAllOutgoingFlowsFromNonScopeTask() {
    // given
    ProcessInstance instance = runtimeService.startProcessInstanceByKey("testProcess");

    // when
    Task task = taskService.createTaskQuery().singleResult();
    taskService.complete(task.getId());

    // then
    assertEquals(2, taskService.createTaskQuery().count());
    assertEquals(1, taskService.createTaskQuery().taskDefinitionKey("task2").count());
    assertEquals(1, taskService.createTaskQuery().taskDefinitionKey("task3").count());

    for (Task followUpTask : taskService.createTaskQuery().list()) {
      taskService.complete(followUpTask.getId());
    }

    testRule.assertProcessEnded(instance.getId());

  }

  @Deployment
  @Test
  public void testTakeAllOutgoingFlowsFromScopeTask() {
    // given
    ProcessInstance instance = runtimeService.startProcessInstanceByKey("testProcess");

    // when
    Task task = taskService.createTaskQuery().singleResult();
    taskService.complete(task.getId());

    // then
    assertEquals(2, taskService.createTaskQuery().count());
    assertEquals(1, taskService.createTaskQuery().taskDefinitionKey("task2").count());
    assertEquals(1, taskService.createTaskQuery().taskDefinitionKey("task3").count());

    for (Task followUpTask : taskService.createTaskQuery().list()) {
      taskService.complete(followUpTask.getId());
    }

    testRule.assertProcessEnded(instance.getId());
  }
}