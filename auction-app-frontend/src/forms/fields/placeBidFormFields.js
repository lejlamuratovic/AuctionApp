import { rules } from 'src/forms/rules';

export const placeBidsFormFields = (startPrice) => [
    {
        type: "number",
        name: "bidAmount",
        rules: {
            ...rules.required("Bid amount"),
            ...rules.minValue(startPrice, "Bid amount must be at least the starting price")
        }
    }
];
