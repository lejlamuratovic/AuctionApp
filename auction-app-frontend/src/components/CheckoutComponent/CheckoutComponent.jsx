import { useState, useEffect } from "react";
import { Elements } from "@stripe/react-stripe-js";
import { loadStripe } from "@stripe/stripe-js";

import { CheckoutAddressForm, CheckoutPaymentForm, LoadingComponent, ErrorComponent } from "src/components";
import { STRIPE_PUBLIC_KEY, CHECKOUT_STEPS } from "src/constants";
import { createPaymentIntent } from "src/services";
import { addPaymentInfo } from "src/services/paymentService";

import "./style.scss";

const stripePromise = loadStripe(STRIPE_PUBLIC_KEY);

const CheckoutComponent = () => {
    const [step, setStep] = useState(CHECKOUT_STEPS.ADDRESS);
    const [clientSecret, setClientSecret] = useState();
    const [loading, setLoading] = useState();
    const [errorMessage, setErrorMessage] = useState();
    const [addressInformation, setAddressInformation] = useState({});

    const onAddressFormSubmit = (addressData) => {
        setAddressInformation(addressData);

        setStep(CHECKOUT_STEPS.PAYMENT);
    };

    const onPaymentSuccess = (token) => {
        const paymentInfo = {
            ...addressInformation,
            stripeToken: token.id
        };

        addPaymentInfo(paymentInfo);
        console.log("Payment info: ", paymentInfo);
    };

    const fetchPaymentIntent = async () => {
        setLoading(true);
        try {
            const response = await createPaymentIntent({
                customerEmail: "johndoe123@example.com",
                customerName: "John Doe",
                product: { highestBid: 2000 }
            });
            if (response.clientSecret) {
                setClientSecret(response.clientSecret);
            }
            setLoading(false);
        } catch (error) {
            setErrorMessage(error.message);
            setLoading(false);
        }
    };

    useEffect(() => {
        if (step === CHECKOUT_STEPS.PAYMENT) {
            fetchPaymentIntent();
        }
    }, [step]);

    if (loading) return <LoadingComponent />;
    if (errorMessage) return <ErrorComponent message={ errorMessage } />;

    return (
        <div className="checkout-container">
            { step === CHECKOUT_STEPS.ADDRESS && (
                <CheckoutAddressForm onAddressFormSubmit={ onAddressFormSubmit } />
            ) }
            { step === CHECKOUT_STEPS.PAYMENT && clientSecret && (
                <Elements stripe={ stripePromise } options={{ clientSecret }}>
                    <CheckoutPaymentForm clientSecret={ clientSecret } onPaymentSuccess={ onPaymentSuccess } />
                </Elements>
            ) }
        </div>
    );
};

export default CheckoutComponent;
