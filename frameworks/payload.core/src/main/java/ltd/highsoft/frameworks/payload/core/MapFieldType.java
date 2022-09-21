package ltd.highsoft.frameworks.payload.core;

import java.util.Map;

public class MapFieldType extends FieldType<Map<?, ?>> {

    public static FieldType<Map<?, ?>> asMap() {
        return new MapFieldType();
    }

    @Override
    protected boolean match(Class<?> underlyingType) {
        return Map.class.isAssignableFrom(underlyingType);
    }

    @Override
    protected Map<?, ?> convert(Object value) {
        return (Map<?, ?>) value;
    }

}
