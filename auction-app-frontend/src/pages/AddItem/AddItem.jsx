import { useState, useEffect } from "react";
import { useLocation } from "react-router-dom";

import { ProductDetailsForm, ProductPriceForm, LocationForm } from "src/components";

import { ADD_ITEM_FORMS_MAP } from "src/constants";

import "./style.scss";

const AddItem = () => {
  const location = useLocation();

  const [activeForm, setActiveForm] = useState(location.hash.replace("#", "") || "details");
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
    console.log(formData);
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
