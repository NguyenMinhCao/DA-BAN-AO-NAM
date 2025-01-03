package vn.duantn.sominamshop.model.constants;

public enum PaymentStatus {
    PENDING, //chưa thanh toán
    COMPLETED, // đã thanh toán
    REFUNDED, // đã hoàn tiền
    PARTIALREFUND // hoàn tiền một phần
}
