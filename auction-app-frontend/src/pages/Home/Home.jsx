import { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import useInfiniteScroll from "react-infinite-scroll-hook";

import {
  ProductGrid,
  ErrorComponent,
  LoadingComponent,
  Button,
  Tabs,
} from "src/components";

import {
  getProductsPaginatedSorted,
  getProductRandom,
  getTopLevelCategories,
} from "src/services";

import { go } from "src/assets/icons";

import { HOME_TABS, ROUTE_PATHS } from "src/constants";

import "./style.scss";

const Home = () => {
  const [activeTab, setActiveTab] = useState(HOME_TABS[0].id);
  const [page, setPage] = useState(0);
  const [items, setItems] = useState([]);
  const [hasMore, setHasMore] = useState(true);
  const [product, setProduct] = useState(null);
  const [categories, setCategories] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  // method to fetch initial data
  const fetchInitialData = () => {
    setLoading(true);

    Promise.all([getProductRandom(), getTopLevelCategories()])
      .then(([randomProduct, topLevelCategories]) => {
        setProduct(randomProduct);
        setCategories(topLevelCategories);
      })
      .catch((err) => {
        setError("Failed to fetch initial data: " + err.message);
      })
      .finally(() => {
        setLoading(false);
      });
  };

  // method to get products based on activeTab and page
  const fetchProducts = () => {
    getProductsPaginatedSorted(activeTab, page, 8)
      .then((res) => {
        setItems((prevItems) =>
          page === 0 ? [...res.content] : [...prevItems, ...res.content]
        );
        setHasMore(res.content.length > 0);
      })
      .catch((err) => {
        setError(err.message);
      });
  };

  // to fetch initial data on component mount
  useEffect(() => {
    fetchInitialData();
  }, []);

  // to fetch products when activeTab or page changes
  useEffect(() => {
    fetchProducts();
  }, [activeTab, page]);

  const setActiveTabHandler = (tabName) => {
    setActiveTab(tabName);
    setPage(0);
    setItems([]);
    setHasMore(true);
  };

  const fetchNextPage = () => {
    setPage((prevPage) => prevPage + 1);
  };

  const [sentryRef] = useInfiniteScroll({
    loading,
    hasNextPage: hasMore,
    onLoadMore: fetchNextPage,
    rootMargin: '0px 0px 200px 0px',
  });

  if (loading) return <LoadingComponent />;
  if (error) return <ErrorComponent message={ error } />;

  return (
    <>
      <div className="home-container">
        <div className="home-upper">
        <div className="categories body-regular">
          <div className="categories-heading">Categories</div>
            <ul>
              { categories.map((category) => (
                <li key={ category.id }>
                  <Link to={ `${ROUTE_PATHS.SHOP}/${category.id}`}>{ category.name }</Link>
                </li>
              )) }
              <li><Link to={ ROUTE_PATHS.SHOP }>All Categories</Link></li>
            </ul>
          </div>
          <div className="highlighted-product">
            <div className="product-container">
              <div className="product-info body-semibold">
                <span className="product-name">{ product.name }</span>
                <span className="product-price">
                  Start From ${ product.startPrice }
                </span>
                <span className="body-regular">{ product.description }</span>
                <Link to={ `${ROUTE_PATHS.SHOP}/product/${product.id}` }>
                  <Button label="BID NOW" iconSrc={ go } />
                </Link>
              </div>
            </div>
            <Link to={ `${ROUTE_PATHS.SHOP}/product/${product.id}` }>
              <div className="product-image">
                <img
                  src={ product.productImages[0].imageUrl }
                  alt={ product.name }
                />
              </div>
            </Link>
          </div>
        </div>
        <div className="products">
          <Tabs
            tabs={ HOME_TABS }
            activeTab={ activeTab }
            onTabClick={ setActiveTabHandler }
          />
          <ProductGrid
            items={ items }
          />
          { (loading || hasMore) && (
            <div ref={ sentryRef }>
              <LoadingComponent />
            </div>
          ) }
        </div>
      </div>
    </>
  );
};

export default Home;
