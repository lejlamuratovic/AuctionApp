import React, { useState, useEffect } from "react";
import { useNavigate, useLocation } from "react-router-dom";

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
  const [productsError, setProductsError] = useState(null);
  const [productsLoading, setProductsLoading] = useState(true);
  const [categoriesError, setCategoriesError] = useState(null);
  const [categoriesLoading, setCategoriesLoading] = useState(true);
  const [checked, setChecked] = useState({});

  const navigate = useNavigate();
  const query = useQuery();

  const categoryId = query.get("category");
  const searchProduct = query.get("search_product");

  const fetchProducts = () => {
    setProductsLoading(true);
    
    getProductsPaginated(page, 9, categoryId, searchProduct)
      .then((res) => {
        setItems((prevItems) =>
          page === 0 ? [...res.content] : [...prevItems, ...res.content]
        );
        setHasMore(!res.last);
      })
      .catch((err) => {
        setProductsError(err.message);
      }).finally(() => {
        setProductsLoading(false);
      });
  };

  const fetchCategories = () => {
    setCategoriesLoading(true);
    
    getCategoriesWithSubcategories()
      .then((res) => {
        setCategories(res);
        const activeCat = res.find(cat => cat.id === categoryId);

        !!activeCat && setActiveCategory(activeCat.name)
      })
      .catch((err) => {
        setCategoriesError(err.message);
      }).finally(() => {
        setCategoriesLoading(false);
      });
  };

  // fetch products on page load
  useEffect(() => {
    fetchProducts();
  }, [page, categoryId, searchProduct]);

  useEffect(() => {
    fetchCategories();
  }, [categoryId]);

  const fetchNextPage = () => {
    setPage((prevPage) => prevPage + 1);
  };

  const handleCheckboxChange = (id, checked) => {
    setChecked(prev => ({ ...prev, [id]: checked }));
  };

  const handleCategoryChange = (categoryId) => {
    let url = "/shop";
    const queryParams = new URLSearchParams();

    if (searchProduct) {
      queryParams.set("search_product", searchProduct);
    }

    queryParams.set("category", categoryId);
    url += `?${queryParams.toString()}`;
  
    navigate(url);
  };

  if(productsLoading || categoriesLoading) return <LoadingComponent />;
  if(productsError || categoriesError) return <ErrorComponent error={ productsError || categoriesError } />;

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
                      onChange={ (checked) => handleCheckboxChange(subcategory.id, checked) }
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
