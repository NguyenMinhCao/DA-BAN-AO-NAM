package vn.duantn.sominamshop.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.duantn.sominamshop.model.Origin;
import vn.duantn.sominamshop.model.Pattern;
import vn.duantn.sominamshop.repository.PatternRepository;

import java.util.List;

@Service
public class PatternService {
    private final PatternRepository patternRepository;



    public PatternService(PatternRepository patternRepository) {
        this.patternRepository = patternRepository;
    }

    public List<Pattern> getAllPatterns() {
        return this.patternRepository.findAll();
    }


    public Page<Pattern> getPatterns(String originName, int page) {
        Pageable pageable = PageRequest.of(page, 5);
        if (originName.isEmpty()) {
            return patternRepository.findAll(pageable);
        } else {
            return patternRepository.findByPatternNameContainingIgnoreCase(originName, pageable);
        }
    }

    public Pattern getPatternById(Long id) {
        return patternRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy mẫu với ID: " + id));
    }

    public Pattern addPattern(Pattern pattern) {
        if (pattern.getPatternName() == null || pattern.getPatternName().isEmpty()) {
            throw new IllegalArgumentException("Tên màu không được để trống");
        }
        return patternRepository.save(pattern);
    }

    public Pattern updatePattern(Long id, Pattern updatePattern) {
        Pattern existingPattern = getPatternById(id);

        if (updatePattern.getPatternName() != null && !updatePattern.getPatternName().isEmpty()) {
            existingPattern.setPatternName(updatePattern.getPatternName());
        }

        if (updatePattern.getStatus() != null) {
            existingPattern.setStatus(updatePattern.getStatus());
        }

        return patternRepository.save(existingPattern);
    }
    public Pattern setStatus(Long id) {
        Pattern searchPattern = patternRepository.findById(id).get();
        if (searchPattern != null) {
            if (searchPattern.getStatus() == 1) {
                searchPattern.setStatus(0);
            } else {
                searchPattern.setStatus(1);
            }
            return patternRepository.save(searchPattern);
        }
        return null;
    }
    public List<Pattern> getAllActive() {
        return patternRepository.getAllActive();
    }

}
