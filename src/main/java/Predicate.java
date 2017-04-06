/**
 * Created by ivan on 06.04.17.
 */

@FunctionalInterface
interface Predicate<T> extends Function1<T, Boolean> {
    default public Predicate<T> not() {
        return (t) -> !apply(t);
    }

    default public Predicate<T> or(Predicate<? super T> other) {
        return (t) -> apply(t) || other.apply(t);
    }

    default public Predicate<T> and(Predicate<? super T> other) {
        return (t) -> apply(t) && other.apply(t);
    }

    public Predicate<?> ALWAYS_TRUE = (t) -> true;

    public Predicate<?> ALWAYS_FALSE = (t) -> false;

}