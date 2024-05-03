import { useState } from "react";

import { ProductBidsTable } from "src/components";

import { cart } from "src/assets/icons";
import { SELLER_TABS } from "src/constants";

import "./style.scss";

const SellerTab = () => {
  const [activeTab, setActiveTab] = useState(SELLER_TABS[0].id);

  const noItemsComponent = (
    <div className="no-items-message body-semibold">
      <img src={ cart } alt="cart icon" className="no-items-icon" />
      <span className="body-regular"> You do not have any scheduled items for sale </span>
      <span className="body-bold"> START SELLING </span>
    </div>
  );

  const onTabClick = (tabId) => {
    setActiveTab(tabId);
  }  

  return (
    <div className="seller-tab-container">
      <div className="seller-tabs">
        { SELLER_TABS.map((tab) => (
          <span 
            key={ tab.id } 
            className={ `tab body-semibold ${activeTab === tab.id ? 'active' : ''}` }
            onClick={ () => onTabClick(tab.id) }
          >
            { tab.label }
          </span>
        )) }
      </div>
      <ProductBidsTable emptyMessageComponent={ noItemsComponent } />
    </div>
  )
}

export default SellerTab;
