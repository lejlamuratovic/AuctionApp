import { useState, useEffect } from "react";

import { getRandomProduct } from "src/services/productService";

const useProduct = (id) => {
  const [product, setProduct] = useState({});
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchData = async () => {
      setLoading(true);
      try {
        const result = await getRandomProduct();
        setProduct(result);
      } catch (err) {
        setError(err);
      } finally {
        setLoading(false);
      }
    };

    fetchData();
  }, []);

  return { product, loading, error };
};

export default useProduct;
