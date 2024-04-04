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

const getProductsPaginated = (page, size, categoryId, searchQuery) => {
  // construct query string based on the presence of categoryId or search query
  const categoryParam = categoryId ? `&category_id=${categoryId}` : '';
  const searchParam = searchQuery ? `&search=${encodeURIComponent(searchQuery)}` : '';
  
  // find correct endpoint based on the presence of search query
  const endpoint = searchQuery ? `/products/search` : `/products`;

  return getRequest(`${endpoint}?page=${page}&size=${size}${categoryParam}${searchParam}`);
};

export { getProductsPaginated, getProduct, getProductRandom, getProductsPaginatedSorted };
