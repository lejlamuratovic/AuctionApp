import { productImage1 } from "src/assets/images";

import "./style.scss";

const ProductCard = ({ image, name, startPrice }) => {
  return (
    <div className="product-card">
      <div className="img-container">
        <img src={productImage1} alt={name} />
      </div>
      <div className="product-info">
        <h5>{name}</h5>
        <span className="body-medium">
          Start From <span className="body-bold">${startPrice}</span>
        </span>
      </div>
    </div>
  );
};

export default ProductCard;
