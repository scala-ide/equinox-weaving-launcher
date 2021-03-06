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

import org.eclipse.pde.ui.launcher.JUnitWorkbenchLaunchShortcut;

public class EquinoxWeavingJUnitWorkbenchLaunchShortcut extends JUnitWorkbenchLaunchShortcut {
  @Override
  protected String getLaunchConfigurationTypeId() {
    return "org.scala-ide.ew.launcher.JunitLaunchConfig";
  }
}
