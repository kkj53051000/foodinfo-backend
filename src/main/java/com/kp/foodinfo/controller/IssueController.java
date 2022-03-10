package com.kp.foodinfo.controller;

import com.kp.foodinfo.request.IssueRequest;
import com.kp.foodinfo.service.IssueService;
import com.kp.foodinfo.vo.BasicVo;
import com.kp.foodinfo.vo.IssueEventListVo;
import com.kp.foodinfo.vo.IssueListVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
public class IssueController {
    private final IssueService issueService;

    @PostMapping("/admin/issueprocess")
    public BasicVo issueProcess(@RequestPart(name = "file", required = true) MultipartFile file, @RequestPart(name = "value", required = false) IssueRequest issueRequest) throws IOException {
        issueService.saveIssue(file, issueRequest);

        return new BasicVo("success");
    }

    @PostMapping("/admin/normalissueprocess")
    public BasicVo normalIssueProcess(@RequestBody IssueRequest issueRequest) throws IOException {
        issueService.saveNoramlIssue(issueRequest);

        return new BasicVo("success");
    }

    @GetMapping("/issuelist/{id}")
    public IssueListVo issueList(@PathVariable("id") long brand_id) {
        IssueListVo issueListVo = issueService.getIssueList(brand_id);

        return issueListVo;
    }

    @PostMapping("/issueeventlist/{id}")
    public IssueEventListVo issueEventList(@PathVariable("id") long brand_id) {
        IssueEventListVo issueEventListVo = issueService.getIssueEventList(brand_id);

        return issueEventListVo;
    }

    @PostMapping("/admin/issuedelete/{id}")
    public BasicVo issueDelete(@PathVariable("id") long issue_id) {
        return issueService.deleteIssue(issue_id);
    }
}
