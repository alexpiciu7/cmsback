package com.project.cms.service;

import java.nio.file.Path;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface IFilesStorageService {
    void init();

    void saveCv(MultipartFile file, String name);

    Resource loadCv(String filename);

    void saveTimetable(MultipartFile file, String name);

    Resource loadTimetable(String filename);


    Stream<Path> loadAll();
}