import { getRequest } from "src/utils/httpUtils";

const getTopLevelCategories = () => {
  return getRequest("/categories/top-level");
};

const getCategoriesWithSubcategories = () => {
  return getRequest("/categories/with-subcategories");
};

const getSubcategoriesByParentCategory = (id) => {
  return getRequest(`/categories/${id}/subcategories`);
}

export { getTopLevelCategories, getCategoriesWithSubcategories, getSubcategoriesByParentCategory };
