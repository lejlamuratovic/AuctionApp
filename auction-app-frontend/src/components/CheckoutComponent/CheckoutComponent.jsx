import { useState, useEffect } from "react";
import { Elements } from "@stripe/react-stripe-js";
import { loadStripe } from "@stripe/stripe-js";

import { CheckoutForm, LoadingComponent, ErrorComponent } from "src/components";
import { STRIPE_PUBLIC_KEY } from "src/constants";
import { createPaymentIntent } from "src/services";

const stripePromise = loadStripe(STRIPE_PUBLIC_KEY);

const CheckoutComponent = () => {
    const [clientSecret, setClientSecret] = useState();
    const [loading, setLoading] = useState();
    const [errorMessage, setErrorMessage] = useState();

    const fetchPaymentIntent = () => {
        // dummy charge request
        const chargeRequest = {
            customerEmail: "johndoe123@example.com",
            customerName: "John Doe",
            product: { highestBid: 2000 }
        };

        setLoading(true);

        createPaymentIntent(chargeRequest)
            .then((response) => {
                if (response.clientSecret) {
                    setClientSecret(response.clientSecret);
                    setLoading(false);
                }
            }).catch((error) => {
                setErrorMessage(error.message);
                setLoading(false);
            });
    }
                    
    useEffect(() => {
        fetchPaymentIntent();
    }, []);

    if (loading) return <LoadingComponent />
    if (errorMessage) return <ErrorComponent message={ errorMessage } />

    return (
        <Elements stripe={ stripePromise }  options={{ clientSecret }}>
            <CheckoutForm clientSecret={ clientSecret } />
        </Elements>
    );
};

export default CheckoutComponent;
