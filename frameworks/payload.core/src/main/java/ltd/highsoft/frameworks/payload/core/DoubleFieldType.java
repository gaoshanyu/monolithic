package ltd.highsoft.frameworks.payload.core;

public class DoubleFieldType extends FieldType<Double> {

    public static FieldType<Double> asDouble() {
        return new DoubleFieldType();
    }

    @Override
    protected boolean match(Class<?> underlyingType) {
        return Number.class.isAssignableFrom(underlyingType);
    }

    @Override
    protected Double convert(Object value) {
        return ((Number) value).doubleValue();
    }

}
