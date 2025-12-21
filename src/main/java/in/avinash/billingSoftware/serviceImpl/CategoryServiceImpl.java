package in.avinash.billingSoftware.serviceImpl;

import in.avinash.billingSoftware.entity.CategoryEntity;
import in.avinash.billingSoftware.io.CategoryRequest;
import in.avinash.billingSoftware.io.CategoryResponse;
import in.avinash.billingSoftware.repository.CategoryRepository;
import in.avinash.billingSoftware.service.CategoryService;
import in.avinash.billingSoftware.service.FileUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private FileUploadService fileUploadService;

    @Override
    public CategoryResponse addCategory(CategoryRequest request, MultipartFile file) {
        String imageUrl = fileUploadService.uploadFile(file);
        CategoryEntity newCategory = convertToEntity(request);
        newCategory.setImageUrl(imageUrl);
        newCategory = categoryRepository.save(newCategory);
         return convertToResponse(newCategory);
    }

    @Override
    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryEntity->convertToResponse(categoryEntity))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteCategory(String categoryId) {
     CategoryEntity existingCategory = categoryRepository.findByCategoryId(categoryId)
             .orElseThrow(()->new RuntimeException("Category not found" +categoryId));
     fileUploadService.deleteFile(existingCategory.getImageUrl());
     categoryRepository.delete(existingCategory);
    }

    private CategoryResponse convertToResponse(CategoryEntity newCategory) {
        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setCategoryId(newCategory.getCategoryId());
        categoryResponse.setName(newCategory.getName());
        categoryResponse.setDescription(newCategory.getDescription());
        categoryResponse.setBgColor(newCategory.getBgColor());
        categoryResponse.setImageUrl(newCategory.getImageUrl());
        categoryResponse.setCreatedAt(newCategory.getCreatedAt());
        categoryResponse.setUpdatedAt(newCategory.getUpdatedAt());
        return categoryResponse;
    }

    private CategoryEntity convertToEntity(CategoryRequest request) {
       CategoryEntity newCategory1 = new CategoryEntity();
            newCategory1.setCategoryId(UUID.randomUUID().toString());
            newCategory1.setName(request.getName());
            newCategory1.setDescription(request.getDescription());
            newCategory1.setBgColor(request.getBgColor());
            return newCategory1;
    }
}
