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

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.jdt.core.IJavaModelMarker;
import org.eclipse.pde.launching.EclipseApplicationLaunchConfiguration;
import org.eclipse.pde.launching.IPDELauncherConstants;

public class EquinoxWeavingApplicationLaunchConfiguration extends EclipseApplicationLaunchConfiguration {
  @Override
  public String[] getProgramArguments(ILaunchConfiguration configuration) throws CoreException {
    String[] args = super.getProgramArguments(configuration);
    if (!configuration.getAttribute(IPDELauncherConstants.USEFEATURES, false)) {
      File configFile = new File(getConfigDir(configuration), "config.ini");
      EquinoxWeavingLauncherConfigurationHelper.updateConfiguration(configFile);
    }
    return args;
  }

  @Override
  protected boolean isLaunchProblem(IMarker problemMarker) throws CoreException {
    Integer severity = (Integer) problemMarker.getAttribute(IMarker.SEVERITY);
    boolean isError = (severity != null) ? severity.intValue() >= IMarker.SEVERITY_ERROR : false;

    return super.isLaunchProblem(problemMarker)
        || (isError && problemMarker.isSubtypeOf(IJavaModelMarker.JAVA_MODEL_PROBLEM_MARKER));
  }
}
