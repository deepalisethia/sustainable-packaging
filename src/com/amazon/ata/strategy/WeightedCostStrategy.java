package com.amazon.ata.strategy;

import com.amazon.ata.cost.CostStrategy;
import com.amazon.ata.cost.MonetaryCostStrategy;
import com.amazon.ata.types.ShipmentCost;
import com.amazon.ata.types.ShipmentOption;

import java.math.BigDecimal;

public class WeightedCostStrategy implements CostStrategy {
    CarbonCostStrategy carbonCostStrategy;
    MonetaryCostStrategy monetaryCostStrategy;

    public WeightedCostStrategy (MonetaryCostStrategy monetaryCostStrategy,
                                 CarbonCostStrategy carbonCostStrategy) {
        this.monetaryCostStrategy = monetaryCostStrategy;
        this.carbonCostStrategy = carbonCostStrategy;
    }

    @Override
    public ShipmentCost getCost(ShipmentOption shipmentOption) {
        ShipmentCost carbonCost = carbonCostStrategy.getCost(shipmentOption);
        ShipmentCost monetaryCost = monetaryCostStrategy.getCost(shipmentOption);
        BigDecimal weightedCost = (carbonCost.getCost().multiply(BigDecimal.valueOf(.2)))
                .add((monetaryCost.getCost().multiply(BigDecimal.valueOf(.8))));
        return new ShipmentCost(shipmentOption, weightedCost);
    }
}
