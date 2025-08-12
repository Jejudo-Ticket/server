package org.funding.domain.category.dao;

import org.funding.domain.category.dto.CategoryRequestDTO;
import org.funding.domain.category.dto.CategoryResponseDTO;
import org.funding.domain.category.vo.CategoryVO;

import java.util.List;

public interface CategoryDAO {

    CategoryResponseDTO selectCategory(String name);

    List<CategoryVO> selectAllCategories();

    void insertCategory(CategoryRequestDTO requestDTO);

    void deleteCategory(String name);

}
