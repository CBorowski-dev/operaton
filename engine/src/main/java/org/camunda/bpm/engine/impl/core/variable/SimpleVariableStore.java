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
package org.camunda.bpm.engine.impl.core.variable;

import org.camunda.bpm.engine.delegate.CoreVariableInstance;

/**
 * @author Daniel Meyer
 * @author Roman Smirnov
 * @author Sebastian Menski
 *
 */
public class SimpleVariableStore extends MapBasedVariableStore<CoreVariableInstance> {

  public static class SimpleVariableInstance implements CoreVariableInstance {

    protected String name;
    protected Object value;

    public SimpleVariableInstance(String name, Object value) {
      this.name = name;
      this.value = value;
    }

    public String getName() {
      return name;
    }
    public void setName(String name) {
      this.name = name;
    }
    public Object getValue() {
      return value;
    }
    public void setValue(Object value) {
      this.value = value;
    }
  }

  public void setVariableInstanceValue(CoreVariableInstance variableInstance, Object value, CoreVariableScope<CoreVariableInstance> sourceActivityExecution) {
    ((SimpleVariableInstance)variableInstance).value = value;
  }

  public CoreVariableInstance createVariableInstance(String variableName, Object value, CoreVariableScope<CoreVariableInstance> sourceActivityExecution) {
    SimpleVariableInstance instance = new SimpleVariableInstance(variableName, value);
    variables.put(variableName, instance);
    return instance;
  }

}
