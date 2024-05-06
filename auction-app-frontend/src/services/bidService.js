import { postRequest, getRequest } from "src/utils/httpUtils";

const placeBid = (data) => {
    return postRequest("/bids/place-bid", data);
}

const findBidsByUserId = (userId) => {
    return getRequest(`/bids/${userId}`);
}

export { placeBid, findBidsByUserId };
