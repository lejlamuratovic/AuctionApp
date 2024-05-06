import { useState, useEffect } from "react";

import { ProductBidsTable, LoadingComponent, ErrorComponent } from "src/components";

import { useUser } from "src/store/UserContext";
import { findBidsByUserId } from "src/services/bidService";

import { gavel } from "src/assets/icons";
import { BUTTON_LABELS } from "src/constants";

import "./style.scss";

const BidsTab = () => {
  const [bids, setBids] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  const { userId } = useUser(); 

  const fetchBids = () => {
    setLoading(true);

    findBidsByUserId(userId)
      .then((bids) => {
        setBids(bids);
      })
      .catch((error) => {
        setError(error.message);
      })
      .finally(() => {
        setLoading(false);
      });
  }

  useEffect(() => {
    fetchBids();
  }, [userId]);

  const noItemsComponent = (
      <div className="no-items-message body-semibold">
        <img src={ gavel } alt="gavel icon" className="no-items-icon" />
        <span className="body-regular"> You don’t have any bids and there are so many cool products available for sale </span>
        <span className="body-bold"> START BIDDING </span>
      </div>
  );
    
  if (loading) return <LoadingComponent />;
  if (error) return <ErrorComponent message={ error } />;

  return (
      <div className="bids-tab-container">
          <ProductBidsTable emptyMessageComponent={ noItemsComponent } items = { bids } buttonLabel = { BUTTON_LABELS.BID } />
      </div>
  )
}

export default BidsTab;
