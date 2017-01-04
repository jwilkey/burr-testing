package com.tentkeep.burrtesting;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;
import org.openqa.selenium.Dimension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.Arrays;
import java.util.List;

@PropertySource("classpath:../test/application.properties")
@Component
public class Burr {

    @Value("${base_url}")
    private String baseUrl;

    @Value("${server.port}")
    private String port;

    public static MockViewModel mockViewModel() {
        MockViewModel mockViewModel = new MockViewModel();
        TestController.setViewModel(mockViewModel);
        return mockViewModel;
    }

    public String getBaseUrl() {
        return port != null ? (baseUrl + ":" + port) : baseUrl;
    }

    public void open(String path) throws Exception {
        open(path, Arrays.asList());
    }

    public void open(String path, @NotNull List<NameValuePair> parameters) throws Exception {
        URI uri = new URI("/burrtests" + path);

        URIBuilder ub = new URIBuilder();
        ub.setPath(getBaseUrl() + uri.getPath())
                .setParameters(parameters)
                .setFragment(uri.getFragment());

        Selenide.open(ub.toString());
    }

    public static void resizeToSmallGlass() {
        WebDriverRunner.getWebDriver().manage().window().setSize(new Dimension(375, 627));
    }

    public static void resizeToFullScreen() {
        WebDriverRunner.getWebDriver().manage().window().maximize();
    }

    public static void resizeToMediumGlass() {
        WebDriverRunner.getWebDriver().manage().window().setSize(new Dimension(800, 627));
    }

}
