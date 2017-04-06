import java.util.Iterator;

/**
 * Created by ivan on 06.04.17.
 */

public class Collections {
    public static <T, R> Iterable<R> map(Function1<? super T, ? extends R> function,
                                         Iterable<? extends T> iterable) {

        Iterator<? extends T> iterator = iterable.iterator();

        return () -> new Iterator<R>() {
            @Override
            public boolean hasNext() {
                return iterator.hasNext();
            }

            @Override
            public R next() {
                return function.apply(iterator.next());
            }
        };
    }

    public static <T> Iterable<T> filter(Predicate<? super T> predicate,
                                         Iterable<? extends T> iterable) {

        Iterator<? extends T> iterator = iterable.iterator();

        return () -> new Iterator<T>() {
            T next;

            @Override
            public boolean hasNext() {
                while (true) {
                    if (iterator.hasNext()) {
                        next = iterator.next();
                        if (predicate.apply(next)) {
                            return true;
                        }
                    } else {
                        return false;
                    }
                }
            }

            @Override
            public T next() {
                return next;
            }
        };
    }

    public static <T> Iterable<T> takeWhile(Predicate<? super T> predicate,
                                            Iterable<? extends T> iterable) {

        Iterator<? extends T> iterator = iterable.iterator();

        return () -> new Iterator<T>() {
            T next;

            @Override
            public boolean hasNext() {
                if (iterator.hasNext()) {
                    next = iterator.next();
                    return predicate.apply(next);
                } else {
                    return false;
                }
            }

            @Override
            public T next() {
                return next;
            }
        };
    }

    public static <T> Iterable<T> takeUnless(Predicate<? super T> predicate,
                                             Iterable<? extends T> iterable) {

        return takeWhile(predicate.not(), iterable);
    }

    public static <T> T foldl(Function2<T, T, T> reduce, T initialValue,
                              Iterable<? extends T> iterable) {

        return foldlRec(reduce, initialValue, iterable.iterator());
    }

    private static <T> T foldlRec(Function2<T, T, T> reduce, T intermediate,
                                 Iterator<? extends T> iterator) {

        if (iterator.hasNext()) {
            return foldlRec(reduce, reduce.apply(intermediate, iterator.next()), iterator);
        }

        return intermediate;
    }

    public static <T> T foldr(Function2<T, T, T> reduce, T initialValue,
                              Iterable<? extends T> iterable) {

        return foldrRec(reduce, initialValue, iterable.iterator());
    }

    private static <T> T foldrRec(Function2<T, T, T> reduce, T initialValue,
                                  Iterator<? extends T> iterator) {

        if (iterator.hasNext()) {
            return reduce.apply(iterator.next(), foldrRec(reduce, initialValue, iterator));
        }

        return initialValue;
    }
}