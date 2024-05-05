import { useState, useEffect } from "react";
import { useLocation } from "react-router-dom";

import { ProductDetailsForm, ProductPriceForm, LocationForm } from "src/components";

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
      case "details":
        return <ProductDetailsForm />;
      case "prices":
        return <ProductPriceForm />;
      case "shipping":
        return <LocationForm />;
      default:
        return null;
    }
  };

  return (
    <div className="add-item-form-container">
      <div>
        { renderActiveForm() }
      </div>
    </div>
  );
}

export default AddItem;
