import { getRequest } from "src/utils/httpUtils";

// fetch products paginated based on the endpoint (paginated, newArrivals, lastChance)
const fetchProducts = (endpoint, page, size) => {
  return getRequest(`/products/${endpoint}?page=${page}&size=${size}`);
};

// fetch a single product based on the product id
const getProduct = (id) => getRequest(`/products/${id}`);

// get a random product
const getRandomProduct = () => getRequest("/products/random");

export { fetchProducts, getProduct, getRandomProduct };
