import { useState, useEffect } from "react";

import { ProductGrid, ErrorComponent, LoadingComponent } from "src/components";

import {
  useCategoriesPaginated,
  useProductsPaginated,
  useProduct,
} from "src/hooks";

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
  } = useProduct(31); // hardcoded id for now
  const {
    categories,
    loading: categoriesLoading,
    error: categoriesError,
  } = useCategoriesPaginated(0, 9);

  // determine fetching function based on activeTab
  const endpoint = activeTab === "newArrivals" ? "newArrivals" : "lastChance";
  const {
    data: products,
    loading: productsLoading,
    error: productsError,
  } = useProductsPaginated(endpoint, page, 8);

  useEffect(() => {
    // map from existing items for deduplication
    const newItemsMap = new Map(items.map((item) => [item.id, item]));

    // append new products and ignore duplicates
    products.forEach((product) => {
      newItemsMap.set(product.id, product);
    });

    // map back to an array
    const uniqueItems = Array.from(newItemsMap.values());

    setItems(uniqueItems);
    setHasMore(products.length > 0);
  }, [products]);

  const fetchMoreData = () => {
    setPage((prevPage) => prevPage + 1);
  };

  if (categoriesLoading || productsLoading || productLoading)
    return <LoadingComponent />;
  if (categoriesError || productsError || productError)
    return <ErrorComponent />;

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
                <p className="body-regular">{product.description}</p>
                <button className="bid-btn body-bold">Bid Now</button>
              </div>
            </div>
            <div className="product-image">
              <img src={product.imageUrl} alt={product.name} />
            </div>
          </div>
        </div>

        <div className="products">
          <div className="tabs">
            <h5
              onClick={() => {
                setActiveTab("newArrivals");
                setPage(0);
                setItems([]);
              }}
            >
              New Arrivals
            </h5>
            <h5
              onClick={() => {
                setActiveTab("lastChance");
                setPage(0);
                setItems([]);
              }}
            >
              Last Chance
            </h5>
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
