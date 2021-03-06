# Gauge Testrail sync plugin

This projects is plugin for [gauge](http://getgauge.io) to sync gauge test cases with [TestRail](http://http://www.gurock.com/testrail/)

## Getting started

### Pre-requisite

- [Install Gauge](https://docs.gauge.org/installing.html#installation)
- [Java 8+](https://www.java.com/en/download/index.jsp)
- [Maven](https://maven.apache.org/install.html)

### Compile plugin
Call `mvn clean install` in the root directory

### Build plugin
To build the plugin, call `mvn clean package`.
This will build the assembly file `artifacts/testrail-sync-<version>.zip`, which can be installed as gauge plugin

### Install plugin
Call `gauge install testrail-sync -f <path>/testrail-sync-<version>.zip`

### Use plugin
In the gauge project make sure to add `testrail-sync` as plugin in the `manifest.json`, as also create the `testrails.properties` file under `env/default`.

The properties file must contain the following entries:
```
testrail.user = // the user to login. must be an email known to TestRail
testrail.token = // the token of the user (see http://docs.gurock.com/testrail-api2/accessing)
testrail.url = // the base url of the TestRail instance
testrail.project = // the id of the project where to sync
testrail.gauge.template = // the testrail template to use (it expects to have the simple step field)
testrail.automation.type = // optional. the id of the automation type to use
```

Call `gauge docs testrail-sync`

### Uninstall plugin
Call `gauge uninstall testrail-sync` and remove the entry from the `manifest.json` of the gauge project

### Concept
The plugin inspects all scenarios in the project and checks, whether the scenarios have a TestRail tag (`Cxxx`).
If not, the test case is uploaded to TestRail and the spec file is changed with the newly created TestRail case id added.

__Attention__ if the gauge project, the plugin is running on, is under version control, any changes made by the plugin must be committed manually !

### Important notice
The specifications and scenarios __must__ be written with the `#` or `##`, respectively !

### TestRail configuration
the plugin assumes, that a template is configured in TestRail. This template can be any template, as long as it has the `steps` field from TestRail configured

### Todo
* Adding reference option to upload to TestRail
