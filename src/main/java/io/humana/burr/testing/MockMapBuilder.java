package io.humana.burr.testing;

import com.tentkeep.burrviews.MapBuilder;
import org.mockito.Mockito;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class MockMapBuilder<T,V> implements MapBuilder<T,V> {

    private static MockMapBuilder currentMapBuilder;
    private Map<T,V> map = new HashMap<>();

    public static MockMapBuilder mock() {
        currentMapBuilder = Mockito.spy(MockMapBuilder.class);
        return currentMapBuilder;
    }

    public static MockMapBuilder getCurrentMapBuilder() {
        return currentMapBuilder;
    }

    protected MockMapBuilder() {}

    @Override
    public MapBuilder<T, V> put(T key, V value) {
        map.put(key, value);
        return this;
    }

    @Override
    public Map<T,V> build() {
        Map<T,V> mapCopy = map;
        map = new HashMap<>();
        return mapCopy;
    }
}
