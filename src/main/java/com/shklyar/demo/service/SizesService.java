package com.shklyar.demo.service;

import com.shklyar.demo.dao.SizeRepository;
import com.shklyar.demo.entities.Sizes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SizesService {
    @Autowired
    SizeRepository sizeRepository;



    @Autowired
    public SizesService(SizeRepository sizeRepository) {
        this.sizeRepository = sizeRepository;
    }

    public Sizes initSize(String string) {


        Sizes sizes = sizeRepository.getByTitle(string);

        if (sizes == null) {
            sizes = new Sizes();
            sizes.setTitle(string);
            sizeRepository.save(sizes);
        }

        return sizes;
    }
}
