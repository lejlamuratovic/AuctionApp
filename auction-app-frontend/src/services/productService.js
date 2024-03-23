import { getRequest } from "src/utils/httpUtils";

const getProductsPaginated = (page, size) => {
  return getRequest(`/products/paginated?page=${page}&size=${size}`);
};

export { getProductsPaginated };
