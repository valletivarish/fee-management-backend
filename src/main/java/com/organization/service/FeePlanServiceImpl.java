package com.organization.service;

import com.organization.entity.FeePlan;
import com.organization.repository.FeePlanRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeePlanServiceImpl implements FeePlanService {

    private final FeePlanRepository repo;

    public FeePlanServiceImpl(FeePlanRepository repo) {
        this.repo = repo;
    }

    @Override
    public FeePlan create(FeePlan feePlan) {
        return repo.save(feePlan);
    }

    @Override
    public List<FeePlan> list() {
        return repo.findAll();
    }

    @Override
    public List<FeePlan> list(String course, String academicYear) {
        if (course != null && academicYear != null) {
            return repo.findByCourseAndAcademicYear(course, academicYear).stream().toList();
        } else if (course != null) {
            return repo.findByCourse(course);
        } else if (academicYear != null) {
            return repo.findByAcademicYear(academicYear);
        } else {
            return repo.findAll();
        }
    }

    @Override
    public FeePlan get(String id) {
        return repo.findById(id).orElseThrow();
    }

    @Override
    public FeePlan update(String id, FeePlan feePlan) {
        feePlan.setId(id);
        return repo.save(feePlan);
    }

    @Override
    public void delete(String id) {
        repo.deleteById(id);
    }
}
