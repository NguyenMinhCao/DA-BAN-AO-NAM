package vn.duantn.sominamshop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.duantn.sominamshop.model.Color;
import vn.duantn.sominamshop.repository.ColorRepository;

import java.util.List;

@Service
@RequiredArgsConstructor

public class ColorService {
    private final ColorRepository colorRepository;
//
//    public ColorService(ColorRepository colorRepository) {
//        this.colorRepository = colorRepository;
//    }
//
//    public List<Color> getAllColors() {
//        return this.colorRepository.findAll();
//    }
//
//
//    public Page<Color> getColors(String colorName, int page) {
//        Pageable pageable = PageRequest.of(page, 5);
//        if (colorName.isEmpty()) {
//            return colorRepository.findAll(pageable);
//        } else {
//            return colorRepository.findByColorNameContainingIgnoreCase(colorName, pageable);
//        }
//    }
//
//    public Color getColorById(Long id) {
//        return colorRepository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy màu với ID: " + id));
//    }
//
//    public Color addColor(Color color) {
//        if (color.getColorName() == null || color.getColorName().isEmpty()) {
//            throw new IllegalArgumentException("Tên màu không được để trống");
//        }
//        return colorRepository.save(color);
//    }
//
//    public Color updateColor(Long id, Color updatedColor) {
//        Color existingColor = getColorById(id);
//        if (updatedColor.getColorName() != null && !updatedColor.getColorName().isEmpty()) {
//            existingColor.setColorName(updatedColor.getColorName());
//        }
//        if (updatedColor.getStatus() != null) {
//            existingColor.setStatus(updatedColor.getStatus());
//        }
//        return colorRepository.save(existingColor);
//    }
public List<Color> getAllColors() {
    return colorRepository.findAll();
}

    public Color findByIdColor(Long id) {
        return colorRepository.findById(id).orElse(null);
    }

    public List<Color> getColorForProduct(Integer productId) {
        return colorRepository.findDistinctByProductDetails_ProductId(productId);
    }


    public List<Color> getAll() {
        return colorRepository.findAll();
    }

    public List<Color> getAllActive() {
        return colorRepository.getAllActive();
    }

    public Color findById(Long id) {
        return colorRepository.findById(id).get();
    }

    public List<Color> findByName(String name) {
        return colorRepository.findByName(name);
    }

    public Color add(Color color) {
        return colorRepository.save(color);
    }

    public Color update(Color color, Long id) {
        Color searchColor = colorRepository.findById(id).get();
        if (searchColor != null) {
            searchColor.setColorName(color.getColorName());
            return colorRepository.save(searchColor);
        }
        return null;
    }

    public Color setStatus(Long id) {
        Color searchColor = colorRepository.findById(id).get();
        if (searchColor != null) {
            if (searchColor.getStatus() == 1) {
                searchColor.setStatus(0);
            } else {
                searchColor.setStatus(1);
            }
            return colorRepository.save(searchColor);
        }
        return null;
    }
}
