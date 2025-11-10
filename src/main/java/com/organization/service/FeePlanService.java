package com.organization.service;

import com.organization.entity.FeePlan;

import java.util.List;

public interface FeePlanService {
    FeePlan create(FeePlan feePlan);
    List<FeePlan> list();
    List<FeePlan> list(String course, String academicYear);
    FeePlan get(String id);
    FeePlan update(String id, FeePlan feePlan);
    void delete(String id);
}
