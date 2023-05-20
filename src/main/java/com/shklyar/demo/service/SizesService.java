package com.shklyar.demo.service;

import com.shklyar.demo.dao.SizeRepository;
import com.shklyar.demo.entities.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SizesService {

    SizeRepository sizeRepository;
    @Autowired
    public SizesService(SizeRepository sizeRepository) {
        this.sizeRepository = sizeRepository;
    }

    public Size initSize(String string) {


        Size sizes = sizeRepository.findTopByTitle(string);

        if (sizes == null) {
            sizes = new Size();
            sizes.setTitle(string);
            sizeRepository.save(sizes);
        }

        return sizes;
    }
}
