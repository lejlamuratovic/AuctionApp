import { Button } from "src/components";

import { BUTTON_VARIANTS } from "src/constants";

import "./style.scss"; 

const ProductBidsItem = ({ imgSrc, title, timeLeft, yourPrice, highestBid, noBids, buttonLabel, onButtonClick, highestBidder }) => {
  return (
    <div className="product-bid-item">
      <div className="item-image">
        <img src={ imgSrc } alt={ title } />
      </div>
      <div className="item-title">{ title }</div>
      <div className="item-time-left">{ timeLeft }</div>
      <div className={ `item-your-price ${highestBidder ? "highestBid" : "lowerBid"}` }>${ yourPrice }</div>
      <div className="item-no-bids"> { noBids } </div>
      <div className="item-highest-bid">${ highestBid }</div>
      <div className="item-actions">
        <Button variant={ BUTTON_VARIANTS.OUTLINED } label={ buttonLabel } onButtonClick={ onButtonClick }/>
      </div>
    </div>
  );
}

export default ProductBidsItem;
