package com.amazon.ata.types;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Represents a packaging option.
 *
 * This packaging supports standard boxes, having a length, width, and height.
 * Items can fit in the packaging so long as their dimensions are all smaller than
 * the packaging's dimensions.
 */
public abstract class Packaging {
    /**
     * The material this packaging is made of.
     */
    private Material material;

    /**
     * This packaging's length.
     */
//    protected BigDecimal length;
//
//    /**
//     * This packaging's smallest dimension.
//     */
//    protected BigDecimal width;
//
//    /**
//     * This packaging's largest dimension.
//     */
//    protected BigDecimal height;

    /**
     * Instantiates a new Packaging object.
     * @param material - the Material of the package
     */
    public Packaging(Material material) {
        this.material = material;
//        this.length = length;
//        this.width = width;
//        this.height = height;
    }

    public Material getMaterial() {
        return material;
    }

//    public BigDecimal getLength() {
//        return length;
//    }
//
//    public BigDecimal getWidth() {
//        return width;
//    }
//
//    public BigDecimal getHeight() {
//        return height;
//    }

    /**
     * Returns whether the given item will fit in this packaging.
     *
     * @param item the item to test fit for
     * @return whether the item will fit in this packaging
     */
    public abstract boolean canFitItem(Item item);
//    {
//        return this.length.compareTo(item.getLength()) > 0 &&
//                this.width.compareTo(item.getWidth()) > 0 &&
//                this.height.compareTo(item.getHeight()) > 0;
//    }

    /**
     * Returns the mass of the packaging in grams. The packaging weighs 1 gram per square centimeter.
     * @return the mass of the packaging
     */
    public abstract BigDecimal getMass();
//    {
//        BigDecimal two = BigDecimal.valueOf(2);
//
//        // For simplicity, we ignore overlapping flaps
//        BigDecimal endsArea = length.multiply(width).multiply(two);
//        BigDecimal shortSidesArea = length.multiply(height).multiply(two);
//        BigDecimal longSidesArea = width.multiply(height).multiply(two);
//
//        return endsArea.add(shortSidesArea).add(longSidesArea);
//        return null;
//    }

    @Override
    public boolean equals(Object o) {
        // Can't be equal to null
        if (o == null) {
            return false;
        }

        // Referentially equal
        if (this == o) {
            return true;
        }

        // Check if it's a different type
        if (getClass() != o.getClass()) {
            return false;
        }

        Packaging packaging = (Packaging) o;
        return getMaterial() == packaging.getMaterial();
    }

    @Override
    public int hashCode() {
        return Objects.hash(material);
    }
}
