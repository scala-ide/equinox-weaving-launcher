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

import org.osgi.framework.BundleContext;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.plugin.AbstractUIPlugin;

public class EquinoxWeavingLauncherPlugin extends AbstractUIPlugin {
  public static final String pluginId = "org.scala-ide.equinox-weaving-launcher";
  
  public static final String extraProgramArgs = "-clean -console";
  public static final String defaultVMArgs = "-server -Xms256m -Xmx1024m -Xss1M -XX:MaxPermSize=256m -XX:+DoEscapeAnalysis -XX:+UseConcMarkSweepGC";


  private static EquinoxWeavingLauncherPlugin plugin = null;
  
  public static EquinoxWeavingLauncherPlugin plugin() {
    return plugin;
  }
  
  @Override
  public void start(BundleContext context) throws Exception {
    plugin = this;
    
    super.start(context);
  }

  public void logError(Throwable t) {
    logError(t.getClass() + ":" + t.getMessage(), t);
  }
  
  public void logError(String msg, Throwable t) {
    Throwable t1;
    if (t != null)
      t1 = t;
    else {
      t1 = new Exception();
      t1.fillInStackTrace();
    }
    Status status = new Status(IStatus.ERROR, pluginId, IStatus.ERROR, msg, t1);
    getLog().log(status);
  }
}
