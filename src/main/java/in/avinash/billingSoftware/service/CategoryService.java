package in.avinash.billingSoftware.service;

import in.avinash.billingSoftware.io.CategoryRequest;
import in.avinash.billingSoftware.io.CategoryResponse;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public interface CategoryService {

    CategoryResponse addCategory(CategoryRequest request, MultipartFile file);

    List<CategoryResponse> getAllCategories();

    void deleteCategory(String categoryId);
}
