package com.shklyar.demo.service;

import com.shklyar.demo.dao.ProductInformationRepository;
import com.shklyar.demo.entities.ProductInformation;
import org.springframework.stereotype.Service;

@Service
public class ProductInformationService {
    ProductInformationRepository productInformationRepository;
    public ProductInformationService(ProductInformationRepository productInformationRepository){
        this.productInformationRepository=productInformationRepository;
    }

    public ProductInformation initProductInformation(ProductInformation productInformation){

        if(productInformation.getId() !=null){
            ProductInformation oldProductInformation = productInformationRepository.getById(productInformation.getId());
            oldProductInformation.setTitle(productInformation.getTitle());
            oldProductInformation.setCompound(productInformation.getCompound());
            oldProductInformation.setContent(productInformation.getContent());
            oldProductInformation.setLanguage(productInformation.getLanguage());
            return oldProductInformation;
        }else {
            ProductInformation newProductInformation = new ProductInformation();
            newProductInformation.setTitle(productInformation.getTitle());
            newProductInformation.setCompound(productInformation.getCompound());
            newProductInformation.setContent(productInformation.getContent());
            newProductInformation.setLanguage(productInformation.getLanguage());
            return newProductInformation;
        }







    }
}
