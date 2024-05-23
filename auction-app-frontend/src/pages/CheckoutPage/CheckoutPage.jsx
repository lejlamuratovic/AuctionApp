import { useLocation } from "react-router-dom";

import { CheckoutComponent } from "src/components";

const CheckoutPage = () => {
  const location = useLocation();
  const product = location.state?.product;

  return (
    <div className="checkout-page">
      <CheckoutComponent product={ product } />
    </div>
  );
};

export default CheckoutPage;
