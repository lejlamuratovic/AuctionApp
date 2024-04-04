import React, { useState, useEffect } from "react";
import { useParams, useNavigate, useLocation } from "react-router-dom";

import { 
  Button, 
  Checkbox, 
  ProductGrid,
  ErrorComponent,
  LoadingComponent 
} from "src/components";

import {
  getProductsPaginated,
  getCategoriesWithSubcategories
} from "src/services";
import { collapse, expand } from "src/assets/icons";

import "./style.scss";

const useQuery = () => {
  return new URLSearchParams(useLocation().search);
};

const Shop = () => {
  const [activeCategory, setActiveCategory] = useState();
  const [items, setItems] = useState([]);
  const [page, setPage] = useState(0);
  const [categories, setCategories] = useState([]);
  const [hasMore, setHasMore] = useState(true);
  const [error, setError] = useState(null);
  const [loading, setLoading] = useState(true);

  const { id } = useParams(); 

  const navigate = useNavigate();

  const query = useQuery();
  const searchQuery = query.get("search");

  const fetchProducts = () => {
    setLoading(true);
    getProductsPaginated(page, 9, id, searchQuery)
      .then((res) => {
        setItems((prevItems) =>
          page === 0 ? [...res.content] : [...prevItems, ...res.content]
        );
        setHasMore(!res.last);
      })
      .catch((err) => {
        setError(err.message);
      }).finally(() => {
        setLoading(false);
      });
  };

  const fetchCategories = () => {
    setLoading(true);
    getCategoriesWithSubcategories()
      .then((res) => {
        setCategories(res);
        const activeCat = res.find(cat => cat.id === id);

        if (activeCat) setActiveCategory(activeCat.name);
      })
      .catch((err) => {
        setError(err.message);
      }).finally(() => {
        setLoading(false);
      });
  };

  // fetch products on page load
  useEffect(() => {
    fetchProducts();
  }, [page, id, searchQuery]);

  useEffect(() => {
    fetchCategories();
  }, [id]);

  const fetchNextPage = () => {
    setPage((prevPage) => prevPage + 1);
  };

  const handleCategoryChange = (categoryId) => {
    // if category already active then collapse it
    const categoryName = categories.find(cat => cat.id === categoryId)?.name;
    setActiveCategory(prevActive => prevActive === categoryName ? null : categoryName);

    // update the URL with selected category
    navigate(`/shop/${categoryId}`);
  };

  // if (loading) return <LoadingComponent />;
  if (error) return <ErrorComponent message={ error } />;

  return (
    <div className="shop-container">
      <div className="categories">
        <span className="body-regular">PRODUCT CATEGORIES</span>
        <div className="category-list body-regular">
          { categories.map((category) => (
            <div key={ category.id } className="category-item body-regular">
              <button
                className={ `category-name ${activeCategory === category.name ? "active" : ""}` }
                onClick={ () => handleCategoryChange(category.id) }
              >
                { category.name }
                { activeCategory === category.name ? (
                  <img src={ collapse } alt="Collapse" />
                ) : (
                  <img src={ expand } alt="Expand" />
                ) }
              </button>
               { activeCategory === category.name && category.subCategories && (
                <div className="subcategory-list">
                  { category.subCategories.map((subcategory) => (
                    <Checkbox
                      key={ subcategory.id }
                      label={ `${subcategory.name} (${subcategory.productCount})` }
                    />
                  )) }
                </div>
              ) }
            </div>
          ))}
        </div>
      </div>
      <div className="product-list">
        <ProductGrid items={ items } />
        { hasMore && (
        <div className="explore-btn">
          <Button
            label="Explore More"
            variant="filled"
            onClickBehaviour={ fetchNextPage }
          />
        </div>
        ) }
      </div>
    </div>
  );
};

export default Shop;
