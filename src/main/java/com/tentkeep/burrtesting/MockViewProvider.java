package com.tentkeep.burrtesting;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.tentkeep.burrviews.FragmentRegistry;
import com.tentkeep.burrviews.ViewProvider;
import org.mockito.Mockito;
import org.springframework.core.io.ClassPathResource;
import org.thymeleaf.context.AbstractContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.codeborne.selenide.Selenide.$;

public class MockViewProvider implements ViewProvider {

    private FragmentRegistry fragmentRegistry;
    private static MockViewProvider currentViewProvider;

    protected MockViewProvider() {}

    public static MockViewProvider mock(FragmentRegistry fragmentRegistry) {
        currentViewProvider = Mockito.spy(MockViewProvider.class);
        currentViewProvider.fragmentRegistry = fragmentRegistry;
        return currentViewProvider;
    }

    public static ViewProvider getCurrentViewProvider() {
        if (currentViewProvider == null) {
            currentViewProvider = new MockViewProvider();
        }
        return currentViewProvider;
    }

    public static void assertViewContains(String viewIdentifier) {
        $("." + viewIdentifier + " p").shouldHave(Condition.exactText("no args"));
    }

    public static void assertViewContains(String viewIdentifier, Map<String,Object> expectedArguments) {
        $("." + viewIdentifier + " p").shouldHave(Condition.exactText(argumentsToString(expectedArguments)));
    }

    public static void assertViewContains(SelenideElement scope, String viewIdentifier, Map<String,Object> expectedArguments) {
        scope.find("." + viewIdentifier + " p").shouldHave(Condition.exactText(argumentsToString(expectedArguments)));
    }

    @Override
    public String get(String viewIdentifier, AbstractContext context, Map<String, Object> args) {
        checkForRegistrationOfView(viewIdentifier);
        checkForFragmentExistence(viewIdentifier);
        List<String> escapeCharacters = Arrays.asList("'");
        String arguments = argumentsToString(args);
        for (String character : escapeCharacters) {
            if (arguments.contains(character)) {
                throw new RuntimeException("Strings that must be escaped for Thymeleaf templating must not be used in test data due to the way mock views are rendered. Characters requiring escape: " + escapeCharacters + ". Your test data: " + arguments);
            }
        }
        String viewRef = "mock-view :: mock ('" + viewIdentifier + "', '" + arguments + "')";
        return viewRef;
    }

    @Override
    public String get(String viewIdentifier) {
        checkForRegistrationOfView(viewIdentifier);
        checkForFragmentExistence(viewIdentifier);
        String viewRef = "mock-view :: mock ('" + viewIdentifier + "', 'no args')";
        return viewRef;
    }

    private static String argumentsToString(Map<String,Object> arguments) {
        List<String> keyValuePairs = new ArrayList<>();
        for (Map.Entry<String,Object> entry : arguments.entrySet()) {
            keyValuePairs.add(entry.getKey() + "=" + entry.getValue());
        }
        return String.join(", ", keyValuePairs);
    }

    private void checkForRegistrationOfView(String viewIdentifier) {
        if (fragmentRegistry.get(viewIdentifier) == null) {
            throw new RuntimeException("No view registered for identifier: " + viewIdentifier);
        }
    }

    public void checkForFragmentExistence(String viewIdentifier) {
        String fragmentReference = fragmentRegistry.get(viewIdentifier);
        fragmentReference = fragmentReference.substring(0, fragmentReference.indexOf(" "));
        fragmentReference = "templates/" + fragmentReference + ".html";
        ClassPathResource reference = new ClassPathResource(fragmentReference);
        if (!reference.exists()) {
            throw new RuntimeException("No view file exists at expected location: " + fragmentReference);
        }
    }
}
