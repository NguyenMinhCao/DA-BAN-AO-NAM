package vn.duantn.sominamshop.model.constants;

public enum DeliveryStatus {

    PENDING("Đang chờ xử lý"),
    SHIPPING("Vận chuyển"),
    OUT_FOR_DELIVERY("Đang giao hàng"),
    COMPLETED("Hoàn thành"),
    CANCELED("Đã hủy"),
    RETURNED("Trả hàng");

    private String description;

    DeliveryStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
