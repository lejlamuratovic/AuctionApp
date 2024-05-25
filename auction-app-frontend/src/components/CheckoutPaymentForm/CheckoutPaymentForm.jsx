import { useState } from "react";
import { useStripe, useElements, CardNumberElement, CardExpiryElement, CardCvcElement } from "@stripe/react-stripe-js";
import { Button, ErrorComponent } from "src/components";
import { BUTTON_VARIANTS, BUTTON_LABELS } from "src/constants";

import "./style.scss";

const CheckoutPaymentForm = ({ clientSecret, onPaymentSuccess }) => {
    const [errors, setErrors] = useState({
        stripe: "",
        cardNumber: "",
        cardExpiry: "",
        cardCvc: "",
        cardholderName: ""
    });
    const [successMessage, setSuccessMessage] = useState("");
    const [cardholderName, setCardholderName] = useState("");

    const stripe = useStripe();
    const elements = useElements();

    const validateCardholderName = (name) => {
        if (!name) return "Name on card is required";
        if (name.length < 3) return "Name on card must be at least 3 characters";
        return "";
    };

    const handleSubmit = async (event) => {
        event.preventDefault();

        const nameError = validateCardholderName(cardholderName);

        if (nameError) {
            setErrors(prevErrors => ({ ...prevErrors, cardholderName: nameError }));

            return;
        }

        if (!stripe || !elements) {
            setErrors(prevErrors => ({ ...prevErrors, stripe: "Stripe has not initialized" }));

            return;
        }

        const cardElement = elements.getElement(CardNumberElement);

        if (!cardElement) {
            setErrors(prevErrors => ({ ...prevErrors, stripe: "Card details not available" }));

            return;
        }

        try {
            const { token, error } = await stripe.createToken(cardElement, { name: cardholderName });
            if (error) {
                setErrors(prevErrors => ({ ...prevErrors, stripe: error.message }));
            } else {
                setSuccessMessage("Payment confirmed successfully");

                onPaymentSuccess(token);
            }
        } catch (error) {
            setErrors(prevErrors => ({ ...prevErrors, stripe: error.message || "An error occurred during payment confirmation." }));
        }
    };

    const handleChange = (type) => (event) => {
        setErrors(prevErrors => ({ ...prevErrors, [type]: event.error ? event.error.message : "" }));
    };

    const handleNameChange = (event) => {
        const newName = event.target.value;
        setCardholderName(newName);
        setErrors(prevErrors => ({ ...prevErrors, cardholderName: validateCardholderName(newName) }));
    };

    return (
        <div className="form-container">
            <form onSubmit={ handleSubmit }>
                <label className="body-regular">
                    Name on Card
                    <div className="input-container">
                        <input
                            type="text"
                            value={ cardholderName }
                            onChange={ handleNameChange }
                            placeholder="Name on Card"
                        />
                    </div>
                    { errors.cardholderName && <div className="error-message">{ errors.cardholderName }</div> }
                </label>
                <label className="body-regular">
                    Card Number
                    <div className="input-container">
                        <CardNumberElement
                            onChange={ handleChange("cardNumber") }
                        />
                    </div>
                    { errors.cardNumber && <div className="error-message">{ errors.cardNumber }</div> }
                </label>
                <div className="form-row">
                    <label className="body-regular">
                        Expiration Date
                        <div className="input-container">
                            <CardExpiryElement
                                onChange={ handleChange("cardExpiry") }
                            />
                        </div>
                        { errors.cardExpiry && <div className="error-message">{ errors.cardExpiry }</div> }
                    </label>
                    <label className="body-regular">
                        CVC
                        <div className="input-container">
                            <CardCvcElement
                                onChange={ handleChange("cardCvc") }
                            />
                        </div>
                        { errors.cardCvc && <div className="error-message">{ errors.cardCvc }</div> }
                    </label>
                </div>
                <div className="button-container">
                    <Button
                        variant={ BUTTON_VARIANTS.FILLED }
                        label={ BUTTON_LABELS.PAY }
                        disabled={ !stripe || !elements }
                        type="submit"
                    />
                </div>
                { successMessage && <div className="success-message">{ successMessage }</div> }
            </form>
        </div>
    );
};

export default CheckoutPaymentForm;
