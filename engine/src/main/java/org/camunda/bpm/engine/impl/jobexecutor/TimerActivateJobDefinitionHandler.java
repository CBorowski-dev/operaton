/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.camunda.bpm.engine.impl.jobexecutor;

import org.camunda.bpm.engine.impl.cmd.ActivateJobDefinitionCmd;
import org.camunda.bpm.engine.impl.interceptor.CommandContext;
import org.camunda.bpm.engine.impl.persistence.entity.ExecutionEntity;
import org.camunda.bpm.engine.impl.util.json.JSONObject;

/**
 * @author roman.smirnov
 */
public class TimerActivateJobDefinitionHandler extends TimerChangeJobDefinitionSuspensionStateJobHandler {

  public static final String TYPE = "activate-job-definition";

  public String getType() {
    return TYPE;
  }

  public void execute(String configuration, ExecutionEntity execution, CommandContext commandContext) {
    JSONObject config = new JSONObject(configuration);

    boolean activateJobs = getIncludeJobs(config);

    ActivateJobDefinitionCmd cmd = null;

    String by = getBy(config);

    if (by.equals(JOB_HANDLER_CFG_JOB_DEFINITION_ID)) {
      String jobDefinitionId = getJobDefinitionId(config);
      cmd = new ActivateJobDefinitionCmd(jobDefinitionId, null, null, activateJobs, null);
    } else

    if (by.equals(JOB_HANDLER_CFG_PROCESS_DEFINITION_ID)) {
      String processDefinitionId = getProcessDefinitionId(config);
      cmd = new ActivateJobDefinitionCmd(null, processDefinitionId, null, activateJobs, null);
    } else

    if (by.equals(JOB_HANDLER_CFG_PROCESS_DEFINITION_KEY)) {
      String processDefinitionKey = getProcessDefinitionKey(config);
      cmd = new ActivateJobDefinitionCmd(null, null, processDefinitionKey, activateJobs, null);
    }

    cmd.disableLogUserOperation().execute(commandContext);
  }

}
