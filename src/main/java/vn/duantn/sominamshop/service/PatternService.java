package vn.duantn.sominamshop.service;

import org.springframework.stereotype.Service;
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

}
