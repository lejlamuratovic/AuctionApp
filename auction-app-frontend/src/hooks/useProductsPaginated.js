import { useState, useEffect } from "react";
import { fetchProducts } from "src/services/productService";

const useProductsPaginated = (endpoint, page, size) => {
  const [data, setData] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchData = async () => {
      console.log(`Fetching: ${endpoint}, Page: ${page}`); // Debug log
      setLoading(true);
      try {
        const result = await fetchProducts(endpoint, page, size);
        setData(result.content);
      } catch (err) {
        setError(err);
      } finally {
        setLoading(false);
      }
    };

    fetchData();
  }, [endpoint, page, size]);

  return { data, loading, error };
};

export default useProductsPaginated;
