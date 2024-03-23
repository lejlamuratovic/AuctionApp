import { getRequest } from "src/utils/httpUtils";

const getCategoriesPaginated = (page, size) => {
  return getRequest(`/categories/paginated?page=${page}&size=${size}`);
};

export { getCategoriesPaginated };
