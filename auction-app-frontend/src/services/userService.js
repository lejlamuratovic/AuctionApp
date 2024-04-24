import { postRequest } from "src/utils/httpUtils";

const registerUser = (data) => {
    return postRequest("/auth/register", data);
}

const loginUser = (data) => {
    return postRequest("/auth/login", data);
}

export { registerUser, loginUser };
