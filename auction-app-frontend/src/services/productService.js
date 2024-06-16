import { getRequest } from "src/utils/httpUtils";
import { postRequest } from "../utils/httpUtils";

const getProductsByCriteria = (type, page, size) => {
  return getRequest(
    `/products/criteria?page=${ page }&size=${ size }&type=${ type }`
  );
};

const getProduct = (id) => {
  return getRequest(`/products/${ id }`);
};

const getProductRandom = () => {
  return getRequest("/products/random");
};

const getProducts = (page, size, categoryId, searchQuery, sortCriteria, sortDirection, subcategoryIds, minPrice, maxPrice) => {
  // construct query string based on the presence of categoryId or search query
  const categoryParam = categoryId ? `&categoryId=${ categoryId }` : "";
  const searchParam = searchQuery
    ? `&searchProduct=${ encodeURIComponent(searchQuery) }`
    : "";

  // handle subcategoryIds array to append each id
  let subcategoryParams = "";
  if (subcategoryIds && subcategoryIds.length > 0) {
    subcategoryParams = subcategoryIds.map(id => `&subcategoryIds=${id}`).join('');
  }

  return getRequest(
    `/products?page=${ page }&size=${ size }${ categoryParam }${ subcategoryParams }${ searchParam }&sortField=${ sortCriteria }&sortDirection=${ sortDirection }&minPrice=${ minPrice }&maxPrice=${ maxPrice }`
  );
};

const getBidData = (productId) => {
  return getRequest(`/products/${ productId }/bid-details`);
};

const addProduct = (formData) => {
  return postRequest("/products", formData);
};

const findProductsByUserIdAndStatus = (userId, status, page, size) => {
  return getRequest(`/products/user-products?userId=${ userId }&status=${ status }&page=${ page }&size=${ size }`);
}

const findFeaturedProducts = (userId, count) => {
  return getRequest(`/products/featured-products/${ userId }?count=${ count }`);
};

const findPopularProducts = (count) => {
  return getRequest(`/products/featured-products?count=${ count }`);
}

const findMinAndMaxPrice = () => {
  return getRequest("/products/prices");
}

const findRandomProductsByCategory = (categoryId, count) => {
  return getRequest(`/products/random/${ categoryId }?count=${ count }`);
}

const addProductUsingCsv = (formData) => {
  return postRequest("/products/csv", formData);
};

export { 
  getProducts, 
  getProduct, 
  getProductRandom, 
  getProductsByCriteria, 
  getBidData, 
  addProduct,
  findProductsByUserIdAndStatus,
  findFeaturedProducts,
  findPopularProducts,
  findMinAndMaxPrice,
  findRandomProductsByCategory,
  addProductUsingCsv,
};
