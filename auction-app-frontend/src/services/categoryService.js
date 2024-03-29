import { getRequest } from "src/utils/httpUtils";

const getTopLevelCategories = async () => {
  return await getRequest("/categories/top-level");
};

export { getTopLevelCategories };
