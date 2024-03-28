import useFetch from "./customHooks";

const ProductService = {
  getProductsPaginated: (type, page, size) => {
    const { data, loading, error } = useFetch(
      `/products/sorted?page=${page}&size=${size}&type=${type}`,
      [type, page] // dependencies
    );

    return {
      data: data ? data : { content: [], last: true, totalPages: 0, number: 0 },
      loading,
      error,
    };
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
