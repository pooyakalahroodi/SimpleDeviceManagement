package com.progiton.trainee.test.simple.devicemanagement.bdd;

import io.cucumber.junit.platform.engine.Constants;
import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

@Suite                                                                    // ← 1
@IncludeEngines("cucumber")                                              // ← 2
@SelectClasspathResource("features")                                     // ← 3
@ConfigurationParameter(
        key = Constants.GLUE_PROPERTY_NAME,                                  // ← 4
        value = "com.progiton.trainee.test.simple.devicemanagement.bdd"
)
@ConfigurationParameter(
        key = Constants.PLUGIN_PROPERTY_NAME,                                // ← 5
        value = "pretty, html:target/cucumber-reports/cucumber.html, json:target/cucumber-reports/cucumber.json"
)
public class RunCucumberIT {
    // This class is intentionally empty - it's just a trigger for JUnit/Cucumber
}
