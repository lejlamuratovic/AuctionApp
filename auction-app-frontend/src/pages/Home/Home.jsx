import { useState } from "react";

import { ProductGrid } from "src/components";

import { CATEGORIES } from "src/constants";
import { products } from "src/products.js";
import { productImage2 } from "src/assets/images";

import "./style.scss";

const Home = () => {
  const [activeTab, setActiveTab] = useState("newArrivals");
  const [items, setItems] = useState(products.slice(0, 9));
  const [hasMore, setHasMore] = useState(true);

  const fetchMoreData = () => {
    if (items.length >= products.length) {
      setHasMore(false);
      return;
    }

    setTimeout(() => {
      setItems(items.concat(products.slice(items.length, items.length + 10)));
    }, 500);
  };

  // sort items based on active tab, not yet implemented
  const sortedItems = [...items].sort((a, b) => {
    return 0;
  });

  return (
    <>
      <div className="home-container">
        <div className="home-upper">
          <div className="categories body-regular">
            <div className="categories-heading">Categories</div>
            <ul>
              {/* hardcoded for now */}
              {CATEGORIES.map((category, index) => (
                <li key={index}>{category}</li>
              ))}
            </ul>
          </div>

          <div className="highlighted-product">
            <div className="product-container">
              <div className="product-info body-semibold">
                <span className="product-name">Running shoes</span>
                <span className="price">Start From $59.00</span>
                <p className="body-regular">
                  Lorem ipsum dolor sit amet, consectetur adipiscing elit.
                  Vestibulum hendrerit odio a erat lobortis auctor. Curabitur
                  sodales pharetra placerat. Aenean auctor luctus tempus. Cras
                  laoreet et magna in dignissim. Nam et tincidunt augue. Vivamus
                  quis malesuada velit. In hac habitasse platea dictumst.
                </p>
                <button className="bid-btn body-bold">Bid Now</button>
              </div>
            </div>
            <div className="product-image">
              <img src={productImage2} alt="Product Image" />
            </div>
          </div>
        </div>

        <div className="products">
          <div className="tabs">
            {activeTab === "newArrivals" ? (
              <h5 onClick={() => setActiveTab("newArrivals")}>New Arrivals</h5>
            ) : (
              <h6 onClick={() => setActiveTab("newArrivals")}>New Arrivals</h6>
            )}
            {activeTab === "lastChance" ? (
              <h5 onClick={() => setActiveTab("lastChance")}>Last Chance</h5>
            ) : (
              <h6 onClick={() => setActiveTab("lastChance")}>Last Chance</h6>
            )}
          </div>
          <ProductGrid
            items={sortedItems}
            fetchMoreData={fetchMoreData}
            hasMore={hasMore}
          />
        </div>
      </div>
    </>
  );
};

export default Home;
