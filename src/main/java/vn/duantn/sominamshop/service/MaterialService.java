package vn.duantn.sominamshop.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.duantn.sominamshop.model.Color;
import vn.duantn.sominamshop.model.Material;
import vn.duantn.sominamshop.repository.MaterialRepository;

import java.util.List;

@Service
public class MaterialService {
    private final MaterialRepository materialRepository;

    public MaterialService(MaterialRepository materialRepository) {
        this.materialRepository = materialRepository;
    }

    public List<Material> getAllMaterials() {
        return this.materialRepository.findAll();
    }


    public Page<Material> getMaterial(String materialName, int page) {
        Pageable pageable = PageRequest.of(page, 5);
        if (materialName.isEmpty()) {
            return materialRepository.findAll(pageable);
        } else {
            return materialRepository.findByMaterialNameContainingIgnoreCase(materialName, pageable);
        }
    }

    public Material getMaterialById(Long id) {
        return materialRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy : " + id));
    }

    public Material addMaterial(Material material) {
        if (material.getMaterialName() == null || material.getMaterialName().isEmpty()) {
            throw new IllegalArgumentException("Tên được để trống");
        }
        return materialRepository.save(material);
    }

    public Material updateMaterial(Long id, Material updatedMaterial) {
        Material existing = getMaterialById(id);
        if (updatedMaterial.getMaterialName() != null && !updatedMaterial.getMaterialName().isEmpty()) {
            existing.setMaterialName(updatedMaterial.getMaterialName());
        }
        if (updatedMaterial.getStatus() != null) {
            existing.setStatus(updatedMaterial.getStatus());
        }
        return materialRepository.save(existing);
    }


}
