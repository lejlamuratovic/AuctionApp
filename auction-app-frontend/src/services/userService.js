import { postRequest, getRequest } from "src/utils/httpUtils";

const registerUser = (data) => {
    return postRequest("/auth/register", data);
}

const loginUser = (data) => {
    return postRequest("/auth/login", data);
}

const logoutUser = () => {
    return getRequest("/auth/logout");
}

const getPaymentInfoByUser = (userId) => {
    return getRequest(`/user/${userId}/payment-info`);
}

const addPaymentInfoToUser = (userId, data) => {
    return postRequest(`/user/${userId}/payment-info`, data);
}

export { registerUser, loginUser, logoutUser, getPaymentInfoByUser, addPaymentInfoToUser };
