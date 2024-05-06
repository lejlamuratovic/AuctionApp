import { useNavigate } from "react-router-dom";

import { ProductBidsItem } from "src/components";

import { HEADERS, ROUTE_PATHS } from "src/constants";
import { calculateTimeLeft } from "src/utils/calculateTimeDifference";

import "./style.scss";

const ProductBidsTable = ({ emptyMessageComponent, items, buttonLabel }) => {
  const navigate = useNavigate();

  const hasItems = items ? items.length > 0 : false;

  const navigateToProduct = (id) => {
    navigate(`${ ROUTE_PATHS.PRODUCT }/${ id }`);
  }

  return (
    <div className="table">
      <div className="table-header body-bold">
        { HEADERS.map(header => (
          <div className={ `${ header.id } header-item` } key={ header.id }>
            { header.text }
          </div>
        )) }
      </div>
      { hasItems ? (
        <div className="table-content body-bold">
          {items.map(item => (
            <ProductBidsItem
              key={ item.id }
              imgSrc={ item.product.productImages[0].imageUrl }
              title={ item.product.name }
              timeLeft={ calculateTimeLeft(item.product.endDate) }
              bidPrice={ item.bidAmount }
              noBids={ item.product.bidsCount }
              highestBid={ item.product.highestBid }
              buttonLabel={ buttonLabel }
              onButtonClick={ () => navigateToProduct(item.product.id) }
              highestBidder={ item.bidAmount === item.product.highestBid }
            />
          )) }
        </div>
      ) : (
        <div className="table-empty">
          { emptyMessageComponent }
        </div>
      ) }
    </div>
  );
}

export default ProductBidsTable;
