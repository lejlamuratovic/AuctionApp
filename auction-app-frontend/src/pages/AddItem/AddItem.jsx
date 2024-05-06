import { useState, useEffect } from "react";
import { useLocation } from "react-router-dom";

import { ProductDetailsForm, ProductPriceForm, LocationForm } from "src/components";

import { ADD_ITEM_FORMS_MAP } from "src/constants";

import "./style.scss";

const AddItem = () => {
  const location = useLocation();

  const [activeForm, setActiveForm] = useState(location.hash.replace("#", "") || "details");

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

  const renderActiveForm = () => {
    switch (activeForm) {
      case ADD_ITEM_FORMS_MAP.DETAILS:
        return <ProductDetailsForm />;
      case ADD_ITEM_FORMS_MAP.PRICES:
        return <ProductPriceForm />;
      case ADD_ITEM_FORMS_MAP.SHIPPING:
        return <LocationForm />;
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
        { Object.keys(ADD_ITEM_FORMS_MAP).map((key) => (
          <>
            <div
              key={ key }
              className={ `circle ${isActive(ADD_ITEM_FORMS_MAP[key]) ? "active" : "inactive"}` }
            />
            <hr />
          </>
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
