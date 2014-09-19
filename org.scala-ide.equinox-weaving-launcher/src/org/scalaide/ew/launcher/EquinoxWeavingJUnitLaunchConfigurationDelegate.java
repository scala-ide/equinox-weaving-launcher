/*
 * Copyright (c) 2010 Chuusai Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
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

package org.scalaide.ew.launcher;

import java.io.File;
import java.util.List;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.jdt.core.IJavaModelMarker;
import org.eclipse.pde.ui.launcher.JUnitLaunchConfigurationDelegate;

public class EquinoxWeavingJUnitLaunchConfigurationDelegate extends JUnitLaunchConfigurationDelegate {
  @SuppressWarnings("unchecked")
  @Override
  protected void collectExecutionArguments(ILaunchConfiguration configuration, List vmArguments, List programArgs) throws CoreException {
    super.collectExecutionArguments(configuration, vmArguments, programArgs);
    File configFile = new File(super.getConfigurationDirectory(configuration), "config.ini");
    EquinoxWeavingLauncherConfigurationHelper.updateConfiguration(configFile);
  }

  @Override
  protected boolean isLaunchProblem(IMarker problemMarker) throws CoreException {
    Integer severity = (Integer) problemMarker.getAttribute(IMarker.SEVERITY);
    boolean isError = (severity != null) ? severity.intValue() >= IMarker.SEVERITY_ERROR : false;

    return super.isLaunchProblem(problemMarker)
        || (isError && problemMarker.isSubtypeOf(IJavaModelMarker.JAVA_MODEL_PROBLEM_MARKER));
  }

}
