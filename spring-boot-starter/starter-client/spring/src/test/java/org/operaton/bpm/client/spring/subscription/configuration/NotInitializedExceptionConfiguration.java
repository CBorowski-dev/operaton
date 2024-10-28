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
package org.operaton.bpm.client.spring.subscription.configuration;

import org.operaton.bpm.client.spring.SpringTopicSubscription;
import org.operaton.bpm.client.spring.annotation.EnableExternalTaskClient;
import org.operaton.bpm.client.spring.annotation.ExternalTaskSubscription;
import org.operaton.bpm.client.task.ExternalTaskHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

@Configuration
@EnableExternalTaskClient("http://localhost:8080/engine-rest")
public class NotInitializedExceptionConfiguration {

  @Autowired
  public SpringTopicSubscription subscription;

  @ExternalTaskSubscription("topic-name")
  @Bean
  public ExternalTaskHandler handler() {
    return (externalTask, externalTaskService) -> {

      // interact with the external task

    };
  }

  @EventListener(ContextRefreshedEvent.class)
  public void eventReceived() {
    subscription.open();
  }

}