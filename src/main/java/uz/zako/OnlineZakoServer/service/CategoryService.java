package uz.zako.OnlineZakoServer.service;

import org.springframework.data.domain.Page;
import uz.zako.OnlineZakoServer.entity.Category;
import uz.zako.OnlineZakoServer.model.Result;

import java.util.List;

public interface CategoryService {
    Result save(Category category);
    Result edit(Category category, Long id);
    Result delete(Long id);
    Category findById(Long id);
    Page<Category> findCategoryPage(int page, int size);
    List<Category> findAll();
}
