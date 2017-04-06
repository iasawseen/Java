/**
 * Created by ivan on 06.04.17.
 */

@FunctionalInterface
interface Function2<T, U, R> {
    default public <V> Function2<T, U, V> compose(Function1<? super R, ? extends V> other) {
        return (t, u) -> other.apply(apply(t, u));
    }

    default public Function1<U, R> bind1(T t) {
        return (u) -> apply(t, u);
    }

    default public Function1<T, R> bind2(U u) {
        return (t) -> apply(t, u);
    }

    default public Function1<T, Function1<U, R>> curry() {
        return (t) -> bind1(t);
    }

    public R apply(T t, U u);

}