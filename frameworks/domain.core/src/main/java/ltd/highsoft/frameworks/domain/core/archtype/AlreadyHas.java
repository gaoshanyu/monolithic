package ltd.highsoft.frameworks.domain.core.archtype;

public class AlreadyHas<Aggregate extends ltd.highsoft.frameworks.domain.core.archtype.Aggregate> implements One<Aggregate> {

    private Aggregate aggregate;

    public AlreadyHas(Aggregate aggregate) {
        this.aggregate = aggregate;
    }

    @Override
    public Aggregate get() {
        return this.aggregate;
    }

    @Override
    public void add(Aggregate aggregate) {
        this.aggregate = aggregate;
    }

    @Override
    public void callOff() {
        this.aggregate = null;
    }

}
