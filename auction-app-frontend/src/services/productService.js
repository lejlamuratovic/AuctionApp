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

const getProductsPaginated = (page, size) => {
  return getRequest(`/products?page=${page}&size=${size}`);
};

export { getProductsPaginated, getProduct, getProductRandom, getProductsPaginatedSorted };
