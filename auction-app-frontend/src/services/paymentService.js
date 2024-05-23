import { postRequest } from "src/utils/httpUtils";

const addPaymentInfo = (paymentInfo) => {
    return postRequest("/payment/add-payment-info", paymentInfo);
};

export { addPaymentInfo };
