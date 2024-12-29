package vn.duantn.sominamshop.service;

import org.springframework.stereotype.Service;
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
}
