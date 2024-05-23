import { useState } from "react";
import { useStripe, useElements, CardNumberElement, CardExpiryElement, CardCvcElement } from '@stripe/react-stripe-js';

import { LoadingComponent, Button, ErrorComponent } from "src/components";
import { BUTTON_VARIANTS, BUTTON_LABELS } from "src/constants";

import "./style.scss";

const CheckoutPaymentForm = ({ clientSecret, onPaymentSuccess }) => {
    const [errorMessage, setErrorMessage] = useState("");
    const [successMessage, setSuccessMessage] = useState("");
    const [cardNumberError, setCardNumberError] = useState("");
    const [cardExpiryError, setCardExpiryError] = useState("");
    const [cardCvcError, setCardCvcError] = useState("");

    const stripe = useStripe();
    const elements = useElements();

    const handleSubmit = async (event) => {
        event.preventDefault();

        if (!stripe || !elements) {
            setErrorMessage("Stripe has not initialized");
            return;
        }

        const cardElement = elements.getElement(CardNumberElement);

        if (!cardElement) {
            setErrorMessage("Card details not available");
            return;
        }

        try {
            const { token, error } = await stripe.createToken(cardElement);
            if (error) {
                setErrorMessage(error.message);
            } else {
                setSuccessMessage("Payment confirmed successfully");
                onPaymentSuccess(token);
            }
        } catch (error) {
            setErrorMessage(error.message || "An error occurred during payment confirmation.");
        }
    };

    const handleCardNumberChange = (event) => {
        setCardNumberError(event.error ? event.error.message : "");
    };

    const handleCardExpiryChange = (event) => {
        setCardExpiryError(event.error ? event.error.message : "");
    };

    const handleCardCvcChange = (event) => {
        setCardCvcError(event.error ? event.error.message : "");
    };

    return (
        <div className="form-container">
            <form onSubmit={handleSubmit}>
                <label className="body-regular">
                    Card Number
                    <div className="input-container">
                        <CardNumberElement 
                            onChange={handleCardNumberChange}
                        />
                    </div>
                    {cardNumberError && <div className="error-message">{cardNumberError}</div>}
                </label>
                <div className="form-row">
                    <label className="body-regular">
                        Expiration Date
                        <div className="input-container">
                            <CardExpiryElement 
                                onChange={handleCardExpiryChange}
                            />
                        </div>
                        { cardExpiryError && <div className="error-message">{ cardExpiryError }</div> }
                    </label>
                    <label className="body-regular">
                        CVC
                        <div className="input-container">
                            <CardCvcElement 
                                onChange={handleCardCvcChange}
                            />
                        </div>
                        { cardCvcError && <div className="error-message">{ cardCvcError }</div> }
                    </label>
                </div>
                <Button
                    variant={ BUTTON_VARIANTS.FILLED }
                    label={ BUTTON_LABELS.PAY }
                    disabled={ !stripe || !elements }
                    type="submit"
                />
                { successMessage && <div>{ successMessage }</div> }
            </form>
        </div>
    );
};

export default CheckoutPaymentForm;
