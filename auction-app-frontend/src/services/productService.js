import { getRequest } from "src/utils/httpUtils";

const getProductsPaginated = async (type, page, size) => {
  return await getRequest(
    `/products/criteria?page=${page}&size=${size}&type=${type}`
  );
};

const getProduct = async (id) => {
  return await getRequest(`/products/${id}`);
};

const getProductRandom = async () => {
  return await getRequest("/products/random");
};

export { getProductsPaginated, getProduct, getProductRandom };
