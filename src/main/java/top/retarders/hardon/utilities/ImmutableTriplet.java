package top.retarders.hardon.utilities;

public class ImmutableTriplet<F, S, T> {

    public final F first;
    public final S second;
    public final T third;

    public ImmutableTriplet(F first, S second, T third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }

}
