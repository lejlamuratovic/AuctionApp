import useFetch from "./customHooks";

const ProductService = {
  getProductsPaginated: (endpoint, page, size) => {
    const { data, loading, error } = useFetch(
      `/products/${endpoint}?page=${page}&size=${size}`,
      [page, size] // dependencies
    );
    return { data: data ? data.content : [], loading, error };
  },

  getProduct: (id) => {
    const { data, loading, error } = useFetch(`/products/${id}`);
    return { product: data, loading, error };
  },

  getProductRandom: () => {
    const { data, loading, error } = useFetch("/products/random");
    return { product: data, loading, error };
  },
};

export default ProductService;
