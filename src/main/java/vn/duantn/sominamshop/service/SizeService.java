package vn.duantn.sominamshop.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.duantn.sominamshop.model.Color;
import vn.duantn.sominamshop.model.Size;
import vn.duantn.sominamshop.repository.SizeRepository;

import java.util.List;

@Service
public class SizeService {
     private final SizeRepository sizeRepository;

    public SizeService(SizeRepository sizeRepository) {
        this.sizeRepository = sizeRepository;
    }

    public List<Size> getAllSizes() {
        return this.sizeRepository.findAll();
    }

    public Page<Size> getSize(String sizeName, int page) {
        Pageable pageable = PageRequest.of(page, 5);
        if (sizeName.isEmpty()) {
            return sizeRepository.findAll(pageable);
        } else {
            return sizeRepository.findBySizeNameContainingIgnoreCase(sizeName, pageable);
        }
    }

    public Size getSizeById(Long id) {
        return sizeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy size với ID: " + id));
    }

    public Size addSize(Size size) {
        if (size.getSizeName() == null || size.getSizeName().isEmpty()) {
            throw new IllegalArgumentException("Tên size không được để trống");
        }
        return sizeRepository.save(size);
    }

    public Size updateSize(Long id, Size updatedSize) {
        Size existingSize = getSizeById(id);
        if (updatedSize.getSizeName() != null && !updatedSize.getSizeName().isEmpty()) {
            existingSize.setSizeName(updatedSize.getSizeName());
        }
        if (updatedSize.getStatus() != null) {
            existingSize.setStatus(updatedSize.getStatus());
        }
        return sizeRepository.save(existingSize);
    }

}
