import { useState, useEffect } from "react";
import { useNavigate, useLocation } from "react-router-dom";

import {
  Button,
  Checkbox,
  ProductGrid,
  ErrorComponent,
  LoadingComponent,
  SelectField,
  MultiRangeSlider
} from "src/components";

import { getProducts, getCategoriesWithSubcategories } from "src/services";
import { useSuggestion } from "src/store/SuggestionContext";
import { collapse, expand } from "src/assets/icons";
import { SHOP_DEFAULT_PAGE_NUMBER, BUTTON_VARIANTS, SHOP_PAGE_SORTING } from "src/constants";
import { findMinAndMaxPrice } from "src/services/productService";

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
  const [selectedSorting, setSelectedSorting] = useState(SHOP_PAGE_SORTING[0]);
  const [sortingDirection, setSortingDirection] = useState(selectedSorting.direction);
  
  const [initialMinPrice, setInitialMinPrice] = useState(0);
  const [initialMaxPrice, setInitialMaxPrice] = useState(100);
  const [minPrice, setMinPrice] = useState(initialMinPrice);
  const [maxPrice, setMaxPrice] = useState(initialMaxPrice);

  const { setSuggestion } = useSuggestion();
  const navigate = useNavigate();
  const query = useQuery();
  const categoryId = query.get("category");
  const searchProduct = query.get("search_product");

  const fetchPriceRange = () => {
    findMinAndMaxPrice()
      .then((response) => {
        setInitialMinPrice(response.minPrice);
        setInitialMaxPrice(response.maxPrice);
        setMinPrice(response.minPrice);
        setMaxPrice(response.maxPrice);
      })
      .catch((error) => {
        setProductsError(error.message);
      });
  };

  const fetchProducts = () => {
    const subcategoryIds = Object.keys(checked).filter(key => checked[key]);
    setProductsLoading(true);

    setTimeout(() => {
      getProducts(
        page, 
        SHOP_DEFAULT_PAGE_NUMBER, 
        categoryId, 
        searchProduct, 
        selectedSorting.criteria, 
        sortingDirection, 
        subcategoryIds,
        minPrice,
        maxPrice
      )
        .then((response) => {
          const { products, suggestion } = response;
          if (suggestion) {
            setSuggestion(suggestion);
          } else {
            setSuggestion(null);
          }

          setItems((prevItems) =>
            page === 0 ? [...products.content] : [...prevItems, ...products.content]
          );
          setHasMore(!products.last);
        })
        .catch((error) => {
          setProductsError(error.message);
        })
        .finally(() => {
          setProductsLoading(false);
        });
    }, 1000);
  };

  const fetchCategories = () => {
    setCategoriesLoading(true);

    getCategoriesWithSubcategories()
      .then((categories) => {
        setCategories(categories);
        const activeCat = categories.find((cat) => cat.id === categoryId);
        !!activeCat && setActiveCategory(activeCat.name);
      })
      .catch((err) => {
        setCategoriesError(err.message);
      })
      .finally(() => {
        setCategoriesLoading(false);
      });
  };

  useEffect(() => {
    fetchProducts();
  }, [checked, page, categoryId, searchProduct, selectedSorting, sortingDirection, minPrice, maxPrice]);

  useEffect(() => {
    fetchCategories();
  }, [categoryId]);

  useEffect(() => {
    fetchPriceRange();
  }, []);

  const fetchNextPage = () => {
    setPage((prevPage) => prevPage + 1);
  };

  const handleCheckboxChange = (categoryId, subcategoryId, checkedState) => {
    setChecked((prev) => ({
      ...prev,
      [subcategoryId]: checkedState
    }));
  };

  const handleCategoryChange = (categoryId) => {
    let url = "/shop";
    const queryParams = new URLSearchParams();

    const isSameCategory = activeCategory === categories.find((cat) => cat.id === categoryId).name;

    if (isSameCategory) {
      queryParams.delete("category");
      setActiveCategory(null);
    } else {
      queryParams.set("category", categoryId);
      setActiveCategory(categories.find((cat) => cat.id === categoryId).name);
    }

    if (searchProduct) {
      queryParams.set("search_product", searchProduct);
    }

    url += queryParams.toString() ? `?${queryParams.toString()}` : "";
    setChecked({});
    navigate(url);
  };

  const handleSortingChange = (value) => {
    const newSorting = SHOP_PAGE_SORTING.find((sort) => sort.value === value);
    setSelectedSorting(newSorting);
    setSortingDirection(newSorting.direction);
  };

  const handleMinPriceChange = (event) => {
    const value = parseInt(event.target.value.replace(/\D/g, '')) || 0;
    setMinPrice(value);
    
    if (value >= maxPrice) {
      setMaxPrice(value + 1);
    }
  };
  
  const handleMaxPriceChange = (event) => {
    const value = parseInt(event.target.value.replace(/\D/g, '')) || minPrice;
    setMaxPrice(Math.max(value, minPrice + 1));
  };  

  useEffect(() => {
    onRangeChange({min: minPrice, max: maxPrice});
  }, [minPrice, maxPrice]);

  const onBlurFormat = (event) => {
    event.target.value = formatPriceInput(event.target.value.replace(/\D/g, ''));
  };
  
  const onFocusUnformat = (event) => {
    event.target.value = event.target.value.replace(/\D/g, '');
  };
  
  const formatPriceInput = (value) => `$${ value }`;
  
  const onRangeChange = (range) => {
    setMinPrice(range.min);
    setMaxPrice(range.max);
  };

  useEffect(() => {
    onRangeChange({ min: minPrice, max: maxPrice });
  }, [minPrice, maxPrice]);

  if (productsError || categoriesError)
    return <ErrorComponent error={ productsError || categoriesError } />;

  return (
    <>
      <div className="shop-container">
        { categoriesLoading ? (
          <LoadingComponent />
        ) : (
          <div className="filters">
            <div className="categories">
              <span className="body-regular">PRODUCT CATEGORIES</span>
              <div className="category-list body-regular">
                { categories.map((category) => (
                  <div key={ category.id } className="category-item body-regular">
                    <button
                      className={ `category-name ${ activeCategory === category.name ? "active" : "" }` }
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
                            label={ `${ subcategory.name } (${ subcategory.productCount })` }
                            onChange={ (checked) =>
                              handleCheckboxChange(category.id, subcategory.id, checked)
                            }
                          />
                        )) }
                      </div>
                    ) }
                  </div>
                )) }
              </div>
            </div>
            <div className="price-range">
              <span className="body-regular">Price Range</span>
              <div className="price-range-inputs">
              <input
                type="text"
                value={ formatPriceInput(minPrice) }
                onChange={ handleMinPriceChange }
                onBlur={ event => onBlurFormat(event, minPrice) }
                onFocus={ onFocusUnformat}
                placeholder="Min"
              />
              <input
                type="text"
                value={ formatPriceInput(maxPrice) }
                onChange={ handleMaxPriceChange }
                onBlur={ event => onBlurFormat(event, maxPrice) }
                onFocus={ onFocusUnformat }
                placeholder="Max"
              />
              </div>
              <MultiRangeSlider
                  min={ initialMinPrice }
                  max={ initialMaxPrice }
                  minValue={ minPrice }
                  maxValue={ maxPrice }
                  onChange={ onRangeChange }
                /> 
            </div>
          </div>
        ) }
        <div className="product-list">
          <div className="product-options">
            <SelectField
              name="sort"
              options={ SHOP_PAGE_SORTING }
              onSelectChange={ handleSortingChange }
              label="Sort By"
              className="sorting-select-field"
              useForm={ false }
              defaultValue={ selectedSorting.value }
            />
          </div>
          <ProductGrid items={ items } />
          { hasMore && (
            <div className="explore-btn">
              <Button
                label="Explore More"
                variant={ BUTTON_VARIANTS.FILLED }
                onButtonClick={ fetchNextPage }
              />
            </div>
          ) }
        </div>
      </div>
    </>
  );
};

export default Shop;
