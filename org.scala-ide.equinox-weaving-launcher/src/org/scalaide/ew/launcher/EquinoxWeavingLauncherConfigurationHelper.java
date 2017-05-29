package org.scalaide.ew.launcher;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.DebugException;
import org.osgi.framework.Bundle;

public class EquinoxWeavingLauncherConfigurationHelper {

  public static void updateConfiguration(File configFile) throws CoreException {
    FileInputStream in = null;
    Properties props = new Properties();
    try {
      in = new FileInputStream(configFile);
      props.load(in);
    } catch (IOException ex) {
      EquinoxWeavingLauncherPlugin.plugin().logError(ex);
      if (in != null) try {
        in.close();
      } catch (IOException ex2) {
        // Deliberately ignored
      }
      return;
    }
    
    getPlatformBundleLocation("org.eclipse.equinox.weaving.hook"); // ensure that the bundle is present
    String weavingAspectJBundleLocation = getPlatformBundleLocation("org.eclipse.equinox.weaving.aspectj");

    // See section "Installing JDT Weaving in a non-default location",
    //   http://wiki.eclipse.org/JDT_weaving_features
    props.put("osgi.framework.extensions", "org.eclipse.equinox.weaving.hook");

    String bundles = props.getProperty("osgi.bundles");
    props.put("osgi.bundles", "reference:file:"+weavingAspectJBundleLocation+"@2:start,"+bundles);
    
    props.put("aj.weaving.verbose", "true");
    props.put("org.aspectj.weaver.showWeaveInfo", "true");
    props.put("org.aspectj.osgi.verbose", "true");

    FileOutputStream out = null;
    try {
      out = new FileOutputStream(configFile);
      props.store(out, "Configuration File");
    } catch (IOException ex) {
      EquinoxWeavingLauncherPlugin.plugin().logError(ex);
    } finally {
      if (in != null) try {
        in.close();
      } catch (IOException ex) {
        // Deliberately ignored
      }
      if (out != null) try {
        out.close();
      } catch (IOException ex) {
        // Deliberately ignored
      }
    }
  }
  
  private static String getPlatformBundleLocation(String symbolicName) throws DebugException {
    Bundle bundle = Platform.getBundle(symbolicName);
    if (bundle != null) {
      try {
        File bundleLocation = FileLocator.getBundleFile(bundle);
        return bundleLocation.getCanonicalPath();
      } catch (IOException ex) {
        // Deliberately ignored
      }
    }
    throw new DebugException(new Status(IStatus.ERROR, EquinoxWeavingLauncherPlugin.pluginId,
        DebugException.INTERNAL_ERROR, "Unable to find installation location of bundle " + symbolicName, null));
  }
}
