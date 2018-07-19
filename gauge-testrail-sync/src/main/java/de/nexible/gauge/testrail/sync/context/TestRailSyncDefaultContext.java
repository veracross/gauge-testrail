package de.nexible.gauge.testrail.sync.context;

import de.nexible.gauge.testrail.config.TestRailDefaultContext;

import static java.lang.Integer.parseInt;
import static java.lang.System.getenv;

/**
 * A {@link TestRailSyncContext} that is used when the plugin runs inside of gauge test run
 *
 * @author ajoecker
 */
public class TestRailSyncDefaultContext extends TestRailDefaultContext implements TestRailSyncContext {
    private static final int UNKNOWN = -100;

    @Override
    public int getGaugeApiPort() {
        return read("GAUGE_API_PORT");
    }

    @Override
    public int getAutomationId() {
        return read("testrail.automation.type");
    }

    @Override
    public int projectId() {
        return read("testrail.project");
    }

    @Override
    public int getTemplateId() {
        return read("testrail.template");
    }

    @Override
    public boolean isKnown(int id) {
        return id != UNKNOWN;
    }

    private int read(String s) {
        String getenv = getenv(s);
        if (getenv == null || getenv.equals("")) {
            return UNKNOWN;
        }
        return parseInt(getenv.trim());
    }

}
