package io.humana.burr.testing;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Controller
public class TestController {

    private static MockViewModel mockViewModel = new MockViewModel();

    public static void setViewModel(MockViewModel viewModel) {
        mockViewModel = viewModel;
    }

    @RequestMapping("/burrtests/**")
    public String renderView(Model model, HttpServletRequest request) throws IOException {
        mockViewModel.populateModel(model);
        String path = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        path = path.replace("/burrtests/", "");
        return path;
    }

}
