package com.order.system.domain.core.entity;

import com.order.system.domain.entity.BaseEntity;
import com.order.system.domain.valueobject.Money;
import com.order.system.domain.valueobject.ProductId;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product extends BaseEntity<ProductId> {
    private String name;
    private Money price;

    public Product(ProductId productId, String name, Money price) {
        super.setId(productId);
        this.name = name;
        this.price = price;
    }

    public Product(ProductId productId) {
        super();
    }

    public void updateWithConfirmedNameAndPrice(String name, Money price){
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public Money getPrice() {
        return price;
    }
}