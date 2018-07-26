package de.nexible.gauge.testrail.sync.sync;

import de.nexible.gauge.testrail.sync.model.GaugeScenario;
import de.nexible.gauge.testrail.sync.model.GaugeSpec;
import de.nexible.gauge.testrail.sync.model.StepItem;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static java.util.stream.Collectors.joining;

public class CaseFormatter {
    private CaseFormatter() {
        // static
    }

    public static Optional<String> getScenarioLocation(GaugeSpec spec, GaugeScenario scenario, String gaugeProjectRoot) {
        long scenarioLocation = scenario.getScenarioLocation();
        if (scenarioLocation == 0) {
            return Optional.empty();
        }
        return Optional.of(Paths.get(gaugeProjectRoot).relativize(spec.getSpecFile()) + ":" + scenarioLocation);
    }

    public static String getCaseText(GaugeSpec spec, GaugeScenario scenario) {
        StringBuilder comments = format(CaseFormatter::formatComments, spec.getComments(), scenario.getComments());
        if (comments.length() > 0) {
            comments.append("\n\n");
        }
        StringBuilder steps = format(CaseFormatter::formatSteps, spec.getSteps(), scenario.getSteps());
        return comments.append(steps).toString();
    }

    private static StringBuilder format(Function<List<StepItem>, String> formatter, List<StepItem> specData, List<StepItem> scenarioData) {
        StringBuilder steps = new StringBuilder();
        steps.append(formatter.apply(specData));
        if (steps.length() > 0) {
            steps.append("\n");
        }
        steps.append(formatter.apply(scenarioData));
        return steps;
    }

    private static String formatComments(List<StepItem> comments) {
        return format(comments, s -> "_" + s.step() + "_");
    }

    private static String formatSteps(List<StepItem> steps) {
        return format(steps, s -> isInlineTable(s) ? s.step() : formatStepWithLevel(s));
    }

    private static boolean isInlineTable(StepItem s) {
        return s.step().startsWith("||");
    }

    private static String formatStepWithLevel(StepItem stepItem) {
        if (stepItem.level() == 0) {
            return "* " + stepItem.step();
        }
        return String.format("%" + stepItem.level() * 4 + "s* %s", "", stepItem.step());
    }

    private static String format(List<StepItem> data, Function<StepItem, String> mapper) {
        if (data.isEmpty()) {
            return "";
        }
        return data.stream().map(mapper).collect(joining("\n"));
    }
}