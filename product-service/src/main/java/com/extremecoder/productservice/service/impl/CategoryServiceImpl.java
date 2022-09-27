package com.extremecoder.productservice.service.impl;import com.extremecoder.productservice.dto.CategoryDetail;import com.extremecoder.productservice.dto.CategoryRegistrationRequest;import com.extremecoder.productservice.exception.InvalidRequestException;import com.extremecoder.productservice.exception.ResourceNotFoundException;import com.extremecoder.productservice.model.CategoryEntity;import com.extremecoder.productservice.repository.CategoryRepository;import com.extremecoder.productservice.service.CategoryService;import lombok.RequiredArgsConstructor;import org.springframework.stereotype.Service;import java.util.List;@Service@RequiredArgsConstructorpublic class CategoryServiceImpl implements CategoryService {    private final CategoryRepository repository;    @Override    public Long create(CategoryRegistrationRequest request) {        CategoryEntity entity = new CategoryEntity();        if (request.getIsParent()) {            if (request.getParentId() == null) {                throw new InvalidRequestException("parentId can not be null", "12345");            } else {                entity.setParentCategory(getCategoryEntity(request.getParentId()));            }        }        entity.setName(request.getName());        entity.setDescription(request.getDescription());        entity.setIsParent(request.getIsParent());        return repository.save(entity).getId();    }    @Override    public List<CategoryEntity> getCategories() {        return null;    }    @Override    public CategoryDetail getCategoryDetail(Long id) {        CategoryEntity entity = getCategoryEntity(id);        CategoryDetail category = new CategoryDetail();        category.setId(id);        category.setName(entity.getName());        category.setDescription(entity.getDescription());        category.setIsParent(entity.getIsParent());        category.setCreatedDate(entity.getCreatedDate());        category.setLastModifiedDate(entity.getLastModifiedDate());        category.setParentCategory(                CategoryDetail.ParentCategory                        .builder()                        .id(entity.getParentCategory().getId())                        .name(entity.getParentCategory().getName())                        .description(entity.getParentCategory().getDescription())                        .build()        );        return category;    }    public CategoryEntity getCategoryEntity(Long id) {        return repository.findById(id).orElseThrow(() ->                new ResourceNotFoundException("Transaction not found for id: " + id, "123")        );    }}