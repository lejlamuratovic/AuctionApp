import { useState, useEffect } from "react";
import { useLocation, useNavigate } from "react-router-dom";

import { ProductDetailsForm, ProductPriceForm, LocationForm } from "src/components";

import { ADD_ITEM_FORMS_MAP, ROUTE_PATHS } from "src/constants";
import { addProduct } from "src/services";
import { useUser } from "src/store/UserContext";

import axios from 'axios';

import "./style.scss";

const AddItem = () => {
  const location = useLocation();
  const navigate = useNavigate();

  const { userId } = useUser();

  const initialForm = location.hash ? location.hash.replace("#", "") : ADD_ITEM_FORMS_MAP.DETAILS;

  const [activeForm, setActiveForm] = useState(initialForm);
  const [formData, setFormData] = useState({
    details: {},
    prices: {},
    shipping: {}
  });

  const handleHashChange = () => {
    const newForm = location.hash.replace("#", "");
    
    if (newForm && newForm !== activeForm) {
      setActiveForm(newForm);
    }
  };

  useEffect(() => {
    navigate(`#${activeForm}`);
  }, []);

  useEffect(() => {
    handleHashChange();

    window.addEventListener("hashchange", handleHashChange, false);

    return () => {
      window.removeEventListener("hashchange", handleHashChange);
    };
  }, [location.hash]);

  const updateFormData = (section, data) => {
    setFormData(prev => ({ ...prev, [section]: data }));
  };

  const handleFinalSubmit = () => {
    // check if all forms are filled and navigate back to a specific one if it isnt
    const isObjectEmpty = (obj) => Object.keys(obj).length === 0;

    if (isObjectEmpty(formData.details)) {
      setActiveForm(ADD_ITEM_FORMS_MAP.DETAILS);
    } else if (isObjectEmpty(formData.prices)) {
      setActiveForm(ADD_ITEM_FORMS_MAP.PRICES);
    } else if (isObjectEmpty(formData.shipping)) {
      setActiveForm(ADD_ITEM_FORMS_MAP.SHIPPING);
    } else {
      handleAddProduct();
    }
  };

  const handleAddProduct = () => {
    // create form data to send to the backend
    const productData = new FormData();

    const startDate = formData.prices.startDate + 'T00:00:00';
    const endDate = formData.prices.endDate + 'T00:00:00';

    const productDetails = {
        name: formData.details.productName,
        categoryId: formData.details.subcategory,
        description: formData.details.description,
        startPrice: formData.prices.startPrice,
        startDate: startDate,
        endDate: endDate,
        userId: userId,
        nameOnCard: formData.shipping.nameOnCard,
        cardNumber: formData.shipping.cardNumber,
        city: formData.shipping.city,
        country: formData.shipping.country,
        zipCode: formData.shipping.zipCode,
        address: formData.shipping.address,
        expirationDate: new Date().toISOString()
    };
  
    // append the product data to the form data object
    productData.append('product', new Blob([JSON.stringify(productDetails)], { type: "application/json" }));
    formData.details.file.forEach(file => productData.append('images', file));
  
    addProduct(productData)
      .then((response) => {
        navigate(`${ ROUTE_PATHS.PRODUCT }/${ response.id }`);
      })
      .catch((error) => {
        console.log(error);
      });
  };

  const renderActiveForm = () => {
    switch (activeForm) {
      case ADD_ITEM_FORMS_MAP.DETAILS:
          return <ProductDetailsForm 
                    formData={ formData.details } 
                    setFormData={ (data) => updateFormData(ADD_ITEM_FORMS_MAP.DETAILS, data) } 
                  />;
      case ADD_ITEM_FORMS_MAP.PRICES:
          return <ProductPriceForm 
                    formData={formData.prices} 
                    setFormData={ (data) => updateFormData(ADD_ITEM_FORMS_MAP.PRICES, data) } 
                  />;
      case ADD_ITEM_FORMS_MAP.SHIPPING:
          return <LocationForm 
                    formData={ formData.shipping } 
                    setFormData={ (data) => updateFormData(ADD_ITEM_FORMS_MAP.SHIPPING, data) } 
                    handleFinalSubmit= {handleFinalSubmit } 
                  />;
      default:
          return null;
  }
  };

  const isActive = (form) => {
    const activeIndex = Object.values(ADD_ITEM_FORMS_MAP).indexOf(activeForm);
    const formIndex = Object.values(ADD_ITEM_FORMS_MAP).indexOf(form);

    return formIndex <= activeIndex;
  };

  return (
    <>
      <div className="form-navigation">
        { Object.keys(ADD_ITEM_FORMS_MAP).map((key, index, array) => (
          <div key={ key } className="circles-container">
            <div
              key={ key }
              className={ `circle ${isActive(ADD_ITEM_FORMS_MAP[key]) ? "active" : "inactive"}` }
            />
            { index < array.length - 1 && <hr /> }
          </div>
       )) }
      </div>
      <div className="add-item-form-container">
        <div>
          { renderActiveForm() }
        </div>
      </div>
    </>
  );
}

export default AddItem;
