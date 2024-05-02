import { ProductBidsItem } from "src/components";

import { HEADERS } from "src/constants";

import "./style.scss";

const ProductBidsTable = ({ emptyMessageComponent, items, buttonLabel }) => {
  const hasItems = items ? items.length > 0 : false;

  return (
    <div className="table">
      <div className="table-header body-bold">
        { HEADERS.map(header => (
          <div className={ `header-item ${header.id}` } key={ header.id }>
            { header.text }
          </div>
        )) }
      </div>
      { hasItems ? (
        <div className="table-content">
          {items.map(item => (
            <ProductBidsItem
              key={ item.id }
              imgSrc={ item.imgSrc }
              title={ item.title }
              timeLeft={ item.timeLeft }
              yourPrice={ item.yourPrice }
              noBids={ item.noBids }
              highestBid={ item.highestBid }
              buttonLabel={ buttonLabel }
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
