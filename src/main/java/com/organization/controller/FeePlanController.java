package com.organization.controller;

import com.organization.entity.FeePlan;
import com.organization.service.FeePlanService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/fee-plans")
public class FeePlanController {

    private final FeePlanService feePlanService;

    public FeePlanController(FeePlanService feePlanService) {
        this.feePlanService = feePlanService;
    }

    @PostMapping
    public FeePlan create(@Valid @RequestBody FeePlan feePlan) {
        return feePlanService.create(feePlan);
    }

    @GetMapping
    public List<FeePlan> list(@RequestParam(required = false) String course,
                                @RequestParam(required = false) String academicYear) {
        return feePlanService.list(course, academicYear);
    }

    @GetMapping("/{id}")
    public FeePlan get(@PathVariable String id) {
        return feePlanService.get(id);
    }

    @PutMapping("/{id}")
    public FeePlan update(@PathVariable String id, @Valid @RequestBody FeePlan feePlan) {
        return feePlanService.update(id, feePlan);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        feePlanService.delete(id);
    }
}
