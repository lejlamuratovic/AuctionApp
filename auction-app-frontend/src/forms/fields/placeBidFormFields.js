import { rules } from "src/forms/rules";

export const placeBidsFormFields = (startPrice, highestBidAmount) => {
    const minBid = highestBidAmount > startPrice ? highestBidAmount + 1 : startPrice;

    return [
        {
            type: "number",
            name: "bidAmount",
            rules: {
                ...rules.required("Bid amount"),
                ...rules.minValue(minBid, "Bid amount")
            }
        }
    ];
};

