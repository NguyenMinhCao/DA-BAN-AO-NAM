package vn.duantn.sominamshop.service;

import org.springframework.stereotype.Service;
import vn.duantn.sominamshop.model.Origin;
import vn.duantn.sominamshop.model.Size;
import vn.duantn.sominamshop.repository.OriginRepository;

import java.util.List;

@Service
public class OriginService {
    private final OriginRepository originRepository;

    public OriginService(OriginRepository originRepository) {
        this.originRepository = originRepository;
    }

    public List<Origin> getAllOrigins() {
        return this.originRepository.findAll();
    }

}
