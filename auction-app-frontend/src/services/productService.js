import { getRequest } from "src/utils/httpUtils";

const getProductsPaginatedSorted = (type, page, size) => {
  return getRequest(
    `/products/criteria?page=${page}&size=${size}&type=${type}`
  );
};

const getProduct = (id) => {
  return getRequest(`/products/${id}`);
};

const getProductRandom = () => {
  return getRequest("/products/random");
};

const getProductsPaginated = (categoryId, page, size) => {
  // if categoryId is provided, add it to the query string
  const categoryParam = categoryId ? `&category_id=${categoryId}` : '';
  
  return getRequest(`/products?page=${page}&size=${size}${categoryParam}`);
};


export { getProductsPaginated, getProduct, getProductRandom, getProductsPaginatedSorted };
