import { useState } from "react";

import { Tabs } from "src/components";

import { PRODUCT_DETAILS_TABS } from "src/constants";

import "./style.scss";

const ProductDetails = () => {
  const [activeTab, setActiveTab] = useState(PRODUCT_DETAILS_TABS[0].id);

  const setActiveTabHandler = (tabId) => {
    setActiveTab(tabId);
  };

  const productDescription = `
  The Jackets is US standard size, Please choose size as your usual wear 
  Material: 100% Polyester 
  Detachable Liner Fabric: Warm Fleece. Detachable 
  Functional Liner: Skin Friendly, Lightweigt and Warm.
  Stand Collar Liner jacket, keep you warm in cold weather. 
  Zippered Pockets: 2 Zippered Hand Pockets, 2 Zippered Pockets on Chest (enough to keep cards or keys)and 1 Hidden Pocket Inside.
  Zippered Hand Pockets and Hidden Pocket keep your things secure. 
  Humanized Design: Adjustable and Detachable Hood and Adjustable cuff to prevent the wind and water,for a comfortable fit.
  3 in 1 Detachable Design provide more convenience, you can separate the coat and inner as needed, or wear it together. It is suitable for different season and help you adapt to different climates.`;

  return (
    <>
      <div className="product-details-container">
        <div className="product-detail-images"></div>
        <div className="product-details">
          <div className="product-name">
            <span className="body-semibold">
              BIYLACLESEN Womens 3-in-1 Snowboard Jacket Winter Coats
            </span>
            <span className="body-regular">
              Starts from <span className="price">$50</span>
            </span>
          </div>
          <div className="product-bid-details"></div>
          <div className="product-information">
            <Tabs
              tabs={PRODUCT_DETAILS_TABS}
              activeTab={activeTab}
              onTabClick={setActiveTabHandler}
            />
            {activeTab === "details" && (
              <div className="product-description">
                <p>{productDescription}</p>
              </div>
            )}
          </div>
        </div>
      </div>
    </>
  );
};

export default ProductDetails;
