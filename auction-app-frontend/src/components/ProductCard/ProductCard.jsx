import { Link } from "react-router-dom";

import { ROUTE_PATHS } from "src/constants";

import "./style.scss";

const ProductCard = ({ id, productImages, name, startPrice, description, showDescription }) => {
  const cardClassName = `product-card ${ showDescription ? "list-view" : "" }`;

  return (
    <div className={ cardClassName }>
      <Link to={ `${ ROUTE_PATHS.PRODUCT }/${ id }` }>
        <div className="img-container">
          <img src={ productImages[0].imageUrl } alt={ name } />
        </div>
      </Link>
      <div className="product-info">
        <h5>{ name }</h5>
        { showDescription && <p className="product-description body-regular">{ description }</p> }
        <span className="body-medium">
          Start From <span className="body-bold">${ startPrice }</span>
        </span>
      </div>
    </div>
  );
};

export default ProductCard;
