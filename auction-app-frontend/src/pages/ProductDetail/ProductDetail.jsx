import { useEffect } from "react";
import { useParams } from "react-router-dom";

import { useBreadcrumb } from "src/store/BreadcrumbContext";

const ProductDetail = () => {
  const { productId } = useParams();
  const { setTitle } = useBreadcrumb();
  const title = "Blue T-Shirt";

  useEffect(() => {
    setTitle(title); // set the title in the breadcrumb context
  }, [productId]);

  return <div>Product Detail Page Content</div>;
};

export default ProductDetail;
