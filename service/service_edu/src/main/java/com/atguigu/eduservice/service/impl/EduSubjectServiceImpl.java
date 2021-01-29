package com.atguigu.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.excel.SubjectData;
import com.atguigu.eduservice.entity.subject.OneSubject;
import com.atguigu.eduservice.entity.subject.TwoSubject;
import com.atguigu.eduservice.listener.SubjectExcelListener;
import com.atguigu.eduservice.mapper.EduSubjectMapper;
import com.atguigu.eduservice.service.EduSubjectService;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author LY
 * @create 2021-01-15 10:30
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    public void saveSubject(MultipartFile file, EduSubjectService subjectService) {
        try {
            // 文件输入流
            InputStream inputStream = file.getInputStream();
            // 调用方法进行读取
            EasyExcel.read(inputStream, SubjectData.class, new SubjectExcelListener(subjectService)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
            throw new GuliException(20002, "添加课程分类失败");
        }
    }

    @Override
    public List<OneSubject> getAllOneTwoSubject() {

        //查询一级目录
        QueryWrapper<EduSubject> oneWrapper = new QueryWrapper<>();
        oneWrapper.eq("parent_id", "0");
        List<EduSubject> oneSubjects = baseMapper.selectList(oneWrapper);

        //查询二级目录
        QueryWrapper<EduSubject> twoWrapper = new QueryWrapper<>();
        twoWrapper.ne("parent_id","0");
        List<EduSubject> twoSubjects = baseMapper.selectList(twoWrapper);

        //该集合存放最终的数据
        ArrayList<OneSubject> list = new ArrayList<>();

        //封装一级目录
        for(EduSubject oneSubject : oneSubjects){
            OneSubject oneSubjectVO = new OneSubject();
            String pid = oneSubject.getId();
//            oneSubjectVO.setId(pid);
//            oneSubjectVO.setTitle(oneSubject.getTitle());
            BeanUtils.copyProperties(oneSubject,oneSubjectVO);
            //封装二级目录
            ArrayList<TwoSubject> twoSubjectVOList = new ArrayList<>();
            for(EduSubject twoSubject : twoSubjects){
                if(pid.equals(twoSubject.getParentId())){
                    TwoSubject twoSubjectVO = new TwoSubject();
//                    twoSubjectVO.setId(twoSubject.getId());
//                    twoSubjectVO.setTitle(twoSubject.getTitle());
                    BeanUtils.copyProperties(twoSubject,twoSubjectVO);
                    twoSubjectVOList.add(twoSubjectVO);
                }
            }
            oneSubjectVO.setChildren(twoSubjectVOList);
            list.add(oneSubjectVO);
        }

        return list;
    }

}
