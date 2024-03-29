import useFetch from "./customHooks";

const categoryService = {
  getTopLevelCategories: () => {
    const { data, loading, error } = useFetch(`/categories/top-level`);
    return { categories: data, loading, error };
  },
};

export default categoryService;
