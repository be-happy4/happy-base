package org.happy.common.core.domain.tree.order;

import lombok.RequiredArgsConstructor;
import org.happy.common.core.domain.Pair;

import java.util.function.Function;
import java.util.function.Supplier;

@RequiredArgsConstructor
public class OrderCalculator {
    private final float threshold;
    private static final float DEFAULT_THRESHOLD = 1e-2f;

    public OrderCalculator() {
        this(DEFAULT_THRESHOLD);
    }

    protected <K> float calculate(
            float now,
            InsertPosition position,
            Function<Float, Float> nextBoundFunc,
            Runnable reorder) {
        return switch (position) {
            case InsertPosition.APPEND -> nextBoundFunc.apply(now);

            case InsertPosition.BEFORE -> {
                var lower = nextBoundFunc.apply(now);
                yield calculateMidOrderWithReorder(lower, now, reorder, () -> {
                    var newLower = nextBoundFunc.apply(now);
                    return new Pair<>(newLower, now);
                });
            }

            case InsertPosition.AFTER -> {
                var upper = nextBoundFunc.apply(now);
                yield calculateMidOrderWithReorder(now, upper, reorder, () -> {
                    var newUpper = nextBoundFunc.apply(now);
                    return new Pair<>(now, newUpper);
                });
            }
        };
    }

    private float mid(float lower, float upper) {
        return lower + (upper - lower) / 2;
    }

    protected float calculateMidOrderWithReorder(
            float lower,
            float upper,
            Runnable reorder,
            Supplier<Pair<Float, Float>> recalculateBounds
    ) {
        var mid = mid(lower, upper);
        if ((mid - lower) < threshold || (upper - mid) < threshold) {
            reorder.run();
            var pair = recalculateBounds.get();
            mid = mid(pair.getFirst(), pair.getSecond());
        }
        return mid;
    }

    public static OrderCalculator getDefault() {
        return new OrderCalculator();
    }
}
