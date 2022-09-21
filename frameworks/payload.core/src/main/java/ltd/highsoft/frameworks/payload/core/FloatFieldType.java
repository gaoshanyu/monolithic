package ltd.highsoft.frameworks.payload.core;

public class FloatFieldType extends FieldType<Float> {

    public static FieldType<Float> asFloat() {
        return new FloatFieldType();
    }

    @Override
    protected boolean match(Class<?> underlyingType) {
        return Number.class.isAssignableFrom(underlyingType);
    }

    @Override
    protected Float convert(Object value) {
        return ((Number) value).floatValue();
    }

}
