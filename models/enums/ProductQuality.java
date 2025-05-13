package models.enums;

import java.util.InvalidPropertiesFormatException;

public enum ProductQuality {
    NORMAL(1.0, 0.0, 0.5),
    SILVER(1.25, 0.5, 0.7),
    GOLD(1.5, 0.7, 0.9),
    IRIDIUM(2.0, 0.9, 100.0);

    private final Double priceCoefficient;
    private final Double startOfTheRange;
    private final Double endOfTheRange;

    ProductQuality(Double priceCoefficient, Double startOfTheRange, Double endOfTheRange) {
        this.priceCoefficient = priceCoefficient;
        this.startOfTheRange = startOfTheRange;
        this.endOfTheRange = endOfTheRange;
    }

    public Double getEndOfTheRange() {
        return endOfTheRange;
    }

    public Double getPriceCoefficient() {
        return priceCoefficient;
    }

    public Double getStartOfTheRange() {
        return startOfTheRange;
    }

    public static ProductQuality getQualityByDouble(Double quality){
        for (ProductQuality value : ProductQuality.values()) {
            if (value.startOfTheRange <= quality &&
                    value.endOfTheRange >= quality){
                return value;
            }
        }
        return  null;
    }
}