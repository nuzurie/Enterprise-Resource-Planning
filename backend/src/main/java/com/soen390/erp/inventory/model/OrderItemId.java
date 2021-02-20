package com.soen390.erp.inventory.model;

import java.io.Serializable;
import java.util.Objects;
//@Embeddable
public class OrderItemId implements Serializable {

    int id;
//    int material;
//    int supplierorder;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass())
            return false;

        OrderItemId that = (OrderItemId) o;
        return Objects.equals(id, that.id);
//                &&
//                Objects.equals(material, that.material)
//                &&
//                Objects.equals(supplierorder, that.supplierorder);
    }

    @Override
    public int hashCode() {
//        return Objects.hash(material, supplierorder);
        return Objects.hash(id);
    }
}
