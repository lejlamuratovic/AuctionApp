import { useState } from "react";

import { Button, ProfileTab, SellerTab, BidsTab, SettingsTab } from "src/components";

import { MY_ACCOUNT_TABS, BUTTON_LABELS, BUTTON_VARIANTS } from "src/constants";

import "./style.scss";

const MyAccountOptions = () => {
  const [activeTab, setActiveTab] = useState(MY_ACCOUNT_TABS[0].id);
  
  window.location.hash = window.location.hash || MY_ACCOUNT_TABS[0].id; // set default tab to profile

  const changeActiveTab = (tabId) => () => {
    setActiveTab(tabId);
    window.location.hash = tabId;
  };

  const renderActiveTabContent = () => {
    switch (activeTab) {
      case "profile":
        return <ProfileTab />;
      case "seller": 
        return <SellerTab />;
      case "bids":
        return <BidsTab />;
      case "settings":
        return <SettingsTab />;
      default:
        return null;
    }
  };

  return (
    <div className="my-account-options-container">
      <div className="my-account-tabs">
        <div className="tab-options">
          { MY_ACCOUNT_TABS.map((tab) => (
            <span
              key={ tab.id }
              onClick={ changeActiveTab(tab.id) }
              className={ `tab ${activeTab === tab.id ? "active" : "inactive"} body-semibold` }
            >
              <img 
                  src={ activeTab === tab.id ? tab.activeIcon : tab.icon } 
                  alt={ `${tab.label} icon` } 
                  className="tab-icon" 
              />
              { tab.label }
            </span> 
          )) }
        </div>
        <div className="add-item-button">
            <Button label={ BUTTON_LABELS.ADD_ITEM } variant= { BUTTON_VARIANTS.FILLED }/>
        </div>
      </div>
      <div>
        { renderActiveTabContent() }
      </div>
    </div>
  )
}

export default MyAccountOptions;
