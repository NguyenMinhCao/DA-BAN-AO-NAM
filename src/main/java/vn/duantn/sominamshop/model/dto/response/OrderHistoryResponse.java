package vn.duantn.sominamshop.model.dto.response;

import java.time.LocalDate;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderHistoryResponse {
    private String date;
    private List<OrderHistoryDetail> orderHisDetail;

    @Getter
    @Setter
    public static class OrderHistoryDetail {
        private String description;
        private String performedBy;
        private String actionTime;
    }
}
