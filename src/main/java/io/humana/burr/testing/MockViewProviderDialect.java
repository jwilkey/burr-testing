package io.humana.burr.testing;

import org.thymeleaf.context.IProcessingContext;
import org.thymeleaf.dialect.AbstractDialect;
import org.thymeleaf.dialect.IExpressionEnhancingDialect;

import java.util.HashMap;
import java.util.Map;

public class MockViewProviderDialect extends AbstractDialect implements IExpressionEnhancingDialect {

    @Override
    public Map<String, Object> getAdditionalExpressionObjects(IProcessingContext processingContext) {
        Map<String, Object> objects = new HashMap<>();
        objects.put("viewProvider", MockViewProvider.getCurrentViewProvider());
        return objects;
    }

    @Override
    public String getPrefix() {
        return "viewProvider";
    }

}
