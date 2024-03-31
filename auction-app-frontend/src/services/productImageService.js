import { getRequest } from "src/utils/httpUtils";

const getProductImages = (id) => {
  return getRequest(`/product-image/${id}`);
};

export { getProductImages };
