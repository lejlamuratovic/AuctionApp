import { Button } from "src/components";

import { BUTTON_VARIANTS, BUTTON_LABELS } from "src/constants";

import "./style.scss"; 

const ProductBidsItem = ({ 
  imgSrc, 
  title, 
  timeLeft, 
  bidPrice, 
  highestBid, 
  noBids, 
  buttonLabel, 
  onButtonClick, 
  highestBidder,
  auctionEnded
}) => {

  const userIsHighestBidder = bidPrice === highestBid;

  return (
    <div className="product-bid-item">
      <div className="item-image">
        <img src={ imgSrc } alt={ title } />
      </div>
      <div className="item-title">{ title }</div>
      <div className="item-time-left">{ timeLeft }</div>
      <div className={ `${ highestBidder ? "highestBid" : "lowerBid" } item-your-price` }>${ bidPrice }</div>
      <div className="item-no-bids"> { noBids } </div>
      <div className="item-highest-bid">${ highestBid }</div>
      <div className="item-actions">
        { auctionEnded && userIsHighestBidder ? (
          <Button 
            variant={ BUTTON_VARIANTS.OUTLINED } 
            label={ BUTTON_LABELS.PAY }
            onButtonClick={ onButtonClick }
          />
        ) : (
          <Button 
            variant={ BUTTON_VARIANTS.OUTLINED } 
            label={ buttonLabel } 
            onButtonClick={ onButtonClick }
          />
        ) }
      </div>
    </div>
  );
}

export default ProductBidsItem;
