import { useState, useEffect } from "react";

import { getProductsPaginated } from "src/services/productService";

const useProductsPaginated = (page, size) => {
  const [products, setProducts] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchProducts = async () => {
      setLoading(true);
      try {
        const data = await getProductsPaginated(page, size);
        setProducts(data.content);
      } catch (err) {
        setError(err);
      } finally {
        setLoading(false);
      }
    };

    fetchProducts();
  }, [page, size]);

  return { products, loading, error };
};

export default useProductsPaginated;
