package io.humana.burr.testing;

import org.apache.http.NameValuePair;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.validation.constraints.NotNull;
import java.util.List;

import static io.humana.burr.testing.Burr.mockViewModel;
import static org.springframework.core.annotation.AnnotationUtils.findAnnotation;

@RunWith(SpringJUnit4ClassRunner.class)
public abstract class BurrTestCase {

    @Autowired
    private Burr burr;

    private MockViewModel viewModel;

    public void openView() throws Exception {
        burr.open(findAnnotation(this.getClass(), BurrTest.class).value());
    }

    public void openView(@NotNull List<NameValuePair> parameters, String fragment) throws Exception {
        burr.open(findAnnotation(this.getClass(), BurrTest.class).value() + fragment, parameters);
    }

    public void openView(@NotNull List<NameValuePair> parameters) throws Exception {
        burr.open(findAnnotation(this.getClass(), BurrTest.class).value(), parameters);
    }

    public void addViewAttribute(String key, Object value) {
        if (viewModel == null) {
            viewModel = mockViewModel();
        }
        viewModel.addAttribute(key, value);
    }

    public void resetViewModel() {
        viewModel = mockViewModel();
    }

}
