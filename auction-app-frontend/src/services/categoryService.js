import { getRequest } from "src/utils/httpUtils";

const getTopLevelCategories = () => {
  return getRequest(`/categories/topLevel`);
};

export { getTopLevelCategories };
