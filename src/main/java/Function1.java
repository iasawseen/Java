/**
 * Created by ivan on 06.04.17.
 */

@FunctionalInterface
interface Function1<T, R> {
    default public <V> Function1<T, V> compose(Function1<? super R, ? extends V> other) {
        return (t) -> other.apply(apply(t));
    }

    public R apply(T t);
}

