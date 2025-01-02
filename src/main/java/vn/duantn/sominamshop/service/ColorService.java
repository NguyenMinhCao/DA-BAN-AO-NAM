package vn.duantn.sominamshop.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.duantn.sominamshop.model.Color;
import vn.duantn.sominamshop.repository.ColorRepository;

import java.util.List;

@Service
public class ColorService {
    private final ColorRepository colorRepository;

    public ColorService(ColorRepository colorRepository) {
        this.colorRepository = colorRepository;
    }

    public List<Color> getAllColors() {
        return this.colorRepository.findAll();
    }


    public Page<Color> getColors(String colorName, int page) {
        Pageable pageable = PageRequest.of(page, 5);
        if (colorName.isEmpty()) {
            return colorRepository.findAll(pageable);
        } else {
            return colorRepository.findByColorNameContainingIgnoreCase(colorName, pageable);
        }
    }

    public Color getColorById(Long id) {
        return colorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy màu với ID: " + id));
    }

    public Color addColor(Color color) {
        if (color.getColorName() == null || color.getColorName().isEmpty()) {
            throw new IllegalArgumentException("Tên màu không được để trống");
        }
        return colorRepository.save(color);
    }

    public Color updateColor(Long id, Color updatedColor) {
        Color existingColor = getColorById(id);
        if (updatedColor.getColorName() != null && !updatedColor.getColorName().isEmpty()) {
            existingColor.setColorName(updatedColor.getColorName());
        }
        if (updatedColor.getStatus() != null) {
            existingColor.setStatus(updatedColor.getStatus());
        }
        return colorRepository.save(existingColor);
    }

}
