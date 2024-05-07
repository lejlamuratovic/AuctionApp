import { useState, useEffect } from "react";

import { ProductBidsTable, LoadingComponent, ErrorComponent } from "src/components";

import { useUser } from "src/store/UserContext";
import { findBidsByUserId } from "src/services/bidService";

import { BUTTON_LABELS, MY_ACCOUNT_TABS_MAP } from "src/constants";

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

  if (loading) return <LoadingComponent />;
  if (error) return <ErrorComponent message={ error } />;

  return (
      <div className="bids-tab-container">
          <ProductBidsTable 
            items = { bids } 
            buttonLabel = { BUTTON_LABELS.BID } 
            tabId = { MY_ACCOUNT_TABS_MAP.BIDS }
          />
      </div>
  )
}

export default BidsTab;
