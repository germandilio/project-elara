package ru.hse.elarateam.orders.web.services;

import org.springframework.stereotype.Component;
import ru.hse.elarateam.orders.model.OrderedItem;

import java.util.List;

@Component
// здесь могла быть ваша эвристика
public class BinPackingSolver3d {
    /**
     * 3d bin packing problem solver.
     * Minimizing the sum of dimentions.
     *
     * @param items list of items to pack.
     * @return minimal dimentions of box to pack all items.
     */
    public List<Double> solve(List<OrderedItem> items) {
        Double totalHeight = 0.0;
        Double totalWidth = 0.0;
        Double totalLength = 0.0;
        Double totalWeight = 0.0;

        for (var item : items) {
            totalHeight += item.getHeight();
            totalWidth += item.getWidth();
            totalLength += item.getLength();
            totalWeight += item.getWeight();
        }

        totalHeight /= items.size();
        totalWidth /= items.size();
        totalLength /= items.size();
        totalWeight /= items.size();

        return List.of(totalHeight*2, totalWidth*2, totalLength*2, totalWeight*2);
    }
}
