package com.tentkeep.burrtesting;

import org.thymeleaf.context.IProcessingContext;
import org.thymeleaf.dialect.AbstractDialect;
import org.thymeleaf.dialect.IExpressionEnhancingDialect;

import java.util.HashMap;
import java.util.Map;

public class MockMapBuilderDialect extends AbstractDialect implements IExpressionEnhancingDialect {

    @Override
    public Map<String, Object> getAdditionalExpressionObjects(IProcessingContext processingContext) {
        Map<String, Object> objects = new HashMap<>();
        objects.put("mapBuilder", MockMapBuilder.getCurrentMapBuilder());
        return objects;
    }

    @Override
    public String getPrefix() {
        return "mapBuilder";
    }

}
