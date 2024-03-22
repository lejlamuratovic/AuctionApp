import { productImage1 } from "src/assets/images";

import "./style.scss";

const ProductCard = () => {
  return (
    <div className="product-card">
      <div className="img-container">
        <img src={productImage1} alt="Product Image" />
      </div>
      <div className="product-info">
        <h5>Shoes Collection</h5>
        <span className="body-medium">
          Start From <span className="body-bold">$59.00</span>
        </span>
      </div>
    </div>
  );
};

export default ProductCard;
