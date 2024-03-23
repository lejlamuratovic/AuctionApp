import { useState, useEffect } from "react";

import { ProductGrid, ErrorComponent, LoadingComponent } from "src/components";

import { useCategoriesPaginated } from "src/hooks";
import { useProductsPaginated } from "src/hooks";

import { productImage2 } from "src/assets/images";

import "./style.scss";

const Home = () => {
  const [activeTab, setActiveTab] = useState("newArrivals");
  const [page, setPage] = useState(0);
  const [items, setItems] = useState([]);
  const [hasMore, setHasMore] = useState(true);

  const {
    categories,
    loading: categoriesLoading,
    error: categoriesError,
  } = useCategoriesPaginated(0, 9);
  const {
    products,
    loading: productsLoading,
    error: productsError,
  } = useProductsPaginated(page, 8);

  useEffect(() => {
    // concat new products to the existing items
    setItems((prevItems) => [...prevItems, ...products]);
    setHasMore(products.length > 0);
  }, [products]);

  const fetchMoreData = () => {
    // fetch more products
    setPage((prevPage) => prevPage + 1);
  };

  if (categoriesLoading || productsLoading) return <LoadingComponent />;
  if (categoriesError || productsError) return <ErrorComponent />;

  return (
    <>
      <div className="home-container">
        <div className="home-upper">
          <div className="categories body-regular">
            <div className="categories-heading">Categories</div>
            <ul>
              {categories.map((category) => (
                <li key={category.id}>{category.name}</li>
              ))}
              <li>All Categories</li>
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
            items={items}
            fetchMoreData={fetchMoreData}
            hasMore={hasMore}
            loading={productsLoading}
          />
        </div>
      </div>
    </>
  );
};

export default Home;
