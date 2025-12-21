package in.avinash.billingSoftware.controller;

import in.avinash.billingSoftware.io.CategoryRequest;
import in.avinash.billingSoftware.io.CategoryResponse;
import in.avinash.billingSoftware.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import tools.jackson.databind.ObjectMapper;
import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping(value = "/addCategory",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryResponse addCategory(@RequestPart("category") String categoryString,
                                        @RequestPart("file") MultipartFile file){
        ObjectMapper objectMapper = new ObjectMapper();
        CategoryRequest request = null;
        try{
            request = objectMapper.readValue(categoryString,CategoryRequest.class);
            return categoryService.addCategory(request,file);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Exception occured while parsing the jasn");
        }
    }

    @GetMapping("/getAllCategories")
    public List<CategoryResponse> getAllCategories(){
        return categoryService.getAllCategories();
    }
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{categoryId}")
    public void deleteCategory(@PathVariable String categoryId){
        try {
            categoryService.deleteCategory(categoryId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());
        }
    }
}
