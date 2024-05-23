import { postRequest } from "src/utils/httpUtils";

const addPaymentInfo = (paymentInfo) => {
    return postRequest("/api/v1/payment", paymentInfo);
};

export { addPaymentInfo };
