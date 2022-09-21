package ltd.highsoft.frameworks.payload.core;

public class StringFieldType extends FieldType<String> {

    public static StringFieldType asString() {
        return new StringFieldType();
    }

    public StringFieldType nullToEmpty() {
        setNullHandler(() -> "");
        return this;
    }

    @Override
    protected boolean match(Class<?> underlyingType) {
        return String.class.isAssignableFrom(underlyingType);
    }

    @Override
    protected String convert(Object value) {
        return (String) value;
    }

}
