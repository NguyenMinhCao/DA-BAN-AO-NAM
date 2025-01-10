package vn.duantn.sominamshop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.duantn.sominamshop.model.Size;
import vn.duantn.sominamshop.repository.SizeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor

public class SizeService {
     private final SizeRepository sizeRepository;



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



    public List<Size> findDistinctByIdAndName(Integer productId) {
        return sizeRepository.findDistinctByIdAndName(productId);
    }

    public Size findSizeById(Long id) {
        return sizeRepository.findById(id).orElse(null);
    }

    public List<Size> findSizeByProductIdAndColorId(Integer productId, Integer colorId) {
        return sizeRepository.findSizeByProductIdAndColorId(productId, colorId);
    }

    public List<Size> getAll() {
        return sizeRepository.findAll();
    }

    public List<Size> getAllActive() {
        return sizeRepository.getAllActive();
    }

    public Size findById(Long id) {
        return sizeRepository.findById(id).get();
    }

    public List<Size> findByName(String name) {
        return sizeRepository.findByName(name);
    }

    public Size add(Size size) {
        return sizeRepository.save(size);
    }

    public Size update(Size size, Long id) {
        Size searchSize = sizeRepository.findById(id).get();
        if (searchSize != null) {
            searchSize.setSizeName(size.getSizeName());

            return sizeRepository.save(searchSize);
        }
        return null;
    }

    public Size setStatus(Long id) {
        Size searchSize = sizeRepository.findById(id).get();
        if (searchSize != null) {
            if (searchSize.getStatus() == 1) {
                searchSize.setStatus(0);
            } else {
                searchSize.setStatus(1);
            }
            return sizeRepository.save(searchSize);
        }
        return null;
    }

}
