import { getRequest } from "src/utils/httpUtils";

// fetch products paginated based on the endpoint (paginated, newArrivals, lastChance)
export const fetchProducts = (endpoint, page, size) => {
  return getRequest(`/products/${endpoint}?page=${page}&size=${size}`);
};

export const getProductsPaginated = (page, size) =>
  fetchProducts("paginated", page, size);
export const getNewArrivals = (page, size) =>
  fetchProducts("newArrivals", page, size);
export const getLastChance = (page, size) =>
  fetchProducts("lastChance", page, size);

// fetch a single product based on the product id
export const getProduct = (id) => getRequest(`/products/${id}`);
