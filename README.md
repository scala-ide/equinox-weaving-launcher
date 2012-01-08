Equinox Weaving Launcher
========================

Equinox Weaving Launcher is an Eclipse component which adds a new launch
configuration type allowing Eclipse Applications and JUnit plug-in tests to be
launched with [Equinox Weaving](http://eclipse.org/equinox/weaving/) enabled.
It was developed to simplify the
development workflow for the [Scala IDE for Eclipse](http://scala-ide.org/),
and it can be used to support development of other Eclipse applications which
use Equinox Weaving.

An [update site](http://www.chuusai.com/eclipse/equinox-weaving-launcher/) and
downloadable
[archive](http://www.chuusai.com/eclipse/equinox-weaving-launcher.zip)
([sha1](http://www.chuusai.com/eclipse/equinox-weaving-launcher.zip.sha1)) are
available.

Usage
-----

First install the Equinox Weaving Launcher from its update site.

* To launch an Eclipse application from your workspace with Equinox Weaving 
enabled, navigate _Run Configurations ... => Eclipse Application with Equinox
Weaving_, then click the _New launch configuration_ icon. Configure this launch 
configuration as you would an ordinary Eclipse application launch configuration
and then click _Run_.

* Launching an Equinox Weaving enabled JUnit plug-in test is similar. Navigate
_Run Configurations ... => JUnit Plug-in Test with Equinox Weaving_, then click
the _New launch configuration_ icon. Configure this launch configuration as you
would an ordinary JUnit plug-in test launch configuration and then click _Run_.
Please note that, as with non-woven plug-in tests, to get fully headless runs
you should select "Run in UI thread" (this option is badly misnamed) on the
Test tab of the launch configuration and select "[No Application] Headless
Mode" as the program to run on the Main tab.

In both cases the default Program and VM arguments have been chosen to be
suitable for development of the Scala IDE for Eclipse (eg. a larger JVM maximum
heap size is configured) and may be adjusted as appropriate.
