package vn.duantn.sominamshop.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.duantn.sominamshop.model.Category;
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


    public Origin getOriginById(Integer originId) {
        return originRepository.findById(originId)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy  với ID: " + originId));
    }

    public Page<Origin> getOrigin(String originName, int page) {
        Pageable pageable = PageRequest.of(page, 5);
        if (originName.isEmpty()) {
            return originRepository.findAll(pageable);
        } else {
            return originRepository.findByOriginNameContainingIgnoreCase(originName, pageable);
        }
    }

    public Origin addOrigin(Origin origin) {
        if (origin.getOriginName() == null || origin.getOriginName().isEmpty()) {
            throw new IllegalArgumentException("Không  được để trống");
        }

        if (originRepository.existsByOriginName(origin.getOriginName())) {
            throw new IllegalArgumentException("Tên đã tồn tại");
        }
        if (origin.getStatus() == null) {
            origin.setStatus(0);
        }

        return originRepository.save(origin);
    }


    public Origin updateOrigin(Integer id, Origin updatedOrigin) {
        Origin existingOrigin = getOriginById(id);

        if (updatedOrigin.getOriginName() != null && !updatedOrigin.getOriginName().isEmpty()) {
            existingOrigin.setOriginName(updatedOrigin.getOriginName());
        }

        if (updatedOrigin.getStatus() != null) {
            existingOrigin.setStatus(updatedOrigin.getStatus());
        }

        return originRepository.save(existingOrigin);
    }
    public Origin setStatus(Integer id) {
        Origin searchOrigin = originRepository.findById(id).get();
        if (searchOrigin != null) {
            if (searchOrigin.getStatus() == 1) {
                searchOrigin.setStatus(0);
            } else {
                searchOrigin.setStatus(1);
            }
            return originRepository.save(searchOrigin);
        }
        return null;
    }
    public List<Origin> getAllActive() {
        return originRepository.getAllActive();
    }

}
