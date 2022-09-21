package ltd.highsoft.frameworks.persistence.mongo;

import lombok.*;
import ltd.highsoft.frameworks.domain.core.fields.Aggregate;

@ToString
@EqualsAndHashCode
public class TestAggregate implements Aggregate {

    private final String id;
    private final String name;

    public TestAggregate(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String id() {
        return id;
    }

    public String name() {
        return name;
    }

    @Override
    public void verify() {
    }

}
