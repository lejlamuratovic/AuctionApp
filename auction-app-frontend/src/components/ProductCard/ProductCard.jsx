import { Link } from "react-router-dom";

import { ROUTE_PATHS } from "src/constants";

import "./style.scss";

const ProductCard = ({ id, productImages, name, startPrice }) => {
  return (
    <Link to={ `${ROUTE_PATHS.SHOP}/${id}` } className="product-card">
      <div className="img-container">
        <img src={ productImages[0].imageUrl } alt={ name } />
      </div>
      <div className="product-info">
        <h5>{ name }</h5>
        <span className="body-medium">
          Start From <span className="body-bold">${ startPrice }</span>
        </span>
      </div>
    </Link>
  );
};

export default ProductCard;
