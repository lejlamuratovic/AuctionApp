import { jwtDecode } from "jwt-decode";

export const getDecodedToken = (token) => {
    try {
        const decodedToken = jwtDecode(token);
        console.log(decodedToken);

        return decodedToken;
    } catch (error) {
        return null;
    }
}
