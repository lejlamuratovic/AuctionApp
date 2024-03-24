import { useState, useEffect } from "react";

import { getCategoriesPaginated } from "src/services/categoryService";

const useCategoriesPaginated = (page, size) => {
  const [categories, setCategories] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchCategories = async () => {
      setLoading(true);
      try {
        const data = await getCategoriesPaginated(page, size);
        setCategories(data.content);
      } catch (err) {
        setError(err);
      } finally {
        setLoading(false);
      }
    };

    fetchCategories();
  }, [page, size]);

  return { categories, loading, error };
};

export default useCategoriesPaginated;
