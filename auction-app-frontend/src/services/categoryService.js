import { useState, useEffect } from "react";

import { getRequest } from "src/utils/httpUtils";

const CategoryService = {
  // fetch functions
  getTopLevelCategories: () => getRequest(`/categories/topLevel`),

  // hooks
  useCategoriesTopLevel: () => {
    const [categories, setCategories] = useState([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);

    useEffect(() => {
      const fetchCategories = async () => {
        setLoading(true);
        try {
          const data = await CategoryService.getTopLevelCategories();
          setCategories(data);
        } catch (err) {
          setError(err);
        } finally {
          setLoading(false);
        }
      };

      fetchCategories();
    }, []);

    return { categories, loading, error };
  },
};

export default CategoryService;
