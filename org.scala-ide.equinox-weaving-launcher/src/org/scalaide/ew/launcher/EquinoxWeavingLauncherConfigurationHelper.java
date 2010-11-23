package org.scalaide.ew.launcher;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Platform;

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
    
    // The is the shared install location
    File pluginDirFile = new File(Platform.getInstallLocation().getURL().getFile(), "plugins");
    File pluginDirOption = pluginDirFile.exists() ? pluginDirFile : null;

    // This is the per-user install location
    File pluginAuxDirFile = new File(new File(Platform.getConfigurationLocation().getURL().getFile()).getParentFile(), "plugins");
    File pluginAuxDirOption = pluginAuxDirFile.exists() ? pluginAuxDirFile : null;
    
    String weavingHookLocation = scanPluginDirForUniqueJar(pluginDirOption, "org.eclipse.equinox.weaving.hook_.+\\.jar");
    if (weavingHookLocation == null) {
      weavingHookLocation = scanPluginDirForUniqueJar(pluginAuxDirOption, "org.eclipse.equinox.weaving.hook_.+\\.jar");
      if (weavingHookLocation == null)
        return;
    }
    
    String weavingAspectJBundleLocation = scanPluginDirForUniqueJar(pluginDirOption, "org.eclipse.equinox.weaving.aspectj_.+\\.jar");
    if (weavingAspectJBundleLocation == null) {
      weavingAspectJBundleLocation = scanPluginDirForUniqueJar(pluginAuxDirOption, "org.eclipse.equinox.weaving.aspectj_.+\\.jar");
      if (weavingAspectJBundleLocation == null)
        return;
    }

    // See section "Installing JDT Weaving in a non-default location",
    //   http://wiki.eclipse.org/JDT_weaving_features
    String framework = props.getProperty("osgi.framework");
    props.put("osgi.frameworkClassPath", framework+",file:"+weavingHookLocation);
    props.remove("osgi.framework");
    String bundles = props.getProperty("osgi.bundles");
    props.put("osgi.bundles", "reference:file:"+weavingAspectJBundleLocation+"@2:start,"+bundles);
    
    props.put("osgi.framework.extensions", "org.eclipse.equinox.weaving.hook");
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
  
  private static String scanPluginDirForUniqueJar(File pluginDirOption, final String filePattern) {
    if (pluginDirOption != null) {
      File[] files = pluginDirOption.listFiles(new FileFilter() {
          public boolean accept(File file) { return file.getName().matches(filePattern) && file.isFile(); }
      });
      if (files.length > 0)
        return files[0].getAbsolutePath();
    }
    return null;
  }
}
