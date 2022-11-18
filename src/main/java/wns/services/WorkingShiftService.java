package wns.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import wns.entity.WorkingShift;
import wns.repo.WorkingShiftRepo;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkingShiftService implements MainService{
    private final WorkingShiftRepo repo;

    public void save(WorkingShift workingShift)
    {
        repo.save(workingShift);
    }

    public WorkingShift findById(long id)
    {
        return repo.findById(id).get();
    }

    @Override
    public  List<WorkingShift> getAll() {
        return (List<WorkingShift>) repo.findAll();
    }

    @Override
    public void delete(long id) {
        repo.deleteById(id);
    }
}
