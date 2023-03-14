package com.xuecheng.content.api;

import com.xuecheng.content.model.dto.CoursePreviewDto;
import com.xuecheng.content.service.CoursePublishService;
import com.xuecheng.content.util.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@RestController
public class CoursePublishController {
    @Autowired
    private CoursePublishService coursePublishService;

    /**
     * 课程预览
     *
     * @param courseId 课程id
     * @return
     */
    @GetMapping("/coursepreview/{courseId}")
    public ModelAndView preview(@PathVariable("courseId") Long courseId) {
        CoursePreviewDto coursePreviewInfo = coursePublishService.getCoursePreviewInfo(courseId);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("course_template");
        modelAndView.addObject("model", coursePreviewInfo);
        return modelAndView;
    }

    /**
     * 提交课程审核
     *
     * @param courseId 课程id
     */
    @PostMapping("/courseaudit/commit/{courseId}")
    public void commitAudit(@PathVariable Long courseId) {
        SecurityUtil.XcUser user = SecurityUtil.getUser();
        Long companyId = null;
        if (StringUtils.isNotEmpty(user.getCompanyId())) {
            companyId = Long.parseLong(user.getCompanyId());
        }
        coursePublishService.commitAudit(companyId, courseId);
    }

    /**
     * 课程发布接口
     *
     * @param courseId 课程id
     */
    @PostMapping("/coursepublish/{courseId}")
    public void coursePublish(@PathVariable Long courseId) {
        SecurityUtil.XcUser user = SecurityUtil.getUser();
        Long companyId = null;
        if (StringUtils.isNotEmpty(user.getCompanyId())) {
            companyId = Long.parseLong(user.getCompanyId());
        }
        coursePublishService.publishCourse(companyId, courseId);
    }
}
