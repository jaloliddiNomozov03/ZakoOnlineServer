package uz.zako.OnlineZakoServer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uz.zako.OnlineZakoServer.entity.Category;
import uz.zako.OnlineZakoServer.model.Result;
import uz.zako.OnlineZakoServer.repository.CategoryRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Override
    public Result save(Category category) {
        try {
            categoryRepository.save(category);
            return new Result(true, "save successful");
        }catch (Exception e){
            System.out.println(e);
        }
        return new Result(false,"save failed");
    }

    @Override
    public Result edit(Category category, Long id) {
        try {
            Category updateCategory=categoryRepository.findById(id).get();
            updateCategory.setNameUz(category.getNameUz());
            updateCategory.setNameRu(category.getNameRu());
            categoryRepository.save(updateCategory);
            return new Result(true,"editinf successful");
        }catch (Exception e){
            System.out.println(e);
        }
        return new Result(false, "editing failed");
    }

    @Override
    public Result delete(Long id) {
        try {
            categoryRepository.deleteById(id);
            return new Result(true, "deleting successful");
        }catch (Exception e){
            System.out.println(e);
        }
        return new Result(false, "deleting failed");
    }

    @Override
    public Category findById(Long id) {
        try {
            return categoryRepository.findById(id).get();
        }catch (Exception e){
            System.out.println(e);
        }
        return null;
    }

    @Override
    public Page<Category> findCategoryPage(int page, int size) {
        try {
            Pageable pageable= PageRequest.of(page, size, Sort.by("createAt").descending());
            return categoryRepository.findAll(pageable);
        }catch (Exception e){
            System.out.println(e);
        }
        return null;
    }

    @Override
    public List<Category> findAll() {
        try {
            return categoryRepository.findAll();
        }catch (Exception e){
            System.out.println(e);
        }
        return new ArrayList<>();
    }
}
