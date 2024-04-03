import React, { useState, useEffect } from "react";
import { useParams } from "react-router-dom";

import { Button, Checkbox, ProductGrid } from "src/components";

import {
  getProductsPaginated,
  getCategoriesWithSubcategories
} from "src/services";

import "./style.scss";

const Shop = () => {
  const [activeCategory, setActiveCategory] = useState();
  const [items, setItems] = useState([]);
  const [page, setPage] = useState(0);
  const [categories, setCategories] = useState([]);
  const [hasMore, setHasMore] = useState(true);
  const [error, setError] = useState(null);
  const [loading, setLoading] = useState(true);

  const { id } = useParams(); 

  const fetchProducts = () => {
    setLoading(true);
    getProductsPaginated(page, 9)
      .then((res) => {
        setItems((prevItems) =>
          page === 0 ? [...res.content] : [...prevItems, ...res.content]
        );
        setHasMore(res.content.length > 0);
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
  }, [page]);

  useEffect(() => {
    fetchCategories();
  }, [id]);

  const fetchNextPage = () => {
    setPage((prevPage) => prevPage + 1);
  };

  return (
    <div className="shop-container">
      <div className="categories">
        <span className="body-regular">PRODUCT CATEGORIES</span>
        <div className="category-list body-regular">
          { categories.map((category) => (
            <div key={ category.id } className="category-item body-regular">
              <button
                className={ `category-name ${activeCategory === category.name ? "active" : ""}` }
                onClick={ () => setActiveCategory(category.name) }
              >
                { category.name }
              </button>
               {activeCategory === category.name && category.subCategories && (
                <div className="subcategory-list">
                  { category.subCategories.map((subcategory) => (
                    <Checkbox
                      key={ subcategory.id }
                      label={ subcategory.name }
                      onChange={ (isChecked) =>
                        console.log(subcategory.name, isChecked)
                      }
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
