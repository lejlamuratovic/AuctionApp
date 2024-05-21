import { Elements } from "@stripe/react-stripe-js"; 
import { loadStripe } from "@stripe/stripe-js"; 

import { CheckoutForm } from "src/components";
import { STRIPE_PUBLIC_KEY } from "src/constants";

const stripePromise = loadStripe(STRIPE_PUBLIC_KEY);

const CheckoutComponent = () => {
    return (
        <Elements stripe={ stripePromise }>
            <CheckoutForm />
        </Elements>
    );
};

export default CheckoutComponent;
