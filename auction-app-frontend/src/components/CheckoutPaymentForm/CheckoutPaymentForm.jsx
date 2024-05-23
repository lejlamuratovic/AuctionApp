import { useState } from "react";
import { PaymentElement, useStripe, useElements } from "@stripe/react-stripe-js";

import { LoadingComponent, Button, ErrorComponent } from "src/components";
import { BUTTON_VARIANTS, BUTTON_LABELS } from "src/constants";

import "./style.scss";

const CheckoutPaymentForm = ({ clientSecret }) => {
    const [errorMessage, setErrorMessage] = useState();
    const [successMessage, setSuccessMessage] = useState();

    // initialize stripe and elements
    const stripe = useStripe();
    const elements = useElements();

    const handleSubmit = async (event) => {
        event.preventDefault();
    
        if (!stripe || !elements) {
            setErrorMessage("Stripe has not initialized");
            return;
        }
    
        try {
            // confirm payment with stripe elements and client secret from parent component
            const confirmResult = await stripe.confirmPayment({
                elements,
                confirmParams: {
                    // return url
                },
                redirect: "if_required",
            });
    
            if (confirmResult.error) {
                setErrorMessage(confirmResult.error.message);
            } else {
                setSuccessMessage("Payment confirmed successfully");
            }
        } catch (error) {
            setErrorMessage(error.message || "An error occurred during payment confirmation.");
        }
    };
    
    if (!stripe || !elements) return <LoadingComponent />;
    if (successMessage) return <div>{ successMessage }</div>;
    if (errorMessage) return <ErrorComponent message={ errorMessage } />;
    
    return (
        <div className="form-container">
            <form onSubmit={ handleSubmit }>
                <PaymentElement />
                <Button
                    variant={ BUTTON_VARIANTS.FILLED }
                    label={ BUTTON_LABELS.PAY }
                    disabled={ !stripe || !elements }
                    type="submit"
                />
            </form>
        </div>
    );
};

export default CheckoutPaymentForm;
