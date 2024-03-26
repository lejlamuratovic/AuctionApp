import { useState, useEffect } from "react";

import { getRequest } from "src/utils/httpUtils";

const ProductService = {
  // fetch functions
  fetchProducts: (endpoint, page, size) =>
    getRequest(`/products/${endpoint}?page=${page}&size=${size}`),
  getProduct: (id) => getRequest(`/products/${id}`),
  getRandomProduct: () => getRequest("/products/random"),

  // hooks
  useProductsPaginated: (endpoint, page, size) => {
    const [data, setData] = useState([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);

    useEffect(() => {
      const fetchData = async () => {
        setLoading(true);
        try {
          const result = await ProductService.fetchProducts(
            endpoint,
            page,
            size
          );
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
  },

  useProduct: (id) => {
    const [product, setProduct] = useState({});
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);

    useEffect(() => {
      const fetchData = async () => {
        setLoading(true);
        try {
          const result = await ProductService.getProduct(id);
          setProduct(result);
        } catch (err) {
          setError(err);
        } finally {
          setLoading(false);
        }
      };

      fetchData();
    }, [id]);

    return { product, loading, error };
  },

  useProductRandom: () => {
    const [product, setProduct] = useState({});
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);

    useEffect(() => {
      const fetchData = async () => {
        setLoading(true);
        try {
          const result = await ProductService.getRandomProduct();
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
  },
};

export default ProductService;
