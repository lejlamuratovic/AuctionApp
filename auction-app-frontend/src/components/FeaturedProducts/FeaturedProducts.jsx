
import { useState, useEffect } from "react";

import { ProductCard, LoadingComponent, ErrorComponent } from "src/components";

import { findFeaturedProducts, findPopularProducts } from "src/services";
import { useUser } from "src/store/UserContext";

import "./style.scss";

const FeaturedProducts = () => {
  const [products, setProducts] = useState([]);
  const [loading, setLoading] = useState();
  const [error, setError] = useState(null);

  const { userId } = useUser();

  const fetchFeaturedProducts = ()  => {
    setLoading(true);

    findFeaturedProducts(userId)
      .then((response) => {
        setProducts(response);
        setLoading(false);
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

    findPopularProducts()
      .then((response) => {
        setProducts(response);
        setLoading(false);
      }).catch((error) => {
        setError(error);
        setLoading(false);
      }).finally(() => {
        setLoading(false);
      });
  };

  useEffect(() => {
    setTimeout(() => {
      setLoading(true);
      
      if (userId) {
        fetchFeaturedProducts();
      } else {
        fetchPopularProducts();
      }
    }, 1000);
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
