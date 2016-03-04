package io.humana.burr.testing;

import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;

public class BurrTestExecutionListener extends AbstractTestExecutionListener {

    @Override
    public void beforeTestMethod(TestContext testContext) throws Exception {
        if (testContext.getTestInstance() instanceof BurrTestCase) {
            BurrTestCase burrTestCase = (BurrTestCase) testContext.getTestInstance();
            burrTestCase.resetViewModel();
        }
    }

}
