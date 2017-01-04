# BurrTesting
A testing library for enabling a unit-test style approach for view fragments in Java Spring Boot applications.    

This framework starts up a TestApplication which exposes all of the templates and fragments from your main application's resources so that they can be tested individually (as opposed to in integration style where each of their included fragments are also rendered).    

This framework enables you to pass arbitrary objects as view model attributes via `addViewAttribute(String, Object)` function, allowing you to test your fragment with mock view data.    

This framework comes bundled with [Selenide](http://selenide.org/) for view inspection/testing.

# Getting started
````
repositories {
  jcenter()
}

testCompile 'com.tentkeep:burr-testing:0.2.0'
````

# Usage
This is a test framework. It is assumed that you are also using BurrViews.    
Create a new class in your test package that looks like this example:

````java
@BurrTest("/fragments/my_template_fragment")
public class MyTemplateFragmentTest extends BurrTestCase {

    ViewProvider mockViewProvider = MockViewProvider.mock(new MyApplicationFragmentRegistry());
    MapBuilder mockMapBuilder = MockMapBuilder.mock();
    MapBuilder mapBuilder = new HashMapBuilder();
    Map<String, Object> expectedArgs;

    @Before
    public void beforeEach() {
        expectedArgs = new HashMap<>();
    }

    @Test
    public void logoFragment_isIncluded() throws Exception {
        openView();

        verify(mockViewProvider).get("logo");
        assertViewContains("logo");
    }

    @Test
    public void anotherFragment_shouldBeIncludedWithCorrectData() throws Exception {
        addViewAttribute("supporting_data", 33);
    
        openView();

        expectedArgs = mapBuilder.put("data", 33).build();
        verify(mockViewProvider).get(eq("another-fragment"), any(AbstractContext.class), eq(expectedArgs));
    }

    @Test
    public void view_displaysExpectedValues() throws Exception {
        openView();

        $("#header").shouldHave(Condition.exactText("Welcome to my site!"));
        $("#counter").shouldHave(Condition.exactText("77"));
        $("#selector").shouldHave(Condition.value("tomorrow"));
    }
}
````
