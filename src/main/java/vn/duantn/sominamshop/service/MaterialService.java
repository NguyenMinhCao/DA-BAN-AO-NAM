package vn.duantn.sominamshop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.duantn.sominamshop.model.Color;
import vn.duantn.sominamshop.model.Material;
import vn.duantn.sominamshop.repository.MaterialRepository;

import java.util.List;

@Service
@RequiredArgsConstructor

public class MaterialService {
    private final MaterialRepository materialRepository;



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
            throw new IllegalArgumentException("Không được để trống");
        }

        if (materialRepository.existsByMaterialName(material.getMaterialName())) {
            throw new IllegalArgumentException("Tên màu đã tồn tại");
        }
        if (material.getStatus() == null) {
            material.setStatus(0);
        }

        return materialRepository.save(material);
    }


    public Material updateMaterial(Long id, Material updatedMaterial) {
        Material existingMaterial = getMaterialById(id);

        if (updatedMaterial.getMaterialName() == null || updatedMaterial.getMaterialName().trim().isEmpty()) {
            throw new IllegalArgumentException("Tên màu không được để trống");
        }

        if (updatedMaterial.getMaterialName() != null && !updatedMaterial.getMaterialName().isEmpty()) {

            if (materialRepository.existsByMaterialNameAndIdNot(updatedMaterial.getMaterialName().trim(), id)) {
                throw new IllegalArgumentException("Tên màu đã tồn tại");
            }
            existingMaterial.setMaterialName(updatedMaterial.getMaterialName().trim());
        }

        if (updatedMaterial.getStatus() != null) {
            existingMaterial.setStatus(updatedMaterial.getStatus());
        }

        return materialRepository.save(existingMaterial);
    }

    public List<Material> getAll() {
        return materialRepository.findAll();
    }

    public List<Material> getAllActive() {
        return materialRepository.getAllActive();
    }


    public Material findById(Long id) {
        return materialRepository.findById(id).get();
    }

    public List<Material> findByName(String name) {
        return materialRepository.finByName(name);
    }

    public Material add(Material material) {
        return materialRepository.save(material);
    }


    public Material update(Material material, Long id) {
        Material searchMaterial = materialRepository.findById(id).get();
        if (searchMaterial != null) {
            searchMaterial.setMaterialName(material.getMaterialName());
            return materialRepository.save(searchMaterial);
        }
        return null;
    }

    public Material setStatus(Long id) {
        Material searchMaterial = materialRepository.findById(id).get();
        if (searchMaterial != null) {
            if (searchMaterial.getStatus() == 1) {
                searchMaterial.setStatus(0);
            } else {
                searchMaterial.setStatus(1);
            }
            return materialRepository.save(searchMaterial);
        }
        return null;
    }


}
