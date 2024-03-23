import { productImage1 } from "src/assets/images";

import "./style.scss";

const ProductCard = ({ image, title, price }) => {
  return (
    <div className="product-card">
      <div className="img-container">
        <img src={productImage1} alt={title} />
      </div>
      <div className="product-info">
        <h5>{title}</h5>
        <span className="body-medium">
          Start From <span className="body-bold">{price}</span>
        </span>
      </div>
    </div>
  );
};

export default ProductCard;
