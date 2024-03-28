import { useState, useEffect } from "react";

import {
  ProductGrid,
  ErrorComponent,
  LoadingComponent,
  Button,
} from "src/components";

import ProductService from "src/services/ProductService";
import CategoryService from "src/services/CategoryService";

import { go } from "src/assets/icons";

import "./style.scss";

const Home = () => {
  const [activeTab, setActiveTab] = useState("newArrivals");
  const [page, setPage] = useState(0);
  const [items, setItems] = useState([]);
  const [hasMore, setHasMore] = useState(true);

  const {
    product,
    loading: productLoading,
    error: productError,
  } = ProductService.getProductRandom();
  const {
    categories,
    loading: categoriesLoading,
    error: categoriesError,
  } = CategoryService.getTopLevelCategories();

  // determine fetching function based on activeTab
  const endpoint = activeTab === "newArrivals" ? "new-arrivals" : "last-chance";

  const {
    data: products,
    loading: productsLoading,
    error: productsError,
  } = ProductService.getProductsPaginated(endpoint, page, 8);

  useEffect(() => {
    const updatedItems = [
      ...items,
      ...products.filter(
        (product) => !items.some((item) => item.id === product.id)
      ),
    ];

    setItems(updatedItems);
    setHasMore(products.length > 0);
  }, [products]);

  const fetchMoreData = () => {
    setPage((prevPage) => prevPage + 1);
  };

  if (categoriesLoading || productLoading) return <LoadingComponent />;

  const errorMessages = [];
  if (categoriesError) errorMessages.push("Categories Loading Error");
  if (productsError) errorMessages.push("Products Loading Error");
  if (productError) errorMessages.push("Product Loading Error");

  if (errorMessages.length > 0)
    return <ErrorComponent message={errorMessages.join(", ")} />;

  const setNewArrivals = () => {
    if (activeTab !== "newArrivals") {
      setActiveTab("newArrivals");
      setPage(0);
      setItems([]);
    }
  };

  const setLastChance = () => {
    if (activeTab !== "lastChance") {
      setActiveTab("lastChance");
      setPage(0);
      setItems([]);
    }
  };

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
                <span className="product-name">{product.name}</span>
                <span className="price">Start From ${product.startPrice}</span>
                <span className="body-regular">{product.description}</span>
                <Button label="Bid now" iconSrc={go} />
              </div>
            </div>
            <div className="product-image">
              <img src={product.imageUrl} alt={product.name} />
            </div>
          </div>
        </div>

        <div className="products">
          <div className="tabs">
            <h5 onClick={setNewArrivals} id="newArrivals">
              New Arrivals
            </h5>
            <h5 onClick={setLastChance} id="lastChance">
              Last Chance
            </h5>
          </div>
          <ProductGrid
            key={activeTab}
            items={items}
            fetchMoreData={fetchMoreData}
            hasMore={hasMore}
            loading={productsLoading}
            activeTab={activeTab}
          />
        </div>
      </div>
    </>
  );
};

export default Home;
