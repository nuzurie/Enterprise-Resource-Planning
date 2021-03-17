package com.soen390.erp.accounting.model;

import com.soen390.erp.inventory.model.SupplierOrder;
import com.soen390.erp.manufacturing.model.Material;
import lombok.*;

import javax.persistence.*;

/**
 * this get created for each item in the PurchaseOrder class
 * @see com.soen390.erp.accounting.model.PurchaseOrder
 *
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class PurchaseOrderItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "purchase_order_id")
    private PurchaseOrder purchaseOrder;

    @ManyToOne
    @JoinColumn(name = "material_id")
    Material material;

    private int quantity;
    private double unitPrice;
}
