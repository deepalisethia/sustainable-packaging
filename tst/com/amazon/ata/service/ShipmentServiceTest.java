package com.amazon.ata.service;

import com.amazon.ata.cost.CostStrategy;
import com.amazon.ata.cost.MonetaryCostStrategy;
import com.amazon.ata.dao.PackagingDAO;
import com.amazon.ata.datastore.PackagingDatastore;
import com.amazon.ata.exceptions.NoPackagingFitsItemException;
import com.amazon.ata.exceptions.UnknownFulfillmentCenterException;
import com.amazon.ata.types.FulfillmentCenter;
import com.amazon.ata.types.Item;
import com.amazon.ata.types.ShipmentCost;
import com.amazon.ata.types.ShipmentOption;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class ShipmentServiceTest {

    private Item smallItem = Item.builder()
            .withHeight(BigDecimal.valueOf(1))
            .withWidth(BigDecimal.valueOf(1))
            .withLength(BigDecimal.valueOf(1))
            .withAsin("abcde")
            .build();

    private Item largeItem = Item.builder()
            .withHeight(BigDecimal.valueOf(1000))
            .withWidth(BigDecimal.valueOf(1000))
            .withLength(BigDecimal.valueOf(1000))
            .withAsin("12345")
            .build();

    private FulfillmentCenter existentFC = new FulfillmentCenter("ABE2");
    private FulfillmentCenter nonExistentFC = new FulfillmentCenter("NonExistentFC");

//    private ShipmentService shipmentService = new ShipmentService(new PackagingDAO(new PackagingDatastore()),
//            new MonetaryCostStrategy());

    @InjectMocks
    private ShipmentService shipmentService;

    @Mock
    private PackagingDAO packagingDAO;

    @Mock
    private CostStrategy costStrategy;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void findBestShipmentOption_existentFCAndItemCanFit_returnsShipmentOption() throws UnknownFulfillmentCenterException, NoPackagingFitsItemException {
        // GIVEN & WHEN
        List<ShipmentOption> shipmentOptions = new ArrayList<>();
        ShipmentOption shipOption = ShipmentOption.builder()
                .withItem(smallItem)
                .withFulfillmentCenter(existentFC)
                .build();

        shipmentOptions.add(shipOption);
        ShipmentCost shipmentCost = new ShipmentCost(shipOption, BigDecimal.valueOf(0));

        when(costStrategy.getCost(shipOption)).thenReturn(shipmentCost);
        when(packagingDAO.findShipmentOptions(any(Item.class), any(FulfillmentCenter.class))).
                thenReturn(shipmentOptions);


        ShipmentOption shipmentOption = shipmentService.findShipmentOption(smallItem, existentFC);

        // THEN
        assertNotNull(shipmentOption);
    }

    @Test
    void findBestShipmentOption_existentFCAndItemCannotFit_returnsShipmentOption() throws UnknownFulfillmentCenterException, NoPackagingFitsItemException {
        // GIVEN & WHEN
        List<ShipmentOption> shipmentOptions = new ArrayList<>();
        ShipmentOption shipOption = ShipmentOption.builder()
                .withItem(largeItem)
                .withPackaging(null)
                .withFulfillmentCenter(existentFC)
                .build();

        shipmentOptions.add(shipOption);
        ShipmentCost shipmentCost = new ShipmentCost(shipOption, BigDecimal.valueOf(0));

        when(costStrategy.getCost(shipOption)).thenReturn(shipmentCost);
        when(packagingDAO.findShipmentOptions(any(Item.class), any(FulfillmentCenter.class))).
                thenReturn(shipmentOptions);

        ShipmentOption shipmentOption = shipmentService.findShipmentOption(largeItem, existentFC);

        // THEN
        assertNotNull(shipmentOption);
    }

    @Test
    void findBestShipmentOption_nonExistentFCAndItemCanFit_returnsShipmentOption() {
        // GIVEN & WHEN & THEN
        assertThrows(RuntimeException.class, () -> {
            shipmentService.findShipmentOption(smallItem, nonExistentFC);
        }, "Unknown Fulfillment Center.");
    }

    @Test
    void findBestShipmentOption_nonExistentFCAndItemCannotFit_returnsShipmentOption() {
        // GIVEN & WHEN & THEN
        assertThrows(RuntimeException.class, () -> {
            shipmentService.findShipmentOption(largeItem, nonExistentFC);
        }, "Unknown Fulfillment Center.");
    }

}
