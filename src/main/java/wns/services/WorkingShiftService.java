package wns.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wns.entity.Project;
import wns.entity.WorkingShift;
import wns.repo.WorkingShiftRepo;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class WorkingShiftService {
    private final WorkingShiftRepo repo;

    public void save(WorkingShift workingShift)
    {
        repo.save(workingShift);
    }

    @Transactional(readOnly = true)
    public WorkingShift findById(long id)
    {
        return repo.findById(id).get();
    }

    @Transactional(readOnly = true)
    public  List<WorkingShift> getAll() {
        return (List<WorkingShift>) repo.findAll();
    }

    public void delete(WorkingShift workingShift) {
        repo.delete(workingShift);
    }

}
