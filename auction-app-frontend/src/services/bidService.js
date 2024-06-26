import { postRequest, getRequest } from "src/utils/httpUtils";

const placeBid = (data) => {
    return postRequest("/bids/place-bid", data);
}

const findBidsByUserId = (userId, page, size) => {
    return getRequest(`/bids/${ userId }?page=${ page }&size=${ size }`);
}

const findBidsByProductId = (productId, page, size) => {
    return getRequest(`/bids/product/${ productId }?page=${ page }&size=${ size }`);
}

export { placeBid, findBidsByUserId, findBidsByProductId };
