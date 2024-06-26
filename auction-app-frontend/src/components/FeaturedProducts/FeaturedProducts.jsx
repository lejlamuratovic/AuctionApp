
import { useState, useEffect } from "react";

import { ProductCard, LoadingComponent, ErrorComponent } from "src/components";

import { findFeaturedProducts, findPopularProducts } from "src/services";
import { useUser } from "src/store/UserContext";
import { FEATURED_PRODUCTS_COUNT } from "src/constants";

import "./style.scss";

const FeaturedProducts = () => {
  const [products, setProducts] = useState([]);
  const [loading, setLoading] = useState();
  const [error, setError] = useState(null);

  const { userId } = useUser();

  const fetchFeaturedProducts = ()  => {
    setLoading(true);

    findFeaturedProducts(userId, FEATURED_PRODUCTS_COUNT)
      .then((response) => {
        setProducts(response);
      })
      .catch((error) => {
        setError(error);
        setLoading(false);
      }).finally(() => {
        setLoading(false);
      });
  }

  const fetchPopularProducts = ()  => {
    setLoading(true);

    findPopularProducts(FEATURED_PRODUCTS_COUNT)
      .then((response) => {
        setProducts(response);
      }).catch((error) => {
        setError(error);
        setLoading(false);
      }).finally(() => {
        setLoading(false);
      });
  };

  useEffect(() => {
      if (userId) {
        fetchFeaturedProducts();
      } else {
        fetchPopularProducts();
      }
  }, [userId]);

  if(error) return <ErrorComponent message={ error.message } />;

  return (
    <div className="featured-products-container">
        <h5> Featured Products </h5>
        <hr />
        <div className="featured-list">
            { products.map((product) => (
                <ProductCard 
                    key={ product.id } 
                    id={ product.id } 
                    productImages={ product.productImages } 
                    name={ product.name } 
                    startPrice={ product.startPrice } 
                />
            )) }
        </div>
    </div>
  )
}

export default FeaturedProducts
