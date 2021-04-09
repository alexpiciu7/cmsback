package com.project.cms.service;

import java.nio.file.Path;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface IFilesStorageService {
    void init();

    void save(MultipartFile file,String name);

    Resource load(String filename);

    void deleteAll();

    Stream<Path> loadAll();
}