package vn.duantn.sominamshop.service;

import org.springframework.stereotype.Service;
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

}
