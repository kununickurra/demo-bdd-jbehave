package com.cgi.poc.jbehave.commons.stories;

import org.jbehave.core.Embeddable;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.failures.FailingUponPendingStep;
import org.jbehave.core.io.LoadFromClasspath;
import org.jbehave.core.io.StoryFinder;
import org.jbehave.core.junit.JUnitStories;
import org.jbehave.core.reporters.CrossReference;
import org.jbehave.core.reporters.FreemarkerViewGenerator;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.ParameterConverters;
import org.jbehave.core.steps.spring.SpringApplicationContextFactory;
import org.jbehave.core.steps.spring.SpringStepsFactory;
import org.springframework.context.ApplicationContext;

import java.util.List;

import static org.jbehave.core.io.CodeLocations.codeLocationFromClass;
import static org.jbehave.core.reporters.Format.*;

/**
 * Abstract class used as a base class to run Jbehave stories using Junit
 */
public abstract class AbstractStories extends JUnitStories {

    private static final String JBEHAVE_SPRING_CTX = "com/cgi/poc/jbehave/commons/jbehave-spring-config.xml";
    private static final String STORY_NAME_PROPERTY = "story.name";
    private static final String STORY_FILE_PREFIX = "**/";
    private static final String STORY_FILE_SUFFIX = ".story";
    private static final String REPORTS_PROPERTY_NAME = "reports";
    private static final String REPORTS_PROPERTY_VALUE = "com/cgi/poc/jbehave/commons/ftls/jbehave-reports.ftl";

    private final Class<? extends Embeddable> embeddableClass = this.getClass();
    private final CrossReference crossReference;

    protected AbstractStories() {
//        configuredEmbedder().embedderControls().doGenerateViewAfterStories(true).doIgnoreFailureInStories(false)
//                .doIgnoreFailureInView(false).useThreads(1).useStoryTimeoutInSecs(60);
        crossReference = new CrossReference().withJsonOnly();
        crossReference.withOutputAfterEachStory(true);
        crossReference.excludingStoriesWithNoExecutedScenarios(false);
    }

    @Override
    public Configuration configuration() {
        StoryReporterBuilder reporterBuilder = new StoryReporterBuilder();
        reporterBuilder.withCodeLocation(codeLocationFromClass(embeddableClass));
        reporterBuilder.withFailureTrace(true).withFailureTraceCompression(true);
        reporterBuilder.withDefaultFormats().withFormats(CONSOLE, HTML, XML);
        reporterBuilder.withCrossReference(crossReference);
        reporterBuilder.viewResources().put(REPORTS_PROPERTY_NAME, REPORTS_PROPERTY_VALUE);

        ParameterConverters parameterConverters = new ParameterConverters();
        parameterConverters.addConverters(new ParameterConverters.EnumConverter());

        MostUsefulConfiguration configuration = new MostUsefulConfiguration();
        configuration.useFailureStrategy(new FailingUponPendingStep());
        configuration.useStoryLoader(new LoadFromClasspath(embeddableClass));
        configuration.useStoryReporterBuilder(reporterBuilder);
        configuration.useParameterConverters(parameterConverters);
        configuration.useViewGenerator(new FreemarkerViewGenerator(embeddableClass));
        return configuration;
    }

    @Override
    public InjectableStepsFactory stepsFactory() {
        ApplicationContext context = new SpringApplicationContextFactory(JBEHAVE_SPRING_CTX).createApplicationContext();
        return new SpringStepsFactory(configuration(), context);
    }

    @Override
    protected List<String> storyPaths() {
        StringBuilder storiesToRun = new StringBuilder();
        storiesToRun.append(STORY_FILE_PREFIX);
        storiesToRun.append(System.getProperty(STORY_NAME_PROPERTY, "*"));
        storiesToRun.append(STORY_FILE_SUFFIX);
        return new StoryFinder().findPaths(codeLocationFromClass(embeddableClass),
                storiesToRun.toString(), "");
    }

}
