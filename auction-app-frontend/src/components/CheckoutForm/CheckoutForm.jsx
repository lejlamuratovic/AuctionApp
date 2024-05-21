import { useState, useEffect } from "react";
import {
  Elements,
  PaymentElement,
  useStripe,
  useElements
} from "@stripe/react-stripe-js";
import { loadStripe } from "@stripe/stripe-js";

import { LoadingComponent, Button } from "src/components";

import { createPaymentIntent } from "src/services";
import { STRIPE_PUBLIC_KEY, BUTTON_VARIANTS, BUTTON_LABELS } from "src/constants";

import "./style.scss";  

const stripePromise = loadStripe(STRIPE_PUBLIC_KEY);

const CheckoutForm = () => {
    const [clientSecret, setClientSecret] = useState();
    const [errorMessage, setErrorMessage] = useState();
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const chargeRequest = {
            customerEmail: "johndoe@example.com",
            customerName: "John Doe",
            product: {
                highestBid: 1000
            }
        };

        fetchClientSecret(chargeRequest);
    }, []);

    const fetchClientSecret = (chargeRequest) => {
        createPaymentIntent(chargeRequest)
            .then((response) => {
                if (response.clientSecret) {  
                    setClientSecret(response.clientSecret);
                    setLoading(false); 
                } else {
                    setErrorMessage("Failed to fetch client secret");
                }
            }).catch((error) => {
                setErrorMessage(error.message);
                setLoading(false);
            });
    };

    const stripe = useStripe();
    const elements = useElements();

    const handleSubmit = async (event) => {
        event.preventDefault();

        if (!stripe || !elements) {
            return;
        }

        const confirmResult = await stripe.confirmPayment({
            elements,
            clientSecret,
            confirmParams: {
                // sample return URL
                // return_url: "https://example.com/order/123/complete",
            },
        });

        if (confirmResult.error) {
            setErrorMessage(confirmResult.error.message);
        } else {
            console.log("Payment succeeded!");
        }
    };

    if (loading) return <LoadingComponent />;
    if (errorMessage) return <ErrorComponent message={ errorMessage } />;

    return (
        <div className="form-container">
            <form onSubmit={ handleSubmit }>
                <Elements stripe={ stripePromise } options={{ clientSecret }}>
                    <PaymentElement />
                    <Button 
                        variant={ BUTTON_VARIANTS.FILLED }
                        label={ BUTTON_LABELS.PAY }
                        disabled={ !stripe || !elements }
                        type="submit"
                    />
                </Elements>
            </form>
        </div>
    );
};

export default CheckoutForm;
