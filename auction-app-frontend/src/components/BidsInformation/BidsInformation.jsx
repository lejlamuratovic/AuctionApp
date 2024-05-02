import { ProductBidsTable } from "src/components";

import { gavel } from "src/assets/icons";

import "./style.scss";

const BidsInformation = () => {
    const noItemsComponent = (
        <div className="no-items-message body-semibold">
          <img src={ gavel } alt="gavel icon" className="no-items-icon" />
          <span className="body-regular"> You don’t have any bids and there are so many cool products available for sale </span>
          <span className="body-bold"> START BIDDING </span>
        </div>
      );
    
    return (
        <div className="bids-information-container">
            <ProductBidsTable emptyMessageComponent={ noItemsComponent } />
        </div>
    )
}

export default BidsInformation
