package vn.duantn.sominamshop.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderStaticDTO {

    private int month;
    private long orderCount;
    private BigDecimal totalQuantity;
    private int year;



    public OrderStaticDTO(int i, long orderCount, BigDecimal totalQuantity) {
        this.month = i;
        this.orderCount = orderCount;
        this.totalQuantity = totalQuantity;
        this.year = year;
    }
}


