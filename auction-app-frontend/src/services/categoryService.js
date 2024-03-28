import useFetch from "./customHooks";

const CategoryService = {
  getTopLevelCategories: () => {
    const { data, loading, error } = useFetch(`/categories/top-level`);
    return { categories: data, loading, error };
  },
};

export default CategoryService;
