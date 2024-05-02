import { useState } from "react";

import { Button } from "src/components";

import { MY_ACCOUNT_TABS, BUTTON_LABELS, BUTTON_VARIANTS } from "src/constants";

import "./style.scss";

const MyAccountOptions = () => {
  const [activeTab, setActiveTab] = useState(MY_ACCOUNT_TABS[0].id);

  const changeActiveTab = (tabId) => () => {
    setActiveTab(tabId);
  };

  return (
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
  )
}

export default MyAccountOptions;
