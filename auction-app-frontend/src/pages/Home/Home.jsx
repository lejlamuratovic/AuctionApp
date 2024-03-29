import { useState, useEffect } from "react";

import {
  ProductGrid,
  ErrorComponent,
  LoadingComponent,
  Button,
} from "src/components";

import * as productService from "src/services/ProductService";
import * as categoryService from "src/services/CategoryService";

import { go } from "src/assets/icons";
import { TAB_NEW_ARRIVALS, TAB_LAST_CHANCE } from "src/constants";

import "./style.scss";

const Home = () => {
  const [activeTab, setActiveTab] = useState(TAB_NEW_ARRIVALS);
  const [page, setPage] = useState(0);
  const [items, setItems] = useState([]);
  const [hasMore, setHasMore] = useState(true);
  const [product, setProduct] = useState(null);
  const [categories, setCategories] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  // method to fetch initial data
  const fetchInitialData = async () => {
    try {
      setLoading(true);

      const randomProduct = await productService.getProductRandom();
      const topLevelCategories = await categoryService.getTopLevelCategories();

      setProduct(randomProduct);
      setCategories(topLevelCategories);
    } catch (err) {
      setError("Failed to fetch initial data", err);
    } finally {
      setLoading(false);
    }
  };

  // method to get products based on activeTab and page
  const fetchProducts = async () => {
    try {
      const paginatedProducts = await productService.getProductsPaginated(
        activeTab,
        page,
        8
      );

      setItems((currentItems) => {
        const newProducts = paginatedProducts.content.filter(
          (newProduct) =>
            !currentItems.some((item) => item.id === newProduct.id)
        );
        return [...currentItems, ...newProducts];
      });

      setHasMore(!paginatedProducts.last);
    } catch (err) {
      setError("Failed to fetch products", err);
    }
  };

  // to fetch initial data on component mount
  useEffect(() => {
    fetchInitialData();
  }, []);

  // to fetch products based on activeTab and page
  useEffect(() => {
    if (page > 0 || items.length === 0) fetchProducts();
  }, [activeTab, page]);

  const fetchMoreData = () => {
    if (!hasMore) return;
    setPage((prevPage) => prevPage + 1);
  };

  const setActiveTabHandler = (tabName) => {
    setPage(0);
    setItems([]);
    setActiveTab(tabName);
  };

  if (loading) return <LoadingComponent />;
  if (error) return <ErrorComponent message={error} />;

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
            <h5
              onClick={() => setActiveTabHandler(TAB_NEW_ARRIVALS)}
              id={TAB_NEW_ARRIVALS}
            >
              New Arrivals
            </h5>
            <h5
              onClick={() => setActiveTabHandler(TAB_LAST_CHANCE)}
              id={TAB_LAST_CHANCE}
            >
              Last Chance
            </h5>
          </div>
          <ProductGrid
            key={activeTab}
            items={items}
            fetchMoreData={fetchMoreData}
            hasMore={hasMore}
            loading={loading}
            activeTab={activeTab}
          />
        </div>
      </div>
    </>
  );
};

export default Home;
