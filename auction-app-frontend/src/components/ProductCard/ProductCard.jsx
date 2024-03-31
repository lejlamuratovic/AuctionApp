import { Link } from "react-router-dom";

import "./style.scss";

const ProductCard = ({ id, imageUrl, name, startPrice }) => {
  return (
    <Link to={`/shop/${id}`} className="product-card">
      <div className="img-container">
        <img src={imageUrl} alt={name} />
      </div>
      <div className="product-info">
        <h5>{name}</h5>
        <span className="body-medium">
          Start From <span className="body-bold">${startPrice}</span>
        </span>
      </div>
    </Link>
  );
};

export default ProductCard;
