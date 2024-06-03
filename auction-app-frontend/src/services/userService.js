import { postRequest, getRequest, putRequest } from "src/utils/httpUtils";

const registerUser = (data) => {
    return postRequest("/auth/register", data);
}

const loginUser = (data) => {
    return postRequest("/auth/login", data);
}

const logoutUser = () => {
    return getRequest("/auth/logout");
}

const getUser = (userId) => {
    return getRequest(`/users/${userId}`);
}

const addPaymentInfoToUser = (userId, data) => {
    return postRequest(`/users/${userId}/payment-info`, data);
}

const updateUser = (userId, data) => {
    return putRequest(`/users/${userId}`, data);
}

const updateProfileImage = (userId, data) => {
    return putRequest(`/users/${userId}/profile-image`, data);
}

const deactivateAccount = (userId) => {
    return putRequest(`/users/${userId}/deactivate`);
}

export { registerUser, loginUser, logoutUser, getUser, addPaymentInfoToUser, updateUser, updateProfileImage, deactivateAccount };
