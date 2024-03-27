import useFetch from "./customHooks";

const CategoryService = {
  useCategoriesTopLevel: () => {
    const { data, loading, error } = useFetch(`/categories/topLevel`);
    return { categories: data, loading, error };
  },
};

export default CategoryService;
