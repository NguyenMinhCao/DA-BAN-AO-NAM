package vn.duantn.sominamshop.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.duantn.sominamshop.model.Color;
import vn.duantn.sominamshop.model.Origin;
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


    public Page<Origin> getOrigin(String originName, int page) {
        Pageable pageable = PageRequest.of(page, 5);
        if (originName.isEmpty()) {
            return originRepository.findAll(pageable);
        } else {
            return originRepository.findByOriginNameContainingIgnoreCase(originName, pageable);
        }
    }

    public Origin getOriginById(Long id) {
        return originRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy màu với ID: " + id));
    }

    public Origin addOrigin(Origin origin) {
        if (origin.getOriginName() == null || origin.getOriginName().isEmpty()) {
            throw new IllegalArgumentException("Tên màu không được để trống");
        }
        return originRepository.save(origin);
    }

    public Origin updateOrigin(Long id, Origin updatedOrigin) {
        Origin existingOrigin = getOriginById(id);

        if (updatedOrigin.getOriginName() != null && !updatedOrigin.getOriginName().isEmpty()) {
            existingOrigin.setOriginName(updatedOrigin.getOriginName());
        }

        if (updatedOrigin.getStatus() != null) {
            existingOrigin.setStatus(updatedOrigin.getStatus());
        }

        return originRepository.save(existingOrigin);
    }



}
