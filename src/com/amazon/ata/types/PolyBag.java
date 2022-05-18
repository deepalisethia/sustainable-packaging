package com.amazon.ata.types;

import java.math.BigDecimal;
import java.util.Objects;

public class PolyBag extends Packaging {
    /**
     * Instantiates a new Packaging object.
     *
     * @param material - the Material of the package
     * @param length   - the length of the package
     * @param width    - the width of the package
     * @param height   - the height of the package
     */

    private BigDecimal volume;

    public PolyBag(Material material, BigDecimal volume) {
        super(material);
        this.volume = volume;
//        super(material, length, width, height);
    }

    @Override
    public BigDecimal getMass() {
        BigDecimal mass = BigDecimal.valueOf(Math.ceil(Math.sqrt(volume.doubleValue()) * 0.6));
        return mass;
//        return super.getMass();
    }

    public BigDecimal getVolume(BigDecimal length, BigDecimal width, BigDecimal height) {
        BigDecimal volume = length.multiply(width).multiply(height);
        return volume;
    }
    @Override
    public boolean canFitItem(Item item) {
        return this.volume.compareTo(item.getLength().multiply(item.getWidth()).multiply(item.getHeight())) > 0;
    }


    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), volume);
    }



}
