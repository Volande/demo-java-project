package com.shklyar.demo.service;

import com.shklyar.demo.dao.CategoryRepository;
import com.shklyar.demo.dao.CollectionRepository;
import com.shklyar.demo.entities.Category;
import com.shklyar.demo.entities.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CollectionService {

    CollectionRepository collectionRepository;


    @Autowired
    public CollectionService(CollectionRepository collectionRepository) {
        this.collectionRepository = collectionRepository;
    }

    public Collection initCollection(String collection) {


        //  Collection newCollection = collectionRepository.getByTitle(collection);

//        if (newCollection == null) {
        //          newCollection = new Collection();
        //  newCollection.setTitle(collection);
        //      collectionRepository.save(newCollection);
        //    }

        return null;
    }
}
