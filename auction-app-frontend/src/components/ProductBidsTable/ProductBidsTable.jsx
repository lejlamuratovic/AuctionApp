import { useNavigate } from "react-router-dom";

import { ProductBidsItem, EmptyTab } from "src/components";

import { HEADERS, ROUTE_PATHS } from "src/constants";
import { calculateTimeLeft } from "src/utils/calculateTimeDifference";

import "./style.scss";

const ProductBidsTable = ({ items, buttonLabel, tabId }) => {
  const navigate = useNavigate();

  const hasItems = items ? items.length > 0 : false;

  const navigateToProduct = (id) => {
    navigate(`${ ROUTE_PATHS.PRODUCT }/${ id }`);
  }

  const adjustItemData = (items) => {
    return items.map(item => {
      // check if nested
      if (item.product && item.product.id) {
        return {
          ...item.product,
          bidAmount: item.bidAmount,
          bidTime: item.bidTime,
          id: item.id 
        };
      } else {
        return {
          ...item,
          bidAmount: item.startPrice,
          highestBid: item.highestBid ? item.highestBid : 0,
        }
      }
    });
  }

  const adjustedItems = items ? adjustItemData(items) : [];

  
  console.log("items", items);
  console.log("adjustedItems", adjustedItems);

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
          { adjustedItems.map(item => (
            <ProductBidsItem
              key={ item.id }
              imgSrc={ item.productImages[0].imageUrl }
              title={ item.name }
              timeLeft={ calculateTimeLeft(item.endDate) }
              bidPrice={ item.bidAmount }
              noBids={ item.bidsCount }
              highestBid={ item.highestBid }
              buttonLabel={ buttonLabel }
              onButtonClick={ () => navigateToProduct(item.id) }
              highestBidder={ item.bidAmount === item.highestBid }
            />
          )) }
        </div>
      ) : (
        <div className="table-empty">
          <EmptyTab tabId={ tabId } />
        </div>
      ) }
    </div>
  );
}

export default ProductBidsTable;
