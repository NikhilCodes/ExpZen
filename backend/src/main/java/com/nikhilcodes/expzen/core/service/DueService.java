package com.nikhilcodes.expzen.core.service;

import com.nikhilcodes.expzen.core.repository.DueRepository;
import com.nikhilcodes.expzen.model.Due;
import com.nikhilcodes.expzen.shared.dto.DueDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DueService {
    private final DueRepository dueRepository;

    public DueService(DueRepository dueRepository) {
        this.dueRepository = dueRepository;
    }

    public List<DueDTO> getDueByUser(String uid) {
        List<DueDTO> dueData = new ArrayList<>();

        this.dueRepository.findAllByUserIdOrderByCreatedOnDescCreationTimestampDesc(uid).forEach(due -> {
            dueData.add(
              new DueDTO(
                due.getValue(),
                due.getDescription(),
                due.getCreatedOn()
              )
            );
        });

        return dueData;
    }

    public void addDueByUser(String uid, DueDTO dueDTO) {
        Due due = new Due(dueDTO);
        due.setUserId(uid);

        this.dueRepository.saveAndFlush(due);
    }

    public Float getTotalDue(String uid) {
        Float value = this.dueRepository.getTotalDueValueByUserId(uid);
        if (value != null) {
            return value;
        }

        return 0F;
    }
}
